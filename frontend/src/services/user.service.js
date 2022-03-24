import axios from 'axios';
import authHeader from './auth-header';

class UserService {
    addTch(tch) {
        return axios.post("/tch/add", {
            account: tch.account,
            tchName: tch.tchName,
            password: tch.password,
            sex: tch.sex,
            major: tch.major,
            tel: tch.tel,
            email: tch.email,
        },
            { headers: authHeader() }
        )
    }

    addStu(stu) {
        return axios.post("/stu/add", {
            account: stu.account,
            stuName: stu.stuName,
            password: stu.password,
            sex: stu.sex,
            clazz: stu.clazz,
        },
            { headers: authHeader() }
        )
    }
}

export default new UserService();
