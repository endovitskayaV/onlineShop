function loadCharacteristics(element) {
    var categoryId = (element[element.selectedIndex].id);
    $.get("/items/characteristics?categoryId="+categoryId, function (data) {
        var characteristicDiv = $('#characteristicDiv');
        characteristicDiv.html("");
        $.each(data, function (key, characteristic) {
            var newInput = '<label for="'+characteristic.id+'" hidden="hidden">'+characteristic.name+'</label> <input type="text"  class="validate">';
            characteristicDiv.append(newInput);
        });
    });
}
