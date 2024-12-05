import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  username: string | null = '';
  route = inject(ActivatedRoute);

  ngOnInit(): void {
    this.username = this.route.snapshot.paramMap.get('username');
  }

  // constructor() {
  //   // Lắng nghe sự kiện đóng trình duyệt
  //   window.addEventListener('beforeunload', this.updateOfflineStatus.bind(this));
  // }

  // updateOfflineStatus() {
  //   // Gửi yêu cầu cập nhật trạng thái người dùng
  //   navigator.sendBeacon('/api/user/offline');
  // }

  // ngOnDestroy() {
  //   window.removeEventListener('beforeunload', this.updateOfflineStatus);
  // }
}
