export class LoadPostDTO {
    author_id: number = 0;
    limit: number = 0;
    fetched_ids: number[] = [];
    constructor(data: any) {
        this.author_id = data.author_id;
        this.limit = data.limit;
        this.fetched_ids = data.fetched_ids;
    }
}