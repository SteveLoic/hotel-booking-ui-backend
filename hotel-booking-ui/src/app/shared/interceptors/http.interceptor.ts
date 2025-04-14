import { HttpHeaders, HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { EncryptAndDecryptTokenService } from "../services/encrypt-and-decrypt-token.service";

export const httpInterceptor: HttpInterceptorFn = (req, next) => {
  const tokenService = inject(EncryptAndDecryptTokenService);
  const token = tokenService.getFromLocaStorageAndDecrypt("token");
  if (token) {
    const authRequest = req.clone({
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`,
      }),
    });
    return next(authRequest);
  }
  return next(req);
};
