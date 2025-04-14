import { AuthResponseInterface } from "../../../auth/types/auth.response.interface";
import { RoomResponseInterface } from "../../../rooms/types/room.response.interface";

export interface BookingResponseInterface {
  id: number;
  user: AuthResponseInterface;
  room: RoomResponseInterface;
  paymentStatus: string;
  checkedInDate: string;
  checkedOutDate: string;
  totalPrice: 0;
  bookingReference: string;
  bookingStatus: string;
}
