import { CommonModule } from "@angular/common";
import {
  ChangeDetectionStrategy,
  Component,
  computed,
  inject,
  OnInit,
  signal,
} from "@angular/core";
import { SearchRoomComponent } from "../../../shared/components/search-room/search-room.component";
import { RoomsResultatComponent } from "../../../shared/components/rooms-resultat/rooms-resultat.component";
import { RoomStore } from "../../store/room.store";
import { PaginationComponent } from "../../../shared/components/pagination/pagination.component";
import { FormBuilder, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { AuthStore } from "../../../auth/store/auth.store";
import { RoomRequestParamsInterface } from "../../types/room.request.params.interface";
import { RoomResponseInterface } from "../../types/room.response.interface";

@Component({
  selector: "app-rooms",
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    SearchRoomComponent,
    RoomsResultatComponent,
  ],
  standalone: true,
  templateUrl: "./rooms.component.html",
  styleUrl: "./rooms.component.scss",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RoomsComponent implements OnInit {
  roomStore = inject(RoomStore);
  authStore = inject(AuthStore);
  fb = inject(FormBuilder);
  filteredRooms = signal<RoomResponseInterface[]>([]);

  form = this.fb.nonNullable.group({
    selectedRoomType: [""],
  });

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

  handleSearchRooms(searchParams: RoomRequestParamsInterface) {
    this.roomStore.resetMessageAndError("");
    this.roomStore.getAllvailableRooms(searchParams);
  }
}
