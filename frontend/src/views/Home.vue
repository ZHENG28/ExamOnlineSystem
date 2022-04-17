<template>
  <div>
    <div>
      <h1 style="padding: 20px">
        {{ this.username }} {{ this.identity }}，欢迎您！
      </h1>
    </div>
    <div class="row">
      <div class="col-12">
        <!-- <card type="chart">
          <template slot="header">
            <div class="row">
              <div class="col-sm-6">
                <h3 class="card-title">{{ this.cardTitle }}</h3>
              </div>
            </div>
          </template>
          <div class="chart-area">
            <line-chart
              style="height: 100%"
              ref="bigChart"
              chart-id="big-line-chart"
              :chart-data="bigLineChart.chartData"
              :gradient-colors="bigLineChart.gradientColors"
              :gradient-stops="bigLineChart.gradientStops"
              :extra-options="bigLineChart.extraOptions"
            >
            </line-chart>
          </div>
        </card> -->
      </div>
    </div>
  </div>
</template>
<script>
// import config from "@/config";
// import LineChart from "@/components/Charts/LineChart";
// import * as chartConfigs from "@/components/Charts/config";

import authHeader from "@/services/auth-header";

export default {
  components: {
    // LineChart,
  },
  data() {
    return {
      identity: "",
      account: "",
      username: "",
      cardTitle: "",

      bigLineChart: {
        // // 图表数据
        // allData: [],
        // // 图表基本样式
        // chartData: {
        //   datasets: [{}],
        //   labels: [],
        // },
        // activeIndex: 0,
        // extraOptions: chartConfigs.purpleChartOptions,
        // gradientColors: config.colors.primaryGradient,
        // gradientStops: [1, 0.4, 0],
      },
    };
  },
  computed: {
    // currentUser() {
    //   let user = this.$storage.getStorageSync("user");
    //   this.account = user.account;
    //   switch (user.roles[0]) {
    //     case "ROLE_MANAGER":
    //       this.identity = "管理员";
    //       this.username = user.account;
    //       // this.cardTitle = "近七天登录系统的人数";
    //       this.cardTitle = "近七天完成测验的学生人数";
    //       this.getFinishStuNum();
    //       break;
    //     case "ROLE_TEACHER":
    //       this.identity = "教师";
    //       this.cardTitle = "近七天完成测验的学生人数";
    //       this.getUsername(2);
    //       break;
    //     case "ROLE_STUDENT":
    //       this.identity = "同学";
    //       this.cardTitle = "近七天完成测验的次数";
    //       this.getUsername(1);
    //       break;
    //   }
    //   return user;
    // },
  },
  methods: {
    getUsername(role) {
      //   this.$axios
      //     .get("/sys/findUsername", {
      //       headers: authHeader(),
      //       params: {
      //         role: role,
      //         account: this.account,
      //       },
      //     })
      //     .then((res) => {
      //       if (role == 2) {
      //         this.username = res.data.tchName;
      //         this.getFinishStuNum();
      //       } else if (role == 1) {
      //         this.username = res.data.stuName;
      //         this.getFinishTime(this.account);
      //       }
      //     });
    },
    // 学生首页图表
    getFinishTime(account) {
      //   this.$axios
      //     .post("/sys/getFinishTime", this.$qs.stringify({ account: account }), {
      //       headers: authHeader(),
      //     })
      //     .then((response) => {
      //       let arr = response.data;
      //       this.bigLineChart.allData = [arr.reverse()];
      //       this.initBigChart(0);
      //     });
    },
    // 教师首页图表
    getFinishStuNum() {
      //   this.$axios
      //     .post(
      //       "/sys/getFinishStuNum",
      //       {},
      //       {
      //         headers: authHeader(),
      //       }
      //     )
      //     .then((response) => {
      //       let arr = response.data;
      //       this.bigLineChart.allData = [arr.reverse()];
      //       this.initBigChart(0);
      //     });
    },

    initBigChart(index) {
      let nowMonth = new Date().getMonth() + 1;
      let nowDate = new Date().getDate();
      let chartData = {
        // LineChart样式
        datasets: [
          {
            fill: true,
            // 曲线的线
            borderColor: config.colors.vue_state,
            borderWidth: 2,
            borderDash: [],
            borderDashOffset: 0.0,
            // 曲线的点
            pointBackgroundColor: config.colors.vue,
            pointBorderColor: "rgba(255,255,255,0)",
            pointHoverBackgroundColor: config.colors.primary,
            pointBorderWidth: 20,
            pointHoverRadius: 4,
            pointHoverBorderWidth: 15,
            pointRadius: 4,
            data: this.bigLineChart.allData[index],
          },
        ],
        // 横坐标
        labels: [
          nowMonth + "/" + (nowDate - 6),
          nowMonth + "/" + (nowDate - 5),
          nowMonth + "/" + (nowDate - 4),
          nowMonth + "/" + (nowDate - 3),
          nowMonth + "/" + (nowDate - 2),
          nowMonth + "/" + (nowDate - 1),
          nowMonth + "/" + nowDate,
        ],
      };
      this.$refs.bigChart.updateGradients(chartData);
      this.bigLineChart.chartData = chartData;
      this.bigLineChart.activeIndex = index;
    },
  },
};
</script>
<style>
</style>
