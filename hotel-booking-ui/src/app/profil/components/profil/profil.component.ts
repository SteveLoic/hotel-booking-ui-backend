import { CommonModule } from "@angular/common";
import { ChangeDetectionStrategy, Component, inject } from "@angular/core";
import { AuthStore } from "../../../auth/store/auth.store";
import { Router } from "@angular/router";

@Component({
  selector: "app-profil",
  imports: [CommonModule],
  templateUrl: "./profil.component.html",
  styleUrl: "./profil.component.scss",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProfilComponent {
  authStore = inject(AuthStore);
  router = inject(Router);

  onHandleEditProfil(): void {
    this.router.navigate(["/edit-profile"]);
  }

  onHandleLogout(): void {
    this.authStore.logout();
  }

  ngOnInit(): void {
    this.authStore.getAllBookingsMadeByCurrentUser();
  }
}
