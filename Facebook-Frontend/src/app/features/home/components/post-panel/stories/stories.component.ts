import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-stories',
  templateUrl: './stories.component.html',
  styleUrl: './stories.component.css'
})
export class StoriesComponent implements AfterViewInit {
  @ViewChild('scrollableContainer') scrollableContainer!: ElementRef<HTMLDivElement>;
  @ViewChild('scrollLeft') scrollLeftButton!: ElementRef<HTMLButtonElement>;
  @ViewChild('scrollRight') scrollRightButton!: ElementRef<HTMLButtonElement>;

  private scrollAmount = 200; // Số lượng pixel để cuộn mỗi lần nhấn

  ngAfterViewInit() {
    this.updateButtonVisibility();

    this.scrollLeftButton.nativeElement.addEventListener('click', () => this.scrollTo('left'));
    this.scrollRightButton.nativeElement.addEventListener('click', () => this.scrollTo('right'));

    window.addEventListener('resize', () => this.updateButtonVisibility());

    // Thêm sự kiện cuộn để cập nhật nút
    this.scrollableContainer.nativeElement.addEventListener('scroll', () => this.onScroll());
  }

  scrollTo(direction: 'left' | 'right') {
    const container = this.scrollableContainer.nativeElement;
    container.scrollBy({
      left: direction === 'left' ? -this.scrollAmount : this.scrollAmount,
      behavior: 'smooth',
    });
    // Gọi onScroll sau khi cuộn để cập nhật nút
    setTimeout(() => this.onScroll(), 300); // Đợi một thời gian ngắn để cuộn hoàn tất
  }

  onScroll(): void {
    const element = this.scrollableContainer.nativeElement;
    const scrollLeft = element.scrollLeft;
    const scrollWidth = element.scrollWidth;
    const clientWidth = element.clientWidth;

    this.scrollLeftButton.nativeElement.style.display = scrollLeft > 0 ? 'block' : 'none';
    this.scrollRightButton.nativeElement.style.display = scrollLeft < (scrollWidth - clientWidth) - 1 ? 'block' : 'none';
  }

  updateButtonVisibility() {
    this.onScroll(); // Cập nhật hiển thị nút khi tải trang hoặc thay đổi kích thước
  }
}
