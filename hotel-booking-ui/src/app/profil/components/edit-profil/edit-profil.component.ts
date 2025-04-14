import { CommonModule } from "@angular/common";
import { ChangeDetectionStrategy, Component, inject } from "@angular/core";
import { AuthStore } from "../../../auth/store/auth.store";

@Component({
  selector: "app-edit-profil",
  imports: [CommonModule],
  standalone: true,
  templateUrl: "./edit-profil.component.html",
  styleUrl: "./edit-profil.component.scss",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class EditProfilComponent {
  authStore = inject(AuthStore);

  onHandleDeleteProfile(): void {
    if (
      !window.confirm(
        "Are you sure you want to delete your account? If you delete your account, you will lose access to your profile and booking history."
      )
    ) {
      return;
    }
    this.authStore.deleteOwnUserAccount();
  }
}
