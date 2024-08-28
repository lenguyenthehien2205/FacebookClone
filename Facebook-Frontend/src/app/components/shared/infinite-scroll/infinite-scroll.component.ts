import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-infinite-scroll',
  templateUrl: './infinite-scroll.component.html',
  styleUrl: './infinite-scroll.component.css'
})
export class InfiniteScrollComponent implements OnInit {
  @Input() itemsArray: string[] = []; // Mảng dữ liệu
  @Input() initialLoad: number = 20; // Số lượng giá trị ban đầu
  @Input() itemsPerLoad: number = 5; // Số lượng giá trị mỗi lần tải
  @Output() itemsLoaded = new EventEmitter<string[]>(); // Sự kiện để phát ra dữ liệu đã tải

  items: string[] = []; // Danh sách các mục được hiển thị
  isLoading: boolean = false; // Biến để kiểm soát trạng thái tải thêm dữ liệu
  currentIndex: number = 0; // Chỉ số theo dõi vị trí hiện tại trong mảng `itemsArray`

  ngOnInit(): void {
    this.initializeItems(); // Khởi tạo dữ liệu ban đầu
  }

  initializeItems(): void {
    // Tải số lượng giá trị ban đầu từ mảng `itemsArray`
    this.items = this.itemsArray.slice(0, this.initialLoad);
    this.currentIndex = this.initialLoad; // Cập nhật chỉ số hiện tại sau khi tải dữ liệu ban đầu
  }

  onScroll(event: Event): void {
    const element = event.target as HTMLElement;
    if (element.scrollHeight - element.scrollTop <= element.clientHeight + 1) {
      this.loadMoreData();
    }
  }

  loadMoreData(): void {
    if (this.isLoading || this.currentIndex >= this.itemsArray.length) return; // Kiểm tra nếu đang tải dữ liệu hoặc hết dữ liệu

    this.isLoading = true;
    setTimeout(() => {
      // Lấy thêm các mục từ `itemsArray`
      const newItems: string[] = this.itemsArray.slice(this.currentIndex, this.currentIndex + this.itemsPerLoad);
      this.items = [...this.items, ...newItems]; // Kết hợp các mục mới với danh sách hiện tại
      this.currentIndex += this.itemsPerLoad; // Cập nhật chỉ số hiện tại
      this.isLoading = false; // Đặt lại trạng thái sau khi tải xong
      this.itemsLoaded.emit(this.items); // Phát ra sự kiện với danh sách đã tải
    }, 500); // Giả lập thời gian tải dữ liệu (0.5 giây)
  }
}


// import { Component, OnInit } from '@angular/core';
// import { DataService } from './data.service';

// @Component({
//   selector: 'app-infinite-scroll',
//   templateUrl: './infinite-scroll.component.html',
//   styleUrls: ['./infinite-scroll.component.css']
// })
// export class InfiniteScrollComponent implements OnInit {
//   items: string[] = [];
//   offset: number = 0;
//   limit: number = 20;
//   isLoading: boolean = false;

//   constructor(private dataService: DataService) {}

//   ngOnInit(): void {
//     this.loadItems();
//   }

//   onScroll(event: Event): void {
//     const element = event.target as HTMLElement;
//     if (element.scrollHeight - element.scrollTop <= element.clientHeight + 1) {
//       this.loadMoreItems();
//     }
//   }

//   loadItems(): void {
//     this.isLoading = true;
//     this.dataService.getItems(this.offset, this.limit).subscribe(data => {
//       this.items = [...this.items, ...data];
//       this.offset += this.limit;
//       this.isLoading = false;
//     });
//   }

//   loadMoreItems(): void {
//     if (this.isLoading) return;
//     this.loadItems();
//   }
// }


// Service
// import { HttpClient } from '@angular/common/http';
// import { Injectable } from '@angular/core';
// import { Observable } from 'rxjs';

// @Injectable({
//   providedIn: 'root'
// })
// export class DataService {
//   private apiUrl = 'https://example.com/api/items';

//   constructor(private http: HttpClient) {}

//   getItems(offset: number, limit: number): Observable<string[]> {
//     return this.http.get<string[]>(`${this.apiUrl}?offset=${offset}&limit=${limit}`);
//   }
// }
