let url = window.location.pathname;
let id = url.substring(url.lastIndexOf('/') + 1);
$(document).ready(function () {
    getClosedVoting();
});

function getClosedVoting() {
    blockCardDody();
    $.ajax({
        type: "GET",
        url: "get/"+id,
        success: function (response) {
            console.log(response);
            setText(response);
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
}

function setText(response) {
    const responseMap = new Map(Object.entries((response)));
    responseMap.forEach((value, key) => {
        $("#" + key).text(value);
    });
    $("#date").text(moment(response.endDate, 'YYYY-MM-DD HH:MM').format('DD.MM.YYYY HH:MM'));
    $("#userVote").empty();
    if(response.vote == null){
        $("#userVote").text("Ви не прийняли участь в голосуванні");
    } else {
        $("#userVote").append("<div>Ви проголосували: "+getUserVoteSpan(response.vote)+"</div>");
    }
    $("#statusSpan").append(getStatusSpan(response.status));
    $("#resultStatusSpan").append(getResultStatusSpan(response.resultStatus));
    const votesStatistic = response.votesStatistic;
    $("#agreed").text(votesStatistic[0]);
    $("#disagreed").text(votesStatistic[1]);
    $("#absent").text(votesStatistic[2]);
    showStatistic(votesStatistic);
}

function getUserVoteSpan(vote) {
    switch (vote) {
        case 'AGREE':
            return '<span style="color: limegreen">За</span>';
        case 'DISAGREE':
            return '<span style="color: red">Проти</span>';
        case 'ABSTAIN':
            return '<span style="color: orange">Утриматися</span>';
    }
}

function getStatusSpan(status) {
    switch (status) {
        case 'ACTIVE':
            return '<span style="color: limegreen">Активне</span>';
        case 'CLOSED':
            return '<span style="color: red">Закрито</span>';
    }
}

function getResultStatusSpan(status) {
    switch (status) {
        case 'ACCEPTED':
            return '<span style="color: limegreen">Прийнято</span>';
        case 'REJECTED':
            return '<span style="color: red">Відхилено</span>';
        default:
            return "";
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