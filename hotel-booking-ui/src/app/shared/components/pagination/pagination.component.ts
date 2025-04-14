import { CommonModule } from "@angular/common";
import { Component, input, output } from "@angular/core";

@Component({
  selector: "app-pagination",
  imports: [CommonModule],
  standalone: true,
  templateUrl: "./pagination.component.html",
  styleUrl: "./pagination.component.scss",
})
export class PaginationComponent {
  roomPerPage = input<number>(10);
  totalRooms = input<number>(0);
  currentPage = input<number>(1);
  paginate = output<number>();

  // Method to generate the page numbers
  get pageNumbers(): number[] {
    const pageCount = Math.ceil(this.totalRooms() / this.roomPerPage());
    return Array.from({ length: pageCount }, (_, i) => i + 1);
  }

  // Method to handle page change
  onPageChange(pageNumber: number): void {
    this.paginate.emit(pageNumber); // Emit the page number to parent component
  }
}
