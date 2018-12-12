<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input :placeholder="'关键字'" v-model="listQuery.title" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查询</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="dialogFormVisible=true">添加客户信息</el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      style="width: 100%;"
      highlight-current-row>
      <el-table-column
        type="index"
        width="50">
      </el-table-column>

      <el-table-column label="客户公司名称">
        <template slot-scope="scope">
          {{ scope.row.company }}
        </template>
      </el-table-column>
      <el-table-column label="SID" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.sid }}</span>
        </template>
      </el-table-column>
      <el-table-column label="IP" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.ip }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
          <template slot-scope="scope">
            <!-- <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑</el-button> -->
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
    </el-table>

    <el-dialog title="新增客户" :visible.sync="dialogFormVisible" :append-to-body="true">
      <el-form :model="salesForm" :rules="rules" ref="salesForm" label-width="150px" >
        <el-form-item label="客户公司名称" prop="company">
          <el-input v-model="salesForm.company"></el-input>
        </el-form-item>
        <el-form-item label="SID" prop="sid">
          <el-input v-model="salesForm.sid"></el-input>
        </el-form-item>
        <el-form-item label="服务器IP" prop="ip">
          <el-input v-model="salesForm.ip"></el-input>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="submitForm('salesForm')">提交</el-button>
          <el-button @click="resetForm('salesForm')">重置</el-button>
        </el-form-item>

      </el-form>

    </el-dialog>
  </div>
</template>

<script>
import { getList,saveSales,delSales } from '@/api/sales'

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
        dialogFormVisible:false,
        salesForm: {
          company: '',
          sid:'',
          ip:''
        },
        rules: {
          company: [
            { required: true, message: '请输入客户公司名称', trigger: 'blur' },
          ],
          sid: [
            { required: true, message: '请输入SID', trigger: 'blur' },
          ],
          ip: [
            { required: true, message: '请输入安装服务器IP', trigger: 'blur' },
          ],
        },

       listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      list: [{id:"123","sid":"2342-234234-345634","ip":"123.234.23.23","company":"橘橙科技有限公司"},
              {id:"123","sid":"2342-234234-3456345","ip":"123.234.23.25","company":"太美医疗器械科技有限公司"}],
      listLoading: true
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    handleFilter() {
      this.fetchData();
    },
    handleEdit() {
      
    },
    handleDelete(tIndex,row) {
        this.$confirm('此操作将删除该客户信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
            this.listLoading = true
            delSales(row.id).then(response => {
              this.list = this.list.filter(function(o,i){return o.id != row.id })//本地删除
              this.listLoading = false
            }) 
        });
     
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          saveSales(this.salesForm).then(response => {
            this.fetchData();
            this.dialogFormVisible = false
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    fetchData() {
      this.listLoading = true
      getList(this.listQuery).then(response => {
        this.list = response.data
        this.listLoading = false
      })
    }
  }
}
</script>
<style scoped>
  .filter-container{
    margin-bottom: 10px;
  }
</style>
