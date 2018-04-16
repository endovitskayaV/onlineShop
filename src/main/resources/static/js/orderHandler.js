function confirmOrder() {
    var query = [];

    $("input[id*='itemId']").each(function () {
        var itemId = $(this).val();
        query.push({itemId: itemId, quantity:parseInt( $("#quantity-" + itemId).text())
    });

    var orderId = $("#id").val();

    $.ajax({
        url: '/orders/finish',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            id: orderId,
            userId: 0,
            statusId: 1,
            date: 1488931200000,
            deliveryAddress: $("#deliveryAddress").val(),
            items: query
        }),

        success: function () {
        location.href = document.location.origin + '/orders/' + orderId;
        }

    });
})
}