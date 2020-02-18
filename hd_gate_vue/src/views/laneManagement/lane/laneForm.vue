<template>
  <el-dialog title="编辑" :visible.sync="visible" width='60%'>
    <el-form label-width="100px" :model="laneForm">
      <el-row>
        <el-col :span="8" :xs="16">
          <el-form-item label="车道编号" prop="laneCode"
                        :rules="[ { required: true, message: '此处必填',  trigger: 'blur' },{ max: 200, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="laneForm.laneCode" v-bind:readonly="flag=='update'"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" :xs="8">
          <el-form-item label="车道名称" prop="laneName"
                        :rules="[ { required: true, message: '此处必填',  trigger: 'blur' },{ max: 200, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="laneForm.laneName"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" :xs="8">
          <el-form-item label="进出方向" prop="laneDirection"
                        :rules="[{ max: 255, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="laneForm.laneDirection"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" :xs="16">
          <el-form-item label="socket的ip" prop="apiServiceUrl"
                        :rules="[{ max: 255, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="laneForm.laneIp"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" :xs="16">
          <el-form-item label="socket的端口" prop="apiServiceUrl"
                        :rules="[{ max: 255, message: '输入非法', trigger: 'blur' }]">
            <el-input type="text" v-model="laneForm.lanePort"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" :xs="16">
          <el-form-item label="是否启用" prop="showFlag"
                        :rules="[{ max: 255, message: '输入非法', trigger: 'blur' }]">
            <el-switch v-model="laneForm.showFlag === 'Y'" active-text="启用" inactive-text="禁用" @change="switchShowFlagChange(laneForm.showFlag)">
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
  import bus from '@/assets/eventBus'
  export default {
    name: 'laneForm',
    components: {
    },
    props: {
      laneData: {}
    },
    data: function () {
      return {
        laneForm: {},
        visible: false,
        loading: false,
        flag: 'init',
        parentDept: {}
      }
    },
    methods: {
      show (biaozhi, row) { // 换为本表主键
        this.visible = true // 不管新增还是修改，都设置true显示窗体
        console.log('新增或修改标志:' + biaozhi)
        if (biaozhi === 'add') {
          this.flag = 'add'
          this.laneForm.showFlag = 'Y'
        } else {
          this.flag = 'update'
          this.laneForm = JSON.parse(JSON.stringify(row)) // 避免双向绑定
          this.laneForm.oldLaneCode = this.laneForm.laneCode
          this.laneForm.oldLaneDirection = this.laneForm.laneDirection
          this.laneForm.oldLaneIp = this.laneForm.laneIp
          this.laneForm.oldLanePort = this.laneForm.lanePort
        }
      },
      submit () {
        // 提交后台，进行数据更新
        this.$axios.get('/hdGate/laneManagement/insertOrUpdateLane',
          {params: { laneForm: this.laneForm }}).then(data => {
          console.log('insertOrUpdateLane ', data)
          this.visible = false // 关闭窗口
          bus.$emit('initAllGateLanes', data)
        }, response => {
          console.log('insertOrUpdateLane error')
        })
      },
      cancle () {
        this.visible = false
        this.laneForm = {}
        this.flag = 'init'
      },
      switchShowFlagChange (showFlag) {
        console.log('switchShowFlagChange', showFlag)
        this.laneForm.showFlag = 'Y'
        if (showFlag === 'Y') {
          this.laneForm.showFlag = 'N'
        }
      }
    }
  }
</script>

