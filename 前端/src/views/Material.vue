<template>
    <div>
        <el-card>
            <div style="padding: 10px 0;">
                <el-form label-width="80px">
                    <el-row>
                    <el-input style="width: 200px;" placeholder="请输入设备编号" suffix-icon="el-icon-search" v-model="materialBh"></el-input>
                    <el-input style="width: 200px;" placeholder="请输入设备名称" suffix-icon="el-icon-message" v-model="materialName" class="ml-5"></el-input>
                    <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
                    <el-button type="warning" @click="reset">重置</el-button>
                    </el-row>
                </el-form>
            </div>
            <div style="margin: 10px 0;">
          <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定批量删除这些数据吗？"
              @confirm="delBatch"
          >
            <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </div>

        <el-table :data="tableData" border stripe @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="materialBh" label="设备编号" width="100" align="center"></el-table-column>
          <el-table-column prop="materialName" label="设备名称" width="100" align="center"></el-table-column>
          <el-table-column prop="materialWarehouse" label="所属房间" width="100" align="center"></el-table-column>
          <el-table-column prop="materialLx" label="设备类型" width="130" align="center"></el-table-column>
          <el-table-column prop="materialSl" label="设备数量" width="100" align="center"></el-table-column>
          <el-table-column prop="materialKc" label="库存数量" width="100" align="center"></el-table-column>
          <el-table-column prop="materialSq" label="已安装数量" align="center"></el-table-column>
          <el-table-column prop="materialTime" label="创建时间" width="160" align="center" :formatter="dateFormat"></el-table-column>
          <el-table-column label="操作"  width="250" align="center">
            <template slot-scope="scope">
              <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>

              <el-popconfirm
                  class="ml-5"
                  confirm-button-text='确定'
                  cancel-button-text='我再想想'
                  icon="el-icon-info"
                  icon-color="red"
                  title="您确定删除吗？"
                  @confirm="del(scope.row.materialId)">
              
                <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
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
        <el-dialog title="设备信息" :visible.sync="dialogFormVisible" width="30%" >
          <el-form label-width="80px" size="small">
            <el-form-item label="设备编号">
              <el-input v-model="form.materialBh"></el-input>
            </el-form-item>
            <el-form-item label="所属房间">
              <el-select clearable v-model="form.materialWarehouse" placeholder="请选择房间" style="width: 100%">
                <el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.name"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="设备名称">
              <el-input v-model="form.materialName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="设备类型">
              <el-input v-model="form.materialLx" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="设备数量">
              <el-input v-model="form.materialSl" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="库存数量">
              <el-input v-model="form.materialKc" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="已安装数量">
              <el-input v-model="form.materialSq" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="closeAdd">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </div>
        </el-dialog>
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
      time: new Date().toLocaleTimeString(),
      intervaID: null,
      total: 0,
      pageNum: 1,
      pageSize: 10,
      materialBh: "",
      materialName: "",
      form: {materialBh:""},
      dialogFormVisible: false,
      multipleSelection: [],
      roles: [],
    }
  },
  created() {
    this.load()
  },
  beforeDestroy() {
    // 清除定时任务
    clearInterval(this.timerID);
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
            console.log(res.data)
             this.form.materialBh = res.data
        }
 
    })
    },
    load() {
      this.request.get("/material/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          materialBh: this.materialBh,
          materialName: this.materialName,
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
      this.form = {materialBh:""}
    //   this.intervaID = setInterval(() => {
    //   this.getRfId()
    // }, 2000); // 每秒更新一次时间
    },
    closeAdd(){
      // 清除定时任务
      // console.log(this.intervaID)
      // clearInterval(this.intervaID);
      this.dialogFormVisible = false
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.materialId)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/material/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    del(materialId) {
      this.request.delete("/material/" + materialId).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleEdit(row) {
      this.form = row
      this.dialogFormVisible = true
    //   this.intervaID = setInterval(() => {
    //   this.getRfId()
    // }, 500); // 每秒更新一次时间
    },
    save(){
      this.request.post("/material",this.form).then(res =>{
        if (res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
          clearInterval(this.timerID);
        } else {
          this.$message.error("保存失败")
        }
      })
    },
    reset() {
      this.materialBh = ""
      this.materialName = ""
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