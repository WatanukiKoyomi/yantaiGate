<template>
  <el-container>
    <el-main>
      <div class="filter-container">
        <el-button class="filter-item" @click="addResource" type="primary" v-if="hasBtnPerm('resource_add')">新增</el-button><!--新增-->
        <!-- <el-button class="filter-item" type="danger">删除</el-button>-->
      </div>
      <div :style="{width: '100%'}">
        <el-table :data="tableData" stripe style="width: 100%" v-loading.body="listLoading" border>
          <el-table-column fixed width="150px" align="center" prop="label" label="显示名称"></el-table-column>
          <el-table-column width="250px" align="center" prop="describe" label="描述"></el-table-column>
          <el-table-column width="150px" align="center" prop="icon" label="图标"></el-table-column>
          <el-table-column width="100px" align="center" prop="name" label="对应名称"></el-table-column>
          <el-table-column width="80px" align="center" prop="resourceType" label="资源类型">
            <template slot-scope="scope">
              <span v-if="scope.row.resourceType === 'm'">菜单</span>
              <span v-else>按钮</span>
            </template>
          </el-table-column>
          <el-table-column width="80px" align="center" prop="sortNo" label="排序"></el-table-column>
          <el-table-column width="100px" align="center" prop="state" label="使用状态">
            <template  slot-scope="scope">
              <el-switch
                v-model="scope.row.showState"
                active-color="#13ce66"
                inactive-color="#ff4949">
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column align="center" label="操作" fixed="right">
            <template slot-scope="scope">
              <el-button type="primary" @click='updateResources(scope.$index, scope.row)' size="small" icon="el-icon-edit" v-if="hasBtnPerm('resource_update')">修改</el-button><!--修改-->
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-main>
    <resource-form ref="permForm"></resource-form>
  </el-container>
</template>
<script>
  import resourceForm from './resourceForm'
  const tableData = []
  export default {
    components: { resourceForm },
    data () {
      return {
        listLoading: false,
        tableData: tableData,
        flag: false,
        parentPerm: {} // 单击左侧列表后用来存放父级权限信息
      }
    },
    mounted () {
      this.queryAllResources()
    },
    methods: {
      addResource () {
        this.$refs['permForm'].show('add', this.parentDept)
      },
      updateResources (index, row) {
        this.$refs['permForm'].show('update', row)
      },
      queryAllResources () {
        // console.log('传递的参数:' + deptId)
        this.$axios.get('/hdGate/resourceController/queryAllResources').then(data => {
          // begin 先清空数据
          this.tableData = data
          var datas = tableData.filter(function (i) { return true })
          datas.forEach(function (element) {
            tableData.pop(element)
          })
          // end
          const items = data
          items.forEach(function (element) {
            element.edit = false
            if (element.state === 'open') {
              element.showState = true
            } else {
              element.showState = false
            }
            tableData.push(element)
          })
        }, response => {
          console.log('error')
        })
      }
    }
  }
</script>
