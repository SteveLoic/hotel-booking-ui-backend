import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { RegisterRequestInterface } from "../types/register.request.interface";
import { environment } from "../../../environment/environment";
import { Observable } from "rxjs";
import { LoginRequestInterface } from "../types/login.request.interface";
import { ResponseAuthInterface } from "../types/response.auth.interface";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  http = inject(HttpClient);

  registerUser(registerRequest: RegisterRequestInterface): Observable<any> {
    return this.http.post<any>(
      `${environment.apiUrl}/auth/register`,
      registerRequest
    );
  }

  loginUser(
    loginRequest: LoginRequestInterface
  ): Observable<ResponseAuthInterface> {
    return this.http.post<ResponseAuthInterface>(
      `${environment.apiUrl}/auth/login`,
      loginRequest
    );
  }

  getAllUsers(): Observable<ResponseAuthInterface> {
    return this.http.get<ResponseAuthInterface>(
      `${environment.apiUrl}/users/all`
    );
  }

  getUserAccountDetails(): Observable<ResponseAuthInterface> {
    return this.http.get<ResponseAuthInterface>(
      `${environment.apiUrl}/users/user/account`
    );
  }

  getBookingsMadeByCurrentUser(): Observable<ResponseAuthInterface> {
    return this.http.get<ResponseAuthInterface>(
      `${environment.apiUrl}/users/user/all/bookings`
    );
  }

  deleteOwnUserAccount(): Observable<ResponseAuthInterface> {
    return this.http.delete<ResponseAuthInterface>(
      `${environment.apiUrl}/users/user/delete`
    );
  }

  updateUserAccount(
    userId: string,
    updateRequest: RegisterRequestInterface
  ): Observable<ResponseAuthInterface> {
    return this.http.put<ResponseAuthInterface>(
      `${environment.apiUrl}/users/user/${userId}/update`,
      updateRequest
    );
  }
}
