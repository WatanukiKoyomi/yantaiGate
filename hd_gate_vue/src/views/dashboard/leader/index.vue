<template>
  <el-row :gutter="8">
    <panel-group></panel-group>
    <el-row>
      <el-col :xs="{span: 24}" :sm="{span: 24}" :md="{span: 24}" :lg="{span: 12}" :xl="{span: 16}" style="padding: 1px 10px;margin-bottom:20px;">
        <line-chart></line-chart>
      </el-col>
      <el-col :xs="{span: 24}" :sm="{span: 24}" :md="{span: 24}" :lg="{span: 12}" :xl="{span: 8}" style="padding: 1px 10px;margin-bottom:20px;">
        <pie-chart></pie-chart>
      </el-col>
    </el-row>
    <!--识别率表格-->
    <el-row>
      <el-col :xs="{span: 24}" :sm="{span: 24}" :md="{span: 24}" :lg="{span: 12}" :xl="{span: 12}" style="padding: 1px 10px;margin-bottom:20px;">
        <el-card class="box-card">
          <div>
            <span><strong>车顶号识别率</strong></span><!--车牌识别率-->
          </div>
          <div style="margin-bottom:10px;">
            <car-id-table></car-id-table>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="{span: 24}" :sm="{span: 24}" :md="{span: 24}" :lg="{span: 12}" :xl="{span: 12}" style="padding: 1px 10px;margin-bottom:30px;">
        <el-card class="box-card">
          <div>
            <span><strong>箱号识别率</strong></span><!--箱号识别率-->
          </div>
          <div style="margin-bottom:10px;">
            <box-id-table></box-id-table>
          </div>
        </el-card>
      </el-col>

    </el-row>
  </el-row>
</template>

<script>
  import bus from '@/assets/eventBus'
  import LineChart from './components/LineChart'
  import PieChart from './components/PieChart'
  import carIdTable from './components/carIdTable'
  import boxIdTable from './components/boxIdTable'
  import craneTable from './components/craneTable'
  import PanelGroup from './components/PanelGroup'
  export default {
    name: 'dashboard-leader',
    components: {
      carIdTable,
      boxIdTable,
      LineChart,
      PieChart,
      craneTable,
      PanelGroup
    },
    data () {
      return {
        listRation: ''
      }
    },
    methods: {
      beforeunloadHandler (e) {
        /* var username = JSON.parse(sessionStorage.user).username
        this.$axios.get('/crane/userController/cacheService', {params: { 'username': username }}).then(data => {
          console.log('queryAllUsers success', data)
        }, response => {
          console.log('initShip error')
        }) */
      },
      // 查询集卡号，箱号识别率
      fetchRationData () {
        this.$axios.get('/hdGate/history/queryRation').then(data => {
          console.log('queryCarIdRation', data)
          this.listRation = data
          bus.$emit('listRation', data)
        }, response => {
          console.log('queryCarIdRation error')
        })
      }
    },
    mounted () {
      this.fetchRationData()
      window.addEventListener('beforeunload', e => this.beforeunloadHandler(e))
    },
    destroyed () {
      window.removeEventListener('beforeunload', e => this.beforeunloadHandler(e))
    }
  }
</script>
