import { Component, inject, signal } from '@angular/core';
import { ConversationService } from 'src/app/core/services/conversation.service';
import { ImageService } from 'src/app/core/services/image.service';
import { ApiResponse } from 'src/app/shared/responses/api.response';
import { ConversationResponse } from 'src/app/shared/responses/conversation/conversation.reponse';
import { getName } from 'src/app/shared/utils/name-format-utils';

@Component({
  selector: 'app-chat-list',
  templateUrl: './chat-list.component.html',
  styleUrl: './chat-list.component.css'
})
export class ChatListComponent {
  conversationService = inject(ConversationService);
  imageService = inject(ImageService);
  conversations = signal<ConversationResponse[]>([]);
  filteredConversations = signal<ConversationResponse[]>([]);
  keyword = '';

  ngOnInit(){
    this.conversationService.getConversations().subscribe({
      next: (response: ApiResponse) => {
        this.conversations.set(response.data as ConversationResponse[]);
        this.filteredConversations.set(response.data as ConversationResponse[]);
      }
    });
  }
  getName(conversation: ConversationResponse): string {
    return getName(conversation.first_name, conversation.last_name, conversation.display_format);
  }
  getAvatar(conversation: ConversationResponse): string {
    return this.imageService.getAvatar(conversation.avatar);
  }
  selectConversation(profileId: number){
    this.conversationService.setSelectedProfileId(profileId);
  }

  onSearch() {
    if (!this.keyword) {
      this.filteredConversations.set(this.conversations());
    } else {
      const filtered = this.conversations().filter(conversation => {
        const name = this.getName(conversation).toLowerCase();
        return name.includes(this.keyword.toLowerCase());
      });
      this.filteredConversations.set(filtered); 
    }
  }
}
