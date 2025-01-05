import { HttpClient } from "@angular/common/http";
import { inject, Injectable, signal } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { ApiResponse } from "src/app/shared/responses/api.response";
import { apiConfig } from "src/app/shared/utils/api.utils";
import { TokenService } from "./token.service";

@Injectable({
    providedIn: 'root'
})
export class ConversationService{
    tokenService = inject(TokenService);
    private conversationUrl = 'http://localhost:8088/api/v1/conversations';
    selectedProfileId = signal<number | null>(null);
    
    constructor(private http: HttpClient){}

    getConversations(): Observable<ApiResponse>{
        return this.http.get<ApiResponse>(
            this.conversationUrl+'/'+this.tokenService.getProfileId(),
            apiConfig
        );
    }
    
    setSelectedProfileId(profileId: number) {
        this.selectedProfileId.set(profileId);
    }   
}