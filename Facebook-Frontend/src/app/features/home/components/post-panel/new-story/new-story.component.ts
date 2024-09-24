import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-new-story',
  templateUrl: './new-story.component.html',
  styleUrl: './new-story.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewStoryComponent {

}
