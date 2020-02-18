<template>
<m-navbar :theme="theme.theme.headerTheme">
  <m-navbar-brand>
    <i class="side-switch"
      :class="{
        'el-icon-more': !mini,
        'el-icon-more-outline': mini
      }"
      @click="handleSwitchSide"
      ></i>
  </m-navbar-brand>
  <m-nav>
    <!--<m-nav-item><a href="https://www.baidu.com" target="_blank">主页</a></m-nav-item>-->
  </m-nav>
  <m-nav align="right">
    <m-nav-item padding="0">
      <m-dropdown align="right" v-if="user" padding="0 10px">
        <a href="#" style="display: inline-block; padding: 0px; color: inherit">
          <!--<img src="../assets/user.jpg" alt="" style="border-radius: 3px;vertical-align: middle;">-->
          <span>{{user.showName}}</span>
          <span class="caret"></span>
        </a>
        <m-dropdown-panel>
          <m-dropdown-item>&nbsp;&nbsp;&nbsp;修改密码</m-dropdown-item>
          <div class="test-line"></div>
          <m-dropdown-item name="login"><span @click="logout" style="display:block;">&nbsp;&nbsp;&nbsp;退出账号</span></m-dropdown-item>
        </m-dropdown-panel>
      </m-dropdown>
    </m-nav-item>
    <m-nav-item padding="0 5px">
      <a href="#" @click.stop.prevent="handleSwitchScreen">
        <i
          class="fa"
          :class="isFullScreen ? 'fa-compress' : 'fa-expand'"
          ></i>
      </a>
    </m-nav-item>

    <m-nav-item padding="0">
      <m-dropdown align="right" padding="0 10px">
        <a href="#"  style="padding:0 0px; color: inherit">
          <i class="fa fa-gears"></i>
        </a>
        <m-dropdown-panel style="width: 200px; min-height: 100px;">
          <el-form>
            <el-form-item label="侧边栏">
              <el-switch v-model="showAside" @change="handleSwitchHideSide" />
            </el-form-item>
            <el-form-item label="主题">
              <el-select v-model="themeType" style="width: 100px">
                <el-option
                  v-for="theme in themes"
                  :key="theme.label"
                  :label="theme.label"
                  :value="theme.name"
                  ></el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </m-dropdown-panel>
      </m-dropdown>
    </m-nav-item>
  </m-nav>
</m-navbar>
</template>
<script type="text/javascript">
import {mapActions, mapState} from 'vuex'
import {
  requestFullScreen,
  exitFullscreen
} from '@/utils'
import themes from '@/components/moudules/theme'
export default {
  name: 'layout-header',
  data () {
    return {
      mini: false,
      isFullScreen: false,
      themeType: '',
      themes,
      showAside: true,
      theme: {theme: {headerTheme: 'info'}}
    }
  },
  computed: {
    ...mapState({
      user: ({user}) => user.user
    })
  },
  watch: {
    themeType (val) {
      this.theme = this.themes.find(e => e.name === val) || {}
      this.$emit('set-theme', this.theme)
      localStorage.setItem('theme', val)
    }
  },
  methods: {
    ...mapActions(['getLoginUser', 'logout']),
    handleSwitchSide () {
      this.mini = !this.mini
      this.$emit('switch', this.mini)
    },
    handleSwitchScreen () {
      if (this.isFullScreen) {
        exitFullscreen()
        this.isFullScreen = false
      } else {
        requestFullScreen()
        this.isFullScreen = true
      }
    },
    handleSwitchHideSide () {
      this.$emit('hide-side')
    },
    logout () {
      console.log('console logout')
      /* this.logout({
        username: this.username
      }).then(res => {
        console.log('aaaaaaaaaaaaassssssssssssssss')
      }).catch(function (error) {
        console.log('sssssssssssssss fffffffffffffff  error:', error)
      }) */
      sessionStorage.removeItem('user')
      location.reload() // 避免当前登录退出再次登录时继续提示已经登录
      console.log('dddddddddddd', sessionStorage.user)
    }
  },
  created () {
    this.getLoginUser()
    var theme = localStorage.getItem('theme') || 'default'
    this.themeType = theme
    // 2019.7.16 展开左侧的菜单
    /* var account = JSON.parse(sessionStorage.user).username
    if (account !== 'admin') { // 不是admin隐藏左侧列表
      this.showAside = false
      this.handleSwitchHideSide()
    } */
  }
}
</script>
<style type="text/css">
  .side-switch{
    display: inline-block;
    font-size: 32px;
    cursor: pointer;
    color: inherit;
    margin-top: 10px;
  }
  .side-switch:hover{
    color: #fff;
  }
</style>
<style scoped>
  .m-dropdown-item {
    padding:0;
    min-width:85px;
  }
</style>
