<template>
  <div>
    <div>
      <h1 style="padding: 20px">
        {{ user.username }} {{ roleName }}，欢迎您！
      </h1>
    </div>
    <div class="row">
      <div class="col-12">
        <!-- <card type="chart">
          <template slot="header">
            <div class="row">
              <div class="col-sm-6">
                <h3 class="card-title">{{ cardTitle }}</h3>
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
import authHeader from "@/services/auth-header";
import User from "@/services/user";

export default {
  data() {
    return {
      user: new User("", "", "", "", ""),
      roleName: "",
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
  created() {
    this.loadData();
    // 设置身份称呼
    switch (this.$storage.getStorageSync("user").roles[0]) {
      case "ROLE_ADMIN":
        this.roleName = "管理员";
        // this.cardTitle = "近七天登录系统的人数";
        this.cardTitle = "近七天完成测验的学生人数";
        this.getFinishStuNum();
        break;
      case "ROLE_TEACHER":
        this.roleName = "教师";
        this.cardTitle = "近七天完成测验的学生人数";
        break;
      case "ROLE_STUDENT":
        this.roleName = "同学";
        this.cardTitle = "近七天完成测验的次数";
        break;
    }
  },
  methods: {
    loadData() {
      this.$axios
        .get("/user/findInfoById", {
          headers: authHeader(),
          params: {
            userId: this.$storage.getStorageSync("user").id,
          },
        })
        .then((response) => {
          this.user = response.data;
        });
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
