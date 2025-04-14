export interface PageResponseInterface<T> {
  contents: T[];
  number: number;
  size: number;
  totalElements: number;
  totalPages: number;
}
