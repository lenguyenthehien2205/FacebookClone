import { ChangeDetectionStrategy, Component, inject, input } from '@angular/core';
import { TokenService } from 'src/app/core/services/token.service';
import { getName } from 'src/app/core/utils/name-format-utils';
import { environment } from 'src/app/environments/environment';

@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrl: './tags.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TagsComponent {
  tokenService = inject(TokenService);
  navItems = [
    { name: 'Bạn bè', icon: 'fa-solid fa-user-group', url: '' },
    { name: 'Kỷ niệm', icon: 'fa-regular fa-clock', url: 'memory' },
    { name: 'Đã lưu', icon: 'fa-regular fa-font-awesome',url: 'saved' },
    { name: 'Nhóm', icon: 'fa-solid fa-users', url: 'groups' },
    { name: 'Video', icon: 'fa-regular fa-circle-play', url: 'videos' },
    { name: 'Marketplace', icon: 'fa-solid fa-shop', url: 'marketplace' },
    { name: 'Bảng feed', icon: 'fa-solid fa-radio', url: 'feed' }
  ];
  getAvatar(): string{
    if(this.tokenService.getAvatar()){
      return `${environment.apiBaseUrl}/profiles/avatar_image/${this.tokenService.getAvatar()}`;
    }
    return 'assets/avatars/default-avatar.png';
  }
  avatar = `${environment.apiBaseUrl}/profiles/avatar_image/${this.tokenService.getAvatar()}`;
  getName(): string{
    if(this.tokenService.getPageType() === "profile"){
      return getName(this.tokenService.getFirstName(), this.tokenService.getLastName(), this.tokenService.getDisplayFormat());
    }else if(this.tokenService.getPageType() === "page"){
      return this.tokenService.getFullName();
    }
    return "";
  }
}