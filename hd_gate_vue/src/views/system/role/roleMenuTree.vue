<template>
  <el-row>
    <el-col :span="10">
      <div>
        <el-button class="filter-item" @click="roleMenuSubmit" type="success" v-if="hasBtnPerm('role_save')">保存</el-button>
        <el-tree @node-click="showBtns"
          :data="menuDatas"
          show-checkbox
          node-key="id"
          :default-checked-keys="checkedKeys"
          default-expand-all
          ref='roleMenuTree'
          :highlight-current="false"
          :expand-on-click-node="false">
        </el-tree>
      </div>
    </el-col>
    <el-col :span="14">
      按钮
      <el-table :data="btnsList" stripe style="width: 100%" border>
        <el-table-column align="center" prop="label" label="名称"></el-table-column>
        <el-table-column align="center" prop="describe" label="描述"></el-table-column>
        <el-table-column align="center" label="是否赋权" fixed="right" v-if="hasBtnPerm('role_btn_change')">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.flag"
              active-color="#13ce66"
              @change="switchChange(scope.row)"
              inactive-color="#ff4949">
            </el-switch>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>
</template>
<script>

  export default {
    name: 'rolePermTree',
    components: {
    },
    data () {
      return {
        btnsList: [{label: '', describe: '', flag: false}],
        menuDatas: [{ id: '', label: '', children: [] }]
      }
    },
    props: ['roleId', 'checkedKeys'],
    methods: {
      searchMenu () {
        // 要把时间格式化下再传
        this.$axios.get('/hdGate/sys/findAllMenusTree').then(datas => {
          this.menuDatas = datas
        }, response => {
          console.log('error')
        })
      },
      roleMenuSubmit () {
        var fullKeys = this.$refs.roleMenuTree.getCheckedKeys() // 修改了源码element-ui.common.js 20206行，使该方法返回了全部选中节点，包括半选的父节点 if (node.checked||node.indeterminate) {
        // 提交到后台，存入数据库，刷新页面
        this.$axios.get('/hdGate/sys/updateRoleMenus', {params: { 'permIds': fullKeys.toString(), 'roleId': this.roleId }}).then(response => {
          this.$message({
            type: 'success',
            message: '菜单信息设置成功,请重新登录获取最新权限'
          })
          location.reload()
        }, response => {
          console.log('error')
        })
      },
      showBtns (data) {
        // 查到该页面存在的按钮
        var that = this
        var menuId = data.id
        that.$axios.get('/hdGate/sys/findAllBtnsByMenuId', {params: { roleId: this.roleId, menuId: menuId }}).then(allBtns => {
          allBtns.map(v => {
            that.$set(v, 'flag', false)
          })
          that.$axios.get('/hdGate/sys/findBtnsByRoleAndMenuId', {params: { roleId: this.roleId, menuId: menuId }}).then(datas => {
            datas.forEach(function (element) {
              var btnId = element.id
              allBtns.forEach(function (btn) {
                if (btn.id === btnId) {
                  btn.flag = true
                }
              })
            })
            that.btnsList = allBtns
          }, response => {
            console.log('findBtnsByRoleAndMenuId error')
          })
        }, response => {
          console.log('findAllBtnsByMenuId error')
        })
      },
      switchChange (row) {
        // 更改该角色的按钮权限
        console.log(row)
        this.$axios.get('/hdGate/sys/changeRoleBtn', {params: { 'roleId': this.roleId, 'btnId': row.id, 'flag': row.flag }}).then(response => {
          this.$message({ message: '按钮授权更改成功', type: 'success' })
        }, response => {
          console.log('error')
        })
      }
    },
    mounted () {
      this.searchMenu()
    },
    watch: {
      roleId (val, oldVal) { // roleId变化了就说明已经选择了其他角色，重新加载右侧列表
        this.$refs.roleMenuTree.setCheckedKeys(this.checkedKeys)
        this.searchMenu()
      }
    }
  }
</script>
