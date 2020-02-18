<!--车顶号-->
<template>
  <el-table :data="list" style="width: 100%;padding-top: 15px;">
    <el-table-column label="时间" show-overflow-tooltip><!--时间-->
      <template slot-scope="scope">
        {{scope.row.name}}
      </template>
    </el-table-column>
    <el-table-column label="车次" align="center"><!--车次-->
      <template slot-scope="scope">
        {{scope.row.truckCount}}
      </template>
    </el-table-column>
    <el-table-column label="识别率" align="center"><!--识别率-->
      <template slot-scope="scope">
        {{Math.round(scope.row.truckRation*100)/100}}%
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
      /* fetchData () {
        this.list = [{name: 'b', value: 'b', recognitionRation: '20'}]
        this.$axios.get('/crane/statisticsController/queryTruckNoRation').then(data => {
          console.log('queryCarIdRation', data)
          this.list = data
        }, response => {
          console.log('queryCarIdRation error')
        })
      } */
    }
  }
</script>
