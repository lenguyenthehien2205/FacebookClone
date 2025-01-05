import { MessageResponse } from "./message.response";

export class MessageConversationResponse {
    conversation_id: number = 0;
    first_name: string = '';
    last_name: string = '';
    display_format: string = '';
    avatar: string = '';
    messages: MessageResponse[] = [];
}