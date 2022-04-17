import { useStorage } from 'vue3-storage'

export default function authHeader() {
  let user = useStorage().getStorageSync('user')
  return user && user.token ? { Authorization: 'Bearer ' + user.token } : {}
}
