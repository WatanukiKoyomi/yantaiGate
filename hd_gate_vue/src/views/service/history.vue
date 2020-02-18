<template>
  <el-container>
    <el-main>
      <el-row>
        <el-card class="box-card">
          <div style="">
            <el-row>
              <el-col :span="6" class="col-class">
                <el-input placeholder="请输入车道编号" v-model="laneCode" clearable></el-input>
              </el-col>
              <el-col :span="6" class="col-class">
                <el-input placeholder="请输入车牌号" v-model="truckNo" clearable></el-input>
              </el-col>
              <el-col :span="6" class="col-class">
                <el-input placeholder="请输入箱号" v-model="cntrNo" clearable></el-input>
              </el-col>
              <el-col :span="6" class="col-class">
                <el-date-picker
                  v-model="beginAndEndTime"
                  type="datetimerange"
                  align="left"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  :default-time="['00:00:00', '23:59:59']">
                </el-date-picker>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-row>
      <!--统计内容-->
      <!--搜索结果框-->
      <el-row class="panel-group" :gutter="40">
        <el-col :lg="24" style="margin-top: 10px">
          <el-card class="box-card">
            <div>
              <el-row>
                <el-col :lg="4">
                    车次:<el-input style="width: 120px" v-model="truckCounts"></el-input><!--车次-->
                </el-col>
                <el-col :lg="5">
                    车牌识别率:<el-input style="width: 120px" v-model="Math.round(truckPercent*100)/100"></el-input>%<!--车牌识别率-->
                </el-col>
                <el-col :lg="4">
                    箱数:<el-input style="width: 120px" v-model="cntrCounts"></el-input><!--总箱数-->
                </el-col>
                <el-col :lg="6">
                    箱号识别率:<el-input style="width: 120px" v-model="Math.round(cntrPercent*100)/100"></el-input>%<!--箱号识别率-->
                </el-col>
                <el-col :lg="5">
                  平均过车时间:<el-input style="width: 120px" v-model="averageTime"></el-input>秒<!--平均过车时间-->
                </el-col>
              </el-row>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <!--表格内容-->
      <el-row style="margin-top: 10px">
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column align="center" label="车道编号" width="80px">
            <template slot-scope="scope">
              {{scope.row.generalInfo.laneCode}}
            </template>
          </el-table-column>
          <!--车牌号-->
          <el-table-column align="center" label="车牌号" width="125px">
            <template slot-scope="scope">
              <span v-if="scope.row.ocrCarPlate">
                <span v-if="scope.row.carPlate">
                  <el-tooltip v-if="scope.row.carPlate.ocrPlate && scope.row.carPlate.ocrPlate != scope.row.ocrCarPlate.ocrPlate" effect="dark"
                              :content="'原内容：'+scope.row.ocrCarPlate.ocrPlate" placement="top-start">
                    <el-button style="border: 1px solid red">{{scope.row.carPlate.ocrPlate}}</el-button>
                  </el-tooltip>
                  <span v-else>{{scope.row.carPlate.ocrPlate}}{{scope.row.ocrCarPlate.ocrPlate}}</span>
                </span>
                <span v-else>{{scope.row.ocrCarPlate.ocrPlate}}</span>
              </span>
            </template>
          </el-table-column>
          <!--前箱号-->
          <el-table-column align="center" label="前箱号" width="180px">
            <template slot-scope="scope">
              <span v-if="scope.row.ocrFrontContainer">
                <span v-if="scope.row.frontContainer">
                  <el-tooltip v-if="scope.row.frontContainer.ocrContainerNo && scope.row.frontContainer.ocrContainerNo != scope.row.ocrFrontContainer.ocrContainerNo" effect="dark"
                              :content="'原内容：'+scope.row.ocrFrontContainer.ocrContainerNo" placement="top-start">
                    <el-button style="border: 1px solid red">{{scope.row.frontContainer.ocrContainerNo}}</el-button>
                  </el-tooltip>
                  <span v-else>{{scope.row.ocrFrontContainer.ocrContainerNo}}</span>
                </span>
                <span v-else>{{scope.row.ocrFrontContainer.ocrContainerNo}}</span>
              </span>
            </template>
          </el-table-column>
          <!--前箱型-->
          <el-table-column align="center" label="前箱型" width="80px">
            <template slot-scope="scope">
              <span v-if="scope.row.ocrFrontContainer">{{scope.row.ocrFrontContainer.ocrContainerISO}}</span>
            </template>
          </el-table-column>
          <!--前箱空重-->
          <el-table-column align="center" label="前箱空重" width="80px">
            <template slot-scope="scope">
              <span v-if="scope.row.ocrFrontContainer">{{scope.row.ocrFrontContainer.efid}}</span>
            </template>
          </el-table-column>
          <!--后箱号-->
          <el-table-column align="center" label="后箱号" width="180px">
            <template slot-scope="scope">
              <span v-if="scope.row.ocrAfterContainer">
                <span v-if="scope.row.afterContainer">
                  <el-tooltip v-if="scope.row.afterContainer.ocrContainerNo && scope.row.afterContainer.ocrContainerNo != scope.row.ocrAfterContainer.ocrContainerNo" effect="dark"
                              :content="'原内容：'+scope.row.ocrAfterContainer.ocrContainerNo" placement="top-start">
                    <el-button style="border: 1px solid red">{{scope.row.afterContainer.ocrContainerNo}}</el-button>
                  </el-tooltip>
                  <span v-else>{{scope.row.ocrAfterContainer.ocrContainerNo}}</span>
                </span>
                <span v-else>{{scope.row.ocrAfterContainer.ocrContainerNo}}</span>
              </span>
            </template>
          </el-table-column>
          <!--后箱型-->
          <el-table-column align="center" label="后箱型" width="80px">
            <template slot-scope="scope">
              <span v-if="scope.row.ocrAfterContainer">{{scope.row.ocrAfterContainer.ocrContainerISO}}</span>
            </template>
          </el-table-column>
          <!--后箱空重-->
          <el-table-column align="center" label="后箱空重" width="80px">
            <template slot-scope="scope">
              <span v-if="scope.row.ocrAfterContainer">{{scope.row.ocrAfterContainer.efid}}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" prop="arriveTime" label="开始时间" width="100px"></el-table-column>
          <el-table-column align="center" prop="enterTime" label="结束时间" width="100px"></el-table-column>
          <el-table-column align="center" prop="msg" label="反馈消息"></el-table-column>
          <el-table-column align="center" label="操作"><!--可以查看图片-->
            <template slot-scope="scope">
              <el-button type="primary" @click="showHistoryDialog(scope.row)" size="small" icon="fa fa-search">图片</el-button><!--@click='showHistoryImg(scope.row)'-->
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          class="pull-left clearfix"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="pageNo"
          :page-sizes="pageSizesList"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalDataNumber">
        </el-pagination>
      </el-row>
      <!--弹出框-->
      <el-dialog title="历史记录" :visible.sync="dialogVisible" width="85%" top="5vh">
        <el-carousel indicator-position="outside" :autoplay="false" :loop="false" :height="screenHeight*2/3 + 30 + 'px'">
          <el-carousel-item v-for="(item, index) in showImgs" :key="index">
            <viewer :images="showImgs" style="height: 100%;">
              <div class="img-box" style="text-align:center;height: 100%" >
                <img :src="item" style="height:100%;width: 80%;">
              </div>
            </viewer>
          </el-carousel-item>
        </el-carousel>
      </el-dialog>
    </el-main>
  </el-container>
