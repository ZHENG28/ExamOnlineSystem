/*
 =========================================================
 * Vue Black Dashboard - v1.1.0
 =========================================================

 * Product Page: https://www.creative-tim.com/product/black-dashboard
 * Copyright 2018 Creative Tim (http://www.creative-tim.com)

 =========================================================

 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

 */
import Vue from "vue";
import App from "./App";
import RouterPrefetch from 'vue-router-prefetch'
// TIP: change to import router from "./router/starterRouter"; to start with a clean layout
// Vue Router
import router from "./router/index";
import BlackDashboard from "./plugins/blackDashboard";
import i18n from "./i18n";
import './registerServiceWorker';
import ElementUI from "element-ui";

import VueRouter from "vue-router";
// 在Vuex中
import store from "./store";
import axios from "axios";
import VueCookies from 'vue-cookies'
import Vuex from 'vuex';
// 用于CSS
// import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import VeeValidate from 'vee-validate';
import CircularCountDownTimer from "vue-circular-count-down-timer";
import { library } from '@fortawesome/fontawesome-svg-core';
// 可使用icons
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
  faHome,
  faUser,
  faUserPlus,
  faSignInAlt,
  faSignOutAlt
} from '@fortawesome/free-solid-svg-icons';

const config = {
  errorBagName: 'errorBags',
  fieldsBagName: 'fieldBags'
};

library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.use(CircularCountDownTimer);
Vue.use(VeeValidate, config);
Vue.use(ElementUI);
Vue.use(BlackDashboard);
Vue.use(VueRouter);
Vue.use(RouterPrefetch);
Vue.use(VueCookies);
Vue.use(Vuex);

Vue.config.productionTip = false;
Vue.prototype.$qs = require("qs");
Vue.prototype.$ajax = axios;
axios.defaults.baseURL = '/api';

/* eslint-disable no-new */
new Vue({
  store,
  router,
  i18n,
  render: h => h(App)
}).$mount("#app");