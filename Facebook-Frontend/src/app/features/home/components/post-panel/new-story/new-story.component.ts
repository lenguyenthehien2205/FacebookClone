import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { ImageService } from 'src/app/core/services/image.service';
import { TokenService } from 'src/app/core/services/token.service';
import { environment } from 'src/app/environments/environment';

@Component({
  selector: 'app-new-story',
  templateUrl: './new-story.component.html',
  styleUrl: './new-story.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewStoryComponent {
  imageService = inject(ImageService);
  tokenService = inject(TokenService);
  getMyAvatar(): string {
    return this.imageService.getUrlAvatarByProfileId(
      this.tokenService.getProfileId()
    );
  }
}
