<template>
  <div class="dashboard-container">
    <component :is="userDashboard"></component>
  </div>
</template>
<script type="text/javascript">
  import leader from './leader'
  import operator from './operator'
  import service from '../service/monitor.vue'
  export default {
    name: 'dashboard',
    components: { leader, operator, service },
    data () {
      return {
        userDashboard: 'operator'
      }
    },
    mounted () {
      this.initIndex()
      this.initRedis()
    },
    methods: {
      initIndex () {
        var account = JSON.parse(sessionStorage.user).username
        if (account === 'admin') {
          this.userDashboard = 'leader'
        } else {
          this.userDashboard = 'service'
        }
      },
      initRedis () {
        /* this.$axios.get('/crane/sys/initRedis').then(data => {
          console.log('initRedis success')
        }, response => {
          console.log('initRedis error')
        }) */
      }
    }
  }
</script>
<style type="text/css">

</style>
