import { ChangeDetectionStrategy, ChangeDetectorRef, Component, HostListener, inject, input, OnInit, ViewChild} from '@angular/core';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { environment } from 'src/app/environments/environment';
import { PostPanelComponent } from 'src/app/features/home/components/post-panel/post-panel.component';
import { TokenService } from '../../services/token.service';
import { NavigationService} from '../../services/navigation.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeaderComponent implements OnInit{
  tokenService = inject(TokenService);
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
    { name: 'Marketplace', icon: 'fa-solid fa-shop',url: 'marketplace' },
    { name: 'Nhóm', icon: 'fa-solid fa-users', url: 'groups' },
    { name: 'Trò chơi', icon: 'fa-solid fa-gamepad', url: 'game' },
  ];
  
  activeItemNavItem: string | null = null;
  activeButton: string | null = null;

  getAvatar(): string{
    if(this.tokenService.getAvatar()){
      return `${environment.apiBaseUrl}/profiles/avatar_image/${this.tokenService.getAvatar()}`;
    }
    return 'assets/avatars/default-avatar.png';
  }
  // showHistory: boolean = false;
  onSelectNavItem(name: string) {
    this.activeItemNavItem = name;
  }
  onSelectButton(name: string) {
    this.activeButton = name;
  }
  // onHandleFocusInput(){
  //   this.showHistory = true;
  // }
  isHistoryVisible = false;

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    const target = event.target as HTMLElement;
    const historyElement = document.getElementById('history');
    const inputElement = document.getElementById('search');

    if ((inputElement && inputElement.contains(target)) || (historyElement && historyElement.contains(target))) {
      this.isHistoryVisible = true;
    } else {
      this.isHistoryVisible = false;
    }
  }
  private routerSubscription!: Subscription;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.activeItemNavItem = 'Trang chủ';
    // Lắng nghe sự kiện điều hướng hoàn tất
    this.routerSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd || event instanceof NavigationStart) {
        const pathAfterHostname = this.router.url;

        // Cập nhật activeItemNavItem dựa trên URL
        switch (pathAfterHostname) {
          case '/home':
            this.activeItemNavItem = 'Trang chủ';
            console.log("ok");
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
        this.cdr.detectChanges(); // dùng detect do popstate(back trình duyệt) không kích hoạt changeDetect nên phải làm thủ công
      }
    });
  }

  ngOnDestroy(): void {
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
  }
}
