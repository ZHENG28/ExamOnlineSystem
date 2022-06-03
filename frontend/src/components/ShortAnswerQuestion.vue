<template>
  <div>
    <p class="topic">
      <span class="number">{{ number }}</span>
      {{ title }}
    </p>
    <p>
      <el-input
        v-model="text"
        @blur="sendAnswer(text)"
        type="textarea"
        :autosize="{ minRows: 10 }"
        maxlength="1000"
        show-word-limit
        :readonly="isable"
      />
    </p>
  </div>
</template>

<script>
export default {
  props: {
    number: Number,
    title: String,
    content: String,
    questionId: Number,
    isable: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return { text: "" };
  },
  created() {
    if (this.content != undefined) {
      this.text = this.content.split(" ")[1];
    } else {
      this.text = "";
    }
  },
  methods: {
    sendAnswer(val) {
      this.$emit("returnAnswer", this.questionId + " " + val);
    },
  },
};
</script>
<style scoped>
.number {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  width: 27px;
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