<template>
  <el-dialog title="编辑" :visible.sync="visible" width='60%'>
    <el-form label-width="100px" :model="deptForm">
      <el-row>
        <el-col :span="8" :xs="16">
          <el-form-item label="名称" prop="label"
                        :rules="[ { required: true, message: '此处必填',  trigger: 'blur' },{ max: 200, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="deptForm.label"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" :xs="16">
          <el-form-item label="英文名" prop="deptEnName"
                        :rules="[{ max: 255, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="deptForm.deptEnName"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" :xs="16">
          <el-form-item label="简称" prop="deptShortName"
                        :rules="[{ max: 100, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="deptForm.deptShortName"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" :xs="16">
          <el-form-item label="描述" prop="comments"
                        :rules="[{ max: 255, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="deptForm.comments"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="8" :xs="16">
          <el-form-item label="排序" prop="sortNo"
                        :rules="[{ max: 100, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="deptForm.sortNo"></el-input>
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
    name: 'deptForm',
    props: {
      deptData: {}
    },
    data: function () {
      return {
        deptForm: {},
        visible: false,
        loading: false,
        flag: 'init',
        parentDept: {}
      }
    },
    methods: {
      show (biaozhi, row) { // 换为本表主键
        this.visible = true // 不管新增还是修改，都设置true显示窗体
        console.log('biaozhi:' + biaozhi, row)
        if (biaozhi === 'add') {
          this.parentDept = row
          // 新增
          this.flag = 'add'
        } else {
          this.flag = 'update'
          this.deptForm = JSON.parse(JSON.stringify(row)) // 避免双向绑定
          this.deptForm.oldDeptName = this.deptForm.label
          this.deptForm.oldDeptEnName = this.deptForm.deptEnName
          this.deptForm.oldDeptShortName = this.deptForm.deptShortName
          this.deptForm.oldDeptSortNo = this.deptForm.deptSortNo
        }
      },
      submit () {
        // this.parentDept.children = null
        console.log('sssssssssss', this.parentDept, this.flag, this.deptForm)
        this.parentDept.children = null
        this.deptForm.children = null
        // 提交到后台
        this.$axios.get('/hdGate/deptController/addOrUpdateDept', {params: { 'flag': this.flag, 'deptForm': this.deptForm, 'parentDept': this.parentDept }}).then(response => {
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
        this.deptForm = {}
        this.flag = 'init'
      }
    }
  }
</script>

