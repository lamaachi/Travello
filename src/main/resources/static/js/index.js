function drawLineChar(month, amount) {
    Highcharts.chart('amounthpermonth', {
        chart: {
            type: 'line',
            width: 500
        },
        title: {
            text: 'Amounts per Month'
        },
        xAxis: {
            categories: month
        },
        tooltip: {
            formatter: function () {
                return this.y+'MAD';
            }
        },
        series: [{
            data: amount
        }]
    });
}

$.ajax({
    url:'panel/invoices/totalAmountPerMonth',
    success:function(result){
        let month = JSON.parse(result).month;
        let amount = JSON.parse(result).amount;
        drawLineChar(month,amount)
    }
})