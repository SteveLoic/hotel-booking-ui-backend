import { BookingResponseInterface } from "../../shared/types/booking/booking.response.interface";
import { AuthResponseInterface } from "./auth.response.interface";

export interface AuthStateInterface {
  authUser: AuthResponseInterface | null;
  allRegisterUsers: AuthResponseInterface[] | [];
  isAuthenticated: boolean;
  token?: string | null;
  role: string | null;
  isLoading: boolean;
  error: string;
  allBookingByCurrentUser: BookingResponseInterface[];
}
