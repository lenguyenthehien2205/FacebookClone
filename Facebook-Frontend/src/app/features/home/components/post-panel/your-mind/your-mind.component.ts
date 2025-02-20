import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { ImageService } from 'src/app/core/services/image.service';
import { TokenService } from 'src/app/core/services/token.service';
import { environment } from 'src/app/environments/environment';

@Component({
  selector: 'app-your-mind',
  templateUrl: './your-mind.component.html',
  styleUrl: './your-mind.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class YourMindComponent {
  isShowPostForm = false;

  tokenService = inject(TokenService);
  imageService = inject(ImageService);
  getMyAvatar(): string {
    if (this.tokenService.getAvatar()) {
      return this.imageService.getUrlAvatarByProfileId(
        this.tokenService.getProfileId()
      );
    }
    return 'assets/avatars/default-avatar.png';
  }
  pageType = this.tokenService.getPageType();
  getName(): string {
    if(this.pageType === "profile"){
      return this.tokenService.getFirstName();
    }else if(this.pageType === "page"){
      return this.tokenService.getFullNamePage();
    }
    return "";
  }
  getPathname(): string {
    return this.tokenService.getPathname();
  }
  onShowPostForm(){
    this.isShowPostForm = true;
  }
  onHidePostForm(){
    this.isShowPostForm = false;
  }
  get postId(): number {
    return this.tokenService.getProfileId();
  }

}
