function setItemQuantity(basketId, itemId, isIncrease) {
    var quantityElem = $("#quantity-" + itemId);
    if (parseInt(quantityElem.text())===1 && !isIncrease) {
        //do nothing
    } else {
        $.post(document.location.origin + "/basket/edit/" + basketId + "?isIncrease=" + isIncrease,
            {
                itemId: itemId,
                quantity: parseInt(quantityElem.text())
            },
            function () {
                var newQuantity = parseInt(quantityElem.text());
                if (isIncrease) {
                    newQuantity = newQuantity + 1;
                } else {
                    newQuantity = newQuantity - 1;
                }
                quantityElem.html("").text(newQuantity);
                $("#sum-" + itemId).html("").append(
                    newQuantity * parseInt($("#price-" + itemId).text())
                );
                setOverall();
            })
            .fail(function (data) {
                $("#quantity-" + itemId).val(data.responseJSON);
                showModal('<div class="row">' +
                    '          <div class="card-content">' +
                    '             <p class="center-align">No more items in stock</p>' +
                    '      </div></div></div>');
            });
    }
}


function deleteItem(basketId, itemId) {
    $.ajax({
        url: document.location.origin + '/basket/delete/' + basketId + '/' + itemId,
        type: "DELETE",
        contentType: "application/x-www-form-urlencoded",
        success: function (data) {
            $('#' + itemId).html("");
            setOverall();
            if ($('#anyCardLeft').text() === "") {
                $('#overall').html("");
                $('#top').html("").append('<div class="col s2 offset-s4 card horizontal">\n' +
                    '      <div class="card-stacked">\n' +
                    '          <div class="card-content">\n' +
                    '              <p>No items</p>\n' +
                    '          </div>\n' +
                    '      </div>\n' +
                    '  </div>');
            }
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


function showModal(message) {
    var modalDiv = $('#info-modal');
    modalDiv.html("");
    modalDiv.append(message);
    var elem = document.getElementById('info-modal');
    var instance = M.Modal.init(elem);
    instance.open();
}

function setOverall() {
    var overall = 0;
    var count = parseInt($("#count").text());
    for (var i = 0; i < count; i++) {
        var sum = parseInt($("[name='sum-" + i + "']").text());
        if (!isNaN(sum)) {
            overall = overall + sum;
        }
    }
    $("#overall").html("").append(overall);
}