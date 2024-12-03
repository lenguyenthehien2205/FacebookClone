import { Role } from './role.model';

export interface User {
  user_id: number;
  username: string;
  first_name: string;
  last_name: string;
  display_format: string;
  avatar: string;
  phone_number: string;
  role: Role;
}
export interface UserTag {
  user_id: number;
  profile_id: number;
  first_name: string;
  last_name: string;
  display_format: string;
  avatar: string;
  is_online: boolean;
}
