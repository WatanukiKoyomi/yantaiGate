<template>
  <el-container style="height: 100%;">
    <el-aside style="height: 100%;">
      <users-dept-tree ref='usersDeptTree'></users-dept-tree>
    </el-aside>
    <el-main>
      <user-table ref='userTable' @change="change"></user-table>
    </el-main>
  </el-container>
</template>
<script>
  import usersDeptTree from './usersDeptTree'
  import userTable from './userTable'

  export default {
    components: {
      usersDeptTree, userTable
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
