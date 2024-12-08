import { inject, Injectable } from "@angular/core";
import { TokenService } from "./token.service";
import { environment } from "src/app/environments/environment";

@Injectable({
    providedIn: 'root',
})
export class ImageService {
    tokenService = inject(TokenService);
    avatarApiUrl = `${environment.apiBaseUrl}/profiles/avatar_image`;
    coverPhotoApiUrl = `${environment.apiBaseUrl}/profiles/cover_photo_image`;
    getAvatar(url: string): string{
        return `${this.avatarApiUrl}/${url}`;
    }
    getCoverPhoto(url: string): string{
        return `${this.coverPhotoApiUrl}/${url}`;
    }
}