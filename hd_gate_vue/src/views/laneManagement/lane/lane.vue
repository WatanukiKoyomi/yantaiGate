<template>
  <el-container>
    <el-main>
      <div class="filter-container">
        <el-button class="filter-item" @click="addLane" type="primary" >新增</el-button><!--新增-->
      </div>
      <div>
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column align="center" prop="laneCode" label="车道编号" ></el-table-column><!--车道名称-->
          <el-table-column align="center" prop="laneName" label="车道名称" ></el-table-column><!--车道名称-->
          <el-table-column align="center" prop="laneDirection" label="进出方向" ></el-table-column><!--进出方向-->
          <el-table-column align="center" prop="showFlag" label="禁用状态" ><!--禁用状态-->
            <template slot-scope="scope">
              <el-switch v-model="scope.row.showFlag === 'Y'" active-text="启用" inactive-text="禁用" @change="switchShowFlagChange(scope.row)">
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column align="center" label="操作" min-width="180"><!--操作-->
            <template slot-scope="scope">
              <el-button type="success" @click='updateLane(scope.row)' size="small" icon="el-icon-edit">修改</el-button><!--修改-->
              <el-button type="danger" @click='deleteLane(scope.row)' size="small" icon="el-icon-delete">删除</el-button><!--删除-->
              <el-button type="primary" @click='showLightSetDialog(scope.row)' size="small" icon="el-icon-alarm-clock" style="">补光灯设置</el-button><!--补光灯-->
              <el-button type="warning" @click="liftRod(scope.row)" size="small">抬杆</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
          <span>请选择对车道“{{clickedLane.laneCode}}”补光灯进行设置</span>
          <el-row>
            <el-col :span="12">
              开灯时间
              <el-time-select
                v-model="lightOnTime"
                placeholder="请选择开灯时间"
                :picker-options="{start: '00:00',step: '01:00',end: '24:00'}"
              ></el-time-select>
            </el-col>
            <el-col :span="12">
              关灯时间
              <el-time-select v-model="lightOffTime" :picker-options="{start: '00:00',step: '01:00',end: '24:00'}" placeholder="请选择关灯时间"></el-time-select>
            </el-col>
          </el-row>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="lightSet(lightOnTime,lightOffTime,clickedLane.laneCode)">确 定</el-button>
            <el-button type="warning" @click="lightOn(clickedLane)">开 灯</el-button>
            <el-button type="danger" @click="lightOff(clickedLane)">关 灯</el-button>
          </span>
        </el-dialog>
        <lane-form ref="laneForm"></lane-form>
      </div>
    </el-main>
  </el-container>
