<template>
  <el-container>
    <el-aside width="40%">
      <roleTable ref='roleTable' @selRoleId="selRoleId"></roleTable>
    </el-aside>
    <el-main>
      <el-tabs v-model="activeName">
        <el-tab-pane label="菜单" name="menu">
          <role-menu-tree :roleId="roleId" :checkedKeys="checkedKeys" v-if="activeName=='menu'&&roleId"></role-menu-tree>
        </el-tab-pane>
        <!--<el-tab-pane label="按钮" name="btn">
          <btnRole :roleId="roleId" v-if="activeName=='btn'&&roleId"></btnRole>
        </el-tab-pane>-->
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
  import roleTable from './roleTable'
  import roleMenuTree from './roleMenuTree'
  import btnRole from './btnRole'
  import bus from '@/assets/eventBus'
  export default {
    components: {
      roleTable, roleMenuTree, btnRole
    },
    data () {
      return {
        roleId: null,
        activeName: 'menu',
        checkedKeys: []
      }
    },
    methods: {
      selRoleId: function (roleId) {
        this.roleId = roleId
      },
      queryMenusByRoleId () {
        var that = this
        this.checkedKeys = []
        this.$axios.get('/hdGate/roleController/queryMenusByRoleId', {params: { roleId: this.roleId }}).then(datas => {
          that.checkedKeys = datas
          // console.log('sssssssssssss  this.checkedKeys', that.checkedKeys)
        }, response => {
          console.log('error')
        })
      }
    },
    mounted () {
      var that = this
      bus.$on('roleTablesClickedRow', function (msg) {
        that.roleId = msg.roleId
        // 查询
        that.queryMenusByRoleId()
      })
    }
  }
</script>
