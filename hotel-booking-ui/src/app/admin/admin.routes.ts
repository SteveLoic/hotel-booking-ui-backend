import { Route } from "@angular/router";
import { AdminComponent } from "./components/admin/admin.component";
import { ManagerRoomsComponent } from "./components/manager-rooms/manager-rooms.component";
import { AddRoomComponent } from "./components/add-room/add-room.component";
import { EditRoomComponent } from "./components/edit-room/edit-room.component";
import { ManageBookingComponent } from "./components/manage-booking/manage-booking.component";
import { UpdateBookingComponent } from "./components/update-booking/update-booking.component";
import { AdminRegisterComponent } from "./components/admin-register/admin-register.component";

export const adminRoutes: Route[] = [
  {
    path: "",
    component: AdminComponent,
  },
];
export const manageRoomsRoutes: Route[] = [
  {
    path: "",
    component: ManagerRoomsComponent,
  },
];
export const addRoomRoutes: Route[] = [
  {
    path: "",
    component: AddRoomComponent,
  },
];
export const editRoomRoutes: Route[] = [
  {
    path: "",
    component: EditRoomComponent,
  },
];
export const manageBookingRoutes: Route[] = [
  {
    path: "",
    component: ManageBookingComponent,
  },
];
export const updateBookingRoutes: Route[] = [
  {
    path: "",
    component: UpdateBookingComponent,
  },
];
export const adminRegisterRoutes: Route[] = [
  {
    path: "",
    component: AdminRegisterComponent,
  },
];
