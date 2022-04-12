import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import qs from 'qs'
import elementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import locale from 'element-plus/lib/locale/lang/zh-cn'
import * as icons from '@element-plus/icons-vue'

const app = createApp(App)
app.config.globalProperties.$qs = qs
app.config.globalProperties.$axios = axios
axios.defaults.baseURL = '/api'

// locale保证ElementUI为中文（默认英文）
app.use(elementPlus, { locale })
Object.keys(icons).forEach((key) => {
  app.component(key, icons[key])
})

app.use(store)
app.use(router)
app.mount('#app')
