import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  HostListener,
  inject,
  OnDestroy,
  OnInit,
} from '@angular/core';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { TokenService } from '../../services/token.service';
import { ImageService } from '../../services/image.service';
import { ProfileSearchResponse } from 'src/app/shared/responses/profile/profile-search.response';
import { ProfileService } from '../../services/profile.service';
import { ApiResponse } from 'src/app/shared/responses/api.response';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeaderComponent implements OnInit, OnDestroy {
  keyword = '';
  tokenService = inject(TokenService);
  avatarService = inject(ImageService);
  profileService = inject(ProfileService);
  cdr = inject(ChangeDetectorRef);
  roundedButtons = [
    {
      name: 'Menu',
      icon: 'fa-solid fa-table-cells',
    },
    {
      name: 'Messenger',
      icon: 'fa-brands fa-facebook-messenger',
    },
    {
      name: 'Thông báo',
      icon: 'fa-solid fa-bell',
    },
  ];
  navItems = [
    { name: 'Trang chủ', icon: 'fa-solid fa-house', url: 'home' },
    { name: 'Video', icon: 'fa-solid fa-video', url: 'videos' },
    { name: 'Marketplace', icon: 'fa-solid fa-shop', url: 'marketplace' },
    { name: 'Nhóm', icon: 'fa-solid fa-users', url: 'groups' },
    { name: 'Trò chơi', icon: 'fa-solid fa-gamepad', url: 'game' },
  ];

  searchProfiles: ProfileSearchResponse[] = [];

  activeItemNavItem: string | null = null;
  activeButton: string | null = null;

  getAvatar(): string {
    if(this.tokenService.getAvatar()){
      return this.avatarService.getAvatar(this.tokenService.getAvatar());
    }
    return 'assets/avatars/default-avatar.png';
  }
  onSelectNavItem(name: string) {
    this.activeItemNavItem = name;
  }
  onSelectButton(name: string) {
    this.activeButton = name;
  }
  isHistoryVisible = false;

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    const target = event.target as HTMLElement;
    const historyElement = document.getElementById('history');
    const inputElement = document.getElementById('search');
    if (
      (inputElement && inputElement.contains(target)) ||
      (historyElement && historyElement.contains(target))
    ) {
      this.isHistoryVisible = true;
    } else {
      this.isHistoryVisible = false;
    }
  }
  private routerSubscription!: Subscription;

  constructor(private router: Router) {}

  ngOnInit(): void {
    const pathAfterHostname = this.router.url;
    this.updateActiveNavItem(pathAfterHostname);
    // Lắng nghe sự kiện điều hướng hoàn tất
    this.routerSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd || event instanceof NavigationStart) {
        const updatedPathAfterHostname = this.router.url;
        this.updateActiveNavItem(updatedPathAfterHostname);
      }
    });
  }

  ngOnDestroy(): void {
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
  }
  private updateActiveNavItem(url: string): void {
    switch (url) {
      case '/home':
        this.activeItemNavItem = 'Trang chủ';
        break;
      case '/videos':
        this.activeItemNavItem = 'Video';
        break;
      case '/marketplace':
        this.activeItemNavItem = 'Marketplace';
        break;
      case '/groups':
        this.activeItemNavItem = 'Nhóm';
        break;
      case '/game':
        this.activeItemNavItem = 'Trò chơi';
        break;
      default:
        this.activeItemNavItem = '';
        break;
    }
    this.cdr.detectChanges(); // Đảm bảo giao diện được cập nhật
  }
  onLogOut(){
    this.tokenService.removeToken();
    this.router.navigate(['/login']);
  }
  onClearInput(){
    this.keyword = '';
    this.searchProfiles = [];
  }
  onSearch() {
    this.profileService.searchProfiles(this.keyword).subscribe({
      next: (response: ApiResponse) => {
        this.searchProfiles = response.data as ProfileSearchResponse[];
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Lỗi khi tìm kiếm:', error);
      },
    });
  }
}
