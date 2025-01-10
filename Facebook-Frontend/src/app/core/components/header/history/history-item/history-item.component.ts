import { AfterViewInit, ChangeDetectionStrategy, Component, inject, input, output } from '@angular/core';
import { ImageService } from 'src/app/core/services/image.service';

@Component({
  selector: 'app-history-item',
  templateUrl: './history-item.component.html',
  styleUrl: './history-item.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HistoryItemComponent {
  closeHistory = output<void>();
  clearInput = output<void>();
  fullName = input<string>();
  avatar = input.required<string>();
  pathname = input.required<string>();
  imageService = inject(ImageService);
  getAvatarUrl(avatar: string): string {
    return this.imageService.getAvatar(avatar);
  }
  onClose(){
    this.closeHistory.emit();
    this.clearInput.emit();
  }
}
