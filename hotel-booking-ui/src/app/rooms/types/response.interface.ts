import { PageResponseInterface } from "../../shared/types/page-response/page.response.interface";
import { RoomResponseInterface } from "./room.response.interface";

export interface ResponseRoomInterface {
  status: string;
  message: string;
  pageResponse?: PageResponseInterface<RoomResponseInterface>;
  roomResponse?: RoomResponseInterface;
  roomTypes?: string[];
  timeStamp: string;
}
