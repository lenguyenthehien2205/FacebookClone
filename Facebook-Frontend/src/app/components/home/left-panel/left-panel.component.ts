import { Component } from '@angular/core';

@Component({
  selector: 'app-left-panel',
  templateUrl: './left-panel.component.html',
  styleUrl: './left-panel.component.css'
})
export class LeftPanelComponent {
  navItems = [
    { name: 'Bạn bè', icon: 'fa-solid fa-user-group', url: '' },
    { name: 'Kỷ niệm', icon: 'fa-regular fa-clock', url: 'memory' },
    { name: 'Đã lưu', icon: 'fa-regular fa-font-awesome',url: 'saved' },
    { name: 'Nhóm', icon: 'fa-solid fa-users', url: 'groups' },
    { name: 'Video', icon: 'fa-regular fa-circle-play', url: 'videos' },
    { name: 'Marketplace', icon: 'fa-solid fa-shop', url: 'marketplace' },
    { name: 'Bảng feed', icon: 'fa-solid fa-radio', url: 'feed' },
    // { name: 'Xem thêm', icon: 'fa-solid fa-angle-down', url: '' }
  ];
  groupItems = [
    { name: 'Group Lập trình Python', image: 'assets/avatars/thehien.jpg', url: '' },
    { name: 'Cộng đồng Sinh viên SGU', image: 'assets/avatars/thehien.jpg', url: '' },
    { name: 'Dân An Hòa', image: 'assets/avatars/thehien.jpg', url: '' },
    { name: 'Java Viet Nam For Developers', image: 'assets/avatars/thehien.jpg', url: '' },
    { name: 'ĐỀ THI KHOA CNTT SGU', image: 'assets/avatars/thehien.jpg', url: '' },
  ]
}