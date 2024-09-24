import { ChangeDetectionStrategy, Component, HostListener} from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeaderComponent {
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
    // Lắng nghe sự kiện điều hướng hoàn tất
    this.routerSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        const pathAfterHostname = this.router.url;
        console.log(pathAfterHostname);

        // Cập nhật activeItemNavItem dựa trên URL
        switch (pathAfterHostname) {
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
      }
    });
  }

  ngOnDestroy(): void {
    // Hủy đăng ký để tránh rò rỉ bộ nhớ
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
  }
}
