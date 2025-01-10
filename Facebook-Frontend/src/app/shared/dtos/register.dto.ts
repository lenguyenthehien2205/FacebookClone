export class RegisterDTO {
    path_name: string;
    first_name: string;
    last_name: string;
    phone_number: string;
    password: string;
    gender: string;
    date_of_birth: string;
    role_id: number;
    constructor(data: any) {
        this.first_name = data.first_name;
        this.last_name = data.last_name;
        this.phone_number = data.phone_number;
        this.password = data.password;
        this.gender = data.gender;
        this.date_of_birth = data.date_of_birth;
        this.path_name = data.path_name;
        this.role_id = data.role_id;
    }
    // username: string;
    // password: string;
    // avatar: string;
    // phone_number: string;
    // role_id: string;
    // constructor(data: any) {
    //     this.username = data.username;
    //     this.password = data.password;
    //     this.avatar = data.avatar;
    //     this.phone_number = data.phone_number;
    //     this.role_id = data.role_id;
    // }
}