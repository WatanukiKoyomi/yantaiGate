<template>
    <el-tree style="height: 100%;"
      :data="deptDatas"
      node-key="deptId"
      default-expand-all
      :highlight-current="true"
      @node-click="sendMsg"
      :expand-on-click-node="false">
    </el-tree>
</template>

<script>
  import bus from '@/assets/eventBus'
  export default {
    data () {
      return {
        deptDatas: [{ deptId: '', label: '', children: [] }]
      }
    },

    methods: {
      searchDept () {
        var username = JSON.parse(sessionStorage.user).username
        // 要把时间格式化下再传
        this.$axios.get('/hdGate/deptController/queryDeptsByAccount', {params: { username: username }}).then(datas => {
          // const items = datas
          this.deptDatas = datas
        }, response => {
          console.log('error')
        })
      },
      sendMsg (data, node) {
        // var deptId = data.deptId
        bus.$emit('usersClickedDeptId', data)
      }
    },
    mounted () {
      this.searchDept()
    }
  }
</script>

<style>

</style>
