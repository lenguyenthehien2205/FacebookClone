import { Component, HostListener} from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
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
    { name: 'Trang chủ', icon: 'fa-solid fa-house', url: '' },
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

    if (inputElement && inputElement.contains(target)) {
      this.isHistoryVisible = true;
    } else if (historyElement && historyElement.contains(target)) {
      this.isHistoryVisible = true;
    } else {
      this.isHistoryVisible = false;
    }
  }
  constructor(private router: Router) {}

  ngOnInit(): void {
    // Lấy phần đường dẫn phía sau hostname (ví dụ: /path/to/page)
    const pathAfterHostname = this.router.url;
    if(pathAfterHostname === ''){
      this.activeItemNavItem = 'Trang chủ';
    }
  }
}
