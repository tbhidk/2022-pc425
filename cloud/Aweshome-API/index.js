var express = require('express');
var app = express();
const { sensor } = require('./handlers/sensor')
app.get('/sensor', sensor);


const PORT = process.env.PORT || 5050
app.get('/', (req, res) => {
res.send('This is my demo project')
})
app.listen(PORT, function () {
console.log(`Demo project at: ${PORT}!`); });