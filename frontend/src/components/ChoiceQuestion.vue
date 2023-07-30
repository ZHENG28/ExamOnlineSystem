<template>
  <div>
    <p class="topic">
      <span class="number">{{ number }}</span>
      {{ title }}
    </p>
    <p v-for="(answer, index) in answers" :key="index">
      <el-button
        circle
        @click="isable && sendAnswer(index)"
        :type="chooseFlag[index] ? 'primary' : ''"
      >
        {{ answer.answerSign }}
      </el-button>
      <span class="answer-content" @click="isable && sendAnswer(index)">
        {{ answer.content }}
      </span>
    </p>
  </div>
</template>

<script>
export default {
  props: {
    number: Number,
    title: String,
    answers: {
      type: Array,
      default: () => [],
    },
    choose: String,
    isable: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      chooseFlag: [],
    };
  },
  created() {
    if (this.choose != undefined) {
      let reply = this.choose.split(" ");
      for (let i = 0; i < this.answers.length; i++) {
        this.chooseFlag[i] = reply[1] == this.answers[i].answerSign;
      }
    } else {
      this.clearChoose();
    }
  },
  methods: {
    clearChoose() {
      for (let i = 0; i < this.answers.length; i++) {
        this.chooseFlag[i] = false;
      }
    },
    sendAnswer(val) {
      this.clearChoose();
      this.chooseFlag[val] = true;
      this.$emit(
        "returnAnswer",
        this.answers[val].questionId + " " + this.answers[val].answerSign
      );
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