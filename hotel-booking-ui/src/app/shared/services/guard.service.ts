import { inject, Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
} from "@angular/router";
import { AuthStore } from "../../auth/store/auth.store";

@Injectable({
  providedIn: "root",
})
export class GuardService implements CanActivate {
  router = inject(Router);
  authStore = inject(AuthStore);
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const requiredAdmin = route.data["requiredAdmin"] || false;

    const isAdmin = this.authStore.role() === "ADMIN";
    const isAuthenticated = this.authStore.isAuthenticated();

    if (requiredAdmin) {
      if (isAdmin) {
        return true;
      } else {
        this.router.navigate(["/login"], {
          queryParams: { returnUrl: state.url },
        });
        return false;
      }
    } else {
      if (isAuthenticated) {
        return true;
      } else {
        this.router.navigate(["/login"], {
          queryParams: { returnUrl: state.url },
        });
        return false;
      }
    }
  }
}
