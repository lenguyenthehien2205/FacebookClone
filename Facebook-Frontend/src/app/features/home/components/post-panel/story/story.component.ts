import { ChangeDetectionStrategy, Component, input } from '@angular/core';

@Component({
  selector: 'app-story',
  templateUrl: './story.component.html',
  styleUrl: './story.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class StoryComponent {
  avatar = input.required<string>();
  name = input.required<string>();
  link = input.required<string>();
  isOnline = input.required<boolean>();
}
