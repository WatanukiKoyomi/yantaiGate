<!--车道作业界面-->
<template>
  <div class="dashboard-editor-container">
    <el-row class="panel-group" :gutter="40">
      <el-col :lg="24" style="margin-top: 10px">
        <el-card class="box-card">
          <div style="">
            <el-row>
              <!--车道表格数据-->
              <el-col :lg="24" style="margin-top: 0">
                <div>
                  <el-table :data="laneDataList" stripe style="width: 100%" highlight-current-row>
                    <el-table-column align="center" prop="laneCode" width="75px" label="车道"></el-table-column>
                    <el-table-column align="center" prop="laneDirection" width="50px" label="方向"></el-table-column>
                    <!--车牌-->
                    <el-table-column align="center" width="115px" label="车牌">
                      <template slot-scope="scope">
                        <span v-if="scope.row.data.ocrCarPlate">
                          <span v-if="scope.row.data.carPlate">
                            <span v-if="scope.row.data.carPlate.ocrPlate && scope.row.data.carPlate.ocrPlate != scope.row.data.ocrCarPlate.ocrPlate" class="ocrErrorFontColor">
                              {{scope.row.data.carPlate.ocrPlate}}
                            </span>
                            <span v-else>{{scope.row.data.ocrCarPlate.ocrPlate}}</span>
                          </span>
                          <span v-else>{{scope.row.data.ocrCarPlate.ocrPlate}}</span>
                        </span>
                      </template>
                    </el-table-column>
                    <!--前箱号-->
                    <el-table-column align="center" width="150px" label="前箱号">
                      <template slot-scope="scope">
                        <span v-if="scope.row.data.ocrFrontContainer">
                          <span v-if="scope.row.data.frontContainer">
                            <span v-if="scope.row.data.frontContainer.ocrContainerNo && scope.row.data.frontContainer.ocrContainerNo != scope.row.data.ocrFrontContainer.ocrContainerNo"
                                  class="ocrErrorFontColor">{{scope.row.data.frontContainer.ocrContainerNo}}</span>
                            <span v-else>{{scope.row.data.ocrFrontContainer.ocrContainerNo}}</span>
                          </span>
                          <span v-else>{{scope.row.data.ocrFrontContainer.ocrContainerNo}}</span>
                        </span>
                      </template>
                    </el-table-column>
                    <!--前箱型-->
                    <el-table-column align="center" width="100px" label="前箱型">
                      <template slot-scope="scope">
                        <span v-if="scope.row.data.ocrFrontContainer">
                          <span v-if="scope.row.data.frontContainer">
                            <span v-if="scope.row.data.frontContainer.ocrContainerISO && scope.row.data.frontContainer.ocrContainerISO != scope.row.data.ocrFrontContainer.ocrContainerISO"
                                  class="ocrErrorFontColor">{{scope.row.data.frontContainer.ocrContainerISO}}</span>
                            <span v-else>{{scope.row.data.ocrFrontContainer.ocrContainerISO}}</span>
                          </span>
                          <span v-else>{{scope.row.data.ocrFrontContainer.ocrContainerISO}}</span>
                        </span>
                      </template>
                    </el-table-column>
                    <!--前箱空重-->
                    <el-table-column align="center" width="100px" label="前箱空重">
                      <template slot-scope="scope">
                        <span v-if="scope.row.data.ocrFrontContainer">
                           {{scope.row.data.ocrFrontContainer.efid}}
                        </span>
                      </template>
                    </el-table-column>
                    <!--后箱号-->
                    <el-table-column align="center" width="150px" label="后箱号">
                      <template slot-scope="scope">
                        <span v-if="scope.row.data.ocrAfterContainer">
                          <span v-if="scope.row.data.afterContainer">
                            <span v-if="scope.row.data.afterContainer.ocrContainerNo && scope.row.data.afterContainer.ocrContainerNo != scope.row.data.ocrAfterContainer.ocrContainerNo"
                                  class="ocrErrorFontColor">{{scope.row.data.afterContainer.ocrContainerNo}}</span>
                            <span v-else>{{scope.row.data.ocrAfterContainer.ocrContainerNo}}</span>
                          </span>
                          <span v-else>{{scope.row.data.ocrAfterContainer.ocrContainerNo}}</span>
                        </span>
                      </template>
                    </el-table-column>
                    <!--后箱型-->
                    <el-table-column align="center" width="100px" label="后箱型">
                      <template slot-scope="scope">
                        <span v-if="scope.row.data.ocrAfterContainer">
                          <span v-if="scope.row.data.afterContainer">
                            <span v-if="scope.row.data.afterContainer.ocrContainerISO && scope.row.data.afterContainer.ocrContainerISO != scope.row.data.ocrAfterContainer.ocrContainerISO"
                                  class="ocrErrorFontColor">{{scope.row.data.afterContainer.ocrContainerISO}}</span>
                            <span v-else>{{scope.row.data.ocrAfterContainer.ocrContainerISO}}</span>
                          </span>
                          <span v-else>{{scope.row.data.ocrAfterContainer.ocrContainerISO}}</span>
                        </span>
                      </template>
                    </el-table-column>
                    <!--后箱空重-->
                    <el-table-column align="center" width="100px" label="后箱空重">
                      <template slot-scope="scope">
                        <span v-if="scope.row.data.ocrAfterContainer">
                           {{scope.row.data.ocrAfterContainer.efid}}
                        </span>
                      </template>
                    </el-table-column>
                    <!--重量-->
                    <el-table-column align="center" width="115px" label="重量">
                      <template slot-scope="scope">
                        <span v-if="scope.row.data.generalInfo">
                          {{scope.row.data.generalInfo.weight}}
                        </span>
                      </template>
                    </el-table-column>
                    <!--反馈消息-->
                    <el-table-column align="center" width="245px" label="反馈消息">
                      <template slot-scope="scope">
                        {{scope.row.data.msg}}
                      </template>
                    </el-table-column>
                    <!--时间-->
                    <el-table-column align="center" width="100px" label="时间">
                      <template slot-scope="scope">
                        {{scope.row.data.arriveTime}}
                      </template>
                    </el-table-column>
                    <!--操作-->
                    <el-table-column align="center" label="操作" width="140px" fixed="right">
                      <template slot-scope="scope">
                        <el-button type="primary" @click.stop='shibiexiuzhengClick(scope.row)' size="small" icon="el-icon-edit">识别修正</el-button><!--识别修正-->
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 弹出窗体内容 -->
    <el-dialog :visible.sync="dialogVisible" width="95%" top="3vh" title="">
      <el-tabs v-model="activeName">
        <el-tab-pane label="识别内容" name="ocr">
          <el-row>
            <el-row>
              <el-col :xs="1" :sm="1" :md="1" :lg="1">&nbsp;</el-col>
              <!--车牌-->
              <el-col :xs="3" :sm="3" :md="6" :lg="3">
                <el-tooltip content="车牌" placement="top" effect="light"><!--车牌-->
                  <div v-if="clickedData.carPlate">
                    <div v-if="clickedData.carPlate.ocrPlate">
                      车牌：<el-input class="edit-input" size="small" style="width: 110px;" v-model="clickedData.carPlate.ocrPlate"></el-input>
                    </div>
                    <div v-else>
                      车牌：<el-input class="edit-input" size="small" style="width: 110px;" v-model="clickedData.ocrCarPlate.ocrPlate"></el-input>
                    </div>
                  </div>
                  <div v-else>车牌：<el-input class="edit-input" size="small" style="width: 110px;" v-model="clickedData.ocrCarPlate.ocrPlate"></el-input></div>
                </el-tooltip>
              </el-col>
              <!--前箱号-->
              <el-col :xs="3" :sm="3" :md="6" :lg="3">
                <el-tooltip content="前箱号" placement="top" effect="light"><!--车牌-->
                  <div v-if="clickedData.frontContainer">
                    <div v-if="clickedData.frontContainer.ocrContainerNo">
                      前箱号：<el-input class="edit-input" size="small" style="width: 180px;" v-model="clickedData.frontContainer.ocrContainerNo"></el-input>
                    </div>
                    <div v-else>
                      前箱号：<el-input class="edit-input" size="small" style="width: 180px;" v-model="clickedData.ocrFrontContainer.ocrContainerNo"></el-input>
                    </div>
                  </div>
                  <div v-else>前箱号：<el-input class="edit-input" size="small" style="width: 180px;" v-model="clickedData.ocrFrontContainer.ocrContainerNo"></el-input></div>
                </el-tooltip>
              </el-col>
              <!--前箱型-->
              <el-col :xs="3" :sm="3" :md="6" :lg="3">
                <div v-if="clickedData.frontContainer">
                  <div v-if="clickedData.frontContainer.ocrContainerISO">
                    前箱型：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.frontContainer.ocrContainerISO"></el-input>
                  </div>
                  <div v-else>
                    前箱型：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrFrontContainer.ocrContainerISO"></el-input>
                  </div>
                </div>
                <div v-else>前箱型：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrFrontContainer.ocrContainerISO"></el-input></div>
              </el-col>
              <!--前箱空重-->
              <el-col :xs="3" :sm="3" :md="6" :lg="3">
                <div v-if="clickedData.ocrFrontContainer">
                  前箱空重：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrFrontContainer.efid"></el-input>
                </div>
              </el-col>
              <!--前箱铅封状态-->
              <el-col :xs="3" :sm="3" :md="6" :lg="3">
                <div v-if="clickedData.frontContainer">
                  <div v-if="clickedData.frontContainer.leadSealState">
                    前箱铅封状态：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.frontContainer.leadSealState"></el-input>
                  </div>
                  <div v-else>
                    前箱铅封状态：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrFrontContainer.leadSealState"></el-input>
                  </div>
                </div>
                <div v-else>前箱铅封状态：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrFrontContainer.leadSealState"></el-input></div>
              </el-col>
              <!--前箱铅封号-->
              <el-col :xs="3" :sm="3" :md="6" :lg="3">
                <div v-if="clickedData.frontContainer">
                  <div v-if="clickedData.frontContainer.leadSealNo">
                    前箱铅封号：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.frontContainer.leadSealNo"></el-input>
                  </div>
                  <div v-else>
                    前箱铅封号：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrFrontContainer.leadSealNo"></el-input>
                  </div>
                </div>
                <div v-else>前箱铅封号：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrFrontContainer.leadSealNo"></el-input></div>
              </el-col>
              <!--前箱箱属-->
              <el-col :xs="3" :sm="3" :md="6" :lg="3">
                <div v-if="clickedData.frontContainer">
                  <div v-if="clickedData.frontContainer.property">
                    前箱箱属：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.frontContainer.property"></el-input>
                  </div>
                  <div v-else>
                    前箱箱属：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrFrontContainer.property"></el-input>
                  </div>
                </div>
                <div v-else>前箱箱属：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrFrontContainer.property"></el-input></div>
              </el-col>
              <!--提交按钮-->
              <el-col :xs="2" :sm="2" :md="2" :lg="2">
                <el-button type="success" @click.stop="submitClick" size="small" icon="el-icon-circle-check-outline">提交</el-button>
              </el-col>
            </el-row>
            <el-row style="margin-top: 20px">
              <el-col :xs="1" :sm="1" :md="1" :lg="1">&nbsp;</el-col>
              <!--重量-->
              <el-col :xs="3" :sm="3" :md="6" :lg="3">
                重量：
                <el-tooltip content="重量" placement="top" effect="light"><!--重量-->
                  <el-input class="edit-input" size="small" style="width: 100px;" v-model="clickedData.generalInfo.weight"></el-input>
                </el-tooltip>
              </el-col>
              <!--后箱号-->
              <el-col :xs="3" :sm="3" :md="6" :lg="5">
                <div v-if="clickedData.afterContainer">
                  <div v-if="clickedData.afterContainer.ocrContainerNo">
                    后箱号：<el-input class="edit-input" size="small" style="width: 180px;" v-model="clickedData.afterContainer.ocrContainerNo"></el-input>
                  </div>
                  <div v-else>
                    后箱号：<el-input class="edit-input" size="small" style="width: 180px;" v-model="clickedData.ocrAfterContainer.ocrContainerNo"></el-input>
                  </div>
                </div>
                <div v-else>后箱号：<el-input class="edit-input" size="small" style="width: 180px;" v-model="clickedData.ocrAfterContainer.ocrContainerNo"></el-input></div>
              </el-col>
              <!--后箱型-->
              <el-col :xs="3" :sm="3" :md="6" :lg="3">
                <div v-if="clickedData.afterContainer">
                  <div v-if="clickedData.afterContainer.ocrContainerISO">
                    后箱型：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.afterContainer.ocrContainerISO"></el-input>
                  </div>
                  <div v-else>
                    后箱型：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrAfterContainer.ocrContainerISO"></el-input>
                  </div>
                </div>
                <div v-else>后箱型：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrAfterContainer.ocrContainerISO"></el-input></div>
              </el-col>
              <!--后箱空重-->
              <el-col :xs="3" :sm="3" :md="6" :lg="3">
                <div v-if="clickedData.ocrAfterContainer">
                  后箱空重：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrAfterContainer.efid"></el-input>
                </div>
              </el-col>
              <!--后箱铅封状态-->
              <el-col :xs="3" :sm="3" :md="6" :lg="3">
                <div v-if="clickedData.afterContainer">
                  <div v-if="clickedData.afterContainer.leadSealState">
                    后箱铅封状态：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.afterContainer.leadSealState"></el-input>
                  </div>
                  <div v-else>
                    后箱铅封状态：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrAfterContainer.leadSealState"></el-input>
                  </div>
                </div>
                <div v-else>后箱铅封状态：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrAfterContainer.leadSealState"></el-input></div>
              </el-col>
              <!--后箱铅封号-->
              <el-col :xs="3" :sm="3" :md="6" :lg="3">
                <div v-if="clickedData.afterContainer">
                  <div v-if="clickedData.afterContainer.leadSealNo">
                    后箱铅封号：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.afterContainer.leadSealNo"></el-input>
                  </div>
                  <div v-else>
                    后箱铅封号：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrFrontContainer.leadSealNo"></el-input>
                  </div>
                </div>
                <div v-else>后箱铅封号：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrFrontContainer.leadSealNo"></el-input></div>
              </el-col>
              <!--后箱箱属-->
              <el-col :xs="3" :sm="3" :md="6" :lg="3">
                <div v-if="clickedData.afterContainer">
                  <div v-if="clickedData.afterContainer.property">
                    后箱箱属：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.afterContainer.property"></el-input>
                  </div>
                  <div v-else>
                    后箱箱属：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrAfterContainer.property"></el-input>
                  </div>
                </div>
                <div v-else>后箱箱属：<el-input class="edit-input" size="small" style="width: 90px;" v-model="clickedData.ocrAfterContainer.property"></el-input></div>
              </el-col>
              <!--取消按钮-->
              <el-col :xs="2" :sm="2" :md="2" :lg="2">
                <el-button type="warning" @click.stop="cancelClick" size="small" icon="el-icon-refresh">取消</el-button>
              </el-col>
            </el-row>
          </el-row>
          <!-- 图片展示 -->
          <el-row style="margin-top: 10px">
            <el-carousel indicator-position="outside" arrow="always" :autoplay="false" ref="photos" :height="screenHeight*2/3 + 10 + 'px'">
              <el-carousel-item v-for="(item, index) in ocrImgs" :key="index" >
                <div class="img-box" style="text-align:center;height: 100%">
                  <img :src="item" style="height:100%;width: 80%;">
                </div>
              </el-carousel-item>
            </el-carousel>
          </el-row>
        </el-tab-pane>

        <!--<el-tab-pane label="验残内容" name="cdi">
        </el-tab-pane>-->
      </el-tabs>
    </el-dialog>
  </div>
