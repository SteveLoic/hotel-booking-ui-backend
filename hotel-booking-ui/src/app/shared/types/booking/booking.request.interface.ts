import { AuthResponseInterface } from "../../../auth/types/auth.response.interface";
import { RoomResponseInterface } from "../../../rooms/types/room.response.interface";

export interface BookingRequestInterface {
  user?: AuthResponseInterface;
  room: RoomResponseInterface;
  checkedInDate: string;
  checkedOutDate: string;
}
