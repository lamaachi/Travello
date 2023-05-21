

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

function showDeleteConfirmation(event) {
    event.preventDefault();
    console.log("called")
    Swal.fire({
        title: 'Are you sure?',
        text: 'You are about to delete this record.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#dc3545',
        cancelButtonColor: '#6c757d',
        confirmButtonText: 'Delete',
        cancelButtonText: 'Cancel'
    }).then((result) => {
        if (result.isConfirmed) {
            console.log("confirmed")
            // User confirmed, proceed with deletion
            window.location.href = event.target.getAttribute('href');
        }
    });
}
