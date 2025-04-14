import { RoomResponseInterface } from "./room.response.interface";

export interface RoomStateInterface {
  rooms: RoomResponseInterface[] | null;
  roomTypes: string[];
  totalPages: number;
  currentPage: number;
  totalElements: number;
  isLoading: boolean;
  error: string;
  currentId: number;
  successMessage: string;
  currentItem: RoomResponseInterface | null;
}
