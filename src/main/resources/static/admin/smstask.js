$(function() {
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


    var message = confirm("确定启用调度？");
    console.log(message);
    if(message){
        $.ajax({
            url: '/smstask/startSendDispatch',
            success: function (data) {
                // $("#top_messagetab").html(data);
                // alert(data)
                if(data == "success"){
                    toastr.success('启动成功！');
                    $("#dispatchTask").removeClass("switch-off");
                    $("#dispatchTask").attr("class", "switch-on");
                    $("#dispatchTask").removeAttr("onclick");
                    $("#dispatchTask").attr("onclick","shutdownThreadStart();");
                }else{
                    toastr.error(data);
                }
            }
        });
    }

}

function shutdownThreadStart() {
    var message = confirm("确认要停用调度？");
    console.log(message);
    if(message){
        $.ajax({
            url: '/smstask/stopSendDispatch',
            success: function (data) {
                // $("#top_messagetab").html(data);

                if(data == "success"){
                    toastr.success('停止成功！');
                    $("#dispatchTask").removeClass("switch-on");
                    $("#dispatchTask").attr("class", "switch-off");
                    $("#dispatchTask").removeAttr("onclick");
                    $("#dispatchTask").attr("onclick","sendThreadStart();");
                }else{
                    toastr.error(data);
                }


            }
        });
    }

}