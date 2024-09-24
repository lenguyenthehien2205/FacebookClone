import { ChangeDetectionStrategy, Component, input } from '@angular/core';

@Component({
  selector: 'app-more-action-item',
  templateUrl: './more-action-item.component.html',
  styleUrl: './more-action-item.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MoreActionItemComponent {
  iconClass = input.required<string>();
  actionTitle = input.required<string>();
}
