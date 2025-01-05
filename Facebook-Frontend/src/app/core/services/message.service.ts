import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from 'src/app/shared/responses/api.response';
import { apiConfig } from 'src/app/shared/utils/api.utils';

@Injectable({
    providedIn: 'root'
})
export class MessageService{
    private getMessageUrl = 'http://localhost:8088/api/v1/messages';

    constructor(private http: HttpClient){}

    getMessages(conversation_id: number): Observable<ApiResponse> {
        return this.http.get<ApiResponse>(
            `${this.getMessageUrl}/${conversation_id}`,
            apiConfig
        );
    }  
}