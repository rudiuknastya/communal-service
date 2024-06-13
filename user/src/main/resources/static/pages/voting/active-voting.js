let url = window.location.pathname;
let id = url.substring(url.lastIndexOf('/') + 1);
$(document).ready(function () {
    getActiveVoting();
});

function getActiveVoting() {
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
        $("#userVote").text("Ви ще не проголосували");
    } else {
        $("#userVote").append("<div>Ви проголосували: "+getUserVoteSpan(response.vote)+"</div>");
    }
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

$("#abstain-button").on("click", function () {
    sendVote("ABSTAIN");
});

$("#disagree-button").on("click", function () {
    sendVote("DISAGREE");
});

$("#agree-button").on("click", function () {
    sendVote("AGREE");
});

function sendVote(vote) {
    blockCardDody();
    $.ajax({
        type: "POST",
        url: "update-vote/"+id,
        headers: {
            "X-CSRF-TOKEN": token
        },
        data: {
            vote: vote
        },
        success: function () {
            toastr.success("Голос зараховано!");
            getActiveVoting();
        },
        error: function (error) {
            console.log(error);
            toastr.error(errorMessage);
        }
    });
}