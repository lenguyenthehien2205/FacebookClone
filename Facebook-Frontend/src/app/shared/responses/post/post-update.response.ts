import { Media } from "../../models/media.model";

export class PostUpdateResponse {
    postId: number = 0;
    authorName: string = '';
    privacy: string = '';
    content: string = '';
    medias: Media[] = [];
}