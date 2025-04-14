import {
  patchState,
  signalStore,
  withComputed,
  withHooks,
  withMethods,
  withState,
} from "@ngrx/signals";
import { RoomStateInterface } from "../types/room.state.interface";
import { inject } from "@angular/core";
import { RxMethod, rxMethod } from "@ngrx/signals/rxjs-interop";
import { tapResponse } from "@ngrx/operators";
import { RoomService } from "../services/room.service";
import { finalize, pipe, switchMap, tap } from "rxjs";
import { RoomRequestInterface } from "../types/room.request.interface";
import { withDevtools } from "@angular-architects/ngrx-toolkit";
import { RoomRequestParamsInterface } from "../types/room.request.params.interface";
import { RoomResponseInterface } from "../types/room.response.interface";
import { Router } from "@angular/router";

const initialState: RoomStateInterface = {
  rooms: null,
  isLoading: false,
  error: "",
  roomTypes: [],
  totalPages: 0,
  currentPage: 0,
  totalElements: 0,
  currentId: 0,
  currentItem: null,
  successMessage: "",
};

export const RoomStore = signalStore(
  { providedIn: "root", protectedState: false },
  withState(initialState),
  withMethods(
    (store, router = inject(Router), roomService = inject(RoomService)) => ({
      loadAllRooms: rxMethod<void>(
        pipe(
          tap(() => patchState(store, { isLoading: true })),
          switchMap(() =>
            roomService.getAllRooms().pipe(
              tapResponse({
                next: (response) =>
                  patchState(store, {
                    rooms: response.pageResponse?.contents,
                    totalPages: response.pageResponse?.totalPages,
                    currentPage: response.pageResponse?.number,
                    totalElements: response.pageResponse?.totalElements,
                  }),
                error: () =>
                  patchState(store, { error: "Failed to load rooms" }),
              }),
              finalize(() => patchState(store, { isLoading: false }))
            )
          )
        )
      ),
      loadAllRoomsType: rxMethod<void>(
        pipe(
          tap(() => patchState(store, { isLoading: true })),
          switchMap(() =>
            roomService.getAllRoomsTypes().pipe(
              tapResponse({
                next: (response) =>
                  patchState(store, { roomTypes: response.roomTypes }),
                error: () =>
                  patchState(store, { error: "Failed to load rooms Types" }),
              }),
              finalize(() => patchState(store, { isLoading: false }))
            )
          )
        )
      ),
      updateRoom: rxMethod<RoomRequestInterface>(
        pipe(
          tap(() => patchState(store, { isLoading: true })),
          switchMap((request) =>
            roomService.updateRoom(request).pipe(
              tapResponse({
                next: (response) => {
                  patchState(store, (state) => ({
                    rooms: state.rooms?.map((room) =>
                      room.id === response.roomResponse?.id
                        ? response.roomResponse
                        : room
                    ),
                  }));
                  router.navigate(["/admin/manage-rooms"]);
                },
                error: (error) => {
                  patchState(store, {
                    error:
                      (error as { error: { message: string } }).error.message ||
                      "error occured while updated",
                  });
                },
              }),
              finalize(() => patchState(store, { isLoading: false }))
            )
          )
        )
      ),
      getAllvailableRooms: rxMethod<RoomRequestParamsInterface>(
        pipe(
          tap(() => patchState(store, { isLoading: true })),
          switchMap((roomRequestParams) =>
            roomService.getAllAvailableRooms(roomRequestParams).pipe(
              tapResponse({
                next: (response) =>
                  patchState(store, {
                    rooms: response.pageResponse?.contents,
                    totalPages: response.pageResponse?.totalPages,
                    currentPage: response.pageResponse?.number,
                    totalElements: response.pageResponse?.totalElements,
                  }),
                error: (error) =>
                  patchState(store, {
                    error:
                      (error as { error: { message: string } }).error.message ||
                      "Failed to fetch rooms",
                  }),
              }),
              finalize(() => patchState(store, { isLoading: false }))
            )
          )
        )
      ),
      addNewRoom: rxMethod<RoomRequestInterface>(
        pipe(
          tap(() => patchState(store, { isLoading: true })),
          switchMap((request) =>
            roomService.addNewRoom(request).pipe(
              tapResponse({
                next: (response) => {
                  patchState(store, {
                    successMessage: "Room Added successfully.",
                    rooms: response?.roomResponse
                      ? [response.roomResponse, ...(store.rooms() || [])]
                      : store.rooms() || [],
                  });
                  router.navigate(["/admin/manage-rooms"]);
                },
                error: (error) => {
                  patchState(store, {
                    error:
                      (error as { error: { message: string } }).error.message ||
                      "unknown error",
                  });
                },
              })
            )
          )
        )
      ),
      deleteRoom: rxMethod<number>(
        pipe(
          tap(() => patchState(store, { isLoading: true })),
          switchMap((roomId) =>
            roomService.deleteRoom(roomId).pipe(
              tapResponse({
                next: (response) => {
                  patchState(store, {
                    successMessage: "Room deleted successfully.",
                    rooms: store.rooms()?.filter((room) => room.id !== roomId),
                  });
                  router.navigate(["/admin/manage-rooms"]);
                },
                error: (error) => {
                  patchState(store, {
                    error:
                      (error as { error: { message: string } }).error.message ||
                      "error occured while deleting room, please try again",
                  });
                },
              }),
              finalize(() => patchState(store, { isLoading: false }))
            )
          )
        )
      ),
      resetMessageAndError(message: string) {
        patchState(store, {
          error: message,
          successMessage: message,
        });
      },
      setCurrentRoom(currentRoomId: number) {
        patchState(store, {
          currentId: currentRoomId,
          currentItem: findCurrentRoom(store.rooms() || [], currentRoomId),
        });
      },
    })
  ),
  withHooks({
    onInit: (store) => {
      store.loadAllRooms();
      store.loadAllRoomsType();
    },
  }),
  withDevtools("rooms")
);

function findCurrentRoom(rooms: RoomResponseInterface[], currentId: number) {
  return rooms.find((room) => room.id === currentId);
}
