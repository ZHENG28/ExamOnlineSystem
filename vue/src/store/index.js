import Vue from "vue";
import Vuex from 'vuex';
import { auth } from './auth.module';
import user from '../services/user.service';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    auth,
    user
  }
});
