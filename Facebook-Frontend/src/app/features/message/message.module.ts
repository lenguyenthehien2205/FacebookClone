import { NgModule } from '@angular/core';
import { MessageComponent } from './page/message.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatListComponent } from './components/chat-list/chat-list.component';
import { ChatFrameComponent } from './components/chat-frame/chat-frame.component';

@NgModule({
  declarations: [MessageComponent, ChatListComponent, ChatFrameComponent],
  imports: [CommonModule, RouterModule, SharedModule, FormsModule],
  exports: [MessageComponent],
})
export class MessageModule {}
