import { CommonModule } from "@angular/common";
import {
  ChangeDetectionStrategy,
  Component,
  inject,
  OnInit,
  signal,
} from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { RoomStore } from "../../store/room.store";

import { RoomResponseInterface } from "../../types/room.response.interface";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { BookingRequestInterface } from "../../../shared/types/booking/booking.request.interface";
import { BookingStore } from "../../../shared/store/booking.store";

@Component({
  selector: "app-room-details",
  imports: [CommonModule, ReactiveFormsModule],
  standalone: true,
  templateUrl: "./room-details.component.html",
  styleUrl: "./room-details.component.scss",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RoomDetailsComponent implements OnInit {
  roomStore = inject(RoomStore);
  bookingStore = inject(BookingStore);
  router = inject(Router);
  route = inject(ActivatedRoute);
  fb = inject(FormBuilder);

  roomId = signal<string>("");
  room = signal<RoomResponseInterface | null>(null);
  showDatePicker = signal<boolean>(false);
  showBookingPreview = signal<boolean>(false);
  totalDaysToStay = signal<number>(0);
  totalPrice = signal<number>(0);
  errorUi = signal<string>("");

  minDate = signal<string>(new Date().toISOString().split("T")[0]);

  form = this.fb.nonNullable.group({
    checkInDate: [this.minDate(), Validators.required],
    checkOutDate: [this.minDate(), Validators.required],
  });

  private getRoomById(
    rooms: RoomResponseInterface[],
    currentId: number
  ): RoomResponseInterface | null {
    return rooms.find((room) => room.id === currentId) || null;
  }

  ngOnInit(): void {
    this.roomId.set(this.route.snapshot.paramMap.get("roomId") || "");
    if (this.roomId()) {
      this.room.set(
        this.roomStore.rooms()
          ? this.getRoomById(
              this.roomStore.rooms() as RoomResponseInterface[],
              Number(this.roomId())
            )
          : null
      );
    }
  }

  get getCheckInValue(): string {
    return this.form.get("checkInDate")?.value || "";
  }

  get getCheckoutValue(): string {
    return this.form.get("checkOutDate")?.value || "";
  }

  onToggle(): void {
    this.showDatePicker.update((value) => !value);
  }

  onAcceptBooking(): void {
    if (!this.room()) return;

    const formattedCheckInDate = this.form.get("checkInDate")?.value
      ? new Date(this.form.get("checkInDate")?.value || "").toLocaleDateString(
          "en-CA"
        )
      : "";
    const formattedCheckOutDate = this.form.get("checkOutDate")?.value
      ? new Date(this.form.get("checkOutDate")?.value || "").toLocaleDateString(
          "en-CA"
        )
      : "";
    if (this.room()) {
      const booking: BookingRequestInterface = {
        room: this.room() as RoomResponseInterface,
        checkedInDate: formattedCheckInDate,
        checkedOutDate: formattedCheckOutDate,
      };
      this.bookingStore.addBooking(booking);
    }
    this.errorUi.set("");
    this.totalDaysToStay.set(0);
    this.form.reset();
  }

  onCancelBookingPreview(): void {
    this.showBookingPreview.update((value) => !value);
  }

  onHandleConfirmation(): void {
    if (this.form.valid) {
      this.totalPrice.set(this.calculateTotalPrice());
      this.showBookingPreview.update((value) => !value);
    }
  }

  private calculateTotalPrice(): number {
    if (
      !this.form.get("checkInDate")?.value ||
      !this.form.get("checkOutDate")?.value
    )
      return 0;

    const checkIn = new Date(this.form.get("checkInDate")?.value || "");
    const checkOut = new Date(this.form.get("checkOutDate")?.value || "");

    if (isNaN(checkIn.getTime()) || isNaN(checkOut.getTime())) {
      this.errorUi.set("Invalid Date selected");
      return 0;
    }

    const oneDay = 24 * 60 * 60 * 1000;
    const totalDays = Math.round(
      Math.abs((checkOut.getTime() - checkIn.getTime()) / oneDay)
    );
    this.totalDaysToStay.set(totalDays);

    const room = this.room();
    return room ? room.pricePerNight * totalDays : 0;
  }
}
