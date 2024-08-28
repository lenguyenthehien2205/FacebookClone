import { Component } from '@angular/core';

@Component({
  selector: 'app-infinite-scroll',
  templateUrl: './infinite-scroll.component.html',
  styleUrl: './infinite-scroll.component.css'
})
export class InfiniteScrollComponent {
  allItems: string[] = Array.from({ length: 100 }, (_, i) => `Item #${i + 1}`); // Mảng dữ liệu gốc với 100 mục
  items: string[] = []; // Danh sách các mục được hiển thị
  isLoading: boolean = false; // Biến để kiểm soát trạng thái tải thêm dữ liệu
  itemsPerLoad: number = 10; // Số mục tải mỗi lần
  initialLoad: number = 20; // Số mục tải ban đầu
  currentIndex: number = 0; // Chỉ số theo dõi vị trí hiện tại trong mảng `allItems`

  constructor() {
    this.initializeItems(); // Khởi tạo dữ liệu ban đầu
  }

  initializeItems(): void {
    // Tải 20 mục ban đầu từ mảng `allItems`
    this.items = this.allItems.slice(0, this.initialLoad);
    this.currentIndex = this.initialLoad; // Cập nhật chỉ số hiện tại sau khi tải dữ liệu ban đầu
  }

  onScroll(event: Event): void {
    const element = event.target as HTMLElement;
    if (element.scrollHeight - element.scrollTop <= element.clientHeight + 1) {
      this.loadMoreData();
    }
  }

  loadMoreData(): void {
    if (this.isLoading || this.currentIndex >= this.allItems.length) return; // Kiểm tra nếu đang tải dữ liệu hoặc hết dữ liệu

    this.isLoading = true;
    setTimeout(() => {
      // Lấy thêm 5 mục từ `allItems`
      const newItems: string[] = this.allItems.slice(this.currentIndex, this.currentIndex + this.itemsPerLoad);
      this.items = [...this.items, ...newItems]; // Kết hợp các mục mới với danh sách hiện tại
      this.currentIndex += this.itemsPerLoad; // Cập nhật chỉ số hiện tại
      this.isLoading = false; // Đặt lại trạng thái sau khi tải xong
    }, 500); // Giả lập thời gian tải dữ liệu (0.5 giây)
  }
}