export interface RegisterRequestInterface {
  email: string;
  password: string;
  firstname: string;
  lastName: string;
  phoneNumber: string;
  role?: string;
  isActive?: boolean;
}
