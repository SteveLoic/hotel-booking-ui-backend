import { CommonModule } from "@angular/common";
import {
  ChangeDetectionStrategy,
  Component,
  inject,
  signal,
} from "@angular/core";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { RoomStore } from "../../../rooms/store/room.store";
import { ActivatedRoute, Router } from "@angular/router";
import { RoomResponseInterface } from "../../../rooms/types/room.response.interface";
import { RoomRequestInterface } from "../../../rooms/types/room.request.interface";

@Component({
  selector: "app-edit-room",
  imports: [CommonModule, ReactiveFormsModule],
  standalone: true,
  templateUrl: "./edit-room.component.html",
  styleUrl: "./edit-room.component.scss",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class EditRoomComponent {
  roomStore = inject(RoomStore);
  route = inject(ActivatedRoute);
  router = inject(Router);
  fb = inject(FormBuilder);

  roomId = signal<string>("");
  room = signal<RoomResponseInterface | null>(null);

  form = this.fb.nonNullable.group({
    roomNumber: ["", Validators.required],
    roomType: ["", Validators.required],
    pricePerNight: ["", Validators.required],
    capacity: ["", Validators.required],
    description: ["", Validators.required],
    imageUrl: ["", Validators.required],
  });

  imagesUrls: string[] = [
    "room-double.jpg",
    "room-single.jpg",
    "room-suite.jpg",
    "room-triple.jpg",
  ];

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
      if (this.room()) {
        this.form.patchValue({
          roomNumber: String(this.room()?.roomNumber || ""),
          roomType: this.room()?.roomType || "",
          pricePerNight: String(this.room()?.pricePerNight || ""),
          capacity: String(this.room()?.capacity || ""),
          description: this.room()?.description || "",
          imageUrl: this.room()?.imageUrl || "",
        });
      }
    }
  }

  hasError(controlName: string, error: string): boolean | undefined {
    const control = this.form.get(controlName);
    return control?.touched && control?.hasError(error);
  }

  onHandleUpdate(): void {
    if (this.form.valid) {
      const formValue = this.form.getRawValue();
      const request: RoomRequestInterface = {
        ...formValue,
        id: this.room()?.id,
        roomNumber: Number(formValue.roomNumber),
        pricePerNight: Number(formValue.pricePerNight),
        capacity: Number(formValue.capacity),
        description: String(formValue.description),
      };
      this.roomStore.updateRoom(request);
    }
    setTimeout(() => {
      this.roomStore.resetMessageAndError("");
    }, 5000);
  }

  onHandleDelete(): void {
    const roomId = this.room()?.id;
    if (roomId !== undefined) {
      this.roomStore.deleteRoom(roomId);
    }
    setTimeout(() => {
      this.roomStore.resetMessageAndError("");
    }, 5000);
  }
}
