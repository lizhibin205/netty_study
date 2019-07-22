<template>
    <el-card id="wsMessage">
        <div slot="header" class="clearfix">
          <span>客户端ID：{{clientId}}</span>
        </div>
        <el-row>
          <el-col :span="24">
            <div id="wsMessageData" ref="wsMessageData">
                <div v-for="(message, index) in wsMessageList" :key="index">
                    <p :class="message.type + '-message'">{{message.type == 'server' ? '服务器' : '客户端'}} {{message.timeTag}}</p>
                    <p>{{message.message}}</p>
                </div>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="16" ><el-input v-model="wsMessage" placeholder="聊天内容"></el-input></el-col>
            <el-col :span="8" ><el-button v-on:click="wsSendMessage" :disabled="!wsConnectStatus">发送</el-button></el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="16"><el-input v-model="wsUrl" placeholder="ws://127.0.0.1:9100/ws"></el-input></el-col>
            <el-col :span="8"><el-button type="primary" :disabled="wsConnectStatus" v-on:click="wsConnect">连接</el-button>
            <el-button type="danger" :disabled="!wsConnectStatus" v-on:click="wsDisconnect" >断开</el-button></el-col>
        </el-row>
    </el-card>
</template>

<script>
export default {
  data () {
    return {
      clientId: 0,
      wsUrl: 'ws://127.0.0.1:9100/ws',
      wsConnectStatus: false,
      wsMessage: '',
      wsMessageList: [],
      websocket: null
    }
  },
  created: function () {
    this.clientId = Math.round(Math.random() * 1000) + new Date().getTime()
  },
  methods: {
    wsSendMessage: function () {
      if (this.wsMessage === '') {
        return
      }
      this.wsMessageList.push({
        'type': 'client',
        'timeTag': new Date(),
        'message': this.wsMessage
      })
      if (this.wsConnectStatus) {
        console.log('websocket send: ' + this.wsMessage)
        this.websocket.send(this.wsMessage)
      }
    },
    wsConnect: function () {
      console.log('connect to: ' + this.wsUrl)
      this.websocket = new WebSocket(this.wsUrl)
      this.websocket.onopen = this.wsOnOpen
      this.websocket.onerror = this.wsOnError
      this.websocket.onmessage = this.wsOnMessage
      this.websocket.onclose = this.wsOnClose
    },
    wsDisconnect: function () {
      this.websocket.close()
      this.wsMessageList.push({
        'type': 'client',
        'timeTag': new Date(),
        'message': '与服务器的连接已断开'
      })
    },
    wsOnOpen: function () {
      console.log('websocket open.')
      this.wsConnectStatus = true
    },
    wsOnError: function (event) {
      console.log('websocket error: ' + event)
    },
    wsOnMessage: function (event) {
      console.log('websocket received: ' + event)
      this.wsMessageList.push({
        'type': 'server',
        'timeTag': new Date(),
        'message': event.data
      })
    },
    wsOnClose: function () {
      console.log('websocket close.')
      this.wsConnectStatus = false
    }
  },
  mounted: function () {
    this.wsMessageList.push({
      'type': 'client',
      'timeTag': new Date(),
      'message': '客户端初始化完成'
    })
  },
  watch: {
    wsMessage: function (newVal, oldVal) {},
    wsMessageList: function (newVal, oldVal) {
      setTimeout(() => {
        console.log('wsMessageList scroll')
        this.$refs.wsMessageData.scrollTop = this.$refs.wsMessageData.scrollHeight
      }, 200)
    }
  }
}
</script>

<style>
  .el-row {
    margin-bottom: 20px;
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
  #wsMessage {
    width: 600px;
    padding: 10px 10px;
    font-size: 12px;
  }
  #wsMessageData {
    height: 300px;
    overflow-y: scroll;
  }
  .client-message {
      color: green;
  }
  .server-message {
      color: blue;
  }
</style>
