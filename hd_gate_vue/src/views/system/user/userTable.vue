<template>
  <el-container>
    <el-main>
      <div class="filter-container">
        <el-row>
          <el-col :span="4"><h2 style="margin-top: 0px">用户管理:</h2></el-col>
          <el-col :span="20">
            <el-button class="filter-item" @click="addUser" type="primary" v-if="hasBtnPerm('user_add')">新增</el-button><!--{{$t('commons.add')}}-->
            <el-button class="filter-item" @click="deleteUser" type="danger" v-if="hasBtnPerm('user_delete_dept')">删除</el-button><!--删除-->
          </el-col>
        </el-row>
      </div>
      <div :style="{width: '100%'}">
        <el-table :data="tableData" stripe style="width: 100%" v-loading.body="listLoading" border>
          <el-table-column align="center" prop="userName" label="名称"></el-table-column>
          <el-table-column align="center" prop="orgnId" label="部门id"></el-table-column>
          <el-table-column align="center" prop="account" label="登录账号"></el-table-column>
          <el-table-column align="center" label="操作" fixed="right" style="width: 100px;">
            <template slot-scope="scope">
              <el-row>
                <el-col :lg="12">
                  <el-button type="primary" @click='updateUser(scope.$index, scope.row)' size="small" icon="el-icon-edit" v-if="hasBtnPerm('user_update')">修改</el-button><!--修改-->
                </el-col>
                <el-col :lg="12">
                  <el-button type="warning" @click='deleteUser(scope.$index, scope.row)' size="small" icon="el-icon-delete" v-if="hasBtnPerm('user_delete')">删除</el-button><!--删除-->
                </el-col>
              </el-row>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-main>
    <user-form ref="userForm"></user-form>
  </el-container>
</template>
<script>
  import bus from '@/assets/eventBus'
  import userForm from './userForm.vue'
  const tableData = []
  export default {
    components: {
      userForm },
    data () {
      return {
        listLoading: false,
        tableData: tableData,
        flag: false,
        parentDept: {deptId: ''} // 单击左侧列表后用来存放父级部门信息
      }
    },
    mounted () {
      var that = this
      bus.$on('usersClickedDeptId', function (msg) {
        // bus.$off('clickedDeptId')
        // 调用方法，在查询获得数据后先清空原本数据在赋值新的数据
        that.queryUsersByDeptId(msg.deptId)
        this.listLoading = false
        that.flag = true
        that.parentDept = msg
      })
    },
    methods: {
      addUser () {
        if (this.flag) { // 说明选中了左侧列表树，弹出新增框，进行新增操作
          this.$refs['userForm'].show('add', this.parentDept)
        } else {
          this.$message({
            type: 'error',
            message: '请选择部门！'
          })
        }
      },
      updateUser (index, row) {
        this.$refs['userForm'].show('update', row)
      },
      deleteUser (index, row) {
        if (this.flag) { // 说明选中了左侧列表树,可以进行删除操作
          var flag = 'dept'
          var content = this.parentDept.label + '”部门下的所有用户信息'
          if (index >= 0) { // 根据用户删除的
            flag = 'user'
            content = row.userName + '”该用户'
          }
          this.$confirm('此操作将永久删除“' + content + '”, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            console.log('ssssssssssssssss', row, this.parentDept)
            if (row === undefined) { // account 传空字符串
              this.$axios.get('/hdGate/userController/deleteUser', {params: { 'flag': flag, 'account': '', 'parentDeptId': this.parentDept.deptId }}).then(response => {
                console.log('delete dept success')
                location.reload() // 刷新页面
              }, response => {
                console.log('删除部门失败')
              })
            } else { // 不为undefined
              if (row.account === 'admin') {
                // 该用户无法删除
                this.$message('该用户无法删除')
              } else {
                this.$axios.get('/hdGate/userController/deleteUser', {params: { 'flag': flag, 'account': row.account, 'parentDeptId': this.parentDept.deptId }}).then(response => {
                  console.log('delete dept success')
                  location.reload() // 刷新页面
                }, response => {
                  console.log('删除部门失败')
                })
              }
            }
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          })
        } else {
          this.$message({
            type: 'error',
            message: '请选择部门！'
          })
        }
      },
      queryUsersByDeptId (deptId) {
        // console.log('传递的参数:' + deptId)
        this.$axios.get('/hdGate/userController/queryUsersByDeptId', {params: { deptId: deptId }}).then(data => {
          // begin 先清空数据
          var datas = tableData.filter(function (i) { return true })
          datas.forEach(function (element) {
            tableData.pop(element)
          })
          // end
          const items = data
          items.forEach(function (element) {
            element.edit = false
            tableData.push(element)
          })
        }, response => {
          console.log('error')
        })
      }
    }
  }
</script>
