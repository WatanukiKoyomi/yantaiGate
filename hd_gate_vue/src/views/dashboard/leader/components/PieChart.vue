<template>
  <div :style="{width: '100%', height: '300px'}" ref="pie"></div>
</template>

<script>
  export default {
    data () {
      return {
        yearsData: [] // [{value: 0, name: ''}, {value: 0, name: ''}]
      }
    },
    mounted () {
      this.fetchYearsData()
    },
    beforeDestroy () {
    },
    methods: {
      fetchYearsData () {
        var that = this
        this.$axios.get('/hdGate/history/queryYearsData').then(data => {
          console.log('queryYearsData', data)
          this.yearsData = []
          data.forEach(function (d) {
            var val = d.name.substring(5) + '月'
            var monthData = {value: d.cntrCount, name: val}
            that.yearsData.push(monthData)
          })
          // console.log(that.yearsData, 'ssssssssssssssss')
          this.initChart()
        }, response => {
          console.log('queryYearsData error')
        })
      },
      initChart () {
        var that = this
        var myChart = this.$echarts.init(this.$refs.pie)
        var option = {
          // backgroundColor: '#2c343c',
          title: {
            text: '当年，每月箱量分布图',
            left: 'center',
            top: 20,
            textStyle: {
              color: 'black'
            }
          },
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          visualMap: {
            show: false,
            min: 150,
            max: 5000,
            inRange: {
              colorLightness: [0.4, 1]
            }
          },
          series: [
            {
              name: '箱量',
              type: 'pie',
              radius: '55%',
              center: ['50%', '50%'],
              data: that.yearsData.sort(function (a, b) { return a.value - b.value }),
              roseType: 'radius',
              label: {
                normal: {
                  textStyle: {
                    color: 'gray'
                  }
                }
              },
              labelLine: {
                normal: {
                  lineStyle: {
                    color: 'gray'
                  },
                  smooth: 0.2,
                  length: 10,
                  length2: 20
                }
              },
              itemStyle: {
                normal: {
                  color: '#c23531',
                  shadowBlur: 200,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              },
              animationType: 'scale',
              animationEasing: 'elasticOut',
              animationDelay: function (idx) {
                return Math.random() * 200
              }
            }
          ]
        }
        window.onresize = myChart.resize
        myChart.setOption(option)
      }
    }
  }
</script>
