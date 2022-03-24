import axios from 'axios';

class AuthService {
  login(user) {
    return axios.post('/sys/login', {
      identity: user.identity,
      account: user.account,
      password: user.password
    })
      .then(response => {
        if (response.data.token) {
          // 将该登录用户的令牌移入store
          localStorage.setItem('user', JSON.stringify(response.data));
        }
        return response.data;
      });
  }

  logout() {
    // 将该登出用户的令牌移出store
    localStorage.removeItem('user');
  }
}

export default new AuthService();
