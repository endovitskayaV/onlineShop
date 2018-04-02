function loadCharacteristics(element) {
    var categoryId = (element[element.selectedIndex].id);
    $.get("/items/characteristics?categoryId="+categoryId, function (data) {
        var characteristicDiv = $('characteristicDiv');
        characteristicDiv.html("");
        $('input').material_select();
        $.each(data, function (key, characteristic) {
            var newInput = '<label for="'+characteristic.toString()+'" hidden="hidden">'+characteristic.toString()+'</label> <input type="text"  class="validate" name="'
                + characteristic.toString()  + '">';
            characteristicDiv.append(newInput);
        });
        $('input').material_select();
    });
}
