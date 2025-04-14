import { CommonModule } from "@angular/common";
import { ChangeDetectionStrategy, Component, inject } from "@angular/core";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { AuthStore } from "../../../auth/store/auth.store";
import { RegisterRequestInterface } from "../../../auth/types/register.request.interface";

@Component({
  selector: "app-admin-register",
  imports: [CommonModule, ReactiveFormsModule],
  standalone: true,
  templateUrl: "./admin-register.component.html",
  styleUrl: "./admin-register.component.scss",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AdminRegisterComponent {
  fb = inject(FormBuilder);
  authStore = inject(AuthStore);

  form = this.fb.nonNullable.group({
    firstName: ["", Validators.required],
    lastName: ["", Validators.required],
    email: ["", [Validators.required, Validators.email]],
    password: ["", Validators.required],
    phoneNumber: ["", Validators.required],
    role: ["", Validators.required],
  });

  onHandleSubmit(): void {
    if (this.form.valid) {
      const registerRequest: RegisterRequestInterface = {
        email: this.form.getRawValue().email,
        password: this.form.getRawValue().password,
        firstname: this.form.getRawValue().firstName,
        lastName: this.form.getRawValue().lastName,
        phoneNumber: this.form.getRawValue().phoneNumber,
      };
      this.authStore.registerUser(registerRequest);
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
