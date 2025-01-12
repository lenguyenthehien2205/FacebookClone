export class UpdatePostDTO {
    privacy: string;
    content: string;
    media_ids: number[] = [];
    
    constructor(data: any) {
        this.privacy = data.privacy;
        this.content = data.content;
    }
}