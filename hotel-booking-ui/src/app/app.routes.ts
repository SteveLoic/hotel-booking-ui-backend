import { Routes } from "@angular/router";
import { GuardService } from "./shared/services/guard.service";

export const routes: Routes = [
  {
    path: "login",
    loadChildren: () => import("./auth/auth.routes").then((m) => m.loginRoute),
  },
  {
    path: "register",
    loadChildren: () =>
      import("./auth/auth.routes").then((m) => m.registerRoute),
  },
  {
    path: "find-booking",
    loadChildren: () =>
      import("./find-booking/find-booking-routes").then(
        (m) => m.findBookingRoutes
      ),
  },

  {
    path: "home",
    loadChildren: () => import("./home/home.routes").then((m) => m.homeRoutes),
  },
  {
    path: "rooms",
    loadChildren: () =>
      import("./rooms/rooms.route").then((m) => m.roomsRoutes),
  },
  {
    path: "room-details/:roomId",
    canActivate: [GuardService],
    loadChildren: () =>
      import("./rooms/rooms.route").then((m) => m.roomDetails),
  },

  {
    path: "profile",
    canActivate: [GuardService],
    loadChildren: () => import("./profil/profil.routes").then((m) => m.profil),
  },
  {
    path: "edit-profile",
    canActivate: [GuardService],
    loadChildren: () =>
      import("./profil/profil.routes").then((m) => m.editProfil),
  },
  {
    path: "admin",
    canActivate: [GuardService],
    data: { requiresAdmin: true },
    loadChildren: () =>
      import("./admin/admin.routes").then((m) => m.adminRoutes),
  },
  {
    path: "admin/manage-rooms",
    canActivate: [GuardService],
    data: { requiresAdmin: true },
    loadChildren: () =>
      import("./admin/admin.routes").then((m) => m.manageRoomsRoutes),
  },
  {
    path: "admin/add-room",
    canActivate: [GuardService],
    data: { requiresAdmin: true },
    loadChildren: () =>
      import("./admin/admin.routes").then((m) => m.addRoomRoutes),
  },
  {
    path: "admin/edit-room/:roomId",
    canActivate: [GuardService],
    data: { requiresAdmin: true },
    loadChildren: () =>
      import("./admin/admin.routes").then((m) => m.editRoomRoutes),
  },
  {
    path: "admin/manage-bookings",
    canActivate: [GuardService],
    data: { requiresAdmin: true },
    loadChildren: () =>
      import("./admin/admin.routes").then((m) => m.manageBookingRoutes),
  },
  {
    path: "admin/edit-booking/:bookingCode",
    canActivate: [GuardService],
    data: { requiresAdmin: true },
    loadChildren: () =>
      import("./admin/admin.routes").then((m) => m.updateBookingRoutes),
  },
  {
    path: "admin/admin-register",
    canActivate: [GuardService],
    data: { requiresAdmin: true },
    loadChildren: () =>
      import("./admin/admin.routes").then((m) => m.adminRegisterRoutes),
  },
  {
    path: "",
    redirectTo: "home",
    pathMatch: "full",
  },
];
