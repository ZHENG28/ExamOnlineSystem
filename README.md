# 在线测验管理系统（Exam Online System）
### 1 项目简介
- 项目介绍：
    - 本项目以在线考试系统为实现基础，将基于遗传算法的智能组卷模块作为核心内容，展开研究，简单阐述用户管理、科目管理、知识点管理、题库管理、考试管理等基础模块的实现，着重讨论基于遗传算法的智能组卷方式与考试结果可视化分析的设计与实现。
    - 本系统基于Spring Boot框架与MySQL数据库进行实现，适用于大多数浏览器。投入使用后，在一定程度上能提高教师的工作效率，减轻教师在考试组织和安排方面的工作压力，同时全面、直观地了解学生的学习情况，高效评估教学效果，有望实现教考分离，推进教学改革。
- 功能：
    - 管理员端：登录地址：http://localhost:8080/admin/login
        1. 可在首页查看系统统计数据以及近七天登录系统的人次
        2. 专业、班级、用户、科目以及问题类型的增删改查
    - 用户端：登录地址：http://localhost:8080/
        - 教师端：
            1. 可在首页查看相关统计数据以及近七天完成测验的人次
            2. 管理自己教授的科目以及各个科目的题目
            3. 在指定科目下发布测验，可指定题库、测验时长以及测验次数
            4. 可查看指定测验的结果，提供学生作答历史记录、客观题作答情况可视化以及主观题部分的文本对比等功能
        - 学生端：
            1. 可在首页查看自己在所有测验中的平均正确率以及最高正确率
            2. 可完成老师发布的测验，测验页面采用题号导航的方式，方便学生在测验时快速跳转题目
            3. 保留学生测验作答情况，可在测验记录处查看历史作答情况
- 所用技术：
	- 前端：vue3、element-plus、vue3-storage、axios、echarts
	- 后端：Spring boot框架、Spring Security（JWT）、Mybatis-plus、jython
- 项目启动命令：
    - 前端：port为8080，`npm run serve`
        1. 启动前提：项目中用到的所有包都已经安装好并且没有报错（可用`npm install`安装，检查frontend文件夹下是否有node_modules文件夹）
    - 后端：port为9090
        1. 使用项目下的res/db/3.0/create.sql文件初始化数据库（管理员账号：123，密码：123）
        2. 修改配置文件application.properties中的各项参数：`spring.datasource.url`和`spring.datasource.password`
### 2 页面展示
#### 1 管理员端
1. 登陆页面：
    ![admin-login](/res/img/2.0/admin-login.jpg)
2. 管理员主页：
    ![admin-home](/res/img/2.0/admin-home.jpg)
#### 2 用户端
1. 登陆页面：
    ![user-login](/res/img/2.0/user-login.jpg)
2. **教师角色**：
    1. 主页：
        ![teacher-home](/res/img/2.0/teacher-home.jpg)
    2. 发布测验：
        ![teacher-publish](/res/img/2.0/teacher-publish.jpg)
    3. 测验结果可视化图表：
        ![teacher-result-chart](/res/img/2.0/teacher-result-chart.jpg)
    4. 测验结果相似度查看：
        ![teacher-result-compare](/res/img/2.0/teacher-result-compare.jpg)
3. **学生角色**：
    1. 主页：
        ![student-home](/res/img/2.0/student-home.jpg)
    2. 测验：
        ![student-test](/res/img/2.0/student-test.jpg)
    3. 测验历史记录：
        ![student-history](/res/img/2.0/student-history.jpg)

### 3 已解决问题
1. **优化python在java中的调用执行时间**：由于java中调用python是采用命令行的方式，因此每次都需要重新import。改用socket之后，SpringBoot项目启动的同时启动python，由socket服务器和客户端来在java和python之间通信，加快项目运行速度
    - https://www.likecs.com/show-421269.html
    - https://blog.csdn.net/weixin_44452346/article/details/107616952

### 4 待解决问题
1. 前端el-table的**sortable和filter仅限于本页内**（后端**框架分页**）
2. 教师端的测验结果处的相似度表格中的**ranking（第几次测验）** 仍存在问题，应作sql语句优化，现在使用的是视图，应改进为存储过程或其他方式
3. 管理员首页的图表，应加入**Redis数据库**，缓存近七天登陆系统的人次，并予以可视化展示
