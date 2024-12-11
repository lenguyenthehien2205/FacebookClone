import {
  ChangeDetectionStrategy,
  Component,
  OnInit,
  signal,
} from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { UserTag } from 'src/app/shared/models/user.model';
import { UserService } from 'src/app/core/services/user.service';
import { getName } from 'src/app/shared/utils/name-format-utils';
import { environment } from 'src/app/environments/environment';
import { ApiResponse } from 'src/app/shared/responses/api.response';
import { LOADING_TIME } from 'src/app/shared/constants/app-config';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrl: './contacts.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ContactsComponent implements OnInit {
  isLoading = signal<boolean>(false);
  delay = signal<boolean>(false);
  users = new BehaviorSubject<UserTag[]>([]);
  constructor(private userService: UserService) {}
  ngOnInit() {
    this.loadContacts();
    console.log('ContactsComponent initialized');
  }

  loadContacts() {
    if (this.delay() || this.users.value.length > 0) return; // Không tải nếu đang đợi hoặc đã có dữ liệu
    this.isLoading.set(true);
    this.delay.set(true);
    this.userService.getContacts().subscribe({
      next: (response: ApiResponse) => {
        const users = response?.data as UserTag[];
        if (users) {
          users.forEach((user: UserTag) => {
            if (user) {
              if (user.avatar === '') {
                user.avatar = `${environment.apiBaseUrl}/users/images/default_image.png`;
              } else {
                user.avatar = `${environment.apiBaseUrl}/profiles/avatar_image/${user.avatar}`;
              }
            }
          });
        }
        setTimeout(() => {
          this.delay.set(false);
          this.isLoading.set(false);
          this.users.next(users);
        }, LOADING_TIME);
      },
      error: (error) => {
        console.error('Lỗi khi tải danh sách người dùng:', error);
      },
    });
  }
  getDisplayName(user: UserTag): string {
    return getName(user.first_name, user.last_name, user.display_format);
  }
}
