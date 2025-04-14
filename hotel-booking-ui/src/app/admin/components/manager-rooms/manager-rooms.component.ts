import { CommonModule } from "@angular/common";
import {
  ChangeDetectionStrategy,
  Component,
  inject,
  signal,
} from "@angular/core";
import { FormBuilder, ReactiveFormsModule } from "@angular/forms";
import { RoomStore } from "../../../rooms/store/room.store";
import { RoomsResultatComponent } from "../../../shared/components/rooms-resultat/rooms-resultat.component";
import { AuthStore } from "../../../auth/store/auth.store";
import { Router } from "@angular/router";
import { RoomResponseInterface } from "../../../rooms/types/room.response.interface";

@Component({
  selector: "app-manager-rooms",
  imports: [CommonModule, ReactiveFormsModule, RoomsResultatComponent],
  standalone: true,
  templateUrl: "./manager-rooms.component.html",
  styleUrl: "./manager-rooms.component.scss",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ManagerRoomsComponent {
  roomStore = inject(RoomStore);
  authStore = inject(AuthStore);
  fb = inject(FormBuilder);
  router = inject(Router);

  form = this.fb.nonNullable.group({
    selectedRoomType: [""],
  });

  filteredRooms = signal<RoomResponseInterface[]>([]);

  onHandleNavigateToAddRoom() {
    this.router.navigate(["/admin/add-room"]);
  }

  ngOnInit(): void {
    this.roomStore.loadAllRooms();
    this.form.valueChanges.subscribe((value) => {
      const rooms = this.roomStore.rooms();
      if (rooms) {
        this.filteredRooms.set(
          rooms.filter((room: RoomResponseInterface) => {
            return room.roomType === value.selectedRoomType;
          })
        );
      } else {
        this.filteredRooms.set([]);
      }
    });
  }
}
