import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-videos',
  standalone: true,
  imports: [],
  templateUrl: './videos.component.html',
  styleUrl: './videos.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class VideosComponent {

}
