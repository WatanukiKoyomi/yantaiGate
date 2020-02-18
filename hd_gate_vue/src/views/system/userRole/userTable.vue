<template>
  <el-container>
    <el-main>
      <div :style="{width: '100%'}">
        <el-table :data="tableData" stripe style="width: 100%" v-loading.body="listLoading" border highlight-current-row @row-click="roleRowClick">
          <el-table-column align="center" prop="userName" label="用户名"></el-table-column>
          <el-table-column align="center" prop="userDescription" label="描述"></el-table-column>
          <el-table-column align="center" prop="account" label="登录账号"></el-table-column>
        </el-table>
      </div>
    </el-main>
  </el-container>
</template>
<script>
  import bus from '@/assets/eventBus'
  const userTableData = []
  export default {
    components: {},
    data () {
      return {
        listLoading: false,
        tableData: userTableData
      }
    },
    mounted () {
      this.queryAllUsers() // 调用方法查询后台数据
    },
    methods: {
      // 角色表单行单击
      roleRowClick (row, event, column) {
        // console.log('roleRowClick:', row)
        bus.$emit('userRole_userRowClicked', row)
      },
      queryAllUsers () {
        this.$axios.get('/hdGate/userController/queryAllUsers').then(datas => {
          datas.forEach(function (element) {
            element.edit = false
            userTableData.push(element)
          })
        }, response => {
          console.log('error')
        })
      }
    }
  }
</script>
