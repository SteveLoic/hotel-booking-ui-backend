import { ChangeDetectionStrategy, Component, inject } from "@angular/core";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { RoomStore } from "../../../rooms/store/room.store";
import { RoomRequestInterface } from "../../../rooms/types/room.request.interface";

@Component({
  selector: "app-add-room",
  imports: [CommonModule, ReactiveFormsModule],
  standalone: true,
  templateUrl: "./add-room.component.html",
  styleUrl: "./add-room.component.scss",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AddRoomComponent {
  roomStore = inject(RoomStore);
  fb = inject(FormBuilder);

  imagesUrls: string[] = [
    "room-double.jpg",
    "room-single.jpg",
    "room-suite.jpg",
    "room-triple.jpg",
  ];

  form = this.fb.nonNullable.group({
    roomNumber: ["", Validators.required],
    roomType: ["", Validators.required],
    pricePerNight: ["", Validators.required],
    capacity: ["", Validators.required],
    description: ["", Validators.required],
    imageUrl: ["", Validators.required],
  });

  hasError(controlName: string, error: string): boolean | undefined {
    const control = this.form.get(controlName);
    return control?.touched && control?.hasError(error);
  }

  onHandleAddRoom(): void {
    if (this.form.valid) {
      const formValue = this.form.getRawValue();
      const request: RoomRequestInterface = {
        ...formValue,
        roomNumber: Number(formValue.roomNumber),
        pricePerNight: Number(formValue.pricePerNight),
        capacity: Number(formValue.capacity),
        description: String(formValue.description),
      };
      this.roomStore.addNewRoom(request);
    }

    setTimeout(() => {
      this.roomStore.resetMessageAndError("");
    }, 5000);
  }
}
