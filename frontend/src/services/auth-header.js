import { useStorage } from 'vue3-storage'

export default function userToken() {
  let user = useStorage().getStorageSync('user')
  return user ? 'Bearer ' + user.token : ''
}
