import { Routes } from "@angular/router";
import { MessageComponent } from "./page/message.component";
import { ChatFrameComponent } from "./components/chat-frame/chat-frame.component";

export const messageRoutes: Routes =  [
    {
        path: ':conversationId',
        component: ChatFrameComponent
    },
];
