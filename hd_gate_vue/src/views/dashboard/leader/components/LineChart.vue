<template>
  <div ref="bar" :style="{width: '100%', height: '300px'}"></div>
</template>

<script>
  export default {
    components: {
    },
    data () {
      return {
        xAxix: [],
        lastWeek: [],
        currWeek: []
      }
    },
    mounted () {
      this.fetchWeeksXAxis()
    },
    methods: {
      // 获取横坐标
      fetchWeeksXAxis () {
        this.$axios.get('/hdGate/history/queryWeeksXAxis').then(data => {
          console.log('queryWeeksXAxis', data)
          this.xAxix = data
          this.fetchWeeksData()
        }, response => {
          console.log('queryWeeksXAxis error')
        })
      },
      fetchWeeksData () {
        var that = this
        this.$axios.get('/hdGate/history/queryWeeksData').then(data => {
          console.log('queryWeeksData', data)
          var datas = data
          that.lastWeek = [] // 清空
          that.currWeek = []
          that.xAxix.forEach(function (xName, index) {
            var curData = ''
            datas.forEach(function (d) {
              if (xName === d.name) {
                curData = d.cntrCount
              }
            })
            if (index < 7) {
              that.lastWeek.push(curData)
            } else {
              that.currWeek.push(curData)
            }
          })
          that.initLineChart() // 绘制图形
        }, response => {
          console.log('queryWeeksData error')
        })
      },
      initLineChart () {
        var myChart = this.$echarts.init(this.$refs.bar)
        // 绘制图表
        var option = {
          title: {
            text: '本周与上周箱量对比图'
          },
          tooltip: {},
          xAxis: {
            data: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日']
          },
          yAxis: {},
          series: [
            {
              name: '本周',
              type: 'bar',
              data: this.currWeek
            },
            {
              name: '上周',
              type: 'bar',
              data: this.lastWeek
            }
          ]
        }
        window.onresize = myChart.resize
        myChart.setOption(option)
      }
    }
  }
</script>
