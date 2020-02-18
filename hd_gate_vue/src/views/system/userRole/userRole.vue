<template>
  <el-container>
    <el-aside width="40%">
      <userTable ref='userTable'></userTable>
    </el-aside>
    <el-main>
      <roleTable ref='roleTable'></roleTable>
    </el-main>
  </el-container>
</template>
<script>
  import userTable from './userTable'
  import roleTable from './roleTable'
  import bus from '@/assets/eventBus'
  export default {
    components: {
      roleTable, userTable
    },
    data () {
      return {
        roleId: null,
        activeName: 'menu'
      }
    },
    methods: {
    },
    mounted () {
      bus.$on('roleRowClick', function (msg) {
        this.roleId = msg.role
      })
    }
  }
</script>
