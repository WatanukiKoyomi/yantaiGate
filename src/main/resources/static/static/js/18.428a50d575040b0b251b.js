webpackJsonp([18],{TyO7:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s={name:"rolePermTree",components:{},data:function(){return{btnsList:[{label:"",describe:"",flag:!1}],menuDatas:[{id:"",label:"",children:[]}]}},props:["roleId","checkedKeys"],methods:{searchMenu:function(){var e=this;this.$axios.get("/hdGate/sys/findAllMenusTree").then(function(t){e.menuDatas=t},function(e){console.log("error")})},roleMenuSubmit:function(){var e=this,t=this.$refs.roleMenuTree.getCheckedKeys();this.$axios.get("/hdGate/sys/updateRoleMenus",{params:{permIds:t.toString(),roleId:this.roleId}}).then(function(t){e.$message({type:"success",message:"菜单信息设置成功,请重新登录获取最新权限"}),location.reload()},function(e){console.log("error")})},showBtns:function(e){var t=this,n=this,s=e.id;n.$axios.get("/hdGate/sys/findAllBtnsByMenuId",{params:{roleId:this.roleId,menuId:s}}).then(function(e){e.map(function(e){n.$set(e,"flag",!1)}),n.$axios.get("/hdGate/sys/findBtnsByRoleAndMenuId",{params:{roleId:t.roleId,menuId:s}}).then(function(t){t.forEach(function(t){var n=t.id;e.forEach(function(e){e.id===n&&(e.flag=!0)})}),n.btnsList=e},function(e){console.log("findBtnsByRoleAndMenuId error")})},function(e){console.log("findAllBtnsByMenuId error")})},switchChange:function(e){var t=this;console.log(e),this.$axios.get("/hdGate/sys/changeRoleBtn",{params:{roleId:this.roleId,btnId:e.id,flag:e.flag}}).then(function(e){t.$message({message:"按钮授权更改成功",type:"success"})},function(e){console.log("error")})}},mounted:function(){this.searchMenu()},watch:{roleId:function(e,t){this.$refs.roleMenuTree.setCheckedKeys(this.checkedKeys),this.searchMenu()}}},o={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-row",[n("el-col",{attrs:{span:10}},[n("div",[e.hasBtnPerm("role_save")?n("el-button",{staticClass:"filter-item",attrs:{type:"success"},on:{click:e.roleMenuSubmit}},[e._v("保存")]):e._e(),e._v(" "),n("el-tree",{ref:"roleMenuTree",attrs:{data:e.menuDatas,"show-checkbox":"","node-key":"id","default-checked-keys":e.checkedKeys,"default-expand-all":"","highlight-current":!1,"expand-on-click-node":!1},on:{"node-click":e.showBtns}})],1)]),e._v(" "),n("el-col",{attrs:{span:14}},[e._v("\n    按钮\n    "),n("el-table",{staticStyle:{width:"100%"},attrs:{data:e.btnsList,stripe:"",border:""}},[n("el-table-column",{attrs:{align:"center",prop:"label",label:"名称"}}),e._v(" "),n("el-table-column",{attrs:{align:"center",prop:"describe",label:"描述"}}),e._v(" "),e.hasBtnPerm("role_btn_change")?n("el-table-column",{attrs:{align:"center",label:"是否赋权",fixed:"right"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-switch",{attrs:{"active-color":"#13ce66","inactive-color":"#ff4949"},on:{change:function(n){e.switchChange(t.row)}},model:{value:t.row.flag,callback:function(n){e.$set(t.row,"flag",n)},expression:"scope.row.flag"}})]}}])}):e._e()],1)],1)],1)},staticRenderFns:[]},l=n("VU/8")(s,o,!1,null,null,null);t.default=l.exports}});