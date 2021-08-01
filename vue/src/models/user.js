export default class User {
    constructor(identity, account, password, captchaCode) {
        this.identity = identity;
        this.account = account;
        this.password = password;
        this.captchaCode = captchaCode;
    }
}
