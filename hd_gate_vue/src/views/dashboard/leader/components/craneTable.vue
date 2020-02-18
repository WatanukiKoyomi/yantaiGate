<!--岸桥作业效率（作业箱数/小时）-->
<template>
  <el-table :data="datalist" style="width: 100%;padding-top: 15px;">
    <el-table-column label="时间" show-overflow-tooltip><!--时间-->
      <template slot-scope="scope">
        {{scope.row[0]}}
      </template>
    </el-table-column>
    <el-table-column  :label="crane" align="center"  v-for="(crane,index) in craneList" :key="crane"><!--车次-->
      <template slot-scope="scope">
        {{Math.round(scope.row[index+1]*100)/100}} <!--乘以100然后四舍五入，之后除以100，保留2位小数-->
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
  export default {
    data () {
      return {
        datalist: [['2019-06-05', 1, 2], ['2019-06-05', 3, 4]],
        craneList: ['G01', 'Q01']
      }
    },
    filters: {
    },
    created () {
      this.queryCraneList()
    },
    methods: {
      // 获取作业过的岸桥列表
      queryCraneList () {
        var that = this
        this.$axios.get('/crane/statisticsController/queryCraneList').then(data => {
          console.log('queryCraneList', data)
          this.craneList = data
          that.$axios.get('/crane/statisticsController/queryCraneEfficiency', {params: { 'craneList': data.toString() }}).then(d => {
            console.log('queryCraneEfficiency', d)
            that.datalist = d
          }, response => {
            console.log('queryCraneEfficiency error')
          })
        }, response => {
          console.log('queryCraneList error')
        })
      }
    }
  }
</script>
