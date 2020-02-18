<template>
  <el-dialog title="编辑" :visible.sync="visible" width='60%'>
    <el-form label-width="100px" :model="roleForm">
      <el-row>
        <el-col :span="8" :xs="16">
          <el-form-item label="名称" prop="roleName"
                        :rules="[ { required: true, message: '此处必填',  trigger: 'blur' },{ max: 200, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="roleForm.roleName"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" :xs="16">
          <el-form-item label="描述" prop="description"
                        :rules="[{ max: 255, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="roleForm.description"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" :xs="16">
          <el-form-item label="管理员角色" prop="isAdmin"
                        :rules="[{ max: 255, message: '输入非法', trigger: 'blur' }]">
            <!--<el-input type="text" v-model="roleForm.isAdmin"></el-input>-->
            <el-radio-group v-model="roleForm.isAdmin">
              <el-radio-button label="1">是</el-radio-button>
              <el-radio-button label="0">否</el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="submit" :loading="loading"> 确 定</el-button>
    </div>
  </el-dialog>

</template>

<script>
  export default {
    name: 'roleForm',
    props: {
    },
    data: function () {
      return {
        roleForm: {isAdmin: '0'},
        visible: false,
        loading: false
      }
    },
    methods: {
      show (biaozhi, row) { // 换为本表主键
        this.visible = true // 不管新增还是修改，都设置true显示窗体
        if (biaozhi === 'add') {
          // 新增
          this.flag = 'add'
        } else {
          this.visible = true
          this.roleForm = JSON.parse(JSON.stringify(row)) // 避免双向绑定
          this.roleForm.oldUserName = this.roleForm.userName
          this.roleForm.oldAccount = this.roleForm.account
        }
      },
      submit () {
        // 提交到后台
        this.$axios.get('/hdGate/roleController/addOrUpdateRole', {params: { 'flag': this.flag, 'roleForm': this.roleForm }}).then(datas => {
          console.log('submit role success')
        }, response => {
          console.log('error')
        })
        this.visible = false
        this.roleForm = {}
        location.reload()
      },
      cancle () {
        this.visible = false
        this.roleForm = {}
        this.flag = 'init'
      }
    }
  }
</script>

