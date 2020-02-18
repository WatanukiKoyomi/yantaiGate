<template>
  <el-container>
    <el-main>
      <div :style="{width: '100%'}">
        <el-table :data="tableData" stripe style="width: 100%" v-loading.body="listLoading" border>
          <el-table-column align="center" prop="roleName" label="角色名"></el-table-column>
          <el-table-column align="center" prop="description" label="描述"></el-table-column>
          <el-table-column align="center" prop="isAdmin" label="是否管理员"></el-table-column>
          <el-table-column align="center" label="授权" fixed="right">
            <template slot-scope="scope">
              <el-switch v-if="hasBtnPerm('resource_update')"
                v-model="scope.row.roleIsChecked"
                active-color="#13ce66"
                @change="switchChange(scope.row)"
                inactive-color="#ff4949">
              </el-switch>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-main>
  </el-container>
</template>
<script>
  import bus from '@/assets/eventBus'
  const roleTableData = []
  export default {
    components: {},
    data () {
      return {
        listLoading: false,
        tableData: roleTableData,
        userId: ''
      }
    },
    mounted () {
      var that = this
      bus.$on('userRole_userRowClicked', function (msg) {
        that.queryAllRoles(msg.userId)
        that.userId = msg.userId
      })
    },
    methods: {
      queryAllRoles (userId) {
        var datas = roleTableData.filter(function (i) { return true })
        datas.forEach(function (element) {
          roleTableData.pop(element)
        })
        var oraData = []
        var that = this
        this.$axios.get('/hdGate/roleController/queryRolesByUser', {params: { 'userId': userId }}).then(datas => {
          oraData = datas
          oraData.forEach(function (element) { // 初始化全部为false
            element.roleIsChecked = false
          })
          // console.log('已经拥有的：', datas)
          // 在查询用户角色完成之后在查询所有角色，避免用户赋权时，查询结构时间差导致赋权失败。
          that.$axios.get('/hdGate/roleController/queryAllRoles').then(datas => {
            const items = datas
            items.forEach(function (oldData) {
              var flag = false
              oraData.forEach(function (element) {
                if (oldData.roleId === element.roleId) {
                  oldData.roleIsChecked = true
                  flag = true
                }
              })
              if (!flag) {
                oldData.roleIsChecked = false
              }
              roleTableData.push(oldData)
            })
            // console.log('fuzhihou:', items, roleTableData)
          }, response => {
            console.log('queryAllRoles error')
          })
        }, response => {
          console.log('queryRolesByUser error')
        })
        // console.log('finalRoleTableData:', roleTableData)
      },
      switchChange (row) {
        // console.log(row) // roleIsChecked:true新增，false删除
        this.$axios.get('/hdGate/roleController/addOrDeleteUserRole', {params: { 'userId': this.userId, 'roleId': row.roleId, 'flag': row.roleIsChecked }}).then(response => {
          this.$message({ message: '用户角色授权更改成功', type: 'success' })
        }, response => {
          console.log('error')
        })
      }
    }
  }
</script>
