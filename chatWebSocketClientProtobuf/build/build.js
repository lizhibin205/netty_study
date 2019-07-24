'use strict'

const browserify = require('browserify')
const fs = require('fs')
const ws = fs.createWriteStream("./dist/chatWebSocketClientProtobuf.js");
const b = browserify();
b.add('./chatWebSocketClientProtobuf.js')
b.bundle().pipe(ws);