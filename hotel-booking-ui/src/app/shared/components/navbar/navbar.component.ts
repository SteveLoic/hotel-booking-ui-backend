import { CommonModule } from "@angular/common";
import { Component, inject } from "@angular/core";
import { Router, RouterModule } from "@angular/router";
import { EncryptAndDecryptTokenService } from "../../services/encrypt-and-decrypt-token.service";
import { AuthStore } from "../../../auth/store/auth.store";

@Component({
  selector: "app-navbar",
  imports: [CommonModule, RouterModule],
  standalone: true,
  templateUrl: "./navbar.component.html",
  styleUrl: "./navbar.component.scss",
})
export class NavbarComponent {
  router = inject(Router);
  authStore = inject(AuthStore);
  encryptAndDecryptTokenService = inject(EncryptAndDecryptTokenService);

  get isAdmin() {
    return this.authStore.role() === "ADMIN";
  }

  get isCustomer() {
    return this.authStore.role() === "CUSTOMER";
  }

  get isAuthenticated() {
    return this.authStore.isAuthenticated();
  }

  onHandleLogout(): void {
    this.authStore.logout();
  }
}
