import { Component, inject } from "@angular/core";
import { SearchRoomComponent } from "../../../shared/components/search-room/search-room.component";
import { RoomsResultatComponent } from "../../../shared/components/rooms-resultat/rooms-resultat.component";
import { RoomStore } from "../../../rooms/store/room.store";
import { RoomRequestParamsInterface } from "../../../rooms/types/room.request.params.interface";
import { AuthStore } from "../../../auth/store/auth.store";

@Component({
  selector: "app-home",
  imports: [SearchRoomComponent, RoomsResultatComponent],
  standalone: true,
  templateUrl: "./home.component.html",
  styleUrl: "./home.component.scss",
})
export class HomeComponent {
  roomStore = inject(RoomStore);
  authStore = inject(AuthStore);

  handleSearchRooms(searchParams: RoomRequestParamsInterface) {
    this.roomStore.resetMessageAndError("");
    this.roomStore.getAllvailableRooms(searchParams);
  }
}
