<template>
  <div class="app-container" id="online" style="width:1000px;">
    <div id="chat">
      <div class="sidebar">
        <!-- <div class="m-card">
          <header>
            <img class="avatar" width="40" height="40" alt="Coffce" src="static/images/1.jpg">
            <p class="name">Coffce</p>
          </header>
          <footer>
            <input class="search" placeholder="search user...">
          </footer>
        </div>-->
        <!--v-component-->
        <div class="m-list">
          <ul>
            <!--v-for-start-->
            <li v-for="(item, index) in appUsers" :key="index" @click="changeAppUser(item)" :class="curAppUser.id==item.id?'active':''">
              <img class="avatar" width="30" height="30" :alt="item.company" src="static/images/h/a.png">
                <el-badge :value="item.newLogCount==0?'':item.newLogCount">
                    <span class="name" v-if="item.company!=''">
                      {{item.company}}
                    </span>
                    <span class="name" v-else>
                      {{item.phone}}
                    </span>
                </el-badge>
            </li>
            
            <!-- <li class="active">
              <img class="avatar" width="30" height="30" alt="示例介绍" src="static/images/2.png">
              <p class="name">示例介绍</p>
            </li>
            <li>
              <img class="avatar" width="30" height="30" alt="webpack" src="static/images/3.jpg">
              <p class="name">webpack</p>
            </li> -->
            <!--v-for-end-->
          </ul>
        </div>
        <!--v-component-->
      </div>
      <div class="main">
        <div class="m-message" id="logMain">
          <div style="text-align: center;"><el-button type="text" @click="getOldChatLog">更多消息...</el-button></div>
          <ul>
            <!--v-for-start-->
            <li v-for="(item, index) in chatLogs" :key="index">
              <p class="time"><span>{{item.time}}</span></p>
              <div :class="item.replyUserName!=''?'main self':'main'">
                <img class="avatar" width="30" height="30" :src="item.replyUserName!=''?'static/images/h/z.png':'static/images/h/a.png'">
                <div  class="text">
                  {{item.content}}
                </div>
              </div>
            </li>

            <!-- <li>
              <p class="time"><span>17:35</span></p>
              <div class="main">
                <img class="avatar" width="30" height="30" src="dist/images/2.png">
                <div  class="text">
                  Hello，这是一个基于Vue + Webpack构建的简单chat示例，聊天记录保存在localStorge。简单演示了Vue的基础特性和webpack配置。
                </div>
              </div>
            </li>
            <li>
              <p class="time">
                <span>17:35</span>
              </p>
              <div class="main self">
                <img class="avatar" width="30" height="30" src="dist/images/2.png">
                <div class="text">项目地址: https://github.com/coffcer/vue-chat</div>
              </div>
            </li> -->
            <!--v-for-end-->
          </ul>
        </div>
        <!--v-component-->
        <div class="m-text">
          <textarea v-show="curAppUser.id" placeholder="按 Ctrl + Enter 发送" v-model="message" @keyup="inputing"></textarea>
        </div>
        <!--v-component-->
      </div>
    </div>
  </div>
</template>

<script>
import { doPost } from "@/api/app";

