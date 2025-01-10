export class CreatePostDTO {
    author_id: number;
    author_type: string;
    post_type:  string;
    privacy: string;
    content: string;
    medias: File[] = [];
    
    constructor(data: any) {
        this.author_id = data.author_id;
        this.author_type = data.author_type;
        this.post_type = data.post_type;
        this.privacy = data.privacy;
        this.content = data.content;
    }
}