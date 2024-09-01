import { Component, input } from '@angular/core';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrl: './post.component.css'
})
export class PostComponent {
  avatar = input.required<string>();
  username = input.required<string>();
  time = input.required<string>();
  media = input.required<string[]>();
  content = input.required<string>();
  privacy = input.required<string>();
  interactionCount = input.required<number>();
  commentCount = input.required<number>();
  shareCount = input.required<number>();
}
