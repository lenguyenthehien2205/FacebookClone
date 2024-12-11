export interface ImageResponse {
    profile_id: number;
    url: string;
}
export class ProfileFriendsReponse {
    total_friends: number = 0;
    mutual_friends: number = 0;
    profile_avatar_friends: ImageWithFullnameResponse[] = [];
}
export interface ImageWithFullnameResponse {
    profile_id: number;
    mutual_friends: number;
    pathname: string;
    fullname: string;
    url: string;
}
export interface ImageProfileResponse {
    media_id: number;
    url: string;
}