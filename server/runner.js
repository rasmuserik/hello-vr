var jsdom = require('jsdom');
var fs = require('fs');
var virtualConsole = jsdom.createVirtualConsole();
virtualConsole.sendTo(console);

jsdom.env({
    html: fs.readFileSync('../index.html'),
    virtualConsole,
    src: [
      "https://openplatform.dbc.dk/v1/dbc_openplatform.min.js",
      fs.readFileSync('../index.js')],
    created: function(err, window) {
        window.process = process;
        window.require = require;
        window.setImmediate = setImmediate;
        window.XMLHttpRequest = require('xmlhttprequest').XMLHttpRequest;
        console.log('created');
    },
    done: function(err, window) {
        console.log('done');
    }
});
