import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';
import { ProfileSearchResponse } from 'src/app/shared/responses/profile/profile-search.response';
import { getName } from 'src/app/shared/utils/name-format-utils';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrl: './history.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HistoryComponent {
  dataArray: string[] = Array.from({ length: 100 }, (_, i) => `Item #${i + 1}`); // Mảng dữ liệu gốc
  searchProfileResponse = input<ProfileSearchResponse[]>();
  handleItemsLoaded(items: string[]): void {
    console.log('Items loaded:', items); // Xử lý dữ liệu đã tải
  }
  closeHistory = output<void>();
  clearInput = output<void>();
  onClose(){
    this.closeHistory.emit();
  }
  onClearInput(){
    this.clearInput.emit();
  }
  getName(item: ProfileSearchResponse): string {
    return getName(item.first_name, item.last_name, item.display_format);
  }
}
