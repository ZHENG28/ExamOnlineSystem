import { ElMessage } from 'element-plus'

function dealSelect(data) {
  if (data.success) {
    return data.data
  } else {
    console.log(data.code)
    ElMessage.warning(data.essage)
  }
}

function dealSaveAndDelete(data) {}

export { dealSelect, dealSaveAndDelete }
