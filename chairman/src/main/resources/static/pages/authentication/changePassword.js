function send() {
    let csrfToken = $("meta[name='_csrf']").attr("content");
    clearAllErrorMessage();
    $.ajax({
        type: "POST",
        url: "changePassword",
        data: {
            token: token,
            password: $("#password").val(),
            confirmPassword: $("#confirmPassword").val()
        },
        headers: {
            "X-CSRF-TOKEN": csrfToken
        },
        success: function () {
            window.location.href = 'success';
        },
        error: function (error) {
            printErrorMessageToField(error);
            if (error.status == 403) {
                window.location.href = 'tokenExpired';
            }
        }
    });
}