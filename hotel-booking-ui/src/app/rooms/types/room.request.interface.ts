export interface RoomRequestInterface {
  id?: number;
  roomNumber: number;
  roomType: string;
  pricePerNight: number;
  capacity: number;
  description: string;
  imageUrl: string;
}
