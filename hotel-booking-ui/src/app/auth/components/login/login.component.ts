import { CommonModule } from "@angular/common";
import { ChangeDetectionStrategy, Component, inject } from "@angular/core";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { AuthStore } from "../../store/auth.store";
import { LoginRequestInterface } from "../../types/login.request.interface";

@Component({
  selector: "app-login",
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: "./login.component.html",
  standalone: true,
  styleUrl: "./login.component.scss",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {
  fb = inject(FormBuilder);
  authStore = inject(AuthStore);

  form = this.fb.nonNullable.group({
    email: ["", Validators.email],
    password: ["", [Validators.required, Validators.min(5)]],
  });

  onHandleSubmit(): void {
    if (this.form.valid) {
      const requestLogin: LoginRequestInterface = {
        email: this.form.getRawValue().email,
        password: this.form.getRawValue().password,
      };
      this.authStore.loginUser(requestLogin);
      this.form.reset();
    }
  }

  hasError(controlName: string, errorName: string) {
    return (
      this.form.get(controlName)?.touched &&
      this.form.get(controlName)?.hasError(errorName)
    );
  }
}
