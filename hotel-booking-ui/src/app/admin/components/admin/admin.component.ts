import { CommonModule } from "@angular/common";
import { ChangeDetectionStrategy, Component, inject } from "@angular/core";
import { AuthStore } from "../../../auth/store/auth.store";
import { Router } from "@angular/router";

@Component({
  selector: "app-admin",
  imports: [CommonModule],
  standalone: true,
  templateUrl: "./admin.component.html",
  styleUrl: "./admin.component.scss",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AdminComponent {
  authStore = inject(AuthStore);
  router = inject(Router);

  navigateToManageRooms(): void {
    this.router.navigate(["/admin/manage-rooms"]);
  }

  navigateToManageBookings(): void {
    this.router.navigate(["/admin/manage-bookings"]);
  }
}
