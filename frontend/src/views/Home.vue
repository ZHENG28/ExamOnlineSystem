<template>
  <div>
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="8" v-for="data in cardData.slice(0, 3)" :key="data.title">
        <number-card
          :title="data.title"
          :subtitle="data.subtitle"
          :src="data.src"
          :number="data.number"
        >
        </number-card>
      </el-col>
    </el-row>
    <el-card style="margin-top: 20px">
      <div ref="lineChart" style="width: 1280px; height: 450px"></div>
    </el-card>
  </div>
</template>
<script>
import userToken from "@/services/auth-header";
import { dealSelect } from "@/services/response";
import User from "@/services/user";

import numberCard from "@/components/NumberCard.vue";

export default {
  components: {
    numberCard,
  },
  data() {
    return {
      user: new User("", "", "", "", ""),
      roleName: "",
      cardData: [
        {
          title: "",
          subtitle: "",
          src: "",
          number: 0,
        },
        {
          title: "",
          subtitle: "",
          src: "",
          number: 0,
        },
        {
          title: "",
          subtitle: "",
          src: "",
          number: 0,
        },
        {
          title: "",
          data: [],
        },
      ],
    };
  },
  created() {
    this.loadUserInfo().then((response) => {
      switch (this.$storage.getStorageSync("user").roles[0]) {
        case "ROLE_ADMIN":
          this.loadAdminData();
          break;
        case "ROLE_TEACHER":
          this.loadTeacherData(this.user.username + "教师");
          break;
        case "ROLE_STUDENT":
          this.loadStudentData(this.user.username + "同学");
          break;
      }
    });
  },
  methods: {
    loadUserInfo() {
      return this.$axios
        .get("/user/findInfoById", {
          headers: { Authorization: userToken() },
          params: {
            userId: this.$storage.getStorageSync("user").id,
          },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.user = res;
          }
        });
    },

    loadAdminData() {
      this.$axios
        .get("/user/loadHomeData", {
          headers: { Authorization: userToken() },
          params: { userId: this.user.userId, roleId: this.user.roleId },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.cardData = [
              {
                title: "班级总数",
                subtitle: "本系统中的班级总数",
                src: require("../assets/home/icon_clazz.png"),
                number: res.clazz,
              },
              {
                title: "用户总数",
                subtitle: "本系统中的用户总数",
                src: require("../assets/home/icon_user.png"),
                number: res.user,
              },
              {
                title: "科目总数",
                subtitle: "本系统中的科目总数",
                src: require("../assets/home/icon_subject.png"),
                number: res.subject,
              },
              {
                title: "近七天登录系统的人次",
                data: [6, 4, 5, 8, 12, 14, 15],
              },
            ];
            this.drawObjectChart();
          }
        });
    },
    loadTeacherData(suffix) {
      this.$axios
        .get("/user/loadHomeData", {
          headers: { Authorization: userToken() },
          params: { userId: this.user.userId, roleId: this.user.roleId },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.cardData = [
              {
                title: "所授科目总数",
                subtitle: suffix + "管理的所有科目总计",
                src: require("../assets/home/icon_subject.png"),
                number: res.subject,
              },
              {
                title: "创建题目总数",
                subtitle: suffix + "在所有科目下创建的题目数量总和",
                src: require("../assets/home/icon_question.png"),
                number: res.question,
              },
              {
                title: "发布测验总数",
                subtitle: suffix + "在所有科目下发布的测试数量总和",
                src: require("../assets/home/icon_test.png"),
                number: res.test,
              },
              {
                title: "近七天完成测验的人次",
                data: [2, 3, 7, 12, 3, 6, 9],
              },
            ];
            this.drawObjectChart();
          }
        });
    },
    loadStudentData(suffix) {
      this.$axios
        .get("/user/loadHomeData", {
          headers: { Authorization: userToken() },
          params: { userId: this.user.userId, roleId: this.user.roleId },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.cardData = [
              {
                title: "未完成测验",
                subtitle: suffix + "尚未完成且未结束的测验总计",
                src: require("../assets/home/icon_complete.png"),
                number: res.complete,
              },
              {
                title: "平均正确率",
                subtitle: suffix + "在所有测验中的平均正确率",
                src: require("../assets/home/icon_test.png"),
                number: res.average.toFixed(2),
              },
              {
                title: "最高正确率",
                subtitle: suffix + "在所有测验中的最高正确率",
                src: require("../assets/home/icon_success.png"),
                number: res.max.toFixed(2),
              },
              {
                title: "近七天完成测验的次数",
                data: [2, 3, 1, 0, 0, 4, 1],
              },
            ];
            this.drawObjectChart();
          }
        });
    },

    drawObjectChart() {
      // let xAxisData = [],
      //   xData = [];
      // data.forEach((item, index) => {
      //   xData.push({ title: item.questionTitle, value: item.correctNumber });
      //   xAxisData.push(index + 1);
      // });
      let now = new Date();
      let date = [];
      for (let i = 0; i < 7; i++) {
        date[7 - i - 1] = now.getMonth() + 1 + "/" + now.getDate();
        now = new Date(now - 24 * 60 * 60 * 1000);
      }
      setTimeout(() => {
        this.chart = this.$echarts.init(this.$refs.lineChart);
        this.chart.setOption({
          color: ["#38d39f"],
          title: {
            text: this.cardData[3].title,
            left: "center",
          },
          // 调整图表与标题之间的距离
          // grid: {
          //   top: "20%",
          // },
          xAxis: {
            type: "category",
            data: date,
          },
          yAxis: {
            type: "value",
          },
          series: [
            {
              type: "line",
              data: this.cardData[3].data,
              itemStyle: {
                normal: {
                  label: {
                    show: true,
                  },
                },
              },
            },
          ],
        });
      }, 0);
    },
  },
};
</script>
<style scoped>
</style>
