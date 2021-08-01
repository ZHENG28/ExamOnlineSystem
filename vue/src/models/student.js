export default class Student {
    constructor(account, stuName, password, sex, clazzId, clazzName, major) {
        this.account = account;
        this.stuName = stuName;
        this.password = password;
        this.sex = sex;
        this.clazz = {
            clazzId: clazzId,
            major: major,
            clazzName: clazzName,
        };
    }
}