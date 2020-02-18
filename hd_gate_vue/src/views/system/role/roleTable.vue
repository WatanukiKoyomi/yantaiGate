<template>
  <el-container>
    <el-main>
      <div class="filter-container">
        <el-button class="filter-item" @click="addRole" type="primary" v-if="hasBtnPerm('role_add')">新增</el-button><!--新增-->
        <el-button class="filter-item" @click="deleteRole" type="danger" v-if="hasBtnPerm('role_delete')">删除</el-button><!--删除-->
      </div>
      <div :style="{width: '100%'}">
        <el-table :data="tableData" stripe style="width: 100%" v-loading.body="listLoading" border highlight-current-row @row-click="roleRowClick">
          <el-table-column align="center" prop="roleName" label="名称"></el-table-column>
          <el-table-column align="center" prop="description" label="描述"></el-table-column>
          <el-table-column align="center" prop="isAdmin" label="是否管理员">
            <template slot-scope="scope">
              <span v-if="scope.row.isAdmin === '1'">是</span>
              <span v-else>否</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="操作" fixed="right">
            <template slot-scope="scope">
              <el-button type="primary" @click='updateRole(scope.$index, scope.row)' size="small" icon="el-icon-edit" v-if="hasBtnPerm('role_update')">修改</el-button><!--修改-->
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-main>
    <role-form ref="roleForm"></role-form>
  </el-container>
</template>
<script>
  import roleForm from './roleForm.vue'
  import bus from '@/assets/eventBus'
  const roleTableData = []
  export default {
    components: { roleForm },
    data () {
      return {
        listLoading: false,
        tableData: roleTableData,
        clickedRoleId: '',
        clickedRoleName: ''
      }
    },
    mounted () {
      var that = this
      // 情况原本数据
      var datas = roleTableData.filter(function (i) { return true })
      datas.forEach(function (element) {
        roleTableData.pop(element)
      })
      that.queryAllRoles() // 调用方法查询后台数据
    },
    methods: {
      // 角色表单行单击
      roleRowClick (row, event, column) {
        this.clickedRoleName = row.roleName
        this.clickedRoleId = row.roleId
        bus.$emit('roleTablesClickedRow', row)
      },
      addRole () {
        this.$refs['roleForm'].show('add', {}) // 新增
      },
      updateRole (index, row) {
        if (row.isAdmin !== '1') {
          row.isAdmin = '0'
        }
        this.$refs['roleForm'].show('update', row)
      },
      deleteRole () {
        if (this.clickedRoleId !== undefined) {
          this.$confirm('此操作将永久删除“' + this.clickedRoleName + '”角色, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$axios.get('/hdGate/roleController/deleteRole', {params: { 'roleId': this.clickedRoleId }}).then(response => {
            }, response => {
              console.log('删除角色失败')
            })
            location.reload() // 刷新页面
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          })
        } else {
          this.$message({
            type: 'error',
            message: '请选择角色！'
          })
        }
      },
      queryAllRoles () {
        this.$axios.get('/hdGate/roleController/queryAllRoles').then(data => {
          const items = data
          items.forEach(function (element) {
            element.edit = false
            roleTableData.push(element)
          })
        }, response => {
          console.log('error')
        })
      }
    }
  }
</script>
