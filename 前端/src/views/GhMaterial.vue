<template>
    <div>
        <el-card>
            <div style="padding: 10px 0;">
                <el-form label-width="80px">
                    <el-row>
                    <el-input style="width: 200px;" placeholder="请输入申领人" suffix-icon="el-icon-search" v-model="name"></el-input>
                    <el-input style="width: 200px;" placeholder="请输入烟叶名称" suffix-icon="el-icon-message" v-model="wzname" class="ml-5"></el-input>
                     <el-input style="width: 200px;" placeholder="请输入烟叶RFID" suffix-icon="el-icon-message" v-model="rfid" class="ml-5"></el-input>
                    <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
                    <el-button type="warning" @click="reset">重置</el-button>
                    </el-row>
                </el-form>
            </div>
            <div style="margin: 10px 0;">
        </div>

        <el-table :data="tableData" border stripe @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="name" label="申领人" width="160" align="center"></el-table-column>
          <el-table-column prop="wzname" label="烟叶名称" width="160" align="center"></el-table-column>
          <el-table-column prop="wznumber" label="申领数量" width="160" align="center"></el-table-column>
          <el-table-column prop="wztime" label="申领时间" width="280" align="center" :formatter="dateFormat"></el-table-column>
          <el-table-column prop="flagnumber" label="是否入库" align="center">
            <template slot-scope="scope">
              <el-tag type="warning" v-if="scope.row.flagnumber === 0">未入库</el-tag>
              <el-tag type="primary" v-if="scope.row.flagnumber === 1">已入库</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作"  width="300" align="center">
            <template slot-scope="scope">
              <el-popconfirm
                  class="ml-5"
                  confirm-button-text='确定'
                  cancel-button-text='我再想想'
                  icon="el-icon-info"
                  icon-color="red"
                  title="您确定入库吗？"
                  @confirm="Warehousing(scope.row.id)">
              
                <el-button type="primary" slot="reference">入库<i class="el-icon-remove-outline"></i></el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
        <div style="padding: 10px 0;">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[10, 15, 20, 25]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </div>
        </el-card>
    </div>
</template>
<script>
import moment from 'moment'
export default {
  name: "Material",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      wzname: "",
      rfid:"",
      intervaID: null,
      form: {
        wzname : ""
      },
      dialogFormVisible: false,
      multipleSelection: [],
      roles: [],
    }
  },
  created() {
   this.intervaID = setInterval(() => {
      this.getRfId()
    }, 5000); // 每秒更新一次时间
    this.load()
  },
  beforeDestroy(){
   clearInterval(this.intervaID)
  },
  methods: {
    // 编写日期格式化方法
    dateFormat: function(row, column) {
      const date = row[column.property]
      if (date === undefined) {
        return ''
      }
      // 这里的格式根据需求修改
      return moment(date).format('YYYY-MM-DD HH:mm:ss')
    },
        getRfId(){
      this.request.get("/data/rfid").then(res => {
        console.log(res)
        if(res.data){
             this.rfid = res.data
        }
 
    })
    },
    load() {
      this.request.get("/wzsl/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          wzname: this.wzname,
          rfid:this.rfid
        }
      }).then(res => {
        if(res.code === '200'){
          this.tableData = res.data.records
          this.total = res.data.total
        }else{
          this.$router.push('/login')
          this.$message.success("退出成功")
        }
      })
      this.request.get("/warehouse").then(res => {
        this.roles = res.data
      })
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    Warehousing(id) {
      this.request.put("/wzsl/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("入库成功")
          this.load()
        } else {
          this.$message.error("入库失败")
        }
      })
    },
    reset() {
      this.name = ""
      this.wzname = ""
      this.load()
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    }
  }
}
</script>