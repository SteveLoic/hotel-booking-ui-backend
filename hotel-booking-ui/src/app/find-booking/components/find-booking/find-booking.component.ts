import { CommonModule } from "@angular/common";
import { ChangeDetectionStrategy, Component, inject } from "@angular/core";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { BookingStore } from "../../../shared/store/booking.store";

@Component({
  selector: "app-find-booking",
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: "./find-booking.component.html",
  styleUrl: "./find-booking.component.scss",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FindBookingComponent {
  fb = inject(FormBuilder);
  bookingstore = inject(BookingStore);

  form = this.fb.nonNullable.group({
    confirmationCode: ["", Validators.required],
  });

  onHandleSearch(): void {
    if (this.form.valid) {
      const bookingReference = this.form.get("confirmationCode")?.value;
      if (bookingReference) {
        this.bookingstore.getBookingByReference(bookingReference);
      }
    }
  }

  hasError(controlName: string, errorName: string) {
    return (
      this.form.get(controlName)?.touched &&
      this.form.get(controlName)?.hasError(errorName)
    );
  }
}
