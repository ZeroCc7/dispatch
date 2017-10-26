
// 获取公告栏信息
$(function() {
    $.ajax({
        url: '/index/message/messages',
        success: function (data) {
            $("#top_messagetab").html(data);
        }
    });
});
var stompClient = null;

function connect() {
    console.log("connect");
    var socket = new SockJS('/my-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected:' + frame);
        stompClient.subscribe('/topic/callback', function (response) {
            showResponse(response.body);
        })
    });
}

function disconnect() {
    console.log("disconnect");

    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log('Disconnected');
}
function send() {
    console.log("send");
    var message = $('#name').val();
    console.log('name:' + message);
    stompClient.send("/topic/send", {}, JSON.stringify({'message': message}));
}
function showResponse(message) {

    console.log(message);
    // var msg='<div class=\"media\">'+
    //             '<div class="media-body pull-right">'+message+'<br/>'+
    //                 '<small>'+message+'</small>'+
    //              '</div>'+
    //         '</div>'
    $("#messagebody").html(message);
}
