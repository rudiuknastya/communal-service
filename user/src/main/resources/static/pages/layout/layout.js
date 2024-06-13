const errorMessage = "Виникла помилка!";
const dataNotFound = "Дані не знайдено";

let token = $("meta[name='_csrf']").attr("content");
$(document).ready(function () {
    const currHref = window.location.pathname;
    const currentUrl = window.location.href;
    const myArray = currentUrl.split("/");
    let root = myArray[3];
    $("#nav-avatar").attr("src", "/"+root+"/uploads/"+authenticatedAvatar);
    $('.menu-item a').each(function (i, item) {
        if (currHref.includes($(item).attr('href'))) {
            $(item).parent().addClass('active');
            $(item).css("color","#7367f0");
        }
    });
});

$("#logoutLink").on("click", function (e) {
    e.preventDefault();
    if ($('#logoutModal').length === 0) {
        $("#main-card").append(
            `<div class="modal fade" tabindex="-1" aria-hidden="true" id="logoutModal">
                          <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                              <div class="modal-header">
                                <button
                                  type="button"
                                  class="btn-close"
                                  data-bs-dismiss="modal"
                                  aria-label="Close"
                                ></button>
                              </div>
                              <div class="modal-body">
                                <h4>Ви впевнені що хочете вийти?</h4>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-danger" onclick="logout()" id="logoutButton"">
                                    Так
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>`)
    }
    $('#logoutModal').modal('show');
});

function logout() {
    const currentUrl = window.location.href;
    const myArray = currentUrl.split("/");
    let root = myArray[3];
    $.ajax({
        type: "POST",
        url: "/" + root + "/user/logout",
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function () {
            window.location.href = 'login?logout';
        },
        error: function () {
        }
    });
}