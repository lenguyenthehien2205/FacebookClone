import { ChangeDetectionStrategy, Component, input } from '@angular/core';

@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrl: './tags.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TagsComponent {
  navItems = [
    { name: 'Bạn bè', icon: 'fa-solid fa-user-group', url: '' },
    { name: 'Kỷ niệm', icon: 'fa-regular fa-clock', url: 'memory' },
    { name: 'Đã lưu', icon: 'fa-regular fa-font-awesome',url: 'saved' },
    { name: 'Nhóm', icon: 'fa-solid fa-users', url: 'groups' },
    { name: 'Video', icon: 'fa-regular fa-circle-play', url: 'videos' },
    { name: 'Marketplace', icon: 'fa-solid fa-shop', url: 'marketplace' },
    { name: 'Bảng feed', icon: 'fa-solid fa-radio', url: 'feed' }
  ];

}