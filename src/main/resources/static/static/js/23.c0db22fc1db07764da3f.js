webpackJsonp([23],{zu9G:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=a("pFYg"),r=a.n(n),o={components:{},data:function(){return{emptyData:{laneCode:"",frontCamera:"",backCamera:"",leftCamera:"",rightCamera:"",leftCdiCamera:"",rightCdiCamera:"",topCdiCamera:"",truckNoCamera:"",truckCamera:"",led:"",plc:"",printer:"",intercom:"",comServer:"",workstation:""},tableData:[{laneCode:"",laneDirection:"",data:{laneCode:"",frontCamera:"",backCamera:"",leftCamera:"",rightCamera:"",leftCdiCamera:"",rightCdiCamera:"",topCdiCamera:"",truckNoCamera:"",truckCamera:"",led:"",plc:"",printer:"",intercom:"",comServer:"",workstation:""}}],listLoading:!0,websockets:[]}},created:function(){this.initAllGateLanesStatus()},methods:{initAllGateLanesStatus:function(){var e=this;this.$axios.get("/hdGate/laneManagement/queryShowGateLanes").then(function(t){t.map(function(t){return t.data=e.emptyData,t}),console.log("queryShowGateLanes",t);var a=window.location.host.split(":")[0],n=JSON.parse(sessionStorage.user).username;t.forEach(function(t){e.$axios.get("/hdGate/monitor/queryLaneEquipmentStatus",{params:{laneCode:t.laneCode}}).then(function(a){t.data=""===a||null===a||void 0===a?e.emptyData:a,console.log("lanedata:",t.data)},function(e){console.log("queryLaneEquipmentStatus error")});var r=new WebSocket("ws://"+a+":8085/hdGate/ws/equipment:"+n+t.laneCode);r.onopen=function(){console.log("equipment:"+n+t.laneCode+"链接webSocket equipment成功...")},r.onerror=function(){console.log("equipment:"+n+lane.laneCode+"链接webSocket失败")},r.onmessage=function(t){console.log("数据已接收...",t);var a=JSON.parse(t.data);e.tableData.forEach(function(e){e.laneCode===a.laneCode&&(e.data=a)})},r.onclose=function(e){console.log("websocket 断开: "+e.code+" "+e.reason+" "+e.wasClean),console.log("equipment:"+n+lane.laneCode+"链接已关闭...")}}),e.tableData=t},function(e){return console.log("queryShowGateLanes error"),e})},isObj:function(e){return e&&"object"==(void 0===e?"undefined":r()(e))&&"[object object]"==Object.prototype.toString.call(e).toLocaleLowerCase()},isArray:function(e){return e&&"object"==(void 0===e?"undefined":r()(e))&&e.constructor==Array},getLength:function(e){var t=0;for(var a in e)t++;return t},compareObj:function(e,t,a){for(var n in e){if(!a)break;if(!t.hasOwnProperty(n)){a=!1;break}if(this.isObj(e[n])){if(!this.isObj(t[n])){a=!1;break}var r=e[n],o=t[n];for(var l in r){if(!a)break;a=this.compareObj(r[l],o[l],a)}}else if(t[n]!==e[n]){a=!1;break}}},compareArr:function(e,t,a){for(var n in e){if(!a)break;a=this.compareObj(e[n],t[n],a)}return a}}},l={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-container",[a("el-main",[a("div",[a("el-table",{staticStyle:{width:"100%"},attrs:{data:e.tableData,stripe:""}},[a("el-table-column",{attrs:{align:"center",prop:"laneCode",label:"车道编号"}}),e._v(" "),a("el-table-column",{attrs:{align:"center",prop:"laneDirection",label:"进出方向"}}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"前相机"},scopedSlots:e._u([{key:"default",fn:function(t){return["0"===t.row.data.frontCamera?a("span",{staticStyle:{color:"red"}},[e._v("异常")]):a("span",{staticStyle:{color:"green"}},[e._v("正常")])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"后相机"},scopedSlots:e._u([{key:"default",fn:function(t){return["0"===t.row.data.backCamera?a("span",{staticStyle:{color:"red"}},[e._v("异常")]):a("span",{staticStyle:{color:"green"}},[e._v("正常")])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"左相机"},scopedSlots:e._u([{key:"default",fn:function(t){return["0"===t.row.data.leftCamera?a("span",{staticStyle:{color:"red"}},[e._v("异常")]):a("span",{staticStyle:{color:"green"}},[e._v("正常")])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"右相机"},scopedSlots:e._u([{key:"default",fn:function(t){return["0"===t.row.data.rightCamera?a("span",{staticStyle:{color:"red"}},[e._v("异常")]):a("span",{staticStyle:{color:"green"}},[e._v("正常")])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"左验残相机"},scopedSlots:e._u([{key:"default",fn:function(t){return["0"===t.row.data.leftCdiCamera?a("span",{staticStyle:{color:"red"}},[e._v("异常")]):a("span",{staticStyle:{color:"green"}},[e._v("正常")])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"右验残相机"},scopedSlots:e._u([{key:"default",fn:function(t){return["0"===t.row.data.rightCdiCamera?a("span",{staticStyle:{color:"red"}},[e._v("异常")]):a("span",{staticStyle:{color:"green"}},[e._v("正常")])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"上验残相机"},scopedSlots:e._u([{key:"default",fn:function(t){return["0"===t.row.data.topCdiCamera?a("span",{staticStyle:{color:"red"}},[e._v("异常")]):a("span",{staticStyle:{color:"green"}},[e._v("正常")])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"车牌相机"},scopedSlots:e._u([{key:"default",fn:function(t){return["0"===t.row.data.truckNoCamera?a("span",{staticStyle:{color:"red"}},[e._v("异常")]):a("span",{staticStyle:{color:"green"}},[e._v("正常")])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"车架相机"},scopedSlots:e._u([{key:"default",fn:function(t){return["0"===t.row.data.truckCamera?a("span",{staticStyle:{color:"red"}},[e._v("异常")]):a("span",{staticStyle:{color:"green"}},[e._v("正常")])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"led"},scopedSlots:e._u([{key:"default",fn:function(t){return["0"===t.row.data.led?a("span",{staticStyle:{color:"red"}},[e._v("异常")]):a("span",{staticStyle:{color:"green"}},[e._v("正常")])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"plc"},scopedSlots:e._u([{key:"default",fn:function(t){return["0"===t.row.data.plc?a("span",{staticStyle:{color:"red"}},[e._v("异常")]):a("span",{staticStyle:{color:"green"}},[e._v("正常")])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"打印机"},scopedSlots:e._u([{key:"default",fn:function(t){return["0"===t.row.data.printer?a("span",{staticStyle:{color:"red"}},[e._v("异常")]):a("span",{staticStyle:{color:"green"}},[e._v("正常")])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"对讲终端"},scopedSlots:e._u([{key:"default",fn:function(t){return["0"===t.row.data.intercom?a("span",{staticStyle:{color:"red"}},[e._v("异常")]):a("span",{staticStyle:{color:"green"}},[e._v("正常")])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"串口服务器"},scopedSlots:e._u([{key:"default",fn:function(t){return["0"===t.row.data.comServer?a("span",{staticStyle:{color:"red"}},[e._v("异常")]):a("span",{staticStyle:{color:"green"}},[e._v("正常")])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"识别工作站"},scopedSlots:e._u([{key:"default",fn:function(t){return["0"===t.row.data.workstation?a("span",{staticStyle:{color:"red"}},[e._v("异常")]):a("span",{staticStyle:{color:"green"}},[e._v("正常")])]}}])})],1)],1)])],1)},staticRenderFns:[]},c=a("VU/8")(o,l,!1,null,null,null);t.default=c.exports}});