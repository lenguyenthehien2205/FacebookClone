import { Component, input } from '@angular/core';

@Component({
  selector: 'app-group-chat-item',
  templateUrl: './group-chat-item.component.html',
  styleUrl: './group-chat-item.component.css'
})
export class GroupChatItemComponent {
  image = input.required<string>();
  groupName = input.required<string>();
  isOnline = input.required<boolean>();
}
