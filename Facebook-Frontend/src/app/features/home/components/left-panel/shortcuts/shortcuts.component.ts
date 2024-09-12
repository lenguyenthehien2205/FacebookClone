import { Component } from '@angular/core';

@Component({
  selector: 'app-shortcuts',
  templateUrl: './shortcuts.component.html',
  styleUrl: './shortcuts.component.css'
})
export class ShortcutsComponent {
  groupItems = [
    { name: 'Group Lập trình Python', image: 'assets/avatars/thehien.jpg', url: '' },
    { name: 'Cộng đồng Sinh viên SGU', image: 'assets/avatars/thehien.jpg', url: '' },
    { name: 'Dân An Hòa', image: 'assets/avatars/thehien.jpg', url: '' },
    { name: 'Java Viet Nam For Developers', image: 'assets/avatars/thehien.jpg', url: '' },
    { name: 'ĐỀ THI KHOA CNTT SGU', image: 'assets/avatars/thehien.jpg', url: '' },
  ]
}
