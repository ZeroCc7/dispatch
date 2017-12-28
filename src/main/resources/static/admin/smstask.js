$(function() {
    $.ajax({
        url: '/smstask/messages',
        success: function (data) {
            $("#top_messagetab").html(data);
        }
    });
    toastr.options = {
        closeButton: false,
        debug: false,
        progressBar: true,
        positionClass: "toast-top-right",
        onclick: null,
        showDuration: "300",
        hideDuration: "1000",
        timeOut: "2000",
        extendedTimeOut: "1000",
        showEasing: "swing",
        hideEasing: "linear",
        showMethod: "fadeIn",
        hideMethod: "fadeOut"
    };
});

function sendThreadStart() {
    $.ajax({
        url: '/smstask/startSendDispatch',
        success: function (data) {
            // $("#top_messagetab").html(data);
            // alert(data)
            if(data == "success"){
                toastr.success('启动成功！');
            }else{
                toastr.error(data);
            }
        }
    });
}

function shutdownThreadStart() {
    $.ajax({
        url: '/smstask/stopSendDispatch',
        success: function (data) {
            // $("#top_messagetab").html(data);

            if(data == "success"){
                toastr.success('停止成功！');
            }else{
                toastr.error(data);
            }


        }
    });
}