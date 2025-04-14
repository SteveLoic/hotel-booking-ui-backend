import { patchState, signalStore, withMethods, withState } from "@ngrx/signals";
import { BookingStateInterface } from "../types/booking/booking.state.interface";
import { inject } from "@angular/core";
import { BookingService } from "../services/booking.service";
import { rxMethod } from "@ngrx/signals/rxjs-interop";
import { RequestParamsInteface } from "../types/booking/request.params";
import { finalize, pipe, switchMap, tap } from "rxjs";
import { tapResponse } from "@ngrx/operators";
import { BookingRequestInterface } from "../types/booking/booking.request.interface";
import { BookingResponseInterface } from "../types/booking/booking.response.interface";
import { withDevtools } from "@angular-architects/ngrx-toolkit";
import { Router } from "@angular/router";

const initialState: BookingStateInterface = {
  isLoading: false,
  bookings: [],
  error: "",
  currentBookingId: 0,
  currentBookingByReference: null,
  successMessage: "",
};

export const BookingStore = signalStore(
  { providedIn: "root" },
  withState(initialState),
  withMethods(
    (
      store,
      router = inject(Router),
      bookingService = inject(BookingService)
    ) => ({
      loadAllBooking: rxMethod<RequestParamsInteface>(
        pipe(
          tap(() => patchState(store, { isLoading: true })),
          switchMap((requestParams) =>
            bookingService.getAllBookings(requestParams).pipe(
              tapResponse({
                next: (response) =>
                  patchState(store, {
                    bookings: [...(response.pageResponse?.contents || [])],
                  }),
                error: () =>
                  patchState(store, { error: "Failed to load Bookings" }),
              }),
              finalize(() => patchState(store, { isLoading: false }))
            )
          )
        )
      ),
      addBooking: rxMethod<BookingRequestInterface>(
        pipe(
          tap(() => patchState(store, { isLoading: true })),
          switchMap((requestBooking) =>
            bookingService.addBooking(requestBooking).pipe(
              tapResponse({
                next: (response) => {
                  patchState(store, {
                    successMessage:
                      "Your Booking is Successful. An Email of your booking details and the payment link has been sesnt to you",
                    bookings: [
                      response.bookingResponse,
                      ...(store.bookings() || []),
                    ],
                  });
                  router.navigate(["/rooms"]);
                },
                error: (error) => {
                  patchState(store, {
                    error:
                      `${
                        (error as any)?.error?.message
                      }. Please try to login` || "An unknown error occurred",
                  });
                },
              }),
              finalize(() => patchState(store, { isLoading: false }))
            )
          )
        )
      ),
      getBookingByReference: rxMethod<string>(
        pipe(
          tap(() => patchState(store, { isLoading: true })),
          switchMap((bookingsReference) =>
            bookingService.getBookingByReference(bookingsReference).pipe(
              tapResponse({
                next: (response) => {
                  patchState(store, {
                    currentBookingByReference: response.bookingResponse,
                  });
                },
                error: (error) => {
                  patchState(store, {
                    error:
                      `${(error as any)?.error?.message}` ||
                      "An unknown error occurred",
                  });
                  router.navigate(["/admin/manage-bookings"]);
                },
              }),
              finalize(() => patchState(store, { isLoading: false }))
            )
          )
        )
      ),
      resetSuccessAndErrorMessage() {
        patchState(store, { successMessage: "", error: "" });
      },
      updateBooking: rxMethod<BookingResponseInterface>(
        pipe(
          tap(() => patchState(store, { isLoading: true })),
          switchMap((requestBooking) =>
            bookingService.updateBooking(requestBooking).pipe(
              tapResponse({
                next: (response) => {
                  patchState(store, (state) => ({
                    bookings: state.bookings?.map((booking) =>
                      booking.id === response.bookingResponse.id
                        ? response.bookingResponse
                        : booking
                    ),
                    successMessage: "Booking updated successfully.",
                  }));
                  router.navigate(["/admin/manage-bookings"]);
                },
                error: (error) => {
                  patchState(store, {
                    error:
                      `${(error as any)?.error?.message}` ||
                      "An unknown error occurred",
                  });
                },
              }),
              finalize(() => patchState(store, { isLoading: false }))
            )
          )
        )
      ),
    })
  ),
  withDevtools("booking")
);
