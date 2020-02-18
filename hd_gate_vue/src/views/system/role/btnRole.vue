<template>
  <div style="height: 100%;">
    <el-button class="filter-item" type="success">保存</el-button>
    321:{{roleId}}
  </div>
</template>
<script>
  export default{
    name: 'menuRole',
    data () {
      return {
      }
    },
    props: ['roleId']
  }
</script>
