import { CommonModule } from "@angular/common";
import {
  ChangeDetectionStrategy,
  Component,
  inject,
  input,
  OnInit,
  output,
} from "@angular/core";
import { RoomResponseInterface } from "../../../rooms/types/room.response.interface";
import { Router } from "@angular/router";

@Component({
  selector: "app-rooms-resultat",
  imports: [CommonModule],
  templateUrl: "./rooms-resultat.component.html",
  styleUrl: "./rooms-resultat.component.scss",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RoomsResultatComponent {
  router = inject(Router);
  results = input<RoomResponseInterface[] | []>();
  isAdmin = input.required<string | null>();
  navigateToEditRoom = output<string>();
  navigateToToRoomDetails = output<string>();

  onHandleNavigateToEditRoom(roomId: number): void {
    this.router.navigate([`/admin/edit-room/${roomId}`]);
  }

  onHandleNavigateToRoomDetails(roomId: number) {
    this.router.navigate([`/room-details/${roomId}`]);
  }
}
