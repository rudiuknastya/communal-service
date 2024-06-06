function send(){
    let csrfToken = $("meta[name='_csrf']").attr("content");
    clearAllErrorMessage();
    $.ajax({
        type : "POST",
        url : "forgotPassword",
        data : {
            email: $('#email').val()
        },
        headers: {
            "X-CSRF-TOKEN": csrfToken
        },
        success : function() {
            window.location.href = 'sentToken';
        },
        error : function(error) {
            printErrorMessageToField(error);
        }
    });
}