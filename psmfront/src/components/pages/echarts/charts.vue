<template>
     <div ref="charts" :style="chartStyle"></div>
</template>

<script setup lang="ts">
import {ref,onMounted,watchEffect} from 'vue'
import * as echarts from 'echarts'
const props=defineProps({
    chartOptions: {     // 接收配置项的参数
      type: Object,
      required: true
    },
    chartStyle: {    // 接收样式的参数
      type: Object,
      default: () => ({ width: '400px', height: '300px' })
    },
})
const charts = ref()
const styles=ref()
const options = ref()
let chart: echarts.ECharts
onMounted(()=>{
  styles.value = props.chartStyle
  options.value = props.chartOptions
  initEcharts()
})
watchEffect(()=>{
  styles.value = props.chartStyle
  options.value = props.chartOptions
  
  setTimeout(()=>{
    initEcharts()
  },100)
  
})
const initEcharts = ()=>{
    if(chart!==null&&chart!=undefined){
      chart.dispose()
    }
    chart = echarts.init(charts.value)
    chart.setOption(props.chartOptions);
}
</script>

<style scoped lang="scss">

</style>