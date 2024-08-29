import { Component, input } from '@angular/core';

@Component({
  selector: 'app-section-with-header',
  templateUrl: './section-with-header.component.html',
  styleUrl: './section-with-header.component.css'
})
export class SectionWithHeaderComponent {
  header = input<string>();
}
