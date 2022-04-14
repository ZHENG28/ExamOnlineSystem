import { createStore } from 'vuex'
import userService from '../services/user.service'

const user = JSON.parse(localStorage.getItem('user'))
const initialState = user
  ? { status: { isLogin: true }, user }
  : { status: { isLogin: false }, user: null }

export default createStore({
  // namespaced: true,
  state: { initialState },
  mutations: {
    loginSuccess(state, user) {
      state.status.isLogin = true
      state.user = user
    },
    loginFailure(state) {
      state.status.isLogin = false
      state.user = null
    },
    logout(state) {
      state.status.isLogin = false
      state.user = null
    },
  },
  actions: {
    // login({ commit }, user) {
    //   return AuthService.login(user).then(
    //     (user) => {
    //       commit('loginSuccess', user)
    //       return Promise.resolve(user)
    //     },
    //     (error) => {
    //       commit('loginFailure')
    //       return Promise.reject(error)
    //     },
    //   )
    // },
    // logout({ commit }) {
    //   AuthService.logout()
    //   commit('logout')
    // },
  },
  modules: {
    userService,
  },
})
