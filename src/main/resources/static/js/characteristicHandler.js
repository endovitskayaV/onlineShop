function loadCharacteristics(element) {
    var categoryId = (element[element.selectedIndex].id);
    var characteristicsDiv = $('#characteristicsDiv');
    var i = $("#counter").val();
    $.get("/categories/" + categoryId, function (data) {
        $.each(data, function (key, characteristic) {
            var newInput = '<input type="number" name="characteristics[' + i + '].id" class="validate" hidden="hidden" value="' + characteristic.id + '"> ' +
                '<input type="text" name="characteristics[' + i + '].type" class="validate" hidden="hidden" value="' + characteristic.type + '"> ' +
                '<input type="text" name="characteristics[' + i + '].name" class="validate" hidden="hidden" value="' + characteristic.name + '"> ' +
                '    <div class="row">\n' +
                '                <div class="input-field col s3">\n' +
                '                    <input id="value" name="characteristics[' + i + '].value" type="text" class="validate">\n' +
                '                    <label for="value">' + characteristic.name + '</label>\n' +
                ' <span class="helper-text">' + characteristic.type + '</span>' +
                '                </div>' +
                '            </div>';
            characteristicsDiv.append(newInput);
            i++;
        });
    });
}
