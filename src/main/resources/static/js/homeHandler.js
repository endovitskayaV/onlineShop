function deleteItem(id) {
    $.ajax({
        url: document.location.origin + '/items/delete/' + id,
        type: "DELETE",
        contentType: "application/x-www-form-urlencoded",
        success: function (data) {
            $('#' + id).html("");
            showModal('<div class="row">' +
                '          <div class="card-content">' +
                '             <p class="center-align">Deleted</p>' +
                '      </div></div></div>');
        },
        error: function (data) {
            showModal('<div class="row">' +
                '          <div class="card-content">' +
                '             <p class="center-align">' + data.responseText +
                '</p></div></div>');
        }
    });
}


function addItemToBasket(id) {
    $.get("basket/add?itemId="+id, function() {
        alert( "success" );
    })
        .done(function() {
            alert( "second success" );
        })
        .fail(function() {
            alert( "error" );
        })
        .always(function() {
            alert( "finished" );
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