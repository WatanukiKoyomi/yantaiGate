<template>
<el-container class="app-container"
  :class="{
    'mini-side': isCollapse,
    'hide-side': hideSide
  }"
  >
  <el-aside class="app-side" :width="hideSide ? '0' : sideWidth + 'px'"
    :style="{background: theme.theme.backgroundColor}"
    >
    <div class="app-header-logo-box"
      :style="{height: headerHeight + 'px', color: theme.theme.activeTextColor}"
      >
      <img src="../assets/logo.png" alt="" class="header-logo">
      <span class="header-logo-text" >控制台</span>
    </div>
    <layout-side :collapse="isCollapse" :theme="theme.theme"></layout-side>
  </el-aside>
  <el-container style="overflow-x: auto">
    <el-header class="app-header" :height="headerHeight + 'px'">
      <layout-header @switch="handleSideSwitch" @set-theme="handleSetTheme" @hide-side="handleSwitchHideSide"></layout-header>
    </el-header>
    <el-main class="app-body">
      <el-container style="height: 100%;min-height: 100%;overflow: auto" id="appBody">
        <el-main class="app-page-body"><router-view></router-view></el-main>
        <el-footer class="app-footer" :height="footerHeight + 'px'">
          <layout-footer></layout-footer>
        </el-footer>
      </el-container>
    </el-main>
  </el-container>
  <m-back-top body-id="appBody"></m-back-top>
</el-container>

</template>
<script type="text/javascript">
import LayoutHeader from '@/layout/layout-header'
import LayoutSide from '@/layout/layout-side'
import LayoutFooter from '@/layout/layout-footer'
export default {
  name: 'container-main',
  data () {
    return {
      isCollapse: false,
      hideSide: false,
      sideWidth: 200,
      footerHeight: 50,
      headerHeight: 50,
      theme: {theme: {}}
    }
  },
  components: {
    LayoutFooter, LayoutHeader, LayoutSide
  },
  created () {
    this.init()
  },
  methods: {
    handleSideSwitch (val) {
      this.isCollapse = val
      this.sideWidth = val ? 60 : 200
    },
    handleSwitchHideSide () {
      this.hideSide = !this.hideSide
    },
    handleSetTheme (theme) {
      this.theme = theme
    },
    init () {
      if (sessionStorage.user === undefined) {
        // 跳转登录页面
        console.log('没有登录跳转登录页面')
        this.$router.push({name: 'login'})
      } else {
        this.initWebsocket()
      }
    },
    initWebsocket: function () {
      var s = window.location.host.split(':')[0] // + ':' + window.location.port
      // console.log('sessionStorage.user:', sessionStorage.user)
      var user = JSON.parse(sessionStorage.user)
      var ws = new WebSocket('ws://' + s + ':8085/hdGate/ws/user:' + user.username)
      // const ws = new SockJS('http://localhost:8085/hdGate/webSocketServer')
      ws.onopen = () => {
        // Web Socket 已连接上，使用 send() 方法发送数据
        console.log(user.username + '主页的websocket链接成功...')
      }
      ws.onerror = function () {
        console.log('主页的websocket链接失败')
      }
      ws.onmessage = evt => {
        var msg = evt.data
        // console.log('kkkkkkkkkkkkkkkkkkkkkkkkkkkkk', msg)
        if (msg === 'kickout') {
          this.$alert('该用户已在其他地方登录', '消息', {
            confirmButtonText: '确定',
            callback: action => {
              this.$router.push({name: 'login'})
              location.reload()
            }
          })
        } else {
          console.log('提示信息：', msg)
          var index = msg.indexOf(',')
          var beginStr = msg.substring(0, index)
          var endStr = msg.substring(index)
          console.log(beginStr, endStr)
          endStr = endStr.replace(/"0"/g, '异常').replace(/0/g, '异常')
          endStr = endStr.replace(/"1"/g, '正常').replace(/1/g, '正常')
          // 将英文转换成中文
          endStr = endStr.replace(/"laneCode"/g, '车道').replace(/"truckNoCamera"/g, '车牌相机').replace(/"leftCdiCamera"/g, '左侧验残相机')
            .replace(/"rightCdiCamera"/g, '右侧验残相机').replace(/"topCdiCamera"/g, '顶部验残相机')
            .replace(/"frontLeftOcrCamera"/g, '前方左侧箱号相机').replace(/"afterLeftOcrCamera"/g, '后方左侧箱号相机')
            .replace(/"frontRightOcrCamera"/g, '前方右侧箱号相机').replace(/"afterRightOcrCamera"/g, '后方右侧箱号相机')
            .replace(/"backCamera"/g, '后相机').replace(/"truckScales"/g, '地磅').replace(/"plc"/g, 'PLC').replace(/"print"/g, '打印机')
          var alterMsg = beginStr + endStr.replace(/异常/g, '<span style="color: red;">异常</span>')
          console.log('更改后msg:', alterMsg)
          this.$alert(alterMsg, '提示', {
            dangerouslyUseHTMLString: true, // 启用页面代码
            confirmButtonText: '确定'
          })
        }
      }
      ws.onclose = function () {
        // 关闭 websocket
        console.log('equipment连接已关闭...')
      }
    }
  }
}
</script>
<style type="text/css">
  .app-container{
    margin: 0 auto;
    position: absolute;
    width: 100%;
    height: 100%;
  }
  .app-container .app-header{
    padding: 0;
    background: #16AAD8;
    overflow: visible;
    z-index: 100;
  }
  .app-container .app-side{
    width: 200px;
    transition: all 0.5s ease;
  }
  .app-container .app-body{
    background: #ECF0F5;
    padding: 0;
  }
  .app-container .app-footer{
    background: #fff;
    border-top: solid 1px rgba(48, 54, 62, 0.14);
  }
  .app-container .app-page-body{
    overflow: visible;
    padding: 0px;
  }
  .app-header-logo-box{
    padding: 15px;
    box-sizing: border-box;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .app-header-logo-box .header-logo{
    height: 42px;
    margin-top: -17px;
    margin-right: 5px;
    vertical-align: middle;
  }
  .app-header-logo-box .header-logo-text{
    font-size: 20px;
    font-weight: bold;
    opacity: 1;
  }

  /*mini-side*/
  .app-container.mini-side .app-side{
    overflow: visible;
  }
  .app-container.mini-side .app-side .el-menu--collapse{
    width: 60px;
  }
  .app-container.mini-side .header-logo{
    margin-left: -10px;
  }
  .app-container.mini-side .header-logo-text{
    opacity: 0;
  }
  /*hide-side*/
  .app-container.hide-side .app-side{
    display: none;
  }
</style>
