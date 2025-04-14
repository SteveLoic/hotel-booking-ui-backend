import { CommonModule } from "@angular/common";
import { ChangeDetectionStrategy, Component, inject } from "@angular/core";
import { FormBuilder, ReactiveFormsModule, Validators } from "@angular/forms";
import { AuthStore } from "../../store/auth.store";
import { RegisterRequestInterface } from "../../types/register.request.interface";

@Component({
  selector: "app-register",
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: "./register.component.html",
  standalone: true,
  styleUrl: "./register.component.scss",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RegisterComponent {
  fb = inject(FormBuilder);
  authStore = inject(AuthStore);

  form = this.fb.nonNullable.group({
    firstName: ["", Validators.required],
    lastName: ["", Validators.required],
    email: ["", [Validators.required, Validators.email]],
    password: ["", [Validators.required, Validators.min(5)]],
    phoneNumber: ["", [Validators.required, Validators.min(5)]],
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
