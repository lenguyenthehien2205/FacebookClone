import { Media } from "./media.model";

export class Post {
    post_id: number;
    author_id: string;
    author_name: string;
    author_type: string;
    first_name: string;
    last_name: string;
    display_format: string;
    content: string;
    privacy: string;
    medias: Media[];
    avatar: string;
    created_at: number[];
    updated_at: number[];
    is_online: boolean;
    constructor(data: any) {
        this.post_id = data.post_id;
        this.author_id = data.author_id;
        this.author_name = data.author_name;
        this.author_type = data.author_type;
        this.first_name = data.first_name;
        this.last_name = data.last_name;
        this.display_format = data.display_format;
        this.avatar = data.avatar;
        this.content = data.content;
        this.privacy = data.privacy;
        this.medias = data.medias;
        this.created_at = data.created_at;
        this.updated_at = data.updated_at;
        this.is_online = data.is_online;
    }
}