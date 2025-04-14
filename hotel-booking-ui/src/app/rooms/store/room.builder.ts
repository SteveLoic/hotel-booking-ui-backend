import { RoomResponseInterface } from "../types/room.response.interface";

export function findRoomDependsCurrentRoomId(
  rooms: RoomResponseInterface[],
  currentRoomId: number
): RoomResponseInterface | null {
  console.log(rooms);
  console.log(currentRoomId);
  const res = rooms.find((room) => room.id === currentRoomId);
  console.log(res);
  return res || null;
}
