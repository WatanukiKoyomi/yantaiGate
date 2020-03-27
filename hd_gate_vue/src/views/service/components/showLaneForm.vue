<template>
  <el-dialog title="车道" :visible.sync="visible" width='60%'>
    <el-form label-width="100px" :model="laneForm">
      <el-row>
        <el-col :span="8" :xs="16">
          <el-checkbox-group v-model="laneCheckedList">
            <el-checkbox :label="lane" v-for="(lane,index) in laneShowList" :key="index"></el-checkbox>
          </el-checkbox-group>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel">取消</el-button><!--取消-->
      <el-button type="primary" @click="submit" :loading="loading">确定</el-button><!--确定-->
    </div>
  </el-dialog>

</template>

<script>
  import bus from '@/assets/eventBus'
  export default {
    name: 'showLaneForm',
    props: {
      laneData: {}
    },
    data: function () {
      return {
        laneForm: {},
        laneShowList: ['车道1', '车道2'], // 从数据库获取的完整的车道
        laneCheckedList: ['车道1', '车道2'], // 从数据库获取的需要展示的车道
        visible: false,
        loading: false
      }
    },
    methods: {
      show (laneShowList, laneCheckedList) { // 换为本表主键
        this.visible = true // 不管新增还是修改，都设置true显示窗体
        this.laneShowList = laneShowList
        this.laneCheckedList = laneCheckedList
      },
      submit () {
        let username = JSON.parse(sessionStorage.user).username
        // 提交后台，进行数据更新
        let laneCheckedListParam ;
        if(Object.prototype.toString.call(this.laneCheckedList) == '[object Array]'){
          laneCheckedListParam = this.laneCheckedList.sort().toString();
        }else{
          laneCheckedListParam = this.laneCheckedList.toString();
        }
        this.$axios.get('/hdGate/laneManagement/changeUserCheckedShowLane',
          {params: { 'user': username, 'laneCheckedList': laneCheckedListParam }}).then(data => {
          console.log('updateUserCheckedShowLane success')
          this.visible = false
          bus.$emit('changeUserCheckedShowLane', laneCheckedListParam)
        }, response => {
          console.log('changeUserCheckedShowLane error')
        })
      },
      cancel () {
        this.visible = false
        this.laneForm = {}
      }
    }
  }
</script>

