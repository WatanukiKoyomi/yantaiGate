<!--车道监控页面-->
<template>
  <div class="dashboard-editor-container">
    <el-row class="panel-group" :gutter="40">
      <el-col :span="24" style="margin-bottom: 10px;">
        <el-button type="primary" @click="showMonitorLaneDialog" icon="el-icon-document">选择查看车道</el-button>
        <show-lane-form ref="showLaneForm"></show-lane-form>
      </el-col>
      <el-col v-if="laneCheckedList.toString().indexOf(lane.laneCode)>=0" :xs="24" :sm="12" :md="12" :lg="8" class="box-panel-col" v-for="(lane,index) in laneDataList" :key="index">
        <div>
          <!--头信息-->
          <div class='box-panel box-panel-height'>
            <div class="box-panel-icon-wrapper icon-box" style="padding-left: 10px;">
              <svg-icon icon-class="truck1" class-name="box-panel-icon" />
            </div>
            <!--车道编号-->
            <div class="box-panel-description">
              <div class="box-panel-text"><span>{{lane.laneCode}}</span></div><!-- {{lane.laneName}}:{{lane.laneCode}} -->
            </div>
            <div class="box-panel-icon-image icon-box" v-if="lane.data">
              <img v-if="lane.data.ftpImages.imagePath" src="lane.data.ftpImages.imagePath + '/Plate.jpg?random=' + Math.random()" style="width: 124px;height:46px;" />
            </div>
          </div>
          <!--中间业务数据-->
          <div class='box-panel' style="border-top: 1px dashed #97a8be; padding-top: 10px;padding-right: 15px;">
            <div style="padding-left: 0px;padding-right: 10px;">
              <el-col :span="24" style="padding: 10px 0px;">
                <!--车牌-->
                <el-col :xs="10" :sm="10" :md="12" :lg="10" style="padding-bottom: 5px;"><div class='box-font'>车牌:</div></el-col>
                <el-col :xs="14" :sm="14" :md="12" :lg="14" style="padding-bottom: 5px;">
                  <div class='box-font'>
                    <span v-if="lane.data.ocrCarPlate">
                      <span v-if="lane.data.carPlate">
                        <span v-if="lane.data.carPlate.ocrPlate">{{lane.data.carPlate.ocrPlate}}&nbsp;</span>
                        <span v-else>{{lane.data.ocrCarPlate.ocrPlate}}&nbsp;</span>
                      </span>
                      <span v-else>{{lane.data.ocrCarPlate.ocrPlate}}&nbsp;</span>
                    </span>
                    <span v-else>&nbsp;</span>
                  </div>
                </el-col>
                <!--地磅-->
                <el-col :xs="6" :sm="11" :md="12" :lg="10" style="padding-bottom: 5px;"><div class='box-font'>地磅数:</div></el-col><!--地磅数-->
                <el-col :xs="6" :sm="13" :md="12" :lg="14" style="padding-bottom: 5px;">
                  <div class='box-font'>
                    <span v-if="lane.data.generalInfo">
                      <span v-if="lane.data.generalInfo.weight">{{lane.data.generalInfo.weight}}&nbsp;</span>
                      <span v-else>&nbsp;</span>
                    </span>
                    <span v-else>&nbsp;</span>
                  </div>
                </el-col>
                <!--前集装箱-->
                <el-col :xs="8" :sm="24" :md="12" :lg="10" style="padding-bottom: 5px;"><div class='box-font'>前集装箱:</div></el-col><!--前集装箱号-->
                <el-col :xs="12" :sm="24" :md="12" :lg="14" style="padding-bottom: 5px;">
                  <div class='box-font'>
                    <span v-if="lane.data.ocrFrontContainer">
                      <span v-if="lane.data.frontContainer">
                        <span v-if="lane.data.frontContainer.ocrContainerNo">{{lane.data.frontContainer.ocrContainerNo}} / {{lane.data.frontContainer.ocrContainerISO}} / {{lane.data.frontContainer.efId}}&nbsp;</span>
                         <span v-else>{{lane.data.ocrFrontContainer.ocrContainerNo}} / {{lane.data.ocrFrontContainer.ocrContainerISO}} / {{lane.data.ocrFrontContainer.efid}}&nbsp;</span>
                      </span>
                      <span v-else>{{lane.data.ocrFrontContainer.ocrContainerNo}} / {{lane.data.ocrFrontContainer.ocrContainerISO}} / {{lane.data.ocrFrontContainer.efid}}&nbsp;</span>
                      </span>
                    <span v-else>&nbsp;</span>
                  </div>
                </el-col>
                <!--后集装箱-->
                <el-col :xs="8" :sm="24" :md="12" :lg="10" style="padding-bottom: 5px;"><div class='box-font'>后集装箱:</div></el-col><!--后集装箱号-->
                <el-col :xs="12" :sm="24" :md="12" :lg="14" style="padding-bottom: 5px;">
                  <div class='box-font'>
                    <span v-if="lane.data.ocrAfterContainer">
                      <span v-if="lane.data.afterContainer">
                        <span v-if="lane.data.afterContainer.ocrContainerNo">{{lane.data.afterContainer.ocrContainerNo}} / {{lane.data.afterContainer.ocrContainerISO}} / {{lane.data.afterContainer.efId}}&nbsp;</span>
                         <span v-else>{{lane.data.ocrAfterContainer.ocrContainerNo}} / {{lane.data.ocrAfterContainer.ocrContainerISO}} / {{lane.data.ocrAfterContainer.efid}}&nbsp;</span>
                      </span>
                      <span v-else>{{lane.data.ocrAfterContainer.ocrContainerNo}} / {{lane.data.ocrAfterContainer.ocrContainerISO}} / {{lane.data.ocrAfterContainer.efid}}&nbsp;</span>
                      </span>
                    <span v-else>&nbsp;</span>
                  </div>
                </el-col>
                <!--开始时间-->
                <el-col :xs="6" :sm="11" :md="12" :lg="10" style="padding-bottom: 5px;"><div class='box-font'>开始时间:</div></el-col>
                <el-col :xs="6" :sm="13" :md="12" :lg="14" style="padding-bottom: 5px;">
                  <div class='box-font'>
                    <span v-if="lane.data && lane.data.arriveTime">{{lane.data.arriveTime}}&nbsp;</span>
                    <span v-else>&nbsp;</span>
                  </div>
                </el-col>
                <!--结束时间-->
                <el-col :xs="6" :sm="11" :md="12" :lg="10" style="padding-bottom: 5px;"><div class='box-font'>结束时间:</div></el-col>
                <el-col :xs="6" :sm="13" :md="12" :lg="14" style="padding-bottom: 5px;">
                  <div class='box-font'>
                    <span v-if="lane.data && lane.data.enterTime">{{lane.data.enterTime}}&nbsp;</span>
                    <span v-else>&nbsp;</span>
                  </div>
                </el-col>
                <!--消息反馈-->
                <el-col :xs="8" :sm="12" :md="8" :lg="10" style="padding-bottom: 5px;"><div class='box-font'>消息反馈:</div></el-col>
                <el-col :xs="12" :sm="12" :md="12" :lg="14" style="padding-bottom: 5px;">
                  <div class='box-font'>
                    <textarea :rows="3" v-model="lane.data.msg" readonly />
                  </div>
                </el-col>
              </el-col>
            </div>
          </div>
          <!--下方按钮-->
          <div id="app" class='box-panel' style="border-top: 1px dashed #97a8be; padding-top: 10px;margint-right: 15px;">
            <!--详情-->
            <el-col :xs="8" :sm="8" :md="8" :lg="8" class="text-center" style="padding: 0;margin-bottom: 10px;">
              <el-tooltip content="跳转车道作业界面查看详细信息" placement="bottom" effect="light">
                <el-button name="monitorDetailsBtnName" type="primary" @click="taskClick(index)" icon="el-icon-document">详情</el-button>
              </el-tooltip>
            </el-col>
            <!--小票-->
            <el-col :xs="8" :sm="8" :md="8" :lg="8" class="text-center" style="padding: 0;margin-bottom: 10px;">
              <el-tooltip content="打印小票" placement="bottom" effect="light"><!--打印小票-->
                <el-button name="monitorPrintBtnName" type="primary" @click="receiptClick(index)" style="width: 85px;" icon="el-icon-document">小票</el-button>
              </el-tooltip>
            </el-col>
            <!--抬杆-->
            <el-col :xs="8" :sm="8" :md="8" :lg="8" class="text-center" style="padding: 0;margin-bottom: 10px;">
              <el-tooltip content="抬杆" placement="bottom" effect="light"><!--抬杆-->
                <el-button name="monitorRodBtnName" type="primary" @click="rodClick(index)" style="width: 85px;" icon="el-icon-document">抬杆</el-button>
              </el-tooltip>
            </el-col>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import bus from '@/assets/eventBus'
  import showLaneForm from './components/showLaneForm'
  export default {
    components: {
      showLaneForm
    },
    data () {
      return {
        mathRandom: Math.random(),
        emptyData: { laneCode: '', msg: '', arriveTime: '', enterTime: '', generalInfo: { laneCode: '', weight: '' }, ocrCarPlate: { ocrPlate: '', plateColor: '' }, ocrFrontContainer: { ocrContainerNo: '', ocrContainerDirection: '', ocrContainerISO: '', efId: '' }, ocrAfterContainer: { ocrContainerNo: '', ocrContainerDirection: '', ocrContainerISO: '', efId: '' }, ftpImages: { folder: '', imageName: '' } },
        laneDataList: [],
        laneCheckedList: []
      }
    },
    mounted () {
      var that = this
      // 监听重新选中车道后的车道信息
      bus.$on('changeUserCheckedShowLane', function (msg) {
        that.initLaneChecked()
      })
      this.initLaneChecked()
    },
    watch: {
    },
    methods: {
      // 弹出窗口选择要查看的车道
      showMonitorLaneDialog () {
        this.$refs['showLaneForm'].show(this.laneShowList, this.laneCheckedList)
      },
      // 初始化选中的车道信息，并查询数据
      initLaneChecked () {
        var that = this
        var initLane = []
        var username = JSON.parse(sessionStorage.user).username
        this.$axios.get('/hdGate/laneManagement/queryShowGateLanes').then(data => {
          var s = window.location.host.split(':')[0]
          data.forEach(function (element) {
            // begin websocket
            var ws = new WebSocket('ws://' + s + ':8085/hdGate/ws/monitor:' + username + element.laneCode)
            ws.onopen = () => {
              console.log('monitor:' + username + element.laneCode + '链接webSocket成功...')
            }
            ws.onerror = function () {
              console.log('monitor:' + username + element.laneCode + '链接webSocket失败')
            }
            ws.onmessage = evt => {
              console.log('数据已接收...' + evt.data)
              var data = JSON.parse(evt.data)
              that.laneDataList.forEach(function (laneData) { // 循环现有数据，重新赋值对应车道数据
                if (laneData.laneCode === data.generalInfo.laneCode) {
                  laneData.data = data
                }
              })
            }
            ws.onclose = function () {
              // 关闭 websocket
              console.log('monitor:' + username + element.laneCode + '链接已关闭...')
            }
            // end websocket
            initLane.push(element.laneCode)
            // begin 查询车道对应最新数据（初始化）
            that.laneDataList = []
            that.$axios.get('/hdGate/monitor/queryLatestDataByLane',
              {params: { 'laneCode': element.laneCode }}).then(data => {
              if (data === null || data === '') {
                that.laneDataList.push({laneCode: element.laneCode, laneName: element.laneName, laneDirection: element.laneDirection, data: that.emptyData}) // 查询到最新数据赋值到对应车道上
              } else {
                that.laneDataList.push({laneCode: element.laneCode, laneName: element.laneName, laneDirection: element.laneDirection, data: data}) // 查询到最新数据赋值到对应车道上
              }
            }, response => {
              console.log('queryLatestDataByLane error')
            })
            // end 查询车道对应最新数据
          })
          that.laneShowList = initLane
          // begin 查询用户选中查看的车道
          that.$axios.get('/hdGate/laneManagement/queryGateLaneByUser',
            {params: { 'user': username }}).then(data => {
            if (data === null || data === '') {
              that.laneCheckedList = initLane
            } else {
              that.laneCheckedList = data
            }
          }, response => {
            console.log('queryGateLaneByUser error')
          })
          // end 查询用户选中查看的车道
        }, response => {
          console.log('queryShowGateLanes error')
        })
      },
      // 详情按钮
      taskClick (index) {
        this.$message({ message: '车道作业页面跳转', type: 'success' })
        this.$router.push({ path: '/task' })
      },
      // 打印小票按钮
      receiptClick (index) {
        this.$confirm('确认“' + this.laneDataList[index].laneCode + '”车道重新补打小票的操作？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var laneData = this.laneDataList[index]
          this.$axios.get('/hdGate/monitor/receiptClick', {params: {laneCode: laneData.laneCode}}).then(response => {
            this.$message({ message: '补打小票成功' })
          }, response => {
            console.log('receipt error')
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作'
          })
        })
      },
      // 抬杆按钮
      rodClick (index) {
        this.$confirm('确认“' + this.laneDataList[index].laneCode + '”车道抬杆操作？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var laneData = this.laneDataList[index]
          this.$axios.get('/hdGate/monitor/rodClick', {params: {laneCode: laneData.laneCode}}).then(response => {
            this.$message({ message: '抬杆操作成功' })
          }, response => {
            console.log('rod error')
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作'
          })
        })
      }
    }
  }
</script>
<style rel="stylesheet/scss" scoped>
  .dashboard-editor-container {
    padding: 1px 22px;
    background-color: rgb(240, 242, 245);
  }
  .panel-group {
    margin-top: 18px;
  }
  .box-panel-col{
    margin-bottom: 32px;
  }
  .box-font{
    font-size: 14px;
  }
  .box-panel {
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);
  }
  .box-panel-height {
    height: 48px;
  }
  .box-panel-icon-wrapper {
    float: left;
    margin: 0px 0 0 0px;
    padding: 16px 0;
    transition: all 0.38s ease-out;
    border-radius: 6px;
  }
  .box-panel-icon-image {
    float: right;
    margin: 1px 10px;
    padding: 0;
    transition: all 0.38s ease-out;
    border-radius: 6px;
  }
  .box-panel-icon {
    float: left;
    font-size: 20px;
  }
  .box-panel-description {
    float: left;
    font-weight: bold;
    padding: 16px 6px;
    margin-left: 0px;
  }
  .box-panel-text {
    line-height: 18px;
    color: rgba(0, 0, 0, 0.45);
    font-size: 16px;
    margin-bottom: 12px;
  }
</style>
