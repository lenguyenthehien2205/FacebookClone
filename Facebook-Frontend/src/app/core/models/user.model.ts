import { RoleModel } from "./role.model";

export class UserModel {   
    user_id: number;
    username: string;
    avatar: string;
    phone_number: string;
    role: RoleModel; 
            
    constructor(data: any) {
        this.user_id = data.user_id;    
        this.username = data.username;    
        this.avatar = data.avatar;    
        this.phone_number = data.phone_number;    
        this.role = data.role;    
    }
}