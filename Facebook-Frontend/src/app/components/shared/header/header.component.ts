import { Component, computed, ElementRef, HostListener, inject, input, signal, ViewChild } from '@angular/core';

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
}
