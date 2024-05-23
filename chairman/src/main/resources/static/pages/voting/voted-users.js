let url = window.location.pathname;
let id = url.substring(url.lastIndexOf('/') + 1);
let tableLength = 2;
let request = {
    page: 0,
    pageSize: tableLength,
    fullName: "",
    apartmentNumber: "",
    area: "",
    phoneNumber: "",
    userVote: ""
};

$(document).ready(function () {
    getVotedUsers(0);
    initializeSelect();
});

function getVotedUsers(currentPage) {
    blockCardDody();
    request.page = currentPage;
    request.pageSize = tableLength;
    $.ajax({
        type: "GET",
        url: "get/"+id,
        data: request,
        success: function (response) {
            emptyTableAndPagination();
            drawTable(response);
            console.log(response);
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
}

function emptyTableAndPagination() {
    $("tbody").empty();
    $("ul.pagination").empty();
    $("#paginationElements").empty();
}

function drawTable(response) {
    if (response.numberOfElements == 0) {
        let tdCount = $("td").length;
        $("tbody").append(`<tr class="tr"><td colspan="${tdCount}" class="text-center">${dataNotFound}</td>></tr>`);
    } else {
        let i = response.pageable.pageNumber+1;
        for (let vote of response.content) {
            $("tbody")
                .append(
                    `<tr class="tr text-nowrap">
                        <td>${i}</td>
                        <td>${vote.fullName}</td>
                        <td>${vote.apartmentNumber}</td>
                        <td>${vote.area}</td>
                        <td>${vote.phoneNumber}</td>
                        <td>${getUserVoteSpan(vote.userVote)}</td>
                    </tr>`
                );
            i++;
        }
    }
    buildPagination(response, "getVotedUsers");
}

function getUserVoteSpan(vote) {
    switch (vote) {
        case 'AGREE':
            return '<span class="badge bg-label-success">За</span>';
        case 'DISAGREE':
            return '<span class="badge bg-label-danger">Проти</span>';
        case 'ABSTAIN':
            return '<span class="badge bg-label-warning">Утримались</span>';
    }
}

function initializeSelect() {
    $("#filter-by-userVote").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#dropdownParent"),
        minimumResultsForSearch: -1,
        placeholder: "Оберіть голос",
        allowClear: true,
        ajax: {
            type: "GET",
            url: "get-userVotes",
            processResults: function (response) {
                return {
                    results: $.map(response, function (item) {
                        return {
                            text: getUserVote(item),
                            id: item
                        }
                    })
                };
            }

        }
    });
}

function getUserVote(vote) {
    switch (vote) {
        case 'AGREE':
            return 'За';
        case 'DISAGREE':
            return 'Проти';
        case 'ABSTAIN':
            return 'Утримались';
    }
}

$("#filter-by-fullName").on("change", function () {
    request.fullName = $(this).val();
    getVotedUsers(0);
});

$("#filter-by-apartmentNumber").on("change", function () {
    request.apartmentNumber = $(this).val();
    getVotedUsers(0);
});
$("#filter-by-apartmentNumber").on("input", function () {
    $(this).val($(this).val().replace(/[^0-9]/g, '')
        .replace(/(\..*?)\..*/g, '$1'));
});

$("#filter-by-area").on("change", function () {
    request.area = $(this).val();
    getVotedUsers(0);
});
$("#filter-by-area").on("input", function () {
    $(this).val($(this).val().replace(/[^0-9.]/g, '')
        .replace(/(\..*?)\..*/g, '$1'));
});

$("#filter-by-phoneNumber").on("change", function () {
    request.phoneNumber = $(this).val();
    getVotedUsers(0);
});

$("#filter-by-userVote").on("change", function () {
    request.userVote = $(this).val();
    getVotedUsers(0);
});

$("#clear-filters").on("click", function () {
    $("#filter-by-fullName, #filter-by-apartmentNumber, #filter-by-area, #filter-by-phoneNumber, #filter-by-userVote")
        .val("").trigger('change');
});