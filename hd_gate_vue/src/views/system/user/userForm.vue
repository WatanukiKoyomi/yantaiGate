<template>
  <el-dialog title="编辑" :visible.sync="visible" width='60%'>
    <el-form label-width="100px" :model="userForm">
      <el-row>
        <el-col :span="8" :xs="16">
          <el-form-item label="名称" prop="userName"
                        :rules="[ { required: true, message: '此处必填',  trigger: 'blur' },{ max: 200, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="userForm.userName"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" :xs="16">
          <el-form-item label="账号" prop="account"
                        :rules="[{ required: true, message: '此处必填',  trigger: 'blur' },{ max: 255, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="userForm.account" v-bind:readonly="flag=='update'"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" :xs="16">
          <el-form-item label="密码" prop="orgnId"
                        :rules="[{ max: 100, message: '输入非法', trigger: 'blur' }]">
            <el-input type="password" v-model="userForm.userPwd"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" :xs="16">
          <el-form-item label="语言" prop="comments"
                        :rules="[{ max: 255, message: '输入非法', trigger: 'blur' }]">
            <!--<el-input type="text" v-model="userForm.language"></el-input>-->
            <el-radio-group v-model="userForm.language">
              <el-radio-button label="en">英语</el-radio-button>
              <el-radio-button label="zh">汉语</el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-col>

        <el-col :span="8" :xs="16">
          <el-form-item label="皮肤" prop="sortNo"
                        :rules="[{ max: 100, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="userForm.userSkin"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancle">取消</el-button> <!--取消-->
      <el-button type="primary" @click="submit" :loading="loading">确定</el-button><!--确定-->
    </div>
  </el-dialog>

</template>

<script>
  export default {
    name: 'userForm',
    props: {
    },
    data: function () {
      return {
        userForm: { language: 'zh', account: '' },
        visible: false,
        loading: false,
        flag: 'init',
        parentDept: {}
      }
    },
    watch: {
    },
    methods: {
      show (biaozhi, row) { // 换为本表主键
        this.visible = true // 不管新增还是修改，都设置true显示窗体
        console.log('biaozhi:' + biaozhi)
        if (biaozhi === 'add') {
          this.parentDept = row
          // 新增
          this.flag = 'add'
          this.userForm.language = 'zh'
        } else {
          this.flag = 'update'
          this.visible = true
          this.userForm = JSON.parse(JSON.stringify(row)) // 避免双向绑定
          if (this.userForm.language === null || this.userForm.language === '' || this.userForm.language === undefined) {
            this.userForm.language = 'zh'
          }
        }
      },
      submit () {
        // 提交到后台
        this.userForm.account = this.userForm.account.toUpperCase() // 2019.7.31 新增用户大写
        this.$axios.get('/hdGate/userController/addOrUpdateUser', {params: { 'flag': this.flag, 'userForm': this.userForm, 'parentDeptId': this.parentDept.deptId }}).then(response => {
          console.log('submit success')
        }, response => {
          console.log('error')
        })
        this.visible = false
        this.userForm = {}
        location.reload()
      },
      cancle () {
        this.visible = false
        this.userForm = {}
        this.flag = 'init'
      }
    }
  }
</script>
<style>

</style>

