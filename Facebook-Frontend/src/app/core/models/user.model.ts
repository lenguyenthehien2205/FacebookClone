import { Role } from "./role.model";

export class User {   
    user_id: number;
    username: string;
    first_name: string;
    last_name: string;
    display_format: string;
    avatar: string;
    phone_number: string;
    role: Role; 
            
    constructor(data: any) {
        this.user_id = data.user_id;    
        this.username = data.username;    
        this.first_name = data.first_name;    
        this.last_name = data.last_name;    
        this.display_format = data.display_format;   
        this.avatar = data.avatar;    
        this.phone_number = data.phone_number;    
        this.role = data.role;   
    }
}