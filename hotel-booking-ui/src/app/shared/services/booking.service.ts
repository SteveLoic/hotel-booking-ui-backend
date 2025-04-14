import { HttpClient, HttpParams } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { RequestParamsInteface } from "../types/booking/request.params";
import { Observable } from "rxjs";
import { BookingPageResponseInterface } from "../types/booking/booking.page.response.interface";
import { environment } from "../../../environment/environment";
import { BookingRequestInterface } from "../types/booking/booking.request.interface";
import { BookingResponseInterface } from "../types/booking/booking.response.interface";

@Injectable({
  providedIn: "root",
})
export class BookingService {
  http = inject(HttpClient);

  getAllBookings(
    request: RequestParamsInteface
  ): Observable<BookingPageResponseInterface> {
    const params = new HttpParams()
      .set("page", request.page)
      .set("size", request.size);
    return this.http.get<BookingPageResponseInterface>(
      `${environment.apiUrl}/bookings/all`,
      { params }
    );
  }

  getBookingByReference(
    bookingsReference: string
  ): Observable<BookingPageResponseInterface> {
    return this.http.get<BookingPageResponseInterface>(
      `${environment.apiUrl}/bookings/booking/${bookingsReference}`
    );
  }

  addBooking(
    requestBooking: BookingRequestInterface
  ): Observable<BookingPageResponseInterface> {
    return this.http.post<BookingPageResponseInterface>(
      `${environment.apiUrl}/bookings/booking/add`,
      requestBooking
    );
  }

  updateBooking(
    requestBooking: BookingResponseInterface
  ): Observable<BookingPageResponseInterface> {
    return this.http.post<BookingPageResponseInterface>(
      `${environment.apiUrl}/bookings/booking/${requestBooking.id}/add`,
      requestBooking
    );
  }

  deleteBooking(
    requestBooking: BookingResponseInterface
  ): Observable<BookingPageResponseInterface> {
    return this.http.delete<BookingPageResponseInterface>(
      `${environment.apiUrl}/bookings/booking/${requestBooking.id}/add`
    );
  }
}
