import { ChangeDetectionStrategy, Component, input } from '@angular/core';

@Component({
  selector: 'app-post-content',
  templateUrl: './post-content.component.html',
  styleUrl: './post-content.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PostContentComponent {
  content = input.required<string>();
  media = input.required<string[]>();
}
