import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { Media } from 'src/app/core/models/media.model';

@Component({
  selector: 'app-post-content',
  templateUrl: './post-content.component.html',
  styleUrl: './post-content.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PostContentComponent {
  content = input.required<string>();
  media = input.required<Media[]>(); 
}
