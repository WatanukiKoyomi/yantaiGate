<template>
  <el-dialog title="编辑" :visible.sync="visible" width='60%'>
    <el-form label-width="100px" :model="resourceForm">
      <el-row>
        <el-col :span="12" :xs="16">
          <el-form-item label="名称:" prop="label"
                        :rules="[ { required: true, message: '此处必填',  trigger: 'blur' },{ max: 200, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="resourceForm.label"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" :xs="16">
          <el-form-item label="图标:" prop="icon"
                        :rules="[{ max: 100, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="resourceForm.icon"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="20" :xs="16">
          <el-form-item label="描述:" prop="describe"
                        :rules="[{ max: 255, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="resourceForm.describe"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="7" :xs="16">
          <el-form-item label="对应名称:" prop="url"
                        :rules="[{ max: 255, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="resourceForm.name"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" :xs="16">
          <el-form-item label="资源类型:">
            <el-radio-group v-model="resourceForm.resourceType">
              <el-radio-button label="m">菜单</el-radio-button>
              <el-radio-button label="b">按钮</el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="7" :xs="16">
          <el-form-item label="排序:" prop="sortNo"
                        :rules="[{ max: 100, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="resourceForm.sortNo"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="8" :xs="16">
          <el-form-item label="状态:">
            <el-switch
              v-model="state"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="启用"
              inactive-text="禁用">
            </el-switch>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancle">取消</el-button><!--取消-->
      <el-button type="primary" @click="submit" :loading="loading">确定</el-button><!--确定-->
    </div>
  </el-dialog>

</template>

<script>
  export default {
    name: 'resourceForm',
    props: {
    },
    data: function () {
      return {
        resourceForm: {resourceType: 'm'},
        state: true,
        type: 'm',
        visible: false,
        loading: false,
        flag: 'init'
      }
    },
    methods: {
      show (biaozhi, row) { // 换为本表主键
        this.visible = true // 不管新增还是修改，都设置true显示窗体
        // console.log('biaozhi:' + biaozhi)
        if (biaozhi === 'add') {
          // 新增
          this.flag = 'add'
        } else {
          this.flag = 'update'
          this.resourceForm = JSON.parse(JSON.stringify(row)) // 避免双向绑定
          this.resourceForm.oldUserName = this.resourceForm.userName
          this.resourceForm.oldAccount = this.resourceForm.account
          if (this.resourceForm.state === 'open') {
            this.state = true
          } else {
            this.state = false
          }
        }
      },
      submit () {
        // 提交到后台
        this.$axios.get('/hdGate/resourceController/addOrUpdateResource',
          {params: { 'resourceForm': this.resourceForm, 'state': this.state }}).then(datas => {
          console.log('submit success')
        }, response => {
          console.log('error')
        })
        this.visible = false
        this.deptForm = {}
        location.reload()
      },
      cancle () {
        this.visible = false
        this.resourceForm = {}
        this.flag = 'init'
      }
    }
  }
</script>

