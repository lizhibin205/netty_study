<template>
  <div id="app">
    <WebSocketClient/>
  </div>
</template>

<script>
import WebSocketClient from './components/WebSocketClient'

export default {
  name: 'App',
  components: {
    WebSocketClient
  }
}
</script>

<style>
</style>
