const express = require('express');
const router = express', async (req, res) => {
    const { username, password } = req.body;
    const user = await User.findOne({ username, password });
    if (user) {
        res.send('Login successful');
    } else {
        res.send('Invalid credentials');
    }
});

module.exports = router;