import { Component, ElementRef, inject, input, OnDestroy, OnInit, signal, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ConversationService } from 'src/app/core/services/conversation.service';
import { ImageService } from 'src/app/core/services/image.service';
import { MessageService } from 'src/app/core/services/message.service';
import { TokenService } from 'src/app/core/services/token.service';
import { WebSocketService } from 'src/app/core/services/websocket.service';
import { MessageDTO } from 'src/app/shared/dtos/message.dto';
import { MessageConversationResponse } from 'src/app/shared/responses/message/message-conversation.response';
import { MessageResponse } from 'src/app/shared/responses/message/message.response';
import { getName } from 'src/app/shared/utils/name-format-utils';

@Component({
  selector: 'app-chat-frame',
  templateUrl: './chat-frame.component.html',
  styleUrl: './chat-frame.component.css'
})
export class ChatFrameComponent implements OnInit, OnDestroy {
  conversationId = signal(0);
  message: string = '';
  senderId: number = 0; // Thay bằng ID người gửi
  messageConversation = new MessageConversationResponse();
  webSocketService = inject(WebSocketService);
  messageService = inject(MessageService);
  tokenService = inject(TokenService);
  imageService = inject(ImageService);
  conversationService = inject(ConversationService);
  route = inject(ActivatedRoute);
  @ViewChild('chatContainer') private chatContainer!: ElementRef;

  ngOnInit(): void {
    this.senderId = this.tokenService.getProfileId();
    // Lắng nghe sự thay đổi của tham số 'conversationId'
    this.route.params.subscribe((params) => {
      const newConversationId = Number(params['conversationId']);
      if (newConversationId !== this.conversationId()) {
        this.conversationId.set(newConversationId); // Cập nhật conversationId
        this.conversationService.setSelectedProfileId(this.conversationId()); // Cập nhật profileId
        this.getMessages(); // Tải lại tin nhắn
      }
    });
    this.webSocketService.connect();
    this.webSocketService.getMessages().subscribe((message) => {
      console.log('Message received: ', message);
      if (message) {
        this.messageConversation.messages.push(message);
      }
    });
  }

  ngOnDestroy(): void {
    this.webSocketService.disconnect();
  }
  ngAfterViewChecked(): void {
    this.scrollToBottom();
  }

  getProfileId(): number {
    return this.tokenService.getProfileId();
  }

  sendMessage(): void {
    if (this.message.trim()) {
      const messageData: MessageDTO = {
        sender_id: this.senderId,
        conversation_id: this.conversationId(),
        content: this.message,
      };
      this.webSocketService.sendMessage(messageData);
      console.log('Message sent: ', messageData);
      this.message = '';
    }
  }

  getMessages(): void {
    this.messageService.getMessages(this.conversationId()).subscribe({
      next: (response) => {
        this.messageConversation = response.data as MessageConversationResponse;
        this.messageConversation.avatar = this.imageService.getAvatar(this.messageConversation.avatar);                
      },
      error: (error) => {
        console.error('Error: ', error);
      }
    });
  }

  private scrollToBottom(): void {
    try {
      this.chatContainer.nativeElement.scrollTop = this.chatContainer.nativeElement.scrollHeight;
    } catch (err) {
      console.error('Scroll to bottom failed', err);
    }
  }

  getName(messageConversation: MessageConversationResponse): string {
    return getName(messageConversation.first_name, messageConversation.last_name, messageConversation.display_format);
  }
}
