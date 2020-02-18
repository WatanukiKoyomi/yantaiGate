<template>
  <el-container>
    <el-main>
      <div>
        <el-table :data="tableData" stripe style="width: 100%" >
          <el-table-column align="center" prop="laneCode" label="车道编号"></el-table-column><!--车道名称-->
          <el-table-column align="center" prop="laneDirection"  label="进出方向"></el-table-column><!--进出方向-->
          <el-table-column align="center" label="工控机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.pc === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="车牌相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.truckNoCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="左侧验残相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.leftCdiCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="右侧验残相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.rightCdiCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="顶部验残相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.topCdiCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="前方左侧箱号相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.frontLeftOcrCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="后方左侧箱号相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.afterLeftOcrCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="前方右侧箱号相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.frontRightOcrCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="后方右侧箱号相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.afterRightOcrCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="后相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.backCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="地磅">
            <template slot-scope="scope">
              <span v-if="scope.row.data.truckScales === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="PLC">
            <template slot-scope="scope">
              <span v-if="scope.row.data.plc === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="打印机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.print === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-main>
  </el-container>
</template>
<script>
  export default {
    components: {
    },
    data () {
      return {
        emptyData: {pc: '', truckNoCamera: '', leftCdiCamera: '', rightCdiCamera: '', topCdiCamera: '', frontLeftOcrCamera: '', afterLeftOcrCamera: '', frontRightOcrCamera: '', afterRightOcrCamera: '', backCamera: '', truckScales: '', plc: '', print: ''},
        tableData: [{laneCode: '', laneDirection: '', data: {pc: '', truckNoCamera: '', leftCdiCamera: '', rightCdiCamera: '', topCdiCamera: '', frontLeftOcrCamera: '', afterLeftOcrCamera: '', frontRightOcrCamera: '', afterRightOcrCamera: '', backCamera: '', truckScales: '', plc: '', print: ''}}],
        listLoading: true,
        websockets: []
      }
    },
    created () {
      this.initAllGateLanesStatus()
      var that = this
      window.setInterval(function () {
        that.initAllGateLanesStatus()
      }, 3 * 1000) // 3秒查询一次
    },
    methods: {
      // 获取车道设备状态
      initAllGateLanesStatus: function () {
        var that = this
        this.$axios.get('/hdGate/laneManagement/queryShowGateLanes').then(data => {
          // console.log('queryShowGateLanes', data)
          data.map(v => { // 每个车道增加状态data字段
            v.data = that.emptyData
            return v
          })
          data.forEach(function (laneData) {
            that.$axios.get('/hdGate/monitor/queryLaneEquipmentStatus', {params: { 'laneCode': laneData.laneCode }}).then(d => {
              // console.log('queryLaneEquipmentStatus', d)
              if (d === '' || d === null || d === undefined) {
                laneData.data = that.emptyData
              } else {
                laneData.data = d
              }
            }, response => {
              console.log('queryLaneEquipmentStatus error')
            })
          })
          that.tableData = data
        }, response => {
          console.log('queryShowGateLanes error')
        })
      }
    }
  }
</script>
