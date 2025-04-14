import { CommonModule } from "@angular/common";
import { ChangeDetectionStrategy, Component, inject } from "@angular/core";
import { BookingStore } from "../../../shared/store/booking.store";
import { defaultRequestParams } from "../../../shared/types/booking/request.params";
import { FormBuilder, ReactiveFormsModule } from "@angular/forms";
import { Router } from "@angular/router";

@Component({
  selector: "app-manage-booking",
  imports: [CommonModule, ReactiveFormsModule],
  standalone: true,
  templateUrl: "./manage-booking.component.html",
  styleUrl: "./manage-booking.component.scss",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ManageBookingComponent {
  bookingStore = inject(BookingStore);
  fb = inject(FormBuilder);
  router = inject(Router);

  form = this.fb.nonNullable.group({
    searchTerm: [""],
  });

  ngOnInit(): void {
    this.bookingStore.loadAllBooking(defaultRequestParams);
  }

  manageBooking(bookingReference: string): void {
    this.router.navigate([`/admin/edit-booking/${bookingReference}`]);
  }
}
