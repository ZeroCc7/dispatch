
// 获取公告栏信息
$(function() {
    $.ajax({
        url: '/index/message/messages',
        success: function (data) {
            console.log(data);
            $("#top_messagetab").html(data);
        }
    });
});
// $("#top_message").on("click",function () {
//     // var param = $("#keyword").val();
//     // // 收集参数
//     // var page = $("#now").val();
//     // if (isEmpty(page) || page == 0) {
//     //     page = 1;
//     // }
//
//     // 查询列表
//     $.ajax({
//         url: '/index/message/messages',
//         success: function (data) {
//             $("#top_messagetab").html(data);
//         }
//     });
// })