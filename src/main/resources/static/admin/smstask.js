$(function() {
    $.ajax({
        url: '/smstask/messages',
        success: function (data) {
            $("#top_messagetab").html(data);
        }
    });
});