<template>

<v-menu class="side-menu"
  :collapse="collapse"
  :default-active="defaultActive"
  :menus="menus"
  router
  :background-color="theme.backgroundColor"
  :text-color="theme.textColor"
  :active-text-color="theme.activeTextColor"
  ></v-menu>


</template>
<script type="text/javascript">
  import VMenu from '@/components/vmenu'
  import { findMenuTree } from '@/components/moudules/menu'
  export default {
    props: {
      collapse: Boolean,
      theme: Object
    },
    components: {
      VMenu
    },
    data () {
      return {
        menus: [{id: '', label: '', path: '', icon: 'fa fa-dashboard'}],
        defaultActive: 'home',
        test: 'asdfasdf'
      }
    },
    watch: {
      $route () {
        this.setCurrentRoute()
      }
    },
    methods: {
      setCurrentRoute () {
        // console.log(this.$route)
        this.defaultActive = this.$route.name
      }
    },
    created () {
      this.setCurrentRoute()
      var that = this
      findMenuTree().then(function (data) {
        console.log('获取的树形列表数据：', data)
        that.menus = data
      })
    }
  }
</script>
<style type="text/css">
  .el-menu.side-menu{
    border-right: none;
  }
  .el-menu .fa{
    font-size: 18px;
    margin-right: 7px;
    display: inline-block;
    width: 23px;
    text-align: center;
  }
  .el-menu.side-menu .el-menu-item.is-active {
    color: #409EFF;
    border-right: solid 2px #36c1fa;
    background: #eef3f5;
  }
</style>
