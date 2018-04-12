function increaseItemQuantity(basketId, itemId) {

    $.post(document.location.origin + "/basket/edit/" + basketId,

        {itemId: itemId, quantity: $("#quantity-" + itemId).val()},

        function () {
            $("#sum-" + itemId).html("").append(
                $("#quantity-" + itemId).val() * parseInt($("#price-" + itemId).text())
            );
            setOverall();
        })
        .fail(function (data) {
            $("#quantity").val(data.responseJSON);
            showModal('<div class="row">' +
                '          <div class="card-content">' +
                '             <p class="center-align">No more items in stock</p>' +
                '      </div></div></div>');
        });
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


function makeOrder() {
    var query = [];
    $("input[id*='itemId']").each(function () {
        var itemId = $(this).val();
        query.push({itemId: itemId , quantity: $("#quantity-" + itemId).val()});
    });


    $.ajax({
        url: '/orders/add',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            id: $("#id").val(),
            userId: 0,
            statusId: 1,
            date: 1488931200000,
            deliveryAddress: "someAddress",
            items: query
        }),

        success: function (data) {
            location.href = document.location.origin +'/orders/finish/'+data;
        }

    });
}
