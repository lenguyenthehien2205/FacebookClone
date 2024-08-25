import { Component, input, output, signal } from '@angular/core';

@Component({
  selector: 'app-custom-title',
  standalone: true,
  imports: [],
  templateUrl: './custom-title.component.html',
  styleUrl: './custom-title.component.css'
})
export class CustomTitleComponent {
  titleCustom = input.required<string>();
}
