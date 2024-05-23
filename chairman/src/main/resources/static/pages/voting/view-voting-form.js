let url = window.location.pathname;
let id = url.substring(url.lastIndexOf('/') + 1);
$(document).ready(function () {
    $("#edit-button").attr("href", "edit/"+id);
    $("#show-users").attr("href", "users/"+id);
    getVotingForm();
});

function getVotingForm() {
    blockCardDody();
    $.ajax({
        type: "GET",
        url: "get-for-view/"+id,
        success: function (response) {
            console.log(response);
            setFields(response);
        },
        error: function (error) {
            printErrorMessageToField(error);
        }
    });
}

function setFields(response) {
    const responseMap = new Map(Object.entries((response)));
    responseMap.forEach((value, key) => {
        $("#" + key).text(value);
    });
    $("#status").text(getStatus(response.status));
    $("#resultStatus").text(getResultStatus(response.resultStatus));
    $("#startTime").text(moment(response.startDate, 'YYYY-MM-DD HH:MM').format('DD.MM.YYYY HH:MM'));
    $("#endTime").text(moment(response.endDate, 'YYYY-MM-DD HH:MM').format('DD.MM.YYYY HH:MM'));
    const votesStatistic = response.votesStatistic;
    $("#agreed").text(votesStatistic[0]);
    $("#disagreed").text(votesStatistic[1]);
    $("#absent").text(votesStatistic[2]);
    showStatistic(votesStatistic);
}

function getStatus(status) {
    switch (status) {
        case 'ACTIVE':
            return "Активне";
        case 'CLOSED':
            return "Закрито";
    }
}

function getResultStatus(status) {
    switch (status) {
        case 'ACCEPTED':
            return "Прийнято";
        case 'REJECTED':
            return "Відхилено";
    }
}

function showStatistic(data) {
    new Chart($('#doughnutChart'), {
        type: 'pie',
        data: {
            labels: ['За', 'Проти', 'Утримались'],
            datasets: [
                {
                    data: data,
                    backgroundColor: ['#50C878', '#EE4B2B', '#FFEA00'],
                }
            ]
        },
        options: {
            responsive: true,
            animation: {
                duration: 500
            },
            plugins: {
                legend: {
                    display: false
                },
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            const label = context.labels || '',
                                value = context.parsed;
                            const output = ' ' + label + ' : ' + value + ' %';
                            return output;
                        }
                    },
                    // Updated default tooltip UI
                    rtl: isRtl,
                    backgroundColor: '#fff',
                    titleColor: '#5d596c',
                    bodyColor: '#6f6b7d',
                    borderWidth: 1,
                    borderColor: '#dbdade'
                }
            }
        }
    });
}