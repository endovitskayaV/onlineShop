function addItemToBasket(id) {
    $.post( document.location.origin +"/basket/add?itemId="+id, function() {
        //alert( "success" );
    })
        .done(function() {
            showModal('<div class="row">' +
                '          <div class="card-content">' +
                '             <p class="center-align">Added to basket</p>' +
                '      </div></div></div>');
        })
        .fail(function() {
            alert( "error!!!!!" );
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