import { Route } from "@angular/router";
import { ProfilComponent } from "./components/profil/profil.component";
import { EditProfilComponent } from "./components/edit-profil/edit-profil.component";

export const profil: Route[] = [
  {
    path: "",
    component: ProfilComponent,
  },
];

export const editProfil: Route[] = [
  {
    path: "",
    component: EditProfilComponent,
  },
];
