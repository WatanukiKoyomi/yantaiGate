import axios from 'axios'

/*
 * 菜单管理模块
 */

export const findMenuTree = () => {
  return axios({
    url: '/hdGate/sys/findUsersMenu',
    method: 'get',
    params: { username: JSON.parse(sessionStorage.user).username }
  })
}

