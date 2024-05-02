
$(document).ready(function () {
    initializeSelects();
});

function initializeSelects() {
    initializeCitySelect();
    initializeStreetSelect();
    initializeHouseNumberSelect();
    initializeStatusSelect();
}

function initializeCitySelect() {
    $("#city").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#city").parent(),
        maximumInputLength: 100,
        placeholder: "Оберіть місто",
        ajax: {
            type: "get",
            url: "get-cities",
            data: function (params) {
                return {
                    search: params.term,
                    page: params.page || 1
                };
            },
            processResults: function (response) {
                return {
                    results: $.map(response.content, function (item) {
                        return {
                            text: item.name,
                            id: item.id
                        }
                    }),
                    pagination: {
                        more: (response.pageable.pageNumber + 1) < response.totalPages
                    }
                };
            }

        }
    });
}

function initializeStatusSelect() {
    $("#status").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#status").parent(),
        minimumResultsForSearch: -1,
        placeholder: "Оберіть статус",
        ajax: {
            type: "GET",
            url: "get-statuses",
            processResults: function (response) {
                return {
                    results: $.map(response, function (item) {
                        return {
                            text: getStatus(item),
                            id: item
                        }
                    })
                };
            }

        }
    });
}

function getStatus(status) {
    switch (status) {
        case 'NEW':
            return "Новий";
        case 'ACTIVE':
            return "Активний";
        case 'DISABLED':
            return "Вимкнений";
    }
}
