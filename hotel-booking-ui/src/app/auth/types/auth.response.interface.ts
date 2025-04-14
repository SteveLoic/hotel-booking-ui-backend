export interface AuthResponseInterface {
  id: string;
  email: string;
  firstname: string;
  lastname: string;
  phoneNumber: string;
  userRole: string;
  token?: string | null;
  isActive: boolean;
}
