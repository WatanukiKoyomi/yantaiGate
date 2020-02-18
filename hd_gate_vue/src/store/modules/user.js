import { login, logout } from '@/components/moudules/login'

const state = {
  user: null,
  isLogin: false
}

const actions = {
  async login ({commit}, user) {
    var res = await new Promise((resolve, reject) => {
      login(user.username.trim(), user.password).then(response => {
        console.log('response:', response)
        // 增加显示的中文名称的字段 id字段
        user.userId = response.userId
        user.showName = response.userName
        user.btns = response.btnsList
        sessionStorage.setItem('user', JSON.stringify(user))
        resolve()
      }).catch(error => {
        reject(error)
      })
      setTimeout(() => {
        resolve({bool: true, user})
      }, 2000)
    })
    commit('SET_LOGIN_USER', user)
    // commit('SET_LOGIN_TOKEN', '4eea90fd-2752-481d-ae67-c75f8641a94a')
    commit('SET_DOING_LOGIN', false)
    return res
  },
  async getLoginUser ({commit}) {
    // 模拟请求用户信息
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        var user = sessionStorage.getItem('user')
        // console.log(JSON.parse(user))
        commit('SET_LOGIN_USER', JSON.parse(user))
        resolve({bool: true, user})
      }, 1000)
    })
  },
  async logout ({commit}) {
    // 模拟退出
    return new Promise((resolve, reject) => {
      logout()
      setTimeout(() => {
        sessionStorage.removeItem('user')
        commit('SET_LOGIN_USER', null)
        resolve({bool: true})
      }, 2000)
    })
  }
}

const mutations = {
  SET_DOING_LOGIN (state, isLogin) {
    state.isLogin = isLogin
  },
  SET_LOGIN_USER (state, user) {
    state.user = user
  }
}

export default {
  state,
  actions,
  mutations
}
