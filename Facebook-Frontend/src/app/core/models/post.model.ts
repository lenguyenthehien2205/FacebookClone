import { Interaction } from "./interaction.model";
import { Media } from "./media.model";

export interface Post {
    id: number;
    author_id: string;
    author_type: string;
    post_type: string;
    author_name: string;
    content: string;
    privacy: string;
    avatar: string;
    created_at: number[];
    updated_at: number[];
    medias: Media[];
    interaction_response: Interaction;
    is_active: boolean;
    is_online: boolean;
}
export interface PostFetchData {
    author_id: number;
    limit: number;
    fetched_ids: number[];
}

export interface PostWithMedia {
    post: Post;
    medias: Media[];
}