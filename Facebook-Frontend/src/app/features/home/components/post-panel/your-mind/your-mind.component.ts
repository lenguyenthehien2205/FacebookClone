import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { TokenService } from 'src/app/core/services/token.service';
import { environment } from 'src/app/environments/environment';

@Component({
  selector: 'app-your-mind',
  templateUrl: './your-mind.component.html',
  styleUrl: './your-mind.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class YourMindComponent {
  tokenService = inject(TokenService);
  getAvatar(): string{
    if(this.tokenService.getAvatar()){
      return `${environment.apiBaseUrl}/profiles/avatar_image/${this.tokenService.getAvatar()}`;
    }
    return 'assets/avatars/default-avatar.png';
  }
  pageType = this.tokenService.getPageType();
  getName(): string {
    if(this.pageType === "profile"){
      return this.tokenService.getFirstName();
    }else if(this.pageType === "page"){
      return this.tokenService.getFullName();
    }
    return "";
  }
}
