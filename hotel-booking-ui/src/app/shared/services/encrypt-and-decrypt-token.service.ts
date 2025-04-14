import { Injectable } from "@angular/core";
import CryptoJs from "crypto-js";

@Injectable({
  providedIn: "root",
})
export class EncryptAndDecryptTokenService {
  private static ENCRYPTION_KEY = "Steve-Encry-Key";

  encryptAndSaveToLocalStorrage(key: string, value: string): void {
    const encryptedValue = CryptoJs.AES.encrypt(
      value,
      EncryptAndDecryptTokenService.ENCRYPTION_KEY
    ).toString();
    localStorage.setItem(key, encryptedValue);
  }

  getFromLocaStorageAndDecrypt(key: string): string | null {
    try {
      const encryptedValue = localStorage.getItem(key);
      if (!encryptedValue) return null;
      return CryptoJs.AES.decrypt(
        encryptedValue,
        EncryptAndDecryptTokenService.ENCRYPTION_KEY
      ).toString(CryptoJs.enc.Utf8);
    } catch (error) {
      return null;
    }
  }

  clearAuth(): void {
    localStorage.removeItem("token");
  }
}
