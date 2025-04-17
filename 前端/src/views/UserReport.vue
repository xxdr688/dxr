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
       
        </el-row>
        <el-row>
          <el-col :span="12">
             <el-button type="danger" @click="oper('OPEN')">开门<i class="el-icon-remove-outline"></i></el-button>
            <el-button type="danger" @click="oper('CLOSE')">关门<i class="el-icon-remove-outline"></i></el-button>
          </el-col>
        </el-row>
        
    </el-card>
    <el-card>
       <el-col :span="12">
           <div id="main" style="width: 100%; height: 400px"></div>
       </el-col>
      <el-col :span="12">
            <div id="main2" style="width: 100%; height: 400px"></div>
       </el-col>
    </el-card>
    <el-card>
      
    </el-card>
    <el-card>
      <div id="main3" style="width: 100%; height: 400px"></div>
    </el-card>
    <el-card>
      <div id="main4" style="width: 100%; height: 400px"></div>
    </el-card>
  </div>
</template>

<script>

import * as echarts from 'echarts'

export default {
  name: "UserReport",
  data() {
    return {
        custom: 0,
        medal: 0,
        money: 0,
        shop: 0,
        wdTableData: [],
        wdTotal: 0,
        wdPageNum: 1,
        wdPageSize: 10,
        sdTableData: [],
        sdTotal: 0,
        sdPageNum: 1,
        sdPageSize: 10,
        fireTableData: [],
        fireTotal: 0,
        firePageNum: 1,
        firePageSize: 10,
        wdVal:"",
        sdVal:"",
        fireVal:"",
        timerID: null
    }
  },
  created() {
      this.load();
      // this.loadWd();
      // this.loadSd();
      // this.loadFire();
      // this.loadNew();
    },
     mounted() {  
         this.loadWd();
          this.loadSd();
          //this.loadGz()
          //this.loadFire();
     },
   beforeDestroy() {
    // 清除定时任务
    clearInterval(this.timerID);
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
      },
     getRfId(){
      this.request.get("/data/rfid").then(res => {
        console.log(res)
    })
    },
      oper(state){
        //硬件控制
        this.request.get("/data/control/"+state).then(res => {
          if(res.code === 200){
           this.$message.success("操作成功")
        }
        })
      },
      loadWd(){
        //温度数据
          var chartDom = document.getElementById('main');
              var myChart = echarts.init(chartDom);
              var option;
              option = {
                title: {
                  text: '用电量'
                },
                tooltip: {
                  trigger: 'axis'
                },
                grid: {
                  left: '3%',
                  right: '4%',
                  bottom: '3%',
                  containLabel: true
                },
                toolbox: {
                  feature: {
                    saveAsImage: {}
                  }
                },
                xAxis: {
                  type: 'category',
                  boundaryGap: false,
                  data: []
                },
                yAxis: {
                  type: 'value'
                },
                series: [
                  {
                    name:'电量',
                    type: 'line',
                    stack: 'Total',
                    data: []
                  },
                  {
                    name:'电压',
                    type: 'line',
                    stack: 'Total',
                    data: []
                  }
                ]
              };
        this.request.get("/wd/page", {
                params: {
            pageNum: this.wdPageNum,
                    pageSize: 100,
        }
        }).then(res => {
                this.wdTableData = res.data.records
               
               let data = res.data.records   
              data.forEach((item)=>{
                        option.xAxis.data.push(item.createTime)
                        option.series[0].data.push(item.sjVal)
                        option.series[1].data.push(item.dyVal)
                    })
              option && myChart.setOption(option);
        })
    },
    loadSd(){
        //湿度数据
        var chartDom = document.getElementById('main2');
              var myChart = echarts.init(chartDom);
              var option;
              option = {
                title: {
                  text: '用水量'
                },
                tooltip: {
                  trigger: 'axis'
                },
                grid: {
                  left: '3%',
                  right: '4%',
                  bottom: '3%',
                  containLabel: true
                },
                toolbox: {
                  feature: {
                    saveAsImage: {}
                  }
                },
                xAxis: {
                  type: 'category',
                  boundaryGap: false,
                  data: []
                },
                yAxis: {
                  type: 'value'
                },
                series: [
                  {
                    name:'水量',
                    type: 'line',
                    stack: 'Total',
                    data: []
                  }
                ]
              };
        this.request.get("/wsd/page", {
                params: {
            pageNum: this.sdPageNum,
                    pageSize: 100,
        }
        }).then(res => {
                this.wdTableData = res.data.records
               
               let data = res.data.records   
              data.forEach((item)=>{
                        option.xAxis.data.push(item.createTime)
                        option.series[0].data.push(item.sjVal)
                    })
              option && myChart.setOption(option);
        })
    },
    loadFire(){
        //烟雾
        var chartDom = document.getElementById('main3');
              var myChart = echarts.init(chartDom);
              var option;
              option = {
                title: {
                  text: '烟雾'
                },
                tooltip: {
                  trigger: 'axis'
                },
                grid: {
                  left: '3%',
                  right: '4%',
                  bottom: '3%',
                  containLabel: true
                },
                toolbox: {
                  feature: {
                    saveAsImage: {}
                  }
                },
                xAxis: {
                  type: 'category',
                  boundaryGap: false,
                  data: []
                },
                yAxis: {
                  type: 'value'
                },
                series: [
                  {
                    type: 'line',
                    stack: 'Total',
                    data: []
                  }
                ]
              };
        this.request.get("/fire/page", {
                params: {
            pageNum: this.firePageNum,
                    pageSize: 100,
        }
        }).then(res => {
               let data = res.data.records   
              data.forEach((item)=>{
                        option.xAxis.data.push(item.createTime)
                        option.series[0].data.push(item.sjVal)
                    })
              option && myChart.setOption(option);
        })
    },
     loadGz(){
        //烟雾
        var chartDom = document.getElementById('main4');
              var myChart = echarts.init(chartDom);
              var option;
              option = {
                title: {
                  text: '光照'
                },
                tooltip: {
                  trigger: 'axis'
                },
                grid: {
                  left: '3%',
                  right: '4%',
                  bottom: '3%',
                  containLabel: true
                },
                toolbox: {
                  feature: {
                    saveAsImage: {}
                  }
                },
                xAxis: {
                  type: 'category',
                  boundaryGap: false,
                  data: []
                },
                yAxis: {
                  type: 'value'
                },
                series: [
                  {
                    type: 'line',
                    stack: 'Total',
                    data: []
                  }
                ]
              };
        this.request.get("/gz/page", {
                params: {
            pageNum: this.firePageNum,
                    pageSize: 100,
        }
        }).then(res => {
               let data = res.data.records   
              data.forEach((item)=>{
                        option.xAxis.data.push(item.createTime)
                        option.series[0].data.push(item.sjVal)
                    })
              option && myChart.setOption(option);
        })
    },
    loadNew(){
        this.request.get("/home/news").then(res => {
                this.wdVal = res.data.wd
                this.sdVal = res.data.sd
                this.fireVal = res.data.fire
        })
    },
    handleWdSizeChange(wdPageSize) {
        this.wdPageSize = wdPageSize
        this.loadWd()
    },
    handleWdCurrentChange(wdPageNum) {
        this.wdPageNum = wdPageNum
        this.loadWd()
    },
    handleSdSizeChange(sdPageSize) {
        this.sdPageSize = sdPageSize
        this.loadSd()
    },
    handleSdCurrentChange(sdPageNum) {
        this.sdPageNum = sdPageNum
        this.loadSd()
    },
     handleFireSizeChange(firePageSize) {
        this.firePageSize = firePageSize
        this.loadFire()
    },
    handleFireCurrentChange(firePageNum) {
        this.firePageNum = firePageNum
        this.loadFire()
    }
    }
  // mounted() {  // 页面元素渲染之后再触发
  //   var option = {
  //     title: {
  //       text: '各季度用户数量统计',
  //       subtext: '趋势图',
  //       left: 'center'
  //     },
  //     tooltip: {
  //       trigger: 'item'
  //     },
  //     legend: {
  //       orient: 'vertical',
  //       left: 'left'
  //     },
  //     xAxis: {
  //       type: 'category',
  //       data: ["第一季度", "第二季度", "第三季度", "第四季度"]
  //     },
  //     yAxis: {
  //       type: 'value'
  //     },
  //     series: [
  //       {
  //         name: "用户数量",
  //         data: [],
  //         type: 'line'
  //       },
  //       {
  //         data: [],
  //         type: 'bar'
  //       }
  //     ]
  //   };
    
  //   var chartDom = document.getElementById('main');
  //   var myChart = echarts.init(chartDom);
    
  //   this.request.get("/echarts/members").then(res => {
  //     // 填空
  //     // option.xAxis.data = res.data.x
  //     option.series[0].data = res.data
  //     option.series[1].data = res.data
  //     // 数据准备完毕之后再set
  //     myChart.setOption(option);

  //   })
  //   // 饼图

  //   var pieOption = {
  //     title: {
  //       text: '各季度用户数量统计',
  //       subtext: '比例图',
  //       left: 'center'
  //     },
  //     tooltip: {
  //       trigger: 'item',
  //       formatter: '{a} <br/>{b} : {c} ({d}%)'
  //     },
  //     legend: {
  //       orient: 'vertical',
  //       left: 'left'
  //     },
  //     series: [
  //       {
  //         name: "用户数量",
  //         type: 'pie',
  //         radius: '55%',
  //         center: ['25%', '70%'],
  //         label: {            //饼图图形上的文本标签
  //           normal: {
  //             show: true,
  //             position: 'inner', //标签的位置
  //             textStyle : {
  //               fontWeight : 300 ,
  //               fontSize : 14,    //文字的字体大小
  //               color: "#fff"
  //             },
  //             formatter:'{c}'
  //           }
  //         },
  //         data: [],  // 填空
  //         emphasis: {
  //           itemStyle: {
  //             shadowBlur: 10,
  //             shadowOffsetX: 0,
  //             shadowColor: 'rgba(0, 0, 0, 0.5)'
  //           }
  //         }
  //       },
  //       {
  //         name: "用户数量",
  //         type: 'pie',
  //         radius: '50%',
  //         center: ['75%', '50%'],
  //         label:{            //饼图图形上的文本标签
  //           normal:{
  //             show: true,
  //             position: 'inner', //标签的位置
  //             textStyle : {
  //               fontWeight : 300 ,
  //               fontSize : 14,    //文字的字体大小
  //               color: "#fff"
  //             },
  //             formatter:'{d}%'
  //           }
  //         },
  //         data: [],  // 填空
  //         emphasis: {
  //           itemStyle: {
  //             shadowBlur: 10,
  //             shadowOffsetX: 0,
  //             shadowColor: 'rgba(0, 0, 0, 0.5)'
  //           }
  //         }
  //       }
  //     ]
  //   };

  //   var pieDom = document.getElementById('pie');
  //   var pieChart = echarts.init(pieDom);

  //   this.request.get("/echarts/members").then(res => {

  //       pieOption.series[0].data = [
  //           {name: "第一季度", value: res.data[0]},
  //           {name: "第二季度", value: res.data[1]},
  //           {name: "第三季度", value: res.data[2]},
  //           {name: "第四季度", value: res.data[3]},
  //       ]
  //       pieOption.series[1].data = [
  //           {name: "第一季度", value: res.data[0]},
  //           {name: "第二季度", value: res.data[1]},
  //           {name: "第三季度", value: res.data[2]},
  //           {name: "第四季度", value: res.data[3]},
  //       ]
  //       pieChart.setOption(pieOption)
  //   })

  // }
}
</script>

