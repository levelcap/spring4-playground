function ApplicationModel(stompClient) {
	var self = this;
	
	self.metric = new MetricModel();
	self.notifications = new Array();

	self.connect = function() {
		stompClient.connect('', '', function(frame) {
			console.log('Connected ' + frame);

			stompClient.subscribe("/topic/value.metric.*", function(message) {
				self.metric.processMetric(JSON.parse(message.body));
			});
			stompClient.subscribe("/user/queue/errors", function(message) {
				self.pushNotification("Error " + message.body);
			});
		}, function(error) {
			console.log("STOMP protocol error " + error);
		});
	}

	self.pushNotification = function(text) {
		self.notifications.push({
			notification : text
		});
		if (self.notifications.length > 5) {
			self.notifications.shift();
		}
	}

	self.logout = function() {
		stompClient.disconnect();
		window.location.href = "logout.html";
	}
}

function MetricModel() {
	var self = this;

	self.processMetric = function(metric) {
		//console.log(metric.metricName + ": " + metric.metricValue);
		$(".container").append("<b>" + new Date() + "</b><br/>" + metric.metricName + ": " + metric.metricValue + "<br/>");
	};
};