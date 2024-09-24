import { ChangeDetectionStrategy, Component, input } from '@angular/core';

@Component({
  selector: 'app-post-interactions',
  templateUrl: './post-interactions.component.html',
  styleUrl: './post-interactions.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PostInteractionsComponent {
  interactionCount = input.required<number>();
  commentCount = input.required<number>();
  shareCount = input.required<number>();
}
