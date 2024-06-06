
function buildPagination(response, method) {
    let totalPages = response.totalPages;
    let pageNumber = response.pageable.pageNumber;
    if (totalPages > 1) {
        let prev = '';

        if (pageNumber !== 0) {
            prev = '<li class="page-item prev"><a class="page-link waves-effect" id="prev" href="javascript:void(0);"><i class="ti ti-chevron-left ti-xs"></i></a></li>';
        } else {
            prev = '<li class="page-item prev disabled"><a class="page-link waves-effect" href="javascript:void(0);"><i class="ti ti-chevron-left ti-xs"></i></a></li>';
        }


        let next = '';

        if (pageNumber !== totalPages - 1) {
            next = '<li class="page-item next"><a class="page-link waves-effect" id="next" href="javascript:void(0);"><i class="ti ti-chevron-right ti-xs"></i></a></li>';
        } else {
            next = '<li class="page-item next disabled"><a class="page-link waves-effect" href="javascript:void(0);"><i class="ti ti-chevron-right ti-xs"></i></a></li>';
        }

        let pages = '';
        let dots = '<li class="page-item disabled"><a class="page-link waves-effect" href="javascript:void(0);"> ... </a></li>';
        if (pageNumber < 3) {
            for (let i = 1; i <= 5; i++) {
                if (i <= totalPages) {
                    if (pageNumber + 1 !== i) {
                        pages += '<li class="page-item"><a class="page-link waves-effect" href="javascript:void(0);">' + i + '</a></li>';
                    } else {
                        pages += '<li class="page-item active"><a class="page-link waves-effect" href="javascript:void(0);">' + i + '</a></li>';
                    }
                }
            }
            if (totalPages === 6) {
                pages += '<li class="page-item"><a class="page-link waves-effect" href="javascript:void(0);">' + 6 + '</a></li>';
            }
            if (totalPages > 6) {
                pages += dots;
                pages += '<li class="page-item"><a class="page-link waves-effect" href="javascript:void(0);">' + totalPages + '</a></li>';
            }

        } else if (3 > totalPages - 1 - pageNumber) {
            pages += '<li class="page-item"><a class="page-link waves-effect" href="javascript:void(0);">1</a></li>';
            if (totalPages > 6) {
                pages += dots
            }
            for (let i = totalPages - 4; i <= totalPages; i++) {
                if (i > 1) {
                    if (pageNumber + 1 !== i) {
                        pages += '<li class="page-item"><a class="page-link waves-effect" href="javascript:void(0);">' + i + '</a></li>';
                    } else {
                        pages += '<li class="page-item active"><a class="page-link waves-effect" href="javascript:void(0);">' + i + '</a></li>';
                    }
                }
            }

        } else {
            pages += '<li class="page-item"><a class="page-link waves-effect" href="javascript:void(0);">1</a></li>';
            pages += dots

            pages += '<li class="page-item"><a class="page-link waves-effect" href="javascript:void(0);">' + pageNumber + '</a></li>';
            pages += '<li class="page-item active"><a class="page-link waves-effect" href="javascript:void(0);">' + (pageNumber + 1) + '</a></li>';
            pages += '<li class="page-item"><a class="page-link waves-effect" href="javascript:void(0);">' + (pageNumber + 2) + '</a></li>';

            pages += dots;
            pages += '<li class="page-item"><a class="page-link waves-effect" href="javascript:void(0);">' + totalPages + '</a></li>';
        }

        pages = prev + pages + next;
        let from = (response.size * pageNumber) + 1;
        let to = from + response.numberOfElements - 1;
        $("ul.pagination").append(pages);
        buildPaginationElements(from, to, method, response.numberOfElements, response.size, response.totalElements);
        listenPagination(method);
    }
}

function buildPaginationElements(from, to, method, numberOfElements, size, totalElements) {
    $("#paginationElements").append(
        `<select id="table-length">
            <option value="2">2</option>
            <option value="5">5</option>
            <option value="10">10</option>
            <option value="20">20</option>
        </select>`
    );
    let showed = "Показано від " + from + " до " + to + " з " + totalElements + " елементів"
    if(numberOfElements === size){
        showed = "Показано " + from + " з " + totalElements + " елементів"
    }
    $("#paginationElements").append(
        `<div class="ms-3 text-nowrap">
            ${showed}
        </div>`
    );
    let tableSelect = $("#table-length");
    tableSelect.wrap('<div class="col-4 position-relative"></div>').select2({
        language: "uk",
        minimumResultsForSearch: -1,
        dropdownParent: tableSelect.parent()
    });
    tableSelect.on("change", function () {
        tableLength = this.value;
        window[method](0);
    });
    tableSelect.val(tableLength);
    tableSelect.trigger('change.select2');
}

function listenPagination(method) {
    $("a.page-link").on("click", function () {
        let val = $(this).text();
        let id = $(this).attr('id');
        if (id === "prev") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            window[method](activeValue - 2);
        } else if (id === "next") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            window[method](activeValue);
        } else {
            let currentPage = parseInt(val - 1);
            window[method](currentPage);
        }
    });
}