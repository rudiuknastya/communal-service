$(document).ready(function () {
    const currHref = window.location.pathname;
    $('.menu-item a').each(function (i, item) {
        if (currHref.includes($(item).attr('href'))) {
            $(item).parent().addClass('active');
            $(item).css("color","#7367f0");
        }
    });
});