</template>

<script>
    export default {
        components: {
        },
        data () {
            return {
                dialogVisible: false,
                emptyData: { laneCode: '', msg: '', arriveTime: '', enterTime: '', generalInfo: { laneCode: '', weight: '' }, ocrCarPlate: { ocrPlate: '', plateColor: '' }, ocrFrontContainer: { ocrContainerNo: '', ocrContainerDirection: '', ocrContainerISO: '', efid: '' }, ocrAfterContainer: { ocrContainerNo: '', ocrContainerDirection: '', ocrContainerISO: '', efid: '' }, ftpImages: { folder: '', imageName: '' } },
                clickedData: { laneCode: '', msg: '', arriveTime: '', enterTime: '', generalInfo: { laneCode: '', weight: '' }, ocrCarPlate: { ocrPlate: '', plateColor: '' }, ocrFrontContainer: { ocrContainerNo: '', ocrContainerDirection: '', ocrContainerISO: '', efid: '' }, ocrAfterContainer: { ocrContainerNo: '', ocrContainerDirection: '', ocrContainerISO: '', efid: '' }, ftpImages: { folder: '', imageName: '' } },
                laneDataList: [],
                activeName: 'ocr',
                ocrImgs: [],
                screenHeight: document.documentElement.clientHeight,
                clickedLaneCode: ''
            }
        },
        mounted () {
            let that = this;
            this.initLaneChecked()
            // 窗口大小变更重新计算高度
            window.onresize = () => {
                return (() => {
                    that.screenHeight = document.documentElement.clientHeight
                })
            }
        },
        watch: {
        },
        methods: {
            // 初始化选中的车道信息
            initLaneChecked() {
                let that = this;
                let initLane = [];
                let username = JSON.parse(sessionStorage.user).username;
                this.$axios.get('/hdGate/laneManagement/queryShowGateLanes').then(data => {
                    let s = window.location.host.split(':')[0];
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
                            console.log('数据已接收...' + evt.data);
                            let data = JSON.parse(evt.data);
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
                        initLane.push(element.laneCode);
                        // begin 查询车道对应最新数据
                        that.laneDataList = [];
                        that.$axios.get('/hdGate/monitor/queryLatestDataByLane',
                            {params: {'laneCode': element.laneCode}}).then(data => {
                            if (data === null || data === '') {
                                that.laneDataList.push({
                                    laneCode: element.laneCode,
                                    laneDirection: element.laneDirection,
                                    data: that.emptyData
                                }) // 查询到最新数据赋值到对应车道上
                            } else {
                                that.laneDataList.push({
                                    laneCode: element.laneCode,
                                    laneDirection: element.laneDirection,
                                    data: data
                                }) // 查询到最新数据赋值到对应车道上
                            }
                        }, response => {
                            console.log('queryLatestDataByLane error')
                        })
                        // end 查询车道对应最新数据
                    })
                    that.laneShowList = initLane;
                    // begin 查询用户选中查看的车道
                    that.$axios.get('/hdGate/laneManagement/queryGateLaneByUser',
                        {params: {'user': username}}).then(data => {
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
                console.log('laneDataList', that.laneDataList)
            },
            // 识别修正按钮 ，用来弹出对话框
            shibiexiuzhengClick(laneData) {
                let data = laneData.data;
                this.clickedLaneCode = laneData.laneCode;
                this.dialogVisible = true;
                console.log('data:', data, this.clickedLaneCode);
                // 避免绑定 JSON处理下
                this.clickedData = JSON.parse(JSON.stringify(data));
                this.ocrImgs = [];
                let that = this;
                // begin 图片路径处理
                // var path = data.ftpImages.imagePath
                // if (path !== undefined) {
                //   if (path.indexOf('ftp://') >= 0) {
                //     path = path.substring(6)
                //   }
                //   var index = path.indexOf('/')
                //   path = path.substring(index + 1)
                //   console.log('图片地址：', path)
                //   // end 图片路径处理
                //   data.ftpImages.imageName.split(',').forEach(function (imgName) {
                //     that.ocrImgs.push(path + '/' + imgName)
                //   })
                //2020.02.21对接西井改动
                data.ftpImages.imageName.split(',').forEach(function (imgName) {
                    that.ocrImgs.push(imgName)
                })
            },

            // 弹出框提交按钮
            submitClick: function () {
                console.log('submitClickData:', this.clickedData, this.clickedLaneCode);
                console.log('aaaaaaaaaaa', this.clickedData.visitGuid);
                if (this.clickedData.visitGuid === undefined) { // 新增
                    console.log('fffffffffffff')
                }
                let todoUpdateData = JSON.stringify(this.clickedData);
                let username = JSON.parse(sessionStorage.user).username;
                this.$axios.get('/hdGate/monitor/updateBusinessData',
                    {
                        params: {
                            'account': username,
                            laneCode: this.clickedLaneCode,
                            visitGuid: this.clickedData.visitGuid,
                            todoUpdateData: todoUpdateData
                        }
                    }).then(data => {

                }, response => {
                    console.log('queryGateLaneByUser error')
                })
                this.dialogVisible = false; // 确认后，关闭弹出框
                this.initLaneChecked() // 重新查询数据
            },
            // 弹出框取消按钮
            cancelClick() {
                this.dialogVisible = false
            }
        }
    }

</script>

<style rel="stylesheet/scss" scoped>
  .ocrErrorFontColor {
    color: red;
  }
</style>
<style>
  .el-dialog__header {
    display: none;
  }
</style>
