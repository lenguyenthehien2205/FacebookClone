import { ChangeDetectionStrategy, Component, input } from '@angular/core';

@Component({
  selector: 'app-post-header',
  templateUrl: './post-header.component.html',
  styleUrl: './post-header.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PostHeaderComponent {
  avatar = input.required<string>();
  username = input.required<string>();
  time = input.required<string>();
  privacyIcons = [
    {
      mode: 'public',
      icon: 'fa-solid fa-earth-americas'
    },
    {
      mode: 'friends',
      icon: 'fa-solid fa-user-friends'
    },
    {
      mode: 'only me',
      icon: 'fa-solid fa-lock'
    }
  ]
  privacy = input.required<string>();
  getPrivacyIcon(privacy: string) {
    return this.privacyIcons.find(icon => icon.mode === privacy)?.icon;
  }
}
