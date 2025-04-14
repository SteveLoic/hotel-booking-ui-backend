import {
  patchState,
  signalStore,
  withHooks,
  withMethods,
  withState,
} from "@ngrx/signals";
import { AuthStateInterface } from "../types/auth.state.interface";
import { inject } from "@angular/core";
import { AuthService } from "../services/auth.service";
import { rxMethod } from "@ngrx/signals/rxjs-interop";
import { RegisterRequestInterface } from "../types/register.request.interface";
import { finalize, pipe, switchMap, tap } from "rxjs";
import { tapResponse } from "@ngrx/operators";
import { Router } from "@angular/router";
import { LoginRequestInterface } from "../types/login.request.interface";
import { EncryptAndDecryptTokenService } from "../../shared/services/encrypt-and-decrypt-token.service";
import { withDevtools } from "@angular-architects/ngrx-toolkit";

const initialState: AuthStateInterface = {
  authUser: null,
  isAuthenticated: false,
  role: null,
  isLoading: false,
  error: "",
  token: undefined,
  allRegisterUsers: [],
  allBookingByCurrentUser: [],
};

export const AuthStore = signalStore(
  { providedIn: "root", protectedState: false },
  withState(initialState),
  withMethods(
    (
      store,
      authService = inject(AuthService),
      router = inject(Router),
      tokenService = inject(EncryptAndDecryptTokenService)
    ) => ({
      registerUser: rxMethod<RegisterRequestInterface>(
        pipe(
          tap(() => patchState(store, { isLoading: true })),
          switchMap((registerrequest) =>
            authService.registerUser(registerrequest).pipe(
              tapResponse({
                next: () => router.navigate(["login"]),
                error: (error) => {
                  patchState(store, {
                    error:
                      (error as any)?.error?.message ||
                      "an error occcured while Register User. Please Try again",
                  });
                },
              }),
              finalize(() => patchState(store, { isLoading: false }))
            )
          )
        )
      ),
      loginUser: rxMethod<LoginRequestInterface>(
        pipe(
          tap(() => patchState(store, { isLoading: true })),
          switchMap((loginRequest) =>
            authService.loginUser(loginRequest).pipe(
              tapResponse({
                next: (response) => {
                  patchState(store, {
                    isAuthenticated: true,
                    token: response.token,
                    authUser: response.user,
                    role: response.role,
                  });

                  if (response.token) {
                    tokenService.encryptAndSaveToLocalStorrage(
                      "token",
                      response.token
                    );
                  }
                  router.navigate(["/home"]);
                },
                error: (error) => {
                  patchState(store, {
                    error:
                      (error as any)?.error?.message ||
                      "an error occcured while login. Please Try again",
                  });
                },
              }),
              finalize(() => patchState(store, { isLoading: false }))
            )
          )
        )
      ),
      getAllRegiterUsers: rxMethod<void>(
        pipe(
          tap(() => patchState(store, { isLoading: true })),
          switchMap(() =>
            authService.getAllUsers().pipe(
              tapResponse({
                next: (response) =>
                  patchState(store, {
                    allRegisterUsers: [...(response.users || [])],
                  }),
                error: (error) =>
                  patchState(store, {
                    error:
                      (error as { error: { message: string } }).error.message ||
                      "Failed to fetch all users",
                  }),
              }),
              finalize(() => patchState(store, { isLoading: false }))
            )
          )
        )
      ),
      deleteOwnUserAccount: rxMethod<void>(
        pipe(
          tap(() => patchState(store, { isLoading: true })),
          switchMap(() =>
            authService.deleteOwnUserAccount().pipe(
              tapResponse({
                next: () => {
                  patchState(store, initialState);
                  tokenService.clearAuth();
                  router.navigate(["/login"]);
                },
                error: (error) =>
                  patchState(store, {
                    error:
                      (error as { error: { message: string } }).error.message ||
                      "an error occcured while login. Please Try again",
                  }),
              })
            )
          )
        )
      ),
      getAllBookingsMadeByCurrentUser: rxMethod<void>(
        pipe(
          tap(() => patchState(store, { isLoading: true })),
          switchMap(() =>
            authService.getBookingsMadeByCurrentUser().pipe(
              tapResponse({
                next: (response) =>
                  patchState(store, {
                    allBookingByCurrentUser: [
                      ...(response.bookingResponses || []),
                    ],
                  }),
                error: () =>
                  patchState(store, {
                    error:
                      "error occured while loading booking made by currentUser",
                  }),
              }),
              finalize(() => patchState(store, { isLoading: false }))
            )
          )
        )
      ),
      resetErrorAndSuccessMessage() {
        patchState(store, { error: "" });
      },
      logout() {
        patchState(store, initialState);
        tokenService.clearAuth();
        router.navigate(["/home"]);
      },
    })
  ),
  withHooks({
    onInit: (store) => {},
  }),
  withDevtools("auth")
);
