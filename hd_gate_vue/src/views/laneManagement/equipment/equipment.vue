<template>
  <el-container>
    <el-main>
      <div>
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column align="center" prop="laneCode" label="车道编号"></el-table-column>
          <!--车道名称-->
          <el-table-column align="center" prop="laneDirection" label="进出方向"></el-table-column>
          <!--进出方向-->
          <el-table-column align="center" label="中间层">
            <template slot-scope="scope">
              <span v-if="scope.row.data.pc === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="前相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.frontCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="后相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.backCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="左相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.leftCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="右相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.rightCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="左验残相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.leftCdiCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="右验残相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.rightCdiCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="上验残相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.topCdiCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="车牌相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.truckNoCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="车架相机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.truckCamera === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="led">
            <template slot-scope="scope">
              <span v-if="scope.row.data.led === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="plc">
            <template slot-scope="scope">
              <span v-if="scope.row.data.plc === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="打印机">
            <template slot-scope="scope">
              <span v-if="scope.row.data.printer === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="对讲终端">
            <template slot-scope="scope">
              <span v-if="scope.row.data.intercom === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="串口服务器">
            <template slot-scope="scope">
              <span v-if="scope.row.data.comServer === '0'" style="color: red;">异常</span>
              <span v-else style="color: green;">正常</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="识别工作站">
            <template slot-scope="scope">
              <span v-if="scope.row.data.workstation === '0'" style="color: red;">异常</span>
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
  components: {},
  data() {
    return {
      emptyData: {
        pc: "",
        frontCamera: "",
        backCamera: "",
        leftCamera: "",
        rightCamera: "",
        leftCdiCamera: "",
        rightCdiCamera: "",
        topCdiCamera: "",
        truckNoCamera: "",
        truckCamera: "",
        led: "",
        plc: "",
        printer: "",
        intercom: "",
        comServer: "",
        workstation: ""
      },
      tableData: [
        {
          laneCode: "",
          laneDirection: "",
          data: {
            pc: "",
            frontCamera: "",
            backCamera: "",
            leftCamera: "",
            rightCamera: "",
            leftCdiCamera: "",
            rightCdiCamera: "",
            topCdiCamera: "",
            truckNoCamera: "",
            truckCamera: "",
            led: "",
            plc: "",
            printer: "",
            intercom: "",
            comServer: "",
            workstation: ""
          }
        }
      ],
      listLoading: true,
      websockets: []
    };
  },
  created() {
    this.initAllGateLanesStatus();
    var that = this;
    window.setInterval(function() {
      that.initAllGateLanesStatus();
    }, 3 * 1000); // 3秒查询一次
  },
  methods: {
    // 获取车道设备状态
    initAllGateLanesStatus: function() {
      var that = this;
      this.$axios.get("/hdGate/laneManagement/queryShowGateLanes").then(
        data => {
          console.log("queryShowGateLanes", data);
          data.map(v => {
            // 每个车道增加状态data字段
            v.data = that.emptyData;
            return v;
          });
          data.forEach(function(laneData) {
            that.$axios
              .get("/hdGate/monitor/queryLaneEquipmentStatus", {
                params: { laneCode: laneData.laneCode }
              })
              .then(
                d => {
                  // console.log('queryLaneEquipmentStatus', d)
                  if (d === "" || d === null || d === undefined) {
                    laneData.data = that.emptyData;
                  } else {
                    laneData.data = d;
                  }
                },
                response => {
                  console.log("queryLaneEquipmentStatus error");
                }
              );
          });
          console.log('compare:'+that.compareObj(that.tableData,data,true));
          if(!that.compareObj(that.tableData,data,true)){
            that.tableData = data;
          }
        },
        response => {
          console.log("queryShowGateLanes error");
        }
      );
    },
    isArray: function(object) {
      return object && typeof object == "object" && object.constructor == Array;
    },
    compareObj: function(objA, objB, flag) {
      var that = this;
      for (var key in objA) {
        if (!flag)
          //跳出整个循环
          break;
        if (!objB.hasOwnProperty(key)) {
          flag = false;
          break;
        }
        if (!that.isArray(objA[key])) {
          //子级不是数组时,比较属性值
          if (objB[key] != objA[key]) {
            flag = false;
            break;
          }
        } else {
          if (!that.isArray(objB[key])) {
            flag = false;
            break;
          }
          var oA = objA[key],
            oB = objB[key];
          if (oA.length != oB.length) {
            flag = false;
            break;
          }
          for (var k in oA) {
            if (!flag)
              //这里跳出循环是为了不让递归继续
              break;
            flag = compareObj(oA[k], oB[k], flag);
          }
        }
      }
      return flag;
    }
  }
};
</script>