export default {
  data() {
    return {
      appUsers: [],
      chatLogs: [{ content: "我是客户", replyUserName: "",time:'12:29'},{ content: "我是客服", replyUserName: "admin",time:'12:30' }],
      curAppUser: {},
      message: ""
    };
  },
  mounted: function() {
    this.refrsh();
  },
  methods: {
    getAppUsers: function() {
      doPost("/appUser/getAppUsers", {}).then(response => {
        this.appUsers = response.data;
      });
    },
    changeAppUser: function(user) {
      this.curAppUser = user;
      this.chatLogs = [];
      this.getOldChatLog();

      setInterval(this.refrshChatLog, 1000 * 1);
    },
    getOldChatLog: function() {
      var logId = 0;
      if (this.chatLogs.length != 0) {
        var logId = this.chatLogs[0].id-1;
      }
      var params = { userId: this.curAppUser.id, logId: logId };
      doPost("/appUser/getOldChatLog", params).then(response => {
        var logs = response.data;
        this.chatLogs = logs.concat(this.chatLogs);
        if(logId==0){
          this.scrollToBottom();
        }
      });
    },
    getNewChatLog: function(logId) {
      var params = { userId: this.curAppUser.id, logId: logId };
      doPost("/appUser/getNewChatLog", params).then(response => {
        if (response.data.length > 0) {
          this.chatLogs = this.chatLogs.concat(response.data);
          this.scrollToBottom();
        }
      });
    },
    sendMessage: function() {
      if (this.message) {
        var params = { userId: this.curAppUser.id, content: this.message };
        this.message = "";
        doPost("/appUser/addChatLog", params).then(response => {
          
        });
      }
    },
    refrsh: function() {
      // 刷新用户
      this.getAppUsers();

      // 刷新聊天记录
      if(this.curAppUser.id){
        if (this.chatLogs.length == 0) {
          this.getOldChatLog();
        } else {
          var logId = this.chatLogs[this.chatLogs.length - 1].id;
          this.getNewChatLog(logId);
        }
      }

      //自动刷新
      if(this.$route.path == "/cs/online"){
        setTimeout(() => {
          this.refrsh();
        }, 1000);
      }

    },
    scrollToBottom: function() {
      this.$nextTick(() => {
        var div = document.getElementById("logMain");
        div.scrollTop = div.scrollHeight;
      });
    },
    inputing (e) {
        if (e.ctrlKey && e.keyCode === 13) {
            this.sendMessage();
        }
    }
  }
};
</script>

<style lang="less">
#online{
  background: #f5f5f5 url("/static/images/bg.jpg") no-repeat center;
}
#chat {
  overflow: hidden;
  border-radius: 3px;
  margin: 20px auto;
  width: 800px;
  height: 600px;
  

  ul {
    list-style: none;
    margin: 0;
    padding: 0;
  }

  .sidebar,
  .main {
    height: 100%;
  }
  .sidebar {
    float: left;
    width: 200px;
    color: #f4f4f4;
    background-color: #2e3238;
  }
  .main {
    position: relative;
    overflow: hidden;
    background-color: #eee;
  }
  .m-text {
    position: absolute;
    width: 100%;
    bottom: 0;
    left: 0;
  }
  .m-message {
    height: ~"calc(100% - 160px)";
  }
}

.m-list {
  li {
    padding: 12px 15px;
    border-bottom: 1px solid #292c33;
    cursor: pointer;
    transition: background-color 0.1s;

    &:hover {
      background-color: rgba(255, 255, 255, 0.1);
    }
    &.active {
      background-color: rgba(255, 255, 255, 0.2);
    }
  }
  .avatar,
  .name {
    vertical-align: middle;
  }
  .avatar {
    border-radius: 2px;
  }
  .name {
    display: inline-block;
    margin: 0 0 0 15px;
  }
}
.m-message {
  padding: 10px 15px;
  overflow-y: scroll;

  li {
    margin-bottom: 15px;
  }
  .time {
    margin: 7px 0;
    text-align: center;

    > span {
      display: inline-block;
      padding: 0 18px;
      font-size: 12px;
      color: #fff;
      border-radius: 2px;
      background-color: #dcdcdc;
    }
  }
  .avatar {
    float: left;
    margin: 0 10px 0 0;
    border-radius: 3px;
  }
  .text {
    display: inline-block;
    position: relative;
    padding: 0 10px;
    max-width: ~"calc(100% - 40px)";
    min-height: 30px;
    line-height: 2.5;
    font-size: 12px;
    text-align: left;
    word-break: break-all;
    background-color: #fafafa;
    border-radius: 4px;

    &:before {
      content: " ";
      position: absolute;
      top: 9px;
      right: 100%;
      border: 6px solid transparent;
      border-right-color: #fafafa;
    }
  }

  .self {
    text-align: right;

    .avatar {
      float: right;
      margin: 0 0 0 10px;
    }
    .text {
      background-color: #b2e281;

      &:before {
        right: inherit;
        left: 100%;
        border-right-color: transparent;
        border-left-color: #b2e281;
      }
    }
  }
}
.m-text {
  height: 160px;
  border-top: solid 1px #ddd;

  textarea {
    padding: 10px;
    height: 100%;
    width: 100%;
    border: none;
    outline: none;
    font-family: "Micrsofot Yahei";
    resize: none;
  }
}
</style>