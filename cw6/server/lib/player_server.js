'use strict'
var net = require('net');
var events = require('events');
var util = require('util');

function PlayerServer() {
	var self = this;

    this.server = net.createServer(function(player) {

		player.on('data', function(data) {
            var parts = data.toString().split('\n');
            for(var i = 0; i < parts.length; i++) {
                if(parts[i].length > 0) self.onInput(player, parts[i]);
            }
        });
	});
	
}


util.inherits(PlayerServer, events.EventEmitter);



PlayerServer.prototype.onInput = function(player, data) {
    //TODO
    var message = JSON.parse(data);

	if(message.type == 'REGISTER'){
		this.emit('register', player, message.student_id);
	}
	else if(message.type == 'MOVE'){
		this.emit('move', player, message);
	}

}


PlayerServer.prototype.listen = function(port) {
    //TODO
    this.server.listen(port);

}


PlayerServer.prototype.close = function() {
    //TODO
    this.server.close();
}


module.exports = PlayerServer;
