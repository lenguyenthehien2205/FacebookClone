import {
  ChangeDetectionStrategy,
  Component,
  OnInit,
  signal,
} from '@angular/core';
import { UserTag } from 'src/app/core/models/user.model';
import { UserService } from 'src/app/core/services/user.service';
import { getName } from 'src/app/core/utils/name-format-utils';
import { environment } from 'src/app/environments/environment';
import { ApiResponse } from 'src/app/features/auth/responses/api.response';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrl: './contacts.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ContactsComponent implements OnInit {
  users = signal<UserTag[]>([]);
  constructor(private userService: UserService) {}
  ngOnInit() {
    this.loadContacts();
  }

  loadContacts() {
    this.userService.getContacts().subscribe({
      next: (response: ApiResponse) => {
        const users = response?.data as UserTag[];
        if(users){
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
      
        this.users.set(users);
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
