import { ChangeDetectionStrategy, Component, inject, input } from '@angular/core';
import { ImageService } from 'src/app/core/services/image.service';
import { TokenService } from 'src/app/core/services/token.service';

@Component({
  selector: 'app-story',
  templateUrl: './story.component.html',
  styleUrl: './story.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StoryComponent {
  avatar = input.required<string>();
  name = input.required<string>();
  link = input.required<string>();
  isOnline = input.required<boolean>();
  imageService = inject(ImageService);
  tokenService = inject(TokenService);
  getMyAvatar(): string {
    if (this.tokenService.getAvatar()) {
      return this.imageService.getUrlAvatarByProfileId(
        this.tokenService.getProfileId()
      );
    }
    return 'assets/avatars/default-avatar.png';
  }
}
