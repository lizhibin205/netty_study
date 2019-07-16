<template>
    <div>
        <el-row>
            <div id="wsMessageData">
                <div>
                    <p class="client-message">你 15:44:38</p>
                    <p>414124121241241241</p>
                </div>
                <div>
                    <p class="server-message">服务器 15:44:38</p>
                    <p>414124121241241241</p>
                </div>
            </div>
        </el-row>
        <el-row>
            <el-col :span="8"><el-input v-model="wsMessage" placeholder="聊天内容"></el-input></el-col>
            <el-col :span="16"><el-button>发送</el-button></el-col>
        </el-row>
        <el-row>
            <el-col :span="8"><el-input v-model="wsUrl" placeholder="ws://127.0.0.1:9100/ws"></el-input></el-col>
            <el-col :span="16"><el-button type="primary">连接</el-button><el-button type="danger">断开</el-button></el-col>
        </el-row>
    </div>
</template>

<script>
export default {
  data () {
    return {
      wsUrl: 'ws://127.0.0.1:9100/ws',
      wsMessage: ''
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
  #wsMessageData {
    width: 600px;
    height: 300px;
    overflow-y: scroll;
    font-size: 10px;
  }
  .client-message {
      color: green;
  }
  .server-message {
      color: blue;
  }
</style>
