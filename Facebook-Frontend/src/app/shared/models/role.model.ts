export class Role {
    role_id: number;
    role_name: string;
    constructor(data: any) {
        this.role_id = data.role_id;    
        this.role_name = data.role_name;      
    }
}