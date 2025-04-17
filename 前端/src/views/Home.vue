<template>
    <div>
      <el-card>
        <el-row>
          <el-col :span="6">
            <div style="width: 300px; height: 200px">
              <el-card style="color: #409EFF;">
                <i class="el-icon-s-custom">用户总数</i>
                <br><br>
                <span>{{ custom }}</span>
              </el-card>
            </div>
          </el-col>
          <el-col :span="6">
            <div style="width: 300px; height: 200px">
              <el-card style="color: #67C23A;">
                <i class="el-icon-medal">申请总量</i>
                <br><br>
                <span>{{ medal }}</span>
              </el-card>
            </div>
          </el-col>
          <el-col :span="6">
            <div style="width: 300px; height: 200px">
              <el-card style="color: #F56C6C;">
                <i class="el-icon-money">安装数量</i>
                <br><br>
                <span>{{ money }}</span>
              </el-card>
            </div>
          </el-col>
          <!-- <el-col :span="6">
            <div style="width: 300px; height: 200px">
              <el-card style="color: #E6A23C;">
                <i class="el-icon-s-shop">入库总量</i>
                <br><br>
                <span>{{ shop }}</span>
              </el-card>
            </div>
          </el-col> -->
        </el-row>
        <el-row>
          <!-- <el-col :span="12">
            <div id="main" style="width: 500px; height: 400px"></div>
          </el-col>
          <el-col :span="12">
            <div id="pie" style="width: 500px; height: 400px"></div>
          </el-col> -->
        </el-row>
      </el-card>
    </div>
  </template>
  
  <script>
  
  import * as echarts from 'echarts'
  
  export default {
    name: "Home",
    data() {
      return {
        custom: 0,
        medal: 0,
        money: 0,
        shop: 0,
      }
    },
    created() {
      this.load()
    },
    methods:{
      load(){
        this.request.get("/home/countUsers").then(res => {
        if(res.code === '200'){
          this.custom = res.data.counts
          this.medal = res.data.countMedals
          this.money = res.data.countMoney
          this.shop = res.data.countShop
        }
      })
      }
    },
    mounted() {  // 页面元素渲染之后再触发
      var option = {
        title: {
          text: '各季度发放烟叶次数统计',
          subtext: '趋势图',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        xAxis: {
          type: 'category',
          data: ["第一季度", "第二季度", "第三季度", "第四季度"]
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: "烟叶次数",
            data: [],
            type: 'line'
          },
          {
            data: [],
            type: 'bar'
          }
        ]
      };
      
      var chartDom = document.getElementById('main');
      var myChart = echarts.init(chartDom);
      
      this.request.get("/home/members").then(res => {
        // 填空
        // option.xAxis.data = res.data.x
        option.series[0].data = res.data
        option.series[1].data = res.data
        // 数据准备完毕之后再set
        myChart.setOption(option);
  
      })
      // 饼图
  
      var pieOption = {
        title: {
          text: '各季度发放烟叶次数统计',
          subtext: '比例图',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: "用户数量",
            type: 'pie',
            radius: '55%',
            center: ['25%', '70%'],
            label: {            //饼图图形上的文本标签
              normal: {
                show: true,
                position: 'inner', //标签的位置
                textStyle : {
                  fontWeight : 300 ,
                  fontSize : 14,    //文字的字体大小
                  color: "#fff"
                },
                formatter:'{c}'
              }
            },
            data: [],  // 填空
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          },
          {
            name: "用户数量",
            type: 'pie',
            radius: '50%',
            center: ['75%', '50%'],
            label:{            //饼图图形上的文本标签
              normal:{
                show: true,
                position: 'inner', //标签的位置
                textStyle : {
                  fontWeight : 300 ,
                  fontSize : 14,    //文字的字体大小
                  color: "#fff"
                },
                formatter:'{d}%'
              }
            },
            data: [],  // 填空
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
  
      var pieDom = document.getElementById('pie');
      var pieChart = echarts.init(pieDom);
  
      this.request.get("/home/members").then(res => {
  
          pieOption.series[0].data = [
              {name: "第一季度", value: res.data[0]},
              {name: "第二季度", value: res.data[1]},
              {name: "第三季度", value: res.data[2]},
              {name: "第四季度", value: res.data[3]},
          ]
          pieOption.series[1].data = [
              {name: "第一季度", value: res.data[0]},
              {name: "第二季度", value: res.data[1]},
              {name: "第三季度", value: res.data[2]},
              {name: "第四季度", value: res.data[3]},
          ]
          pieChart.setOption(pieOption)
      })
  
    }
  }
  </script>
  
  