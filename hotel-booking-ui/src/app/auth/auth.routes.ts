import { Route } from "@angular/router";
import { LoginComponent } from "./components/login/login.component";
import { RegisterComponent } from "./components/register/register.component";

export const loginRoute: Route[] = [
  {
    path: "",
    component: LoginComponent,
  },
];

export const registerRoute: Route[] = [
  {
    path: "",
    component: RegisterComponent,
  },
];
