<template>
  <div class="app-container">
    <button @click="addData()" v-show="pageType!=3" type="button" class="btn btn-xs btn-success">添加</button><br><br><br>

    <!-- 列表浏览 -->
    <div class="list-group text-left" v-show="pageStatus===1">
        <div v-for="item in dataList" :key="item.id" class="list-group-item" >
          <button @click="editData(item)" type="button" class="btn btn-xs btn-success">编辑</button>
          <button @click="showData(item)" type="button" class="btn btn-xs btn-success">预览</button>
          <h4>
            {{item.title}}
          </h4>
        </div>
    </div>

    <div class="form-horizontal" v-show="pageStatus===2 || pageStatus===3">

        <el-row type="flex" class="row-bg" justify="end">
          <button @click="goback" type="button" class="btn btn-default">返回</button>
        </el-row>&nbsp;

        <div class="form-group">
            <label for="inputTitle" class="col-sm-1 control-label">标题：</label>
            <div class="col-sm-11">
                    <textarea id="inputTitle" name="inputTitle" class="form-control" rows="1" v-model="curData.title"></textarea>
            </div>
        </div>
        <div class="form-group">
            <label for="inputContent" class="col-sm-1 control-label">内容：</label>
            <div class="col-sm-11">
                    <textarea id="inputContent" name="inputContent" class="form-control" rows="10" v-model="curData.content"></textarea>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <el-button type="primary" @click="saveData">保存</el-button>
                <el-button type="primary" @click="dialogVisible = true">预览</el-button>
            </div>
        </div>
    </div>

    <el-dialog :visible.sync="dialogVisible" width="30%">
      <div v-html="curData.content"></div>
    </el-dialog>

  </div>
</template>

<script>
import {doPost} from '@/api/app';
import {getProductData} from '@/api/app';
import {saveProductData} from '@/api/app';
import {getIndexData} from '@/api/app';
import {saveIndexData} from '@/api/app';
import {getFaqData} from '@/api/app';
import {saveFaqData} from '@/api/app';

export default {
  data() {
    return {
      pageStatus:1,  // 1列表状态 2编辑状态 3添加状态
      pageType:0,  //1主页 2产品介绍 3常见问题
      productType:1, //1审计 2防火墙 3安全运维 4静态脱敏
      dataList: [],
      curData: {},
      dialogVisible: false
    }
  },
  mounted() {
    this.pageType = this.$route.meta.pageType;
    this.productType = this.$route.meta.productType;
    
    this.getListData();
  },
  watch: {
    $route: {
      handler: function(route) {//监视路由meta.type切换
        this.pageType = this.$route.meta.pageType;
        this.productType = this.$route.meta.productType;
        
        this.getListData();
      }
    }
  },
  methods: {
    getListData: function () {
      this.listLoading = true
      this.curData={}

      if(this.pageType == 1){
        getIndexData().then(response => {
          this.pageStatus = 1;
          this.listLoading = false
          this.dataList = response.data;
        })
      }else if(this.pageType == 2){
        getProductData(this.productType).then(response => {
          this.pageStatus = 1;
          this.listLoading = false
          this.dataList = response.data;
        })
      }else if(this.pageType == 3){
        getFaqData(this.productType).then(response => {
          this.pageStatus = 1;
          this.listLoading = false
          this.dataList = response.data;
        })
      }

    },
    showData: function (data) {
        this.curData=data;  
        this.dialogVisible = true;
    },
    editData: function (data) {
        this.curData=data;
        this.pageStatus = 2;
    },
    addData: function (data) {
        this.curData={id:"",title:"",content:"",productType:this.productType}
        this.pageStatus = 3;
    },
    goback: function () {
        this.pageStatus = 1;
    },
    saveData: function () {
      this.listLoading = true

      if(this.pageType == 1){
        saveIndexData(this.curData).then(response => {
          this.listLoading = false
          this.getListData();
        })
      }else if(this.pageType == 2){
        saveProductData(this.curData).then(response => {
          this.listLoading = false
          this.getListData();
        })
      }else if(this.pageType == 3){
        saveFaqData(this.curData).then(response => {
          this.listLoading = false
          this.getListData();
        })
      }

    }
  }
}
</script>

<style scoped>
  .line{
    text-align: center;
  }
</style>