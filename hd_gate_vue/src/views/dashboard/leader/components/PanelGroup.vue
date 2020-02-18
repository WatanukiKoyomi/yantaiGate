<template>
  <el-row style="margin: 20px">
    <!-- 车次 -->
    <el-col :xs="12" :sm="12" :lg="8" style="padding: 0 20px;">
      <div class="card-panel">
        <el-card style="height: 100%">
          <el-row>
            <el-col :xs="12" :sm="12" :lg="12">
              <svg-icon style="height: 100px;width: 45%;" color="green" icon-class="truck"></svg-icon>
            </el-col>
            <el-col :xs="12" :sm="12" :lg="12">
              <div style="text-align: center">
                <span><h3>车次</h3></span>
                <div class="bottom clearfix">
                  {{Math.round(truckNum*100)/100}}（车次/天）
                </div>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </div>
    </el-col>
    <!-- 箱数 -->
    <el-col :xs="12" :sm="12" :lg="8" style="padding: 0 20px;">
      <div class="card-panel">
        <el-card style="height: 100%">
          <el-row>
            <el-col :xs="12" :sm="12" :lg="12">
              <svg-icon style="height: 100px;width: 45%;" color="green" icon-class="cntr"></svg-icon>
            </el-col>
            <el-col :xs="12" :sm="12" :lg="12">
              <div style="text-align: center">
                <span><h3>箱数</h3></span>
                <div class="bottom clearfix">
                  {{Math.round(cntrNum*100)/100}}（箱数/天）
                </div>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </div>
    </el-col>
    <!-- 平均过车时间 -->
    <el-col :xs="12" :sm="12" :lg="8" style="padding: 0 20px;">
      <div class="card-panel">
        <el-card style="height: 100%">
          <el-row>
            <el-col :xs="12" :sm="12" :lg="12">
              <svg-icon style="height: 100px;width: 45%;" color="green" icon-class="xiaolv"></svg-icon>
            </el-col>
            <el-col :xs="12" :sm="12" :lg="12">
              <div style="text-align: center">
                <span><h3>平均过车时间</h3></span>
                <div class="bottom clearfix">
                  {{Math.round(averageTime*100/60)/100}}（分/车次）
                </div>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </div>
    </el-col>
  </el-row>
</template>

<script>
export default {
  components: {
  },
  data () {
    return {
      truckNum: 0,
      cntrNum: 0,
      averageTime: 0
    }
  },
  methods: {
    // 获取作业过的岸桥列表
    queryTitleDataList () {
      var that = this
      this.truckNum = 0
      this.cntrNum = 0
      this.averageTime = 0
      this.$axios.get('/hdGate/history/queryWeeksData').then(data => {
        console.log('queryWeeksData', data)
        data.forEach(function (d) {
          if (d.trucCount !== '' && d.truckCount !== undefined) {
            that.truckNum = that.truckNum + parseInt(d.truckCount)
          }
          if (d.cntrCount !== '' && d.cntrCount !== undefined) {
            that.cntrNum = that.cntrNum + parseInt(d.cntrCount)
          }
        })
        if (data.length === 0) {
          that.truckNum = 0
          that.cntrNum = 0
        } else {
          // 最后重新计算赋值平均值
          that.truckNum = that.truckNum / data.length
          that.cntrNum = that.cntrNum / data.length
        }
      }, response => {
        console.log('queryWeeksData error')
      })
      this.$axios.get('/hdGate/history/queryHistoryDataAverageTime').then(data => {
        console.log('queryHistoryDataAverageTime', data)
        that.averageTime = data
      }, response => {
        console.log('queryWeeksData error')
      })
    }
  },
  created () {
    this.queryTitleDataList()
  }
}
</script>

<style rel="stylesheet/scss" scoped>
  .card-panel {
    height: 100%;
    cursor: pointer;
    font-size: 16px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);
  }
</style>
