
//Dynamic Chart
var plot = null;

$(function() {
    if ($('#dynamic-chart')[0]) {
        plot = $.plot("#dynamic-chart", [0,0], {
            series: {
                label: "Data",
                lines: {
                    show: true,
                    lineWidth: 1,
                    fill: 0.25
                },

                color: 'rgba(255,255,255,0.2)',
                shadowSize: 0
            },
            yaxis: {
                min: 0,
                max: 10000,
                tickColor: 'rgba(255,255,255,0.15)',
                font :{
                    lineHeight: 13,
                    style: "normal",
                    color: "rgba(255,255,255,0.8)"
                },
                shadowSize: 0

            },
            xaxis: {
                tickColor: 'rgba(255,255,255,0.15)',
                show: true,
                font :{
                    lineHeight: 13,
                    style: "normal",
                    color: "rgba(255,255,255,0.8)"
                },
                shadowSize: 0,
                min: 0,
                max: 250
            },
            grid: {
                borderWidth: 1,
                borderColor: 'rgba(255,255,255,0.25)',
                labelMargin:10,
                hoverable: true,
                clickable: true,
                mouseActiveRadius:6
            },
            legend: {
                show: false
            }
        });

        // function update() {
        //     var res = [];
        //     for (var i = 0; i < data.length; ++i) {
        //         res.push([i,data[i]])
        //     }
        //     return res;
        //     plot.setData(res);
        //     // Since the axes don't change, we don't need to call plot.setupGrid()
        //     plot.draw();
        //     // setTimeout(update, updateInterval); //循环时间
        // }
        $("#dynamic-chart").bind("plothover", function (event, pos, item) {
            if (item) {
                var x = item.datapoint[0].toFixed(2),
                    y = item.datapoint[1].toFixed(2);
                $("#dynamic-chart-tooltip").html(item.series.label + " of " + x + " = " + y).css({top: item.pageY+5, left: item.pageX+5}).fadeIn(200);
            }
            else {
                $("#dynamic-chart-tooltip").hide();
            }
        });

        $("<div id='dynamic-chart-tooltip' class='chart-tooltip'></div>").appendTo("body");
    }

    //链接 websocket
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
            update(JSON.parse(response.body));
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
var data = [];
function update(message) {
    var runningNum =message.runningNum;
    if (data.length > 300)
        data = data.slice(1);
    data.push(runningNum);
    var res = [];
    for (var i = 0; i < data.length; ++i) {
        res.push([i,data[i]])
    }
    plot.setData([res]);
    // Since the axes don't change, we don't need to call plot.setupGrid()
    plot.draw();
    // setTimeout(update, 1000); //循环时间
}

