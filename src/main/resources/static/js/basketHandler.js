function increaseItemQuantity(basketId, itemId) {

    $.post(document.location.origin + "/basket/edit/" + basketId,
        {itemId: itemId, quantity: $("#quantity").val()},
        function () {
            var quantity = $("#quantity-"+itemId).val();
            var p=$("#price-"+itemId).text();
            var price = parseInt(p);
            var newSum = quantity * price;
            $("#sum-"+itemId).html("").append(newSum);

            var overall=0;
            var c= $("#count").text();
            var count = parseInt(c);
            for (var i = 0; i < count; i++) {
                var str="[name='sum-"+i+"']";
                var s1= $(str);
                  var s=s1.text();
                var sum = parseInt(s);
                overall=overall+sum;
            }
            $("#overall").html("").append(overall);

        })
        .fail(function (data) {
            $("#quantity").val(data.responseJSON);
            showModal('<div class="row">' +
                '          <div class="card-content">' +
                '             <p class="center-align">No more items in stock</p>' +
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