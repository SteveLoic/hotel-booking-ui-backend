import { Route } from "@angular/router";
import { RoomsComponent } from "./components/rooms/rooms.component";
import { RoomDetailsComponent } from "./components/room-details/room-details.component";

export const roomsRoutes: Route[] = [
  {
    path: "",
    component: RoomsComponent,
  },
];

export const roomDetails: Route[] = [
  { path: "", component: RoomDetailsComponent },
];
