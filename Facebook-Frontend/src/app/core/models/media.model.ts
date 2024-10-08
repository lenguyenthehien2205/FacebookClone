export class Media {
    media_id: number;
    media_type: 'image' | 'video';
    url: string;
    constructor(data: any) {
        this.media_id = data.media_id;    
        this.media_type = data.media_type;      
        this.url = data.url;
    }
}