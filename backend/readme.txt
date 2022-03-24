Spring Security的认证思路：
    1. 带着身份信息的请求
    2. AuthenticationManager认证身份信息
    3. 通过SecurityContextHolder获取SecurityContext
    4. 将认证后的信息存入到SecurityContext中
导入依赖：security validation jjwt mysql jpa
必备组件：加密器 AuthenticationManager（调用authenticate，Spring会自动认证） UserDetailsServiceImpl

认证之前的：
    1. 获取请求包中的Token信息
    2. 去掉Token的头部Bearer
    3. 解析Token，拿到username
    4. 是已登录状态，直接从缓存中读取UserDetails信息即可
    5. 查看：
        1) UserDetails是否为null
        2) Token是否过期
        3) UserDetails中的username是否和Token中的一致
    6. 组装authentication对象，并将其放到上下文对象中，以便于接下来的过滤器可以查看到该请求中的token已经过验证

本项目由于是前后端分离，因此未将token放入缓存，而是直接由前端来控制、注销等操作