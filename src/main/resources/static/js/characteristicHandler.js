function loadCharacteristics(element) {
    var categoryId = (element[element.selectedIndex].id);
    var characteristicsDiv = $('#characteristicsDiv');
    var i = $("#counter").val();
    $.get("/categories/" + categoryId, function (data) {
        $.each(data, function (key, characteristic) {
            var newInput = '<input type="number" name="characteristics[' + i + '].id" class="validate" hidden="hidden" value="' + characteristic.id + '"> ' +
                '<input type="text" name="characteristics[' + i + '].measureUnit" class="validate" hidden="hidden" value="' + characteristic.measureUnit + '"> ' +
                '<input type="text" name="characteristics[' + i + '].name" class="validate" hidden="hidden" value="' + characteristic.name + '"> ' +
                '<input type="text" name="characteristics[' + i + '].valueDataType" class="validate" hidden="hidden" value="' + characteristic.valueDataType + '"> ' +
                '    <div class="row">\n' +
                '                <div class="input-field col s3">\n' +
                '                    <input id="value" name="characteristics[' + i + '].value" type="text" class="validate">\n' +
                '                    <label for="value">' + characteristic.name + '</label>\n' +
                ' <span class="helper-text">' + characteristic.measureUnit + '</span>' +
                '                </div>' +
                '            </div>';
            characteristicsDiv.append(newInput);
            i++;
        });
    });
}
