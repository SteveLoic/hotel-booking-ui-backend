import { BookingResponseInterface } from "./booking.response.interface";

export interface BookingStateInterface {
  isLoading: boolean;
  bookings: BookingResponseInterface[] | [];
  error: string;
  currentBookingId: number;
  successMessage: string;
  currentBookingByReference: BookingResponseInterface | null;
}
