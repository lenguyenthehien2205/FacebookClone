export class RegisterDTO {
    // firstName: string;
    // lastName: string;
    // phoneNumberOrEmail: string;
    // password: string;
    // gender: string;
    // day: number;
    // month: number;
    // year: number;
    // constructor(data: any) {
    //     this.firstName = data.firstName;
    //     this.lastName = data.lastName;
    //     this.phoneNumberOrEmail = data.phoneNumberOrEmail;
    //     this.password = data.password;
    //     this.gender = data.gender;
    //     this.day = data.day;
    //     this.month = data.month;
    //     this.year = data.year;
    // }
    username: string;
    password: string;
    avatar: string;
    phone_number: string;
    role_id: string;
    constructor(data: any) {
        this.username = data.username;
        this.password = data.password;
        this.avatar = data.avatar;
        this.phone_number = data.phone_number;
        this.role_id = data.role_id;
    }
}