<template>
  <div>
    <el-card>
      <img id="card-icon" :src="src" />
      <span id="card-number">{{ number }}</span>
      <p id="card-title">{{ title }}</p>
      <p id="card-subtitle">{{ subtitle }}</p>
    </el-card>
  </div>
</template>

<script>
export default {
  props: {
    number: Number,
    title: String,
    subtitle: String,
    src: String,
  },
};
</script>
<style scoped>
#card-icon {
  width: 40px;
}
#card-title {
  font-size: 19px;
  font-weight: bold;
  margin-bottom: 0 !important;
}
#card-subtitle {
  font-size: 14px;
  margin-bottom: 0 !important;
}
#card-number {
  color: #38d39f;
  font-size: 70px;
  font-family: fantasy;
  float: right;
}
</style>