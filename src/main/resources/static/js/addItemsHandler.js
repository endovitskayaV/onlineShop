function loadCharacteristics(element) {
    var categoryId = (element[element.selectedIndex].id);
    $.get("/characteristics?categoryId=" + categoryId, function (data) {
        var characteristicDiv = $('#characteristicDiv');
        characteristicDiv.html("");

        var i = 0;
        $.each(data, function (key, characteristic) {
            var newInput = '<input type="number" name="characteristics[' + i + '].id" class="validate" hidden="hidden" value="' + characteristic.id + '"> ' +
                '<input type="text" name="characteristics[' + i + '].type" class="validate" hidden="hidden" value="' + characteristic.type + '"> ' +
                '<input type="text" name="characteristics[' + i + '].name" class="validate" hidden="hidden" value="' + characteristic.name + '"> ' +
                '    <div class="row">\n' +
                '                <div class="input-field col s10 offset-s1">\n' +
                '                    <input id="value" name="characteristics[' + i + '].value" type="text" class="validate">\n' +
                '                    <label for="value">' + characteristic.name + '</label>\n' +
                ' <span class="helper-text">' + characteristic.type + '</span>' +
                '                </div>' +
                '            </div>';
            characteristicDiv.append(newInput);
            i++;
        });

    });
}

function addCategory(element) {
    var win = window.open( document.location.origin+'/categories/add', '_blank');
    if (win) {
        //Browser has allowed it to be opened
        win.focus();
    } else {
        //Browser has blocked it
        alert('Please allow popups for this website');
    }
}