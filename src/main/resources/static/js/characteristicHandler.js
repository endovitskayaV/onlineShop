function loadCharacteristics(element) {
    var categoryId = (element[element.selectedIndex].id);
        var characteristicsDiv = $('#characteristicsDiv');
        var i = value = $("#counter").val();
    $.each(data, function (key, characteristic) {
            var newInput = '<input type="number" name="characteristicsList[' + i + '].id" class="validate" hidden="hidden" value="' + characteristic.id + '"> ' +
                '<input type="text" name="characteristicsList[' + i + '].type" class="validate" hidden="hidden" value="' + characteristic.type + '"> ' +
                '<input type="text" name="characteristicsList[' + i + '].name" class="validate" hidden="hidden" value="' + characteristic.name + '"> ' +
                '    <div class="row">\n' +
                '                <div class="input-field col s3">\n' +
                '                    <input id="value" name="characteristicsList[' + i + '].value" type="text" class="validate">\n' +
                '                    <label for="value">' + characteristic.name + '</label>\n' +
                ' <span class="helper-text">' + characteristic.type + '</span>' +
                '                </div>' +
                '            </div>';
            characteristicDiv.append(newInput);
            i++;
        });


}
