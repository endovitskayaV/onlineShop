function loadCharacteristics(elemId) {
    var element = document.getElementById(elemId);
    var categoryId = (element[element.selectedIndex].id);
    $.get("/characteristics?categoryId=" + categoryId, function (data) {
        var characteristicDiv = $('#characteristicDiv');
        characteristicDiv.html("");
        var i = 0;
        $.each(data, function (key, characteristic) {
            var newInput = '<input type="number" name="characteristics[' + i + '].id" ' +
                ' hidden="hidden" value="' + characteristic.id + '"> ' +
                '<input type="text" name="characteristics[' + i + '].type" ' +
                ' hidden="hidden" value="' + characteristic.type + '"> ' +

                '<input  name="characteristics[' + i + '].required" ' +
                ' hidden="hidden" value="' + characteristic.required + '"> ' +

                '<input type="text" name="characteristics[' + i + '].name" ' +
                'hidden="hidden" value="' + characteristic.name + '"> ' +
                '    <div class="row">\n' +
                '                <div class="input-field col s10 offset-s1">\n' +
                '                <input id="value" name="characteristics[' + i + '].value" type="text">\n' +
                '                    <label for="value">' + characteristic.name + '</label>\n' +
                ' <span class="helper-text">' + characteristic.type + '</span>' +
                '                </div>' +
                '            </div>';
            characteristicDiv.append(newInput);
            i++;
        });

    });
}


function showError(field, message) {
    var t =
        $('[name="characteristics[1]-errors"]');
    var str = field + "-errors";
    var e = $('[name=str]');
    $("#" + field + "-errors").append('<span class="cl-c62828">' + message + '</span>');

}