</template>

<script>
  import {parseTime} from '@/utils'
  export default {
    components: {
    },
    data () {
      return {
        truckCounts: '', // 车次
        truckPercent: '', // 车牌识别率
        cntrCounts: '', // 箱数
        averageTime: '', // 平均过车时间
        cntrPercent: '', // 箱号识别率
        screenHeight: document.documentElement.clientHeight,
        clickedData: '',
        showImgs: [],
        laneCode: '',
        truckNo: '',
        cntrNo: '',
        pageNo: 1,
        pageSize: 20,
        pageSizesList: [20, 50, 100, 500],
        tableData: [],
        totalDataNumber: 100,
        dialogVisible: false,
        beginAndEndTime: [parseTime(new Date(new Date(new Date().toLocaleDateString()).getTime())), parseTime(new Date())]
      }
    },
    mounted () {
      var that = this
      this.searchBusinessDataHistory()
      // 窗口大小变更重新计算高度
      window.onresize = () => {
        return (() => {
          that.screenHeight = document.documentElement.clientHeight
        })()
      }
    },
    watch: {
      beginAndEndTime (val, oldVal) {
        if (val === null) { // 避免清空时间后，格式错误，重新赋值时间
          this.beginAndEndTime = [parseTime(new Date(new Date(new Date().toLocaleDateString()).getTime())), parseTime(new Date())]
        } else {
          this.pageNo = 1 // 重新查询默认第一页
          this.searchBusinessDataHistory()
        }
      },
      laneCode (val, oldVal) {
        this.pageNo = 1 // 重新查询默认第一页
        this.searchBusinessDataHistory()
      },
      truckNo (val, oldVal) {
        this.pageNo = 1 // 重新查询默认第一页
        this.searchBusinessDataHistory()
      },
      cntrNo (val, oldVal) {
        this.pageNo = 1 // 重新查询默认第一页
        this.searchBusinessDataHistory()
      }
    },
    methods: {
      // 改变每页显示数量
      handleSizeChange (val) {
        var pageSize = `${val}`
        this.pageNo = 1 // 改变每页显示数量，重置当前页为第一页
        this.pageSize = parseInt(pageSize)
        this.searchBusinessDataHistory()
      },
      // 改变页码
      handleCurrentChange (val) {
        this.searchBusinessDataHistory()
      },
      // 查询业务历史数据
      searchBusinessDataHistory () {
        this.$axios.get('/hdGate/history/queryHistoryDataByParam',
          {params: {laneCode: this.laneCode, truckNo: this.truckNo, cntrNo: this.cntrNo, beginTime: parseTime(this.beginAndEndTime[0]), endTime: parseTime(this.beginAndEndTime[1]), pageNum: this.pageNo, pageSize: this.pageSize}}).then(data => {
          console.log('queryHistoryDataByParam', data)
          this.tableData = data.list
          this.totalDataNumber = data.total
          this.truckCounts = data.list.length // 赋值车次
          var truckFailedCounts = 0
          var cntrCounts = 0
          var cntrFailedCounts = 0
          var averagaTime = 0
          data.list.forEach(function (oneData) {
            // console.log(oneData.carPlate.ocrPlate, oneData.ocrCarPlate.ocrPlate)
            cntrCounts = cntrCounts + parseInt(oneData.generalInfo.cntrSize)
            // 有作业结束时间的才计算平均时间
            if (oneData.enterTime !== null && oneData.enterTime !== '' && oneData.enterTime !== undefined) {
              averagaTime = new Date(oneData.enterTime) - new Date(oneData.arriveTime)
            }
            // 车牌号oneData.carPlate.ocrPlate 不为空 不想等 说明识别错误
            if (oneData.carPlate.ocrPlate !== null && oneData.carPlate.ocrPlate !== '' && oneData.carPlate.ocrPlate !== oneData.ocrCarPlate.ocrPlate) {
              truckFailedCounts++
            }
            // 前箱
            if (oneData.frontContainer.ocrContainerNo !== null && oneData.frontContainer.ocrContainerNo !== '' && oneData.frontContainer.ocrContainerNo !== oneData.ocrFrontContainer.ocrContainerNo) {
              cntrFailedCounts++
            }
            // 后箱
            if (oneData.afterContainer.ocrContainerNo !== null && oneData.afterContainer.ocrContainerNo !== '' && oneData.afterContainer.ocrContainerNo !== oneData.ocrAfterContainer.ocrContainerNo) {
              cntrFailedCounts++
            }
          })
          // console.log('失败车牌号：', truckFailedCounts, '成功车牌号：', this.truckCounts - truckFailedCounts, '总箱数：', cntrCounts, '失败箱数：', cntrFailedCounts)
          // console.log('车牌识别率：', (this.truckCounts - truckFailedCounts) * 100 / this.truckCounts)
          // console.log('箱号识别率：', (cntrCounts - cntrFailedCounts) * 100 / cntrCounts)
          // console.log('平均时间：', averagaTime / 1000)
          this.cntrCounts = cntrCounts // 总箱数赋值
          if (this.truckCounts === 0) {
            this.truckPercent = 0 // 车牌识别率赋值
          } else {
            this.truckPercent = (this.truckCounts - truckFailedCounts) * 100 / this.truckCounts // 车牌识别率赋值
          }
          if (cntrCounts === 0) {
            this.cntrPercent = 0 // 箱号识别率赋值
          } else {
            this.cntrPercent = (cntrCounts - cntrFailedCounts) * 100 / cntrCounts // 箱号识别率赋值
          }
          this.averageTime = averagaTime / 1000
        }, response => {
          console.log('queryHistoryDataByParam error')
        })
      },
      // 打开历史纪录详情dialog
      showHistoryDialog (data) {
        var that = this
        this.dialogVisible = true
        this.showImgs = []
        console.log('showHistoryDialog data:', data)
        this.clickedData = data
        // begin 图片路径处理
        var path = data.ftpImages.imagePath
        if (path.indexOf('ftp://') >= 0) {
          path = path.substring(6)
        }
        var index = path.indexOf('/')
        path = path.substring(index + 1)
        console.log('图片地址：', path)
        // end 图片路径处理
        data.ftpImages.imageName.split(',').forEach(function (imgName) {
          that.showImgs.push(path + '/' + imgName)
        })
        console.log('显示历史纪录该条记录详情', data)
      }
    }
  }
</script>
<style rel="stylesheet/scss" scoped>
  .img-box{
    height:100%;
  }
  .col-class{
    padding: 10px;
  }
</style>
