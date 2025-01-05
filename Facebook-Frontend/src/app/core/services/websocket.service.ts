import { Injectable } from '@angular/core';
import { Client, Message } from '@stomp/stompjs';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private client: Client;
  private messageSubject = new BehaviorSubject<any>(null);

  constructor() {
    this.client = new Client({
      brokerURL: 'ws://localhost:8088/api/v1/ws', // Endpoint của WebSocket Server
      connectHeaders: {},
      debug: (str) => console.log(str),
      onConnect: () => {
        console.log('Connected');
        // Subscribe vào topic '/topic/messages' để nhận message từ server
        this.client.subscribe('/topic/messages', (message: Message) => {
          this.messageSubject.next(JSON.parse(message.body));
          console.log(message);
        });
      },
      onStompError: (frame) => {
        console.error('Error: ' + frame);
      },
      // Tự động kết nối lại nếu mất kết nối(đợi 5s)
      reconnectDelay: 5000,
    });
  }

  public connect() {
    this.client.activate();
  }

  public sendMessage(message: any) {
    // Gửi message lên server thông qua endpoint '/app/send'
    this.client.publish({
      destination: '/app/send',
      body: JSON.stringify(message),
    });
    console.log('Message sent: ', message);
  }

  public getMessages() {
    return this.messageSubject.asObservable();
  }

  public disconnect() {
    this.client.deactivate();
  }
}
