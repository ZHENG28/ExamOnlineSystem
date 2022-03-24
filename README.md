# 在线测验管理系统
- 功能：（待完善）
    1. 主要有学生在线测验及教师管理测验等。
- 所用技术：
	- 前端：Vue、element-UI、vuex、axios、ECharts
	- 后端：Spring boot框架、Spring Security（JWT）、Kaptcha
- 项目启动：
    - 前端：port为8080，`npm run dev`
    - 后端：port为9090
        1. 修改配置文件中的各项参数：`spring.datasource.url/password`, `spring.jpa.hibernate.ddl-auto=create/update`
        2. 初始化好数据库后，再修改为`ddl-auto=update`，同时注释创建管理员的语句（管理员默认创建：账号：admin，密码：123）
- 页面展示：
