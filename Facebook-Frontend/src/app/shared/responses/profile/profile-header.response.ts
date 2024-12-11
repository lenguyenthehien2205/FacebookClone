import { ImageResponse } from '../common/image.response';

export class ProfileHeaderResponse {
  profile_id: number = 0;
  avatar: string = '';
  cover_photo: string = '';
  fullname: string = '';
  total_friends: number = 0;
  mutual_friends: number = 0;
  is_friends: boolean = false;
  is_online: boolean = false;
  avatar_friends: ImageResponse[] = [];
}
