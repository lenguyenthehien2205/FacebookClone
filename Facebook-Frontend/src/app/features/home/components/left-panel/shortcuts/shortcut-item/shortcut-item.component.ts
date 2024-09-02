import { Component, input } from '@angular/core';

@Component({
  selector: 'app-shortcut-item',
  templateUrl: './shortcut-item.component.html',
  styleUrl: './shortcut-item.component.css'
})
export class ShortcutItemComponent {
  url = input.required<string>();
  image = input.required<string>();
  name = input.required<string>();
}
