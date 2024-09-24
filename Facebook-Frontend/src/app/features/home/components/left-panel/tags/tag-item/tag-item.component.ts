import { ChangeDetectionStrategy, Component, input } from '@angular/core';

@Component({
  selector: 'app-tag-item',
  templateUrl: './tag-item.component.html',
  styleUrl: './tag-item.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TagItemComponent {
  url = input.required<string>();
  name = input.required<string>();
  icon = input.required<string>();

}
