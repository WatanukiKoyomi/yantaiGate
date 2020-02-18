<!--集装箱-->
<template>
  <el-table :data="list" style="width: 100%;padding-top: 15px;">
    <el-table-column label="日期" show-overflow-tooltip><!--日期-->
      <template slot-scope="scope">
        {{scope.row.name}}
      </template>
    </el-table-column>
    <el-table-column label="数量" align="center"><!--数量-->
      <template slot-scope="scope">
        {{scope.row.cntrCount}}
      </template>
    </el-table-column>
    <el-table-column label="识别率" align="center"><!--识别率-->
      <template slot-scope="scope">
        {{Math.round(scope.row.cntrRation*100)/100}}%
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
  import bus from '@/assets/eventBus'
  export default {
    data () {
      return {
        list: null
      }
    },
    filters: {
      statusFilter (status) {
        const statusMap = {
          success: 'success',
          pending: 'danger'
        }
        return statusMap[status]
      }
    },
    created () {
      var that = this
      bus.$on('listRation', function (data) {
        that.list = data
      })
    },
    methods: {
    }
  }
</script>
