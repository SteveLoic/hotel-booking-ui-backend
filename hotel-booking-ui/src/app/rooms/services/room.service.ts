import { HttpClient, HttpParams } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { RoomResponseInterface } from "../types/room.response.interface";
import { environment } from "../../../environment/environment";
import { RoomTypeResponseInterface } from "../types/room.type.response.interface";
import { RoomRequestInterface } from "../types/room.request.interface";
import { ResponseRoomInterface } from "../types/response.interface";
import { RoomRequestParamsInterface } from "../types/room.request.params.interface";

@Injectable({
  providedIn: "root",
})
export class RoomService {
  http = inject(HttpClient);

  getAllRooms(): Observable<ResponseRoomInterface> {
    return this.http.get<ResponseRoomInterface>(
      `${environment.apiUrl}/rooms/all`
    );
  }

  getAllRoomsTypes(): Observable<ResponseRoomInterface> {
    return this.http.get<ResponseRoomInterface>(
      `${environment.apiUrl}/rooms/all/types`
    );
  }

  getAllAvailableRooms(
    roomsParams: RoomRequestParamsInterface
  ): Observable<ResponseRoomInterface> {
    const params = new HttpParams()
      .set("checkinDate", roomsParams.checkinDate)
      .set("checkoutDate", roomsParams.checkoutDate)
      .set("roomType", roomsParams.roomType);

    return this.http.get<ResponseRoomInterface>(
      `${environment.apiUrl}/rooms/all/available/rooms`,
      { params: params }
    );
  }

  addNewRoom(request: RoomRequestInterface): Observable<ResponseRoomInterface> {
    return this.http.post<ResponseRoomInterface>(
      `${environment.apiUrl}/rooms/room/add`,
      request
    );
  }

  updateRoom(request: RoomRequestInterface): Observable<ResponseRoomInterface> {
    return this.http.put<ResponseRoomInterface>(
      `${environment.apiUrl}/rooms/room/${request.id}/update`,
      request
    );
  }

  deleteRoom(roomId: number): Observable<ResponseRoomInterface> {
    return this.http.delete<ResponseRoomInterface>(
      `${environment.apiUrl}/rooms/room/${roomId}/delete`
    );
  }
}
