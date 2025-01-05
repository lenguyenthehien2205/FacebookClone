import { Component, ElementRef, inject, OnDestroy, OnInit, signal, ViewChild } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { MessageDTO } from 'src/app/shared/dtos/message.dto';
import { WebSocketService } from 'src/app/core/services/websocket.service';
import { MessageService } from 'src/app/core/services/message.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html'
})
export class MessageComponent implements OnInit{
  conversationId = signal<number>(0);
  route = inject(ActivatedRoute);
  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('conversationId'));
    if (isNaN(id)) {
      console.error('Invalid Conversation ID');
      return;
    }
    this.conversationId.set(id);
    console.log('Conversation ID: ', this.conversationId());
    
  }
}