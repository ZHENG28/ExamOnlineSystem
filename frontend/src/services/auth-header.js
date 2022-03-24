export default function authHeader() {
  let user = JSON.parse(localStorage.getItem('user'));

  if (user && user.token) {
    // 返回用户已经经过验证的Token
    return { Authorization: 'Bearer ' + user.token }; // for Spring Boot back-end
  } else {
    return {};
  }
}
