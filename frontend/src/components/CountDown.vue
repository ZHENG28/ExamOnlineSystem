<template>
  <el-icon><timer /></el-icon>
  <span>
    倒计时：{{ hourString + " : " + minuteString + " : " + secondString }}
  </span>
</template>

<script>
export default {
  emits: ["returnTakeTime"],
  props: {
    remainTime: Number,
    flag: Boolean,
  },
  data() {
    return {
      hour: "",
      minute: "",
      second: "",
      promiseTimer: "",
      takeTime: 0,
    };
  },
  computed: {
    hourString() {
      return this.formatNum(this.hour);
    },
    minuteString() {
      return this.formatNum(this.minute);
    },
    secondString() {
      return this.formatNum(this.second);
    },
  },
  created() {
    if (this.remainTime > 0) {
      this.hour = Math.floor((this.remainTime / 3600) % 24);
      this.minute = Math.floor((this.remainTime / 60) % 60);
      this.second = Math.floor(this.remainTime % 60);
    } else {
      console.log("当前剩余时间：" + this.remainTime + "秒");
    }
    if (this.flag) {
      this.countDown();
    }
  },
  methods: {
    countDown() {
      var self = this;
      clearInterval(this.promiseTimer);
      this.promiseTimer = setInterval(function () {
        self.takeTime++;
        if (self.minute !== 0 && self.second === 0) {
          self.second = 59;
          self.minute -= 1;
        } else if (self.minute === 0 && self.second === 0) {
          if (self.hour === 0) {
            self.second = 0;
            self.$emit("countDownEnd", true);
            clearInterval(self.promiseTimer);
          } else {
            self.hour -= 1;
            self.minute = 59;
            self.second = 59;
          }
        } else {
          self.second -= 1;
        }
        self.$emit("returnTakeTime", self.takeTime);
      }, 1000);
    },
    formatNum(num) {
      return (num < 10 ? "0" : "") + num;
    },
  },
};
</script>