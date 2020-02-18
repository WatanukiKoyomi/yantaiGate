import store from '../index'

// 判断按钮是否在权限内，在则显示，不在则不显示
export function hasBtnPermission (permission) {
  let user = store.state.user.user
  if (user === null) {
    return false
  }
  let myBtns = store.state.user.user.btns
  var boo = false
  // console.log(store.state.user.user.btns)
  myBtns.forEach(function (element) {
    if (element.name === permission) {
      boo = true
    }
  })
  return boo
}
