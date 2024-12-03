import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { TokenService } from 'src/app/core/services/token.service';
import { environment } from 'src/app/environments/environment';

@Component({
  selector: 'app-new-story',
  templateUrl: './new-story.component.html',
  styleUrl: './new-story.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewStoryComponent {
  tokenService = inject(TokenService);
  getAvatar(): string{
    if(this.tokenService.getAvatar()){
      return `${environment.apiBaseUrl}/profiles/avatar_image/${this.tokenService.getAvatar()}`;
    }
    return 'assets/avatars/default-avatar.png';
  }
}
