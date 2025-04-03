const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const loginController = require('./controllers/loginController');

const app = express();
const port = 3000;

mongoose.connect('mongodb://localhost:27017/loginDB', { useNewUrlParser: true, useUnifiedTopology: true });

app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static('views'));
app.use('/', loginController);

app.listen(port, () => {
    console.log(`Server is running on http://localhost:${port}`);
});