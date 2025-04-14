import { PageResponseInterface } from "../page-response/page.response.interface";
import { BookingResponseInterface } from "./booking.response.interface";

export interface BookingPageResponseInterface {
  status: string;
  message: string;
  pageResponse?: PageResponseInterface<BookingResponseInterface>;
  bookingResponse: BookingResponseInterface;
  bookingResponses: BookingResponseInterface[];
  timeStamp: string;
}
