import { ViewportScroller } from "@angular/common";
import { Injectable } from "@angular/core";

@Injectable ({ providedIn: 'root' })
export class ScrollService {
  private scrollPositions = new Map<string, [number, number]>();

  constructor(private viewportScroller: ViewportScroller) {}

  // Lưu vị trí cuộn cho route con (sử dụng ActivatedRoute để nhận tên path con)
  saveScrollPosition(routePath: string): void {
    const position = this.viewportScroller.getScrollPosition();
    this.scrollPositions.set(routePath, position);
    console.log(this.scrollPositions);
  }

  // Khôi phục vị trí cuộn cho route con
  restoreScrollPosition(routePath: string): void {
    const position = this.scrollPositions.get(routePath);
    if (position) {
      requestAnimationFrame(() => this.viewportScroller.scrollToPosition(position));
    }
  }

  // Xóa vị trí cuộn khi không cần thiết nữa
  clearScrollPosition(routePath: string): void {
    this.scrollPositions.delete(routePath);
  }
}