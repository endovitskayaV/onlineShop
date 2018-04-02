function loadCharacteristics(element) {
    var categoryId = (element[element.selectedIndex].id);
    $.get("/items/characteristics?categoryId="+categoryId, function (data) {
        var characteristicDiv = $('#characteristicDiv');
        characteristicDiv.html("");
        $.each(data, function (key, characteristic) {
            var newInput = '<input type="text"  class="validate"> <label for="'+characteristic.id+'" hidden="hidden">'+characteristic.name+'</label>';
            characteristicDiv.append(newInput);
        });
    });
}
