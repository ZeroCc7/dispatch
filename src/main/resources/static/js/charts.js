/* --------------------------------------------------------
 Flot Charts
 -----------------------------------------------------------*/

// //Line Chart
// $(function () {
//     if ($('#line-chart')[0]) {
//         var d1 = [];
//         $.ajax({
//             type: "POST",
//             url: '/smstask/getMonthlySmsTask',
//             // data: {username: username, password: password},
//             async: false,
//             success: function (data) {
//                 for(var i=0;i<data.length;i++){
//                     var day = data[i].day;
//                     var tolnum = data[i].tolnum;
//                     d1.push([day,tolnum]);
//                 }
//             }
//         });
//
//         $.plot('#line-chart', [ {
//             data: d1,
//             label: "Data",
//
//         },],
//
//             {
//                 series: {
//                     lines: {
//                         show: true,
//                         lineWidth: 1,
//                         fill: 0.25,
//                     },
//
//                     color: 'rgba(255,255,255,0.7)',
//                     shadowSize: 0,
//                     points: {
//                         show: true,
//                     }
//                 },
//
//                 yaxis: {
//                     tickColor: 'rgba(255,255,255,0.15)',
//                     tickDecimals: 0,
//                     font :{
//                         lineHeight: 13,
//                         style: "normal",
//                         color: "rgba(255,255,255,0.8)",
//                     },
//                     shadowSize: 0,
//                 },
//                 xaxis: {
//                     min: 1,
//                     max: 30,
//                     tickColor: 'rgba(255,255,255,0)',
//                     tickDecimals: 0,
//                     font :{
//                         lineHeight: 13,
//                         style: "normal",
//                         color: "rgba(255,255,255,0.8)",
//                     }
//                 },
//                 grid: {
//                     borderWidth: 1,
//                     borderColor: 'rgba(255,255,255,0.25)',
//                     labelMargin:10,
//                     hoverable: true,
//                     clickable: true,
//                     mouseActiveRadius:6,
//                 },
//                 legend: {
//                     show: false
//                 }
//             });
//
//         $("#line-chart").bind("plothover", function (event, pos, item) {
//             if (item) {
//                 var x = item.datapoint[0],
//                     y = item.datapoint[1];
//                 $("#linechart-tooltip").html(item.series.label + " of " + x + " = " + y).css({top: item.pageY+5, left: item.pageX+5}).fadeIn(200);
//             }
//             else {
//                 $("#linechart-tooltip").hide();
//             }
//         });
//
//         $("<div id='linechart-tooltip' class='chart-tooltip'></div>").appendTo("body");
//     }
//
// });




//Dynamic Chart
$(function() {
    if ($('#dynamic-chart')[0]) {
        var data = [],
            totalPoints = 300;

        function getRandomData() {
            // if (data.length > 0)
            //     data = data.slice(1);
            //
            // while (data.length < totalPoints) {
            //     var prev = data.length > 0 ? data[data.length - 1] : 50,
            //         y = prev + Math.random() * 10 - 5;
            //     if (y < 0) {
            //         y = 0;
            //     } else if (y > 90) {
            //         y = 90;
            //     }
            //
            //     data.push(y);
            // }
            //
            var res = [];
            // for (var i = 0; i < data.length; ++i) {
                res.push([1,1])
            // }
            //
            return res;

        }


        var updateInterval = 30;
        var plot = $.plot("#dynamic-chart", [ getRandomData() ], {
            series: {
                label: "Data",
                lines: {
                    show: true,
                    lineWidth: 1,
                    fill: 0.25,
                },

                color: 'rgba(255,255,255,0.2)',
                shadowSize: 0,
            },
            yaxis: {
                min: 0,
                max: 100,
                tickColor: 'rgba(255,255,255,0.15)',
                font :{
                    lineHeight: 13,
                    style: "normal",
                    color: "rgba(255,255,255,0.8)",
                },
                shadowSize: 0,

            },
            xaxis: {
                tickColor: 'rgba(255,255,255,0.15)',
                show: true,
                font :{
                    lineHeight: 13,
                    style: "normal",
                    color: "rgba(255,255,255,0.8)",
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
                mouseActiveRadius:6,
            },
            legend: {
                show: false
            }
        });

        function update() {
            plot.setData([getRandomData()]);
            // Since the axes don't change, we don't need to call plot.setupGrid()

            plot.draw();
            setTimeout(update, updateInterval);
        }

        update();

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
});







/* --------------------------------------------------------
 Easy Pie Charts
 -----------------------------------------------------------*/
$(function() {
    $('.pie-chart-tiny').easyPieChart({
        easing: 'easeOutBounce',
        barColor: 'rgba(255,255,255,0.75)',
        trackColor: 'rgba(0,0,0,0.3)',
        scaleColor: 'rgba(255,255,255,0.3)',
        lineCap: 'square',
        lineWidth: 4,
        size: 100,
        animate: 3000,
        onStep: function(from, to, percent) {
            $(this.el).find('.percent').text(Math.round(percent));
        }
    });

    var chart = window.chart = $('.pie-chart-tiny').data('easyPieChart');
    $('.pie-chart-tiny .pie-title > i').on('click', function() {
        $(this).closest('.pie-chart-tiny').data('easyPieChart').update(Math.random()*200-100);
    });
});