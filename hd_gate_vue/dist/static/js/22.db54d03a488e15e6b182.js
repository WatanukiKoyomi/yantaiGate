webpackJsonp([22],{"5pVa":function(e,a,l){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var t=l("mvHQ"),n=l.n(t),o=l("uTVu"),r={name:"laneForm",components:{},props:{laneData:{}},data:function(){return{laneForm:{},visible:!1,loading:!1,flag:"init",parentDept:{}}},methods:{show:function(e,a){this.visible=!0,console.log("新增或修改标志:"+e),"add"===e?(this.flag="add",this.laneForm.showFlag="Y"):(this.flag="update",this.laneForm=JSON.parse(n()(a)),this.laneForm.oldLaneCode=this.laneForm.laneCode,this.laneForm.oldLaneDirection=this.laneForm.laneDirection,this.laneForm.oldLaneIp=this.laneForm.laneIp,this.laneForm.oldLanePort=this.laneForm.lanePort,this.laneForm.oldLaneDB=this.laneForm.oldLaneDB)},submit:function(){var e=this;this.$axios.get("/hdGate/laneManagement/insertOrUpdateLane",{params:{laneForm:this.laneForm}}).then(function(a){console.log("insertOrUpdateLane ",a),e.visible=!1,o.a.$emit("initAllGateLanes",a)},function(e){console.log("insertOrUpdateLane error")})},cancle:function(){this.visible=!1,this.laneForm={},this.flag="init"},switchShowFlagChange:function(e){console.log("switchShowFlagChange",e),this.laneForm.showFlag="Y","Y"===e&&(this.laneForm.showFlag="N")}}},s={render:function(){var e=this,a=e.$createElement,l=e._self._c||a;return l("el-dialog",{attrs:{title:"编辑",visible:e.visible,width:"60%"},on:{"update:visible":function(a){e.visible=a}}},[l("el-form",{attrs:{"label-width":"100px",model:e.laneForm}},[l("el-row",[l("el-col",{attrs:{span:8,xs:16}},[l("el-form-item",{attrs:{label:"车道编号",prop:"laneCode",rules:[{required:!0,message:"此处必填",trigger:"blur"},{max:200,message:"输入非法",trigger:"blur"}]}},[l("el-input",{attrs:{type:"text",readonly:"update"==e.flag},model:{value:e.laneForm.laneCode,callback:function(a){e.$set(e.laneForm,"laneCode",a)},expression:"laneForm.laneCode"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:8,xs:8}},[l("el-form-item",{attrs:{label:"车道名称",prop:"laneName",rules:[{required:!0,message:"此处必填",trigger:"blur"},{max:200,message:"输入非法",trigger:"blur"}]}},[l("el-input",{attrs:{type:"text"},model:{value:e.laneForm.laneName,callback:function(a){e.$set(e.laneForm,"laneName",a)},expression:"laneForm.laneName"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:8,xs:8}},[l("el-form-item",{attrs:{label:"进出方向",prop:"laneDirection",rules:[{max:255,message:"输入非法",trigger:"blur"}]}},[l("el-input",{attrs:{type:"text"},model:{value:e.laneForm.laneDirection,callback:function(a){e.$set(e.laneForm,"laneDirection",a)},expression:"laneForm.laneDirection"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:8,xs:16}},[l("el-form-item",{attrs:{label:"是否启用",prop:"showFlag",rules:[{max:255,message:"输入非法",trigger:"blur"}]}},[l("el-switch",{attrs:{"active-text":"启用","inactive-text":"禁用"},on:{change:function(a){e.switchShowFlagChange(e.laneForm.showFlag)}},model:{value:"Y"===e.laneForm.showFlag,callback:function(a){e.$set(e.laneForm,"showFlag === 'Y'",a)},expression:"laneForm.showFlag === 'Y'"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:8,xs:16}},[l("el-form-item",{attrs:{label:"redis编号",prop:"laneDB",rules:[{max:255,message:"输入非法",trigger:"blur"}]}},[l("el-input",{attrs:{type:"number"},model:{value:e.laneForm.laneDB,callback:function(a){e.$set(e.laneForm,"laneDB",a)},expression:"laneForm.laneDB"}})],1)],1)],1)],1),e._v(" "),l("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[l("el-button",{on:{click:e.cancle}},[e._v("取消")]),e._v(" "),l("el-button",{attrs:{type:"primary",loading:e.loading},on:{click:e.submit}},[e._v("确定")])],1)],1)},staticRenderFns:[]},i=l("VU/8")(r,s,!1,null,null,null);a.default=i.exports}});