import { CommonModule } from "@angular/common";
import {
  ChangeDetectionStrategy,
  Component,
  inject,
  signal,
} from "@angular/core";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router, ActivatedRoute } from "@angular/router";
import { BookingStore } from "../../../shared/store/booking.store";
import { BookingResponseInterface } from "../../../shared/types/booking/booking.response.interface";

@Component({
  selector: "app-update-booking",
  imports: [CommonModule, ReactiveFormsModule],
  standalone: true,
  templateUrl: "./update-booking.component.html",
  styleUrl: "./update-booking.component.scss",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UpdateBookingComponent {
  bookingStore = inject(BookingStore);
  router = inject(Router);
  activatedRoute = inject(ActivatedRoute);
  fb = inject(FormBuilder);

  bookingCode = signal<string>("");
  foundBooking = signal<BookingResponseInterface | null>(null);

  form = this.fb.nonNullable.group({
    bookingStatus: ["", Validators.required],
    paymentStatus: ["", Validators.required],
  });

  ngOnInit(): void {
    this.bookingCode.set(
      this.activatedRoute.snapshot.paramMap.get("bookingCode") || ""
    );
    this.bookingStore.getBookingByReference(this.bookingCode());
    this.foundBooking.set(this.bookingStore.currentBookingByReference());
  }

  onHandleUpdate(): void {
    if (this.form.valid) {
      const updateBooking: BookingResponseInterface =
        this.bookingStore.currentBookingByReference() as BookingResponseInterface;
      (updateBooking.bookingStatus =
        this.form.get("bookingStatus")?.value || ""),
        (updateBooking.paymentStatus =
          this.form.get("paymentStatus")?.value || ""),
        console.log(updateBooking);
    }
  }
}
