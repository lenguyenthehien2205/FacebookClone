import { inject, Injectable } from "@angular/core";
import { TokenService } from "./token.service";
import { environment } from "src/app/environments/environment";
import { Observable } from "rxjs";
import { ApiResponse } from "src/app/shared/responses/api.response";
import { HttpClient } from "@angular/common/http";

@Injectable({
    providedIn: 'root',
})
export class ImageService {
    http = inject(HttpClient);
    tokenService = inject(TokenService);
    avatarApiUrl = `${environment.apiBaseUrl}/profiles/avatar_image`;
    coverPhotoApiUrl = `${environment.apiBaseUrl}/profiles/cover_photo_image`;
    getAvatar(url: string): string{
        return `${this.avatarApiUrl}/${url}`;
    }
    getCoverPhoto(url: string): string{
        return `${this.coverPhotoApiUrl}/${url}`;
    }
    getUrlAvatarByProfileId(profileId: number): string {
        return `${this.avatarApiUrl}/id/${profileId}`;
    }
    uploadAvatar(formData: FormData): Observable<ApiResponse> {
        return this.http.post<ApiResponse>(`${environment.apiBaseUrl}/profiles/upload_avatar/${this.tokenService.getProfileId()}`, formData);
    }   
    uploadCoverPhoto(formData: FormData): Observable<ApiResponse> {
        return this.http.post<ApiResponse>(`${environment.apiBaseUrl}/profiles/upload_cover_photo/${this.tokenService.getProfileId()}`, formData);
    }   
}