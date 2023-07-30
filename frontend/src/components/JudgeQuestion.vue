<template>
  <div>
    <p class="topic">
      <span class="number">{{ number }}</span>
      {{ title }}
    </p>
    <p>
      <el-button
        circle
        size="large"
        @click="isable && sendAnswer(true)"
        :type="isTrue"
      >
        <el-icon><check /></el-icon>
      </el-button>
      <el-button
        circle
        size="large"
        @click="isable && sendAnswer(false)"
        :type="isFalse"
      >
        <el-icon><close /></el-icon>
      </el-button>
    </p>
  </div>
</template>

<script>
export default {
  props: {
    number: Number,
    title: String,
    choose: String,
    questionId: Number,
    isable: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      isTrue: "",
      isFalse: "",
    };
  },
  // 每次刷新需要在component上加上key
  created() {
    if (this.choose != undefined) {
      let reply = this.choose.split(" ");
      if (reply != "") {
        this.changeFlag(JSON.parse(reply[1]));
      }
    }
  },
  methods: {
    changeFlag(value) {
      this.isTrue = value ? "primary" : "";
      this.isFalse = value ? "" : "primary";
    },
    sendAnswer(value) {
      this.changeFlag(value);
      this.$emit("returnAnswer", this.questionId + " " + value);
    },
  },
};
</script>
<style scoped>
.number {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  width: 36px;
  height: 27px;
  background-color: #38d39f;
  color: #fff;
  border-radius: 4px;
  margin-right: 4px;
  font-weight: bold;
}
.topic {
  padding: 20px 0px;
  font-size: 17px;
}
.el-button {
  padding: 10px;
  margin: 0 10px 0 30px;
}
.answer-content:hover {
  color: #409eff;
  cursor: pointer;
}
</style>