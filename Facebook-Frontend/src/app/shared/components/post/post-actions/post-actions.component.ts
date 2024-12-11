import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-post-actions',
  templateUrl: './post-actions.component.html',
  styleUrl: './post-actions.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PostActionsComponent {
  showIcons: boolean = false;
  interactionIcons = [
    {
      name: 'like',
      title: 'Thích',
    },
    {
      name: 'love',
      title: 'Yêu thích',
    },
    {
      name: 'care',
      title: 'Thương thương',
    },
    {
      name: 'haha',
      title: 'Haha',
    },
    {
      name: 'wow',
      title: 'Wow',
    },
    {
      name: 'sad',
      title: 'Buồn',
    },
    {
      name: 'angry',
      title: 'Phẫn nộ',
    },
  ];
}