</template>
<script>
    import bus from '@/assets/eventBus'
    import { parseTime } from '@/utils'
    import laneForm from './laneForm.vue'
    export default {
        components: {
            laneForm
        },
        data () {
            return {
                lightOnTime: '',
                lightOffTime: '',
                clickedLane: '',
                dialogVisible: false, // 用来显示dialog
                tableData: [{ laneCode: '01', laneDirection: '进闸', showFlag: '' }]
            }
        },
        mounted () {
            var that = this;
            this.initGateLanes();
            bus.$on('initAllGateLanes', function (msg) {
                // 监听laneForm，重新获取全部车道信息
                that.initGateLanes()
            })
        },
        methods: {
            // 初始化获取所有车道信息
            initGateLanes: function () {
                this.$axios.get('/hdGate/laneManagement/queryAllGateLanes').then(data => {
                    console.log('queryAllGateLanes', data);
                    this.tableData = data
                }, response => {
                    console.log('error')
                })
            },
            // 车道禁用启用按钮
            switchShowFlagChange (row) {
                console.log('switchShowFlagChange', row);
                var flag = 'Y';
                var msg = '启用';
                if (row.showFlag === 'Y') {
                    flag = 'N';
                    msg = '禁用'
                }
                this.$confirm('确认' + msg + '“' + row.laneCode + '”车道？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios
                        .get('/hdGate/laneManagement/changeGateLaneShowFlag', {params: { 'laneCode': row.laneCode, 'showFlag': flag }})
                        .then(data => {
                            console.log(msg + '“' + row.laneCode + '“成功', data, data === true);
                            if (data) {
                                row.showFlag = flag;
                                this.$message({
                                    type: 'success',
                                    message: msg + '“' + row.laneCode + '“成功!'
                                })
                            } else {
                                console.log(msg + '“' + row.laneCode + '“失败!');
                                this.$message({
                                    type: 'warning',
                                    message: msg + '“' + row.laneCode + '“失败!'
                                })
                            }
                        }, response => {
                            console.log(msg + '“' + row.laneCode + '“失败');
                            this.$message({
                                type: 'warning',
                                message: msg + '“' + row.laneCode + '“失败'
                            })
                        })
                    // location.reload() // 刷新页面
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消操作'
                    })
                })
            },
            // 显示补光灯设置窗口
            showLightSetDialog (data) {
                this.clickedLane = data;
                this.dialogVisible = true
            },
            lightSet (lightOnTime,lightOffTime,laneCode) {
                const that = this;
                this.$axios
                    .get('/hdGate/laneManagement/lightSet',
                        {params: { 'lightOnTime': lightOnTime, 'lightOffTime': lightOffTime, 'laneCode': laneCode}})
                    .then(data => {
                        if(data){
                            that.dialogVisible = false;
                            this.$message({
                                type: 'success',
                                message: laneCode+'补光灯时间设置成功'
                            })
                        } else {
                            this.$message({
                                type: 'warning',
                                message: laneCode+'补光灯时间设置失败'
                            })
                        }
                    })
            },
            light (lane, ope) {
                this.dialogVisible = false; // 关闭窗口
                this.$axios.get('/hdGate/monitor/light', {params: { lane: lane, flag: ope, lightOnTime: parseTime(this.lightOnTime), lightOffTime: parseTime(this.lightOffTime) }}).then(response => {
                    this.$message({ message: '补光灯操作成功' });
                    this.lightOnTime = '';
                    this.lightOffTime = ''
                }, response => {
                    console.log('rod error')
                })
            },
            addLane: function () {
                this.$refs['laneForm'].show('add', null)
            },
            updateLane: function (row) {
                this.$refs['laneForm'].show('update', row);
                console.log('updateLane', row)
            },
            // 删除车道信息
            deleteLane: function (row) {
                console.log('deleteLane', row);
                this.$confirm('此操作将永久删除“' + row.laneCode + '“车道信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.get('/hdGate/laneManagement/deleteLane', {params: { 'laneForm': row }}).then(data => {
                        console.log('delete lane ', true);
                        this.initGateLanes() // 重新获取数据刷新页面数据
                    }, response => {
                        console.log('删除车道信息失败')
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    })
                })
            },
            lightOn: function(row){
                this.$axios.get('/hdGate/laneManagement/lightOn', {params: { 'laneCode': row.laneCode}}).then(data => {
                    if(data){
                        this.$message({
                            type: 'success',
                            message: row.laneCode+'补光灯打开成功'
                        })
                    } else{
                        this.$message({
                            type: 'warning',
                            message: row.laneCode+'补光灯打开失败'
                        })
                    }
                })
            },
            lightOff: function(row){
                this.$axios.get('/hdGate/laneManagement/lightOff', {params: { 'laneCode': row.laneCode}}).then(data => {
                    if(data){
                        this.$message({
                            type: 'success',
                            message: row.laneCode+'补光灯关闭成功'
                        })
                    } else{
                        this.$message({
                            type: 'warning',
                            message: row.laneCode+'补光灯关闭失败'
                        })
                    }
                })
            },
            liftRod: function(row){
                this.$axios.get('/hdGate/laneManagement/liftRod', {params: { 'laneCode': row.laneCode}}).then(data => {
                    if(data){
                        this.$message({
                            type: 'success',
                            message: row.laneCode+'抬杆成功'
                        })
                    } else{
                        this.$message({
                            type: 'warning',
                            message: row.laneCode+'抬杆失败'
                        })
                    }
                })
            }
        }
    }
</script>
