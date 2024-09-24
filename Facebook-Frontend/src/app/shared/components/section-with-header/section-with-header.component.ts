import { ChangeDetectionStrategy, Component, input } from '@angular/core';

@Component({
  selector: 'app-section-with-header',
  templateUrl: './section-with-header.component.html',
  styleUrl: './section-with-header.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SectionWithHeaderComponent {
}
