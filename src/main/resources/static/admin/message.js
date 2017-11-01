
// 获取公告栏信息
$(function() {
    connect();
    $.ajax({
        url: '/index/message/messages',
        success: function (data) {
            $("#top_messagetab").html(data);
        }
    });
});
var stompClient = null;

function connect() {
    var socket = new SockJS('/my-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        //日志获取WEBSCOKET
        // stompClient.subscribe('/topic/getLogFromFile', function (response) {
        //     console.log(response);
        //     showLogResponse(response.body);
        // });
        //建立获取的连接。
        stompClient.subscribe('/topic/send', function (response) {
            showResponse(response.body);
        });

    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
}
function send() {
    var message = $('#chart_message').val();
    var nowUserName = $("#logined_nick_name").text();
    stompClient.send("/app/send", {}, JSON.stringify({'message': message,'username':nowUserName}));
}
function showResponse(message) {
    var nowUserName = $("#logined_nick_name").text();
    var messagevo = JSON.parse(message);
    var userName = messagevo.username;
    var msg;
    if(nowUserName!=userName){
        msg="<div class='media pull-left'>"+
                "<div class='media-body'>"+messagevo.message+"<br/>"+
                     "<small>"+userName+"</small>"+
                 "</div>"+
            "</div>";
    }else {
        msg="<div class='media'>"+
                 "<div class='media-body pull-right'>"+messagevo.message+"<br/>"+
                     "<small>"+userName+"</small>"+
                 "</div>"+
            "</div>";
    }
    $("#messagebody").append(msg);
}
function showLogResponse(message) {
    console.log(message);
    var msg = "<p>"+message+"</p>";
    $("#log_message").append(msg);
}