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
                  <el-table :data="laneDataList" stripe style="width: 100%" highlight-current-row >
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
                    <el-table-column align="center" label="操作" width="280px" fixed="right">
                      <template slot-scope="scope">
                        <el-button type="primary" @click.stop='shibiexiuzhengClick(scope.row)' size="small" icon="el-icon-edit">识别修正</el-button><!--识别修正-->
                        <el-button type="warning" @click.stop="quickSubmit(scope.row)" size="small" icon="el-icon-check">正常提交</el-button>
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
    <el-dialog :visible.sync="dialogVisible" width="98%" top="2vh" title="">
      <el-row>
        <el-col :span="22">
          <el-row :gutter="5">
            <!--车牌-->
            <el-col :sm="5" :md="5" :lg="4">
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
            <el-col :sm="6" :md="5" :lg="4">
              <el-tooltip content="前箱号" placement="top" effect="light">
                <div v-if="clickedData.frontContainer">
                  <div v-if="clickedData.frontContainer.ocrContainerNo">
                    前箱号：<el-input size="small" style="width: 120px;" v-model="clickedData.frontContainer.ocrContainerNo"></el-input>
                  </div>
                  <div v-else>
                    前箱号：<el-input size="small" style="width: 120px;" v-model="clickedData.ocrFrontContainer.ocrContainerNo"></el-input>
                  </div>
                </div>
                <div v-else>前箱号：<el-input size="small" style="width: 120px;" v-model="clickedData.ocrFrontContainer.ocrContainerNo"></el-input></div>
              </el-tooltip>
            </el-col>
            <!--前箱型-->
            <el-col :sm="5" :md="4" :lg="4">
              <div v-if="clickedData.frontContainer">
                <div v-if="clickedData.frontContainer.ocrContainerISO">
                  前箱型：<el-input class="edit-input" size="small" style="width: 80px;" v-model="clickedData.frontContainer.ocrContainerISO"></el-input>
                </div>
                <div v-else>
                  前箱型：<el-input class="edit-input" size="small" style="width: 80px;" v-model="clickedData.ocrFrontContainer.ocrContainerISO"></el-input>
                </div>
              </div>
              <div v-else>前箱型：<el-input class="edit-input" size="small" style="width: 80px;" v-model="clickedData.ocrFrontContainer.ocrContainerISO"></el-input></div>
            </el-col>
            <!--前箱空重-->
            <el-col :sm="5" :md="5" :lg="4">
              <div v-if="clickedData.ocrFrontContainer">
                前箱空重：<el-input class="edit-input" size="small" style="width: 80px;" v-model="clickedData.ocrFrontContainer.efid"></el-input>
              </div>
            </el-col>
            <!--前箱铅封状态-->
            <el-col :sm="5" :md="4" :lg="4">
              <div v-if="clickedData.frontContainer">
                <div v-if="clickedData.frontContainer.leadSealState">
                  前箱铅封状态：<el-input class="edit-input" size="small" style="width: 40px;" v-model="clickedData.frontContainer.leadSealState"></el-input>
                </div>
                <div v-else>
                  前箱铅封状态：<el-input class="edit-input" size="small" style="width: 40px;" v-model="clickedData.ocrFrontContainer.leadSealState"></el-input>
                </div>
              </div>
              <div v-else>前箱铅封状态：<el-input class="edit-input" size="small" style="width: 40px;" v-model="clickedData.ocrFrontContainer.leadSealState"></el-input></div>
            </el-col>
            <!--前箱铅封号-->
            <el-col :sm="6" :md="5" :lg="4">
              <div v-if="clickedData.frontContainer">
                <div v-if="clickedData.frontContainer.leadSealNo">
                  前箱铅封号：<el-input class="edit-input" size="small" style="width: 85px;" v-model="clickedData.frontContainer.leadSealNo"></el-input>
                </div>
                <div v-else>
                  前箱铅封号：<el-input class="edit-input" size="small" style="width: 85px;" v-model="clickedData.ocrFrontContainer.leadSealNo"></el-input>
                </div>
              </div>
              <div v-else>前箱铅封号：<el-input class="edit-input" size="small" style="width: 85px;" v-model="clickedData.ocrFrontContainer.leadSealNo"></el-input></div>
            </el-col>
            <!--前箱箱属-->
            <el-col :sm="5" :md="5" :lg="4">
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
            <!--前箱残损-->
            <el-col :sm="6" :md="5" :lg="4">
              <div v-if="clickedData.frontContainer">
                <div v-if="clickedData.frontContainer.damage_code">
                  前箱残损：<el-select  size="small" style="width: 100px;" v-model="clickedData.frontContainer.ocrDamage" placeholder="残损选项">
                  <el-option v-for="item in selectData" :key="item.value" :value="item.value" :label="item.label"></el-option>
                </el-select>
                </div>
                <div v-else>
                  前箱残损：
                  <el-select  size="small" style="width: 100px;" v-model="clickedData.ocrFrontContainer.ocrDamage" placeholder="残损选项">
                    <el-option v-for="item in selectData" :key="item.value" :value="item.value" :label="item.label"></el-option>
                  </el-select>
                </div>
              </div>
              <div v-else>前箱残损：<el-select  size="small" style="width: 100px;" v-model="clickedData.ocrFrontContainer.ocrDamage" placeholder="残损选项">
                <el-option v-for="item in selectData" :key="item.value" :value="item.value" :label="item.label"></el-option>
              </el-select></div>
            </el-col>
          </el-row>
          <el-row :gutter="5" >
            <!--重量-->
            <el-col :sm="5" :md="5" :lg="4">
              重量：
              <el-tooltip content="重量" placement="top" effect="light"><!--重量-->
                <el-input class="edit-input" size="small" style="width: 110px;" v-model="clickedData.generalInfo.weight"></el-input>
              </el-tooltip>
            </el-col>
            <!--后箱号-->
            <el-col :sm="6" :md="5" :lg="4">
              <div v-if="clickedData.afterContainer">
                <div v-if="clickedData.afterContainer.ocrContainerNo">
                  后箱号：<el-input class="edit-input" size="small" style="width: 120px;" v-model="clickedData.afterContainer.ocrContainerNo"></el-input>
                </div>
                <div v-else>
                  后箱号：<el-input class="edit-input" size="small" style="width: 120px;" v-model="clickedData.ocrAfterContainer.ocrContainerNo"></el-input>
                </div>
              </div>
              <div v-else>后箱号：<el-input class="edit-input" size="small" style="width: 120px;" v-model="clickedData.ocrAfterContainer.ocrContainerNo"></el-input></div>
            </el-col>
            <!--后箱型-->
            <el-col :sm="5" :md="4" :lg="4">
              <div v-if="clickedData.afterContainer">
                <div v-if="clickedData.afterContainer.ocrContainerISO">
                  后箱型：<el-input class="edit-input" size="small" style="width: 80px;" v-model="clickedData.afterContainer.ocrContainerISO"></el-input>
                </div>
                <div v-else>
                  后箱型：<el-input class="edit-input" size="small" style="width: 80px;" v-model="clickedData.ocrAfterContainer.ocrContainerISO"></el-input>
                </div>
              </div>
              <div v-else>后箱型：<el-input class="edit-input" size="small" style="width: 80px;" v-model="clickedData.ocrAfterContainer.ocrContainerISO"></el-input></div>
            </el-col>
            <!--后箱空重-->
            <el-col :sm="5" :md="5" :lg="4">
              <div v-if="clickedData.ocrAfterContainer">
                后箱空重：<el-input class="edit-input" size="small" style="width: 80px;" v-model="clickedData.ocrAfterContainer.efid"></el-input>
              </div>
            </el-col>
            <!--后箱铅封状态-->
            <el-col :sm="5" :md="4" :lg="4">
              <div v-if="clickedData.afterContainer">
                <div v-if="clickedData.afterContainer.leadSealState">
                  后箱铅封状态：<el-input class="edit-input" size="small" style="width: 40px;" v-model="clickedData.afterContainer.leadSealState"></el-input>
                </div>
                <div v-else>
                  后箱铅封状态：<el-input class="edit-input" size="small" style="width: 40px;" v-model="clickedData.ocrAfterContainer.leadSealState"></el-input>
                </div>
              </div>
              <div v-else>后箱铅封状态：<el-input class="edit-input" size="small" style="width: 40px;" v-model="clickedData.ocrAfterContainer.leadSealState"></el-input></div>
            </el-col>
            <!--后箱铅封号-->
            <el-col :sm="6" :md="5" :lg="4">
              <div v-if="clickedData.afterContainer">
                <div v-if="clickedData.afterContainer.leadSealNo">
                  后箱铅封号：<el-input class="edit-input" size="small" style="width: 85px;" v-model="clickedData.afterContainer.leadSealNo"></el-input>
                </div>
                <div v-else>
                  后箱铅封号：<el-input class="edit-input" size="small" style="width: 85px;" v-model="clickedData.ocrAfterContainer.leadSealNo"></el-input>
                </div>
              </div>
              <div v-else>后箱铅封号：<el-input class="edit-input" size="small" style="width: 85px;" v-model="clickedData.ocrAfterContainer.leadSealNo"></el-input></div>
            </el-col>
            <!--后箱箱属-->
            <el-col :sm="5" :md="5" :lg="4">
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
            <!--后箱残损-->
            <el-col :sm="6" :md="5" :lg="4">
              <div v-if="clickedData.afterContainer">
                <div v-if="clickedData.afterContainer.ocrDamage">
                  后箱残损：<el-select  size="small" style="width: 100px;" v-model="clickedData.afterContainer.ocrDamage" placeholder="残损选项">
                  <el-option v-for="item in selectData" :key="item.value" :value="item.value" :label="item.label"></el-option>
                </el-select>
                </div>
                <div v-else>
                  后箱残损：
                  <el-select  size="small" style="width: 100px;" v-model="clickedData.ocrAfterContainer.ocrDamage" placeholder="残损选项">
                    <el-option v-for="item in selectData" :key="item.value" :value="item.value" :label="item.label"></el-option>
                  </el-select>
                </div>
              </div>
              <div v-else>后箱残损：<el-select  size="small" style="width: 100px;" v-model="clickedData.ocrAfterContainer .ocrDamage" placeholder="残损选项">
                <el-option v-for="item in selectData" :key="item.value" :value="item.value" :label="item.label"></el-option>
              </el-select></div>
            </el-col>

          </el-row>
        </el-col>
        <el-col :span="2">
          <el-row>
            <!--确定-->
            <el-col :lg="4">
              <el-button type="success" @click.stop="submitClick" size="small" icon="el-icon-circle-check-outline">提交</el-button>
            </el-col>
          </el-row>
          <el-row style="margin-top: 10px">
            <!--取消-->
            <el-col :lg="2">
              <el-button type="warning" @click.stop="cancelClick" size="small" icon="el-icon-refresh">取消</el-button>
            </el-col>
          </el-row>
        </el-col>
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
        clickedLaneCode: '',
        selectData: [{value: 'ZYNM',label: '中远内贸'},
          {value: 'DZ',label: '钉子'},
          {value: 'NM',label: '正常'},
          {value: 'NMCW',label: '内贸残污'},
          {value: 'SPX',label: '食品箱'},
          {value: 'DL',label: '危险品'},
          {value: 'JZ',label: '机组'},
          {value: 'DR',label: '污箱'},
          {value: 'DM',label: '残损'},
          {value: 'YW',label: '异味'},
          {value: 'YZX',label: '退租箱'},
          {value: 'CWX',label: '残污箱'},
          {value: 'DMS',label: '残损大项目'},
          {value: 'QW',label: '轻污'},
          {value: 'ZW',label: '重污'},
          {value: 'WBX',label: '危标箱'}]
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
    methods: {
      // 初始化选中的车道信息
      initLaneChecked() {
        let that = this;
        let result = that.laneDataList;
        let empty = that.emptyData;
        let initLane = [];
        let username = JSON.parse(sessionStorage.user).username;
        this.$axios.get('/hdGate/laneManagement/queryShowGateLanes').then(data => {
          let s = window.location.host.split(':')[0];
          initShowLane(0,data.length,data);
          function initShowLane(i,length,res){
            let element = res[i];
            initLane.push(element.laneCode);
            let ws;
            let wsUrl = 'ws://' + s + ':8085/hdGate/ws/monitor:' + username + element.laneCode;
            let lockReconnect = false;
            let heartCheck = {
              timeout: 30000,
              timeoutObj: null,
              serverTimeoutObj: null,
              start: function(){
                let self = this;
                this.timeoutObj && clearTimeout(this.timeoutObj);
                this.serverTimeoutObj && clearTimeout(this.serverTimeoutObj);
                this.timeoutObj = setTimeout(function(){
                  ws.send("HeartBeat");
                  self.serverTimeoutObj = setTimeout(function(){
                    ws.close();
                  }, self.timeout)
                },this.timeout)
              },
            };
            function createWebSocket(){
              try{
                ws = new WebSocket(wsUrl);
                websocketInit();
              } catch (e){
                websocketReconnect(wsUrl);
              }
            }
            createWebSocket();

            function websocketInit(){
              ws.onopen = function(evt){
                heartCheck.start();
              };
              ws.onclose = function(evt){
                websocketReconnect(wsUrl);
              };
              ws.onmessage = function(evt){
                heartCheck.start();
                if(evt.data == 'alive'){
                  return;
                }
                let data = JSON.parse(evt.data)
                if(data.ocrFrontContainer.ocrContainerNo != null
                  && data.ocrFrontContainer.ocrContainerNo != ''
                  && data.ocrFrontContainer.ocrContainerNo != undefined){
                  data.ocrFrontContainer.ocrDamage = 'NM'
                }
                if(data.ocrAfterContainer.ocrContainerNo != null
                  && data.ocrAfterContainer.ocrContainerNo != ''
                  && data.ocrAfterContainer.ocrContainerNo != undefined){
                  data.ocrAfterContainer.ocrDamage = 'NM'
                }
                that.laneDataList.forEach(function (laneData) { // 循环现有数据，重新赋值对应车道数据
                  if (laneData.laneCode === data.generalInfo.laneCode) {
                    laneData.data = data
                  }
                })
              };
              ws.onerror = function(evt){
              }
            }

            function websocketReconnect(url){
              if(lockReconnect){
                return;
              }
              lockReconnect = true;
              tt && clearTimeout(tt);
              let tt = setTimeout(function(){
                createWebSocket(url);
                lockReconnect = false;
              }, 5000);
            }
            that.$axios.get('/hdGate/monitor/queryLatestDataByLane',
              {params: {'laneCode': element.laneCode}}).then(data => {
              if (data === null || data === '') {
                result.push({
                  laneCode: element.laneCode,
                  laneDirection: element.laneDirection,
                  data: empty
                }) // 查询到最新数据赋值到对应车道上
              } else {
                result.push({
                  laneCode: element.laneCode,
                  laneDirection: element.laneDirection,
                  data: data
                }) // 查询到最新数据赋值到对应车道上
              }
              if(++i<length){
                initShowLane(i,length,res);
              }
            }, response => {
              console.log('queryLatestDataByLane error')
            })
          }
          // data.forEach(function (element) {
          //
          //   let ws;
          //   let wsUrl = 'ws://' + s + ':8085/hdGate/ws/monitor:' + username + element.laneCode;
          //   let lockReconnect = false;
          //   let heartCheck = {
          //     timeout: 30000,
          //     timeoutObj: null,
          //     serverTimeoutObj: null,
          //     start: function(){
          //       let self = this;
          //       this.timeoutObj && clearTimeout(this.timeoutObj);
          //       this.serverTimeoutObj && clearTimeout(this.serverTimeoutObj);
          //       this.timeoutObj = setTimeout(function(){
          //         ws.send("HeartBeat");
          //         self.serverTimeoutObj = setTimeout(function(){
          //           ws.close();
          //         }, self.timeout)
          //       },this.timeout)
          //     },
          //   };
          //   function createWebSocket(){
          //     try{
          //       ws = new WebSocket(wsUrl);
          //       websocketInit();
          //     } catch (e){
          //       console.log('catch');
          //       websocketReconnect(wsUrl);
          //     }
          //   }
          //   createWebSocket();
          //
          //   function websocketInit(){
          //     ws.onopen = function(evt){
          //       console.log('monitor:' + username + element.laneCode + '链接webSocket成功...')
          //       heartCheck.start();
          //     };
          //     ws.onclose = function(evt){
          //       console.log('websocket 断开: ' + evt.code + ' ' + evt.reason + ' ' + evt.wasClean);
          //       console.log('monitor:' + username + element.laneCode + '链接已关闭...');
          //
          //       websocketReconnect(wsUrl);
          //     };
          //     ws.onmessage = function(evt){
          //       heartCheck.start();
          //       if(evt.data == 'alive'){
          //         return;
          //       }
          //       let data = JSON.parse(evt.data)
          //       if(data.ocrFrontContainer.ocrContainerNo != null
          //         && data.ocrFrontContainer.ocrContainerNo != ''
          //         && data.ocrFrontContainer.ocrContainerNo != undefined){
          //         data.ocrFrontContainer.ocrDamage = 'NM'
          //       }
          //       if(data.ocrAfterContainer.ocrContainerNo != null
          //         && data.ocrAfterContainer.ocrContainerNo != ''
          //         && data.ocrAfterContainer.ocrContainerNo != undefined){
          //         data.ocrAfterContainer.ocrDamage = 'NM'
          //       }
          //       that.laneDataList.forEach(function (laneData) { // 循环现有数据，重新赋值对应车道数据
          //         if (laneData.laneCode === data.generalInfo.laneCode) {
          //           laneData.data = data
          //         }
          //       })
          //     };
          //     ws.onerror = function(evt){
          //       console.log('monitor:' + username + element.laneCode + '链接webSocket失败')
          //     }
          //   }
          //
          //   function websocketReconnect(url){
          //     if(lockReconnect){
          //       return;
          //     }
          //     lockReconnect = true;
          //     tt && clearTimeout(tt);
          //     let tt = setTimeout(function(){
          //       createWebSocket(url);
          //       lockReconnect = false;
          //     }, 5000);
          //   }
          //
          //   // let ws = new WebSocket('ws://' + s + ':8085/hdGate/ws/monitor:' + username + element.laneCode);
          //   //
          //   // ws.onopen = () => {
          //   //   console.log('monitor:' + username + element.laneCode + '链接webSocket成功...')
          //   // };
          //   // ws.onerror = function () {
          //   //   console.log('monitor:' + username + element.laneCode + '链接webSocket失败')
          //   // };
          //   // ws.onmessage = evt => {
          //   //   console.log('数据已接收...' + evt.data);
          //   //   let data = JSON.parse(evt.data);
          //   //   if(data.ocrFrontContainer.ocrContainerNo != null
          //   //     && data.ocrFrontContainer.ocrContainerNo != ''
          //   //     && data.ocrFrontContainer.ocrContainerNo != undefined){
          //   //     data.ocrFrontContainer.ocrDamage = 'NM'
          //   //   }
          //   //   if(data.ocrAfterContainer.ocrContainerNo != null
          //   //     && data.ocrAfterContainer.ocrContainerNo != ''
          //   //     && data.ocrAfterContainer.ocrContainerNo != undefined){
          //   //     data.ocrAfterContainer.ocrDamage = 'NM'
          //   //   }
          //   //   that.laneDataList.forEach(function (laneData) { // 循环现有数据，重新赋值对应车道数据
          //   //     if (laneData.laneCode === data.generalInfo.laneCode) {
          //   //       laneData.data = data
          //   //     }
          //   //   })
          //   // };
          //   // ws.onclose = function (e) {
          //   //   // 关闭 websocket
          //   //   console.log('websocket 断开: ' + e.code + ' ' + e.reason + ' ' + e.wasClean);
          //   //   console.log('monitor:' + username + element.laneCode + '链接已关闭...')
          //   //
          //   // };
          //   // end websocket
          //   initLane.push(element.laneCode);
          //   // begin 查询车道对应最新数据
          //   that.laneDataList = [];
          //   that.$axios.get('/hdGate/monitor/queryLatestDataByLane',
          //     {params: {'laneCode': element.laneCode}}).then(data => {
          //     if (data === null || data === '') {
          //       that.laneDataList.push({
          //         laneCode: element.laneCode,
          //         laneDirection: element.laneDirection,
          //         data: that.emptyData
          //       }) // 查询到最新数据赋值到对应车道上
          //     } else {
          //       that.laneDataList.push({
          //         laneCode: element.laneCode,
          //         laneDirection: element.laneDirection,
          //         data: data
          //       }) // 查询到最新数据赋值到对应车道上
          //     }
          //   }, response => {
          //     console.log('queryLatestDataByLane error')
          //   })
          //   // end 查询车道对应最新数据
          // })
        }, response => {
          console.log('queryShowGateLanes error')
        })
      },
      // 识别修正按钮 ，用来弹出对话框
      shibiexiuzhengClick(laneData) {
        let data = laneData.data;
        this.clickedLaneCode = laneData.laneCode;
        this.dialogVisible = true;
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
      quickSubmit(laneData){
        let data = laneData.data;
        this.clickedLaneCode = laneData.laneCode;
        this.clickedData = JSON.parse(JSON.stringify(data));
        let username = JSON.parse(sessionStorage.user).username;
        if(this.clickedData.ocrFrontContainer.ocrContainerNo != null
          && this.clickedData.ocrFrontContainer.ocrContainerNo != ''
          && this.clickedData.ocrFrontContainer.ocrContainerNo != undefined){
          this.clickedData.ocrFrontContainer.ocrDamage = 'NM'
        }
        if(this.clickedData.ocrAfterContainer.ocrContainerNo != null
          && this.clickedData.ocrAfterContainer.ocrContainerNo != ''
          && this.clickedData.ocrAfterContainer.ocrContainerNo != undefined){
          this.clickedData.ocrAfterContainer.ocrDamage = 'NM'
        }
        let todoUpdateData = JSON.stringify(this.clickedData);
        console.log('todoUpdateData',todoUpdateData);
        this.$axios.get('/hdGate/monitor/updateBusinessData',
          {
            params: {
              'account': username,
              laneCode: this.clickedLaneCode,
              visitGuid: this.clickedData.visitGuid,
              todoUpdateData: todoUpdateData
            }
          }).then(data => {
          this.$message({
            message: '提交成功',
            type: 'success'
          });
        }, response => {
          console.log('queryGateLaneByUser error');
          this.$message({
            message: '提交失败',
            type: 'warning'
          });
        })
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
