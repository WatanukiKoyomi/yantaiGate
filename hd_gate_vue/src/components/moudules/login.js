import axios from 'axios'

/*
 * 系统登录模块
 */

// 登录
export const login = (username, password) => {
  const data = {
    username,
    password
  }
  return axios({
    url: '/hdGate/sys/login',
    method: 'get',
    params: data
  })
}

// 登出
export const logout = () => {
  console.log('jjjjjjjjjjjjjjjjjjjjj')
  return axios({
    url: '/hdGate/sys/logout',
    method: 'get',
    params: { username: JSON.parse(sessionStorage.user).username }
  })
}
