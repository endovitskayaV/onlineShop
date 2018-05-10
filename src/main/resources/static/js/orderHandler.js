function confirmOrder() {
    if (confirm("Are you sure? You will not be able to edit order details")) {
        var query = [];

        $("input[id*='itemId']").each(function () {
            var itemId = $(this).val();
            query.push({
                itemId: itemId, quantity: parseInt($("#quantity-" + itemId).text())
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
                    deliveryAddress: String($("#deliveryAddress").val()).replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;'),
                    items: query
                }),

                success: function () {
                    location.href = document.location.origin + '/orders/' + orderId;
                },
                error: function (data) {

                    $('[name $= "-errors"]').html("");
                    $.each(data.responseJSON, function (index, error) {
                        $("[name='" + error.field + "-errors']").append('<span class="cl-c62828">' + error.message + ". " + '</span>');
                    });
                }
            });
        })
    }
}