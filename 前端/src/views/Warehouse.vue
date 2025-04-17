<template>
    <div>
        <el-card>
            <div style="padding: 10px 0;">
                <el-form label-width="80px">
                    <el-row>
                    <el-input style="width: 200px;" placeholder="请输入房间名称" suffix-icon="el-icon-search" v-model="name"></el-input>
                    <el-input style="width: 200px;" placeholder="请输入房间类型" suffix-icon="el-icon-message" v-model="type" class="ml-5"></el-input>
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
          <el-table-column prop="name" label="房间名称" align="center"></el-table-column>
          <el-table-column prop="type" label="房间类型" align="center"></el-table-column>
          <el-table-column prop="number" label="房间布局" align="center"></el-table-column>
          <el-table-column prop="createTime" label="创建时间" align="center" :formatter="dateFormat"></el-table-column>
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
                  @confirm="del(scope.row.id)">
              
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
        <el-dialog title="房间信息" :visible.sync="dialogFormVisible" width="30%" >
          <el-form label-width="80px" size="small">
            <el-form-item label="房间名称">
              <el-input v-model="form.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="房间类型">
              <el-input v-model="form.type" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="房间布局">
              <el-input v-model="form.number" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
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
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      type: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      roles: [],
    }
  },
  created() {
    this.load()
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
    load() {
      this.request.get("/warehouse/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          type: this.type,
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
      this.request.get("/role").then(res => {
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
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/warehouse/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    del(id) {
      this.request.delete("/warehouse/" + id).then(res => {
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
    },
    save(){
      this.request.post("/warehouse",this.form).then(res =>{
        if (res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("保存失败")
        }
      })
    },
    reset() {
      this.name = ""
      this.type = ""
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