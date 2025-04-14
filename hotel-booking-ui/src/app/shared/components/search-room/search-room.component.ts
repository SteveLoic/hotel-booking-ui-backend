import {
  Component,
  inject,
  output,
  input,
  signal,
  ChangeDetectionStrategy,
} from "@angular/core";
import { CommonModule } from "@angular/common";
import {
  FormBuilder,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from "@angular/forms";
import { RoomRequestParamsInterface } from "../../../rooms/types/room.request.params.interface";

@Component({
  selector: "app-search-room",
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: "./search-room.component.html",
  styleUrl: "./search-room.component.scss",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchRoomComponent {
  fb = inject(FormBuilder);
  roomTypes = input.required<string[]>();
  error = input<string>();
  searchParams = output<RoomRequestParamsInterface>();
  minDate = signal<string>(new Date().toISOString().split("T")[0]);

  form = this.fb.nonNullable.group({
    startDate: [this.minDate(), Validators.required],
    endDate: [this.minDate(), Validators.required],
    roomType: [""],
  });

  onHandleSubmit(): void {
    const searchParamsForms: RoomRequestParamsInterface = {
      checkinDate: this.form.getRawValue().startDate,
      checkoutDate: this.form.getRawValue().endDate,
      roomType: this.form.getRawValue().roomType,
    };
    this.searchParams.emit(searchParamsForms);
  }
}
