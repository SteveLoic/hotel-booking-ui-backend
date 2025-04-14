import { BookingResponseInterface } from "../../shared/types/booking/booking.response.interface";
import { AuthResponseInterface } from "./auth.response.interface";

export interface ResponseAuthInterface {
  status: string;
  message: string;
  role: string;
  token: string;
  user?: AuthResponseInterface;
  users?: AuthResponseInterface[];
  bookingResponses?: BookingResponseInterface[];
  timeStamp: string;
}
