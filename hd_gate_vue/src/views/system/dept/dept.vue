<template>
  <el-container style="height: 100%;">
    <el-aside style="height: 100%;">
      <dept-tree ref='deptTree'></dept-tree>
    </el-aside>
    <el-main>
      <deptTable ref='deptTable' @change="change"></deptTable>
    </el-main>
  </el-container>
</template>
<script>
  import deptTree from './deptTree'
  import deptTable from './deptTable'

  export default {
    components: {
      deptTree, deptTable
    },
    data () {
      return {
      }
    },
    methods: {
      change: function (data) {
      }
    }
  }
</script>
