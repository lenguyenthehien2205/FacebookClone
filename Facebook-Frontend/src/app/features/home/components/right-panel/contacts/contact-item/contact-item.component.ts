import { Component, input } from '@angular/core';

@Component({
  selector: 'app-contact-item',
  templateUrl: './contact-item.component.html',
  styleUrl: './contact-item.component.css'
})
export class ContactItemComponent {
  image = input.required<string>();
  username = input.required<string>();
  isOnline = input.required<boolean>();
}
