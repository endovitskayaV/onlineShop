function confirmOrder() {
    var query = [];

    $("input[id*='itemId']").each(function () {
        var itemId = $(this).val();
        query.push({itemId: itemId , quantity: $("#quantity-" + itemId).val()});
    });


    $.ajax({
        url: '/orders/finish',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            id: $("#id").val(),
            userId: 0,
            statusId: 1,
            date: 1488931200000,
            deliveryAddress:$("#deliveryAddress").val(),
            items: query
        }),

        success: function () {
            location.href = document.location.origin +'/orders';
        }

    });
}
