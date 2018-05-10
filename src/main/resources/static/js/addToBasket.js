function addItemToBasket(id) {
    $.post(document.location.origin + "/basket/add?itemId=" + id, function () {})
        .done(function (data) {
            if (data!=="") {
                $.each(data, function (index, cookie) {
                     $.cookie(cookie.name, cookie.value, {
                        expires: cookie.maxAge,
                        path: cookie.path

                    });
                });
            }

            showModal('<div class="row">' +
                '          <div class="card-content">' +
                '             <p class="center-align">Added to cart</p>' +
                '      </div></div></div>');
        })
        .fail(function () {
            showModal('<div class="row">' +
                '          <div class="card-content">' +
                '             <p class="center-align">Sorry, item is not on sell now</p>' +
                '      </div></div></div>');
        });
}

function showModal(message) {
    var modalDiv = $('#info-modal');
    modalDiv.html("");
    modalDiv.append(message);
    var elem = document.getElementById('info-modal');
    var instance = M.Modal.init(elem);
    instance.open();
}