<template>
  <el-container>
    <el-main>
      <div class="filter-container">
        <el-row>
          <el-col :span="4"><h2 style="margin-top: 0px">部门管理:</h2></el-col>
          <el-col :span="20">
            <el-button class="filter-item" @click="addDept" type="primary" v-if="hasBtnPerm('dept_add')">新增</el-button><!--新增-->
            <el-button class="filter-item" @click="deleteDept" type="danger" v-if="hasBtnPerm('dept_delete')">删除</el-button><!--删除-->
            <el-button class="filter-item" @click="updateDeptName" type="warning" v-if="hasBtnPerm('dept_update')">修改</el-button><!--修改-->
          </el-col>
        </el-row>
      </div>
      <div :style="{width: '100%'}">
        <el-table :data="tableData" stripe style="width: 100%" v-loading.body="listLoading" border>
          <el-table-column align="center" prop="label" label="名称">
            <template slot-scope="scope">
              <template v-if="scope.row.edit">
                <el-input class="edit-input" size="small" v-model="scope.row.label"></el-input>
              </template>
              <span v-else>{{ scope.row.label }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" prop="deptEnName" label="英文名称"></el-table-column>
          <el-table-column align="center" prop="deptShortName" label="名称简写"></el-table-column>
          <el-table-column align="center" prop="sortNo" label="排列序号"></el-table-column>
          <el-table-column align="center" label="操作" fixed="right">
            <template slot-scope="scope">
              <el-button type="primary" @click='updateDept(scope.$index, scope.row)' size="small" icon="el-icon-edit" v-if="hasBtnPerm('dept_update')">修改</el-button><!--修改-->
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-main>
    <dept-form ref="deptForm"></dept-form>
  </el-container>
</template>
<script>
  import bus from '@/assets/eventBus'
  import deptForm from './deptForm.vue'
  // import { parseTime } from '@/utils'
  const tableData = [{ deptId: '', label: '', children: [] }]
  export default {
    components: {
      deptForm },
    data () {
      return {
        listLoading: false,
        tableData: tableData,
        flag: false,
        parentDept: {} // 单击左侧列表后用来存放父级部门信息
      }
    },
    mounted () {
      var that = this
      bus.$on('clickedDeptId', function (msg) {
        // bus.$off('clickedDeptId')
        // begin 新建数组，循环把tableData中的全部值pop掉
        var datas = tableData.filter(function (i) { return true })
        datas.forEach(function (element) {
          tableData.pop(element)
        })
        // end 新建数组，循环把tableData中的全部值pop掉
        if (msg.children !== null) {
          msg.children.forEach(function (element) {
            element.edit = false
            tableData.push(element)
          })
        }
        // console.log(tableData)
        that.listLoading = false
        that.flag = true
        that.parentDept = msg
      })
    },
    methods: {
      addDept () {
        if (this.flag) { // 说明选中了左侧列表树，弹出新增框，进行新增操作
          this.$refs['deptForm'].show('add', this.parentDept)
        } else {
          this.$message({
            type: 'error',
            message: '请选择部门！'
          })
        }
      },
      updateDept (index, row) {
        console.log('updateDept', index)
        console.log('updateDept', row)
        this.$refs['deptForm'].show('update', row)
      },
      updateDeptName (index, row) {
        if (this.flag) {
          console.log('index:', index)
          console.log(this.parentDept)
          this.$refs['deptForm'].show('update', this.parentDept)
          // this.$refs['deptNameForm'].show('update', th3is.parentDept)
        } else {
          this.$message({
            type: 'error',
            message: '请选择部门！'
          })
        }
      },
      deleteDept (index, row) {
        if (this.flag) { // 说明选中了左侧列表树,可以进行删除操作
          // console.log(this.parentDept.deptId)
          if (this.parentDept.deptId === '1') {
            this.$message({
              type: 'warning',
              message: '该部门为根部门，不能删除，如有需要请选择修改'
            })
          } else {
            this.$confirm('此操作将永久删除“' + this.parentDept.label + '”部门及其部门下的所有子部门,及其部门下的所有用户, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              var p = JSON.stringify(this.parentDept)
              console.log('aaa:', this.parentDept, p)
              this.parentDept.children = null
              this.$axios.get('/hdGate/deptController/deleteDept', {params: { 'parentDept': this.parentDept }}).then(response => {
                console.log('delete dept success')
              }, response => {
                console.log('删除部门失败')
              })
              location.reload() // 刷新页面
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消删除'
              })
            })
          }
        } else {
          this.$message({
            type: 'error',
            message: '请选择部门！'
          })
        }
      }
    }
  }
</script>
