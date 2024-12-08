export function getName(firstname: string, lastname: string, displayFormat: string): string{
    if(displayFormat === 'firstname_lastname'){
        return `${firstname} ${lastname}`;
    }else {
        return `${lastname} ${firstname}`;
    }
}