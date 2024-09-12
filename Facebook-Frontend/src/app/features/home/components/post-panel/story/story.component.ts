import { Component, input } from '@angular/core';

@Component({
  selector: 'app-story',
  templateUrl: './story.component.html',
  styleUrl: './story.component.css'
})
export class StoryComponent {
  avatar = input.required<string>();
  name = input.required<string>();
  routerLink = input.required<string>();
  isOnline = input.required<boolean>();
}
