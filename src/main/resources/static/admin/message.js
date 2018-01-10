
// 获取公告栏信息
$(function() {
    connect();
});
var stompClient = null;
function connect() {
    var socket = new SockJS('/endpointDispatch');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        //获取WEBSCOKET
        stompClient.subscribe('/topic/dispatchstatus', function (response) {
            showResponse(JSON.parse(response.body));
        });
    });
}

function showResponse(message) {
    $("#taskTolNum").text(message.taskTolNum);
    $("#dispatchStatrtTime").text(message.dispatchStatrtTime);
    $("#tolMobileNum").text(message.tolMobileNum);
    $("#sqlRedisLength").text(message.sqlRedisLength);
    $("#runningNum").text(message.runningNum);
}
