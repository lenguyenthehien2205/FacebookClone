import { ChangeDetectionStrategy, Component, output } from '@angular/core';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrl: './history.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HistoryComponent {
  dataArray: string[] = Array.from({ length: 100 }, (_, i) => `Item #${i + 1}`); // Mảng dữ liệu gốc

  handleItemsLoaded(items: string[]): void {
    console.log('Items loaded:', items); // Xử lý dữ liệu đã tải
  }
  closeHistory = output<void>();
  onClose(){
    this.closeHistory.emit();
  }
}
