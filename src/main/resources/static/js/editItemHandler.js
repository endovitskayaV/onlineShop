function save(itemId) {
    var query = [];

    $("div[id*='characteristic_']").each(function () {
        var id = $(this).children('#characteristic-id').val();
        var name = $(this).children('#characteristic-name').val();
        var measureUnit = $(this).children('#characteristic-measureUnit').val();
        var valueDataType = $(this).children('#characteristic-valueDataType').val();
        var required = $(this).children('#characteristic-required').val();
        var ch = $(this).children('#1');
        var ch2 = ch.children('#2');
        var value = $(this).children('#1').children('#2').children('#characteristic-value').val();
        query.push({
            id: id,
            name: name,
            measureUnit: measureUnit,
            valueDataType: valueDataType,
            required: required,
            value: value
        });


    });


    $.ajax({
        url: '/items/edit',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            id: itemId,
            name: $("#name").val(),
            producer: $("#producer").val(),
            storage: $("#storage").val(),
            description: $("#description").val(),
            price: $("#price").val(),
            categoryId: $("#categoryId").children(":selected").attr("id"),
            characteristics: query
        }),
        statusCode: {
            200:function (data) {
                location.href = document.location.origin + '/items/' + data;
            },
            400:function (data) {
                $('[name $= "-errors"]').html("");
                $.each(data.responseJSON,function (index,error) {
                    $("[name='" + error.field + "-errors']").append('<span class="cl-c62828">'+error.message+". " + '</span>');
                });
            }
        }
    });
}


function deleteImage(){
    $("#image_div").html("");
}