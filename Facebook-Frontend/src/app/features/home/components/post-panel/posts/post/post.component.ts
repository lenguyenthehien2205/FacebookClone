import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { Media } from 'src/app/core/models/media.model';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrl: './post.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PostComponent {
  username = input.required<string>();
  media = input.required<Media[]>();
  content = input.required<string>();
  privacy = input.required<string>();
  avatar = input.required<string>();
  time = input.required<string>();
  timeAgo = input.required<string>();
  isOnline = input.required<boolean>();
  postId = input.required<number>();
  totalInteractions = input.required<number>();
  highestInteraction = input.required<string>();
  secondHighestInteraction = input.required<string>();
}
