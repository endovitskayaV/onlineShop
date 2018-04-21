function loadCharacteristics(elemId) {
    var element = document.getElementById(elemId);
    var categoryId = (element[element.selectedIndex].id);
    $.get("/characteristics?categoryId=" + categoryId, function (data) {
        var characteristicDiv = $('#characteristicDiv');
        characteristicDiv.html("");
        var i = 0;
        $.each(data, function (key, characteristic) {
            var newInput = ' <div id="characteristic_">' +
                '<input id= "characteristic-id"  type="number" name="characteristics[' + i + '].id" ' +
                ' hidden="hidden" value="' + characteristic.id + '"> ' +
                '<input id= "characteristic-measureUnit"  type="text" name="characteristics[' + i + '].measureUnit" ' +
                ' hidden="hidden" value="' + characteristic.measureUnit + '"> ' +

                '<input  id= "characteristic-required" name="characteristics[' + i + '].required" ' +
                ' hidden="hidden" value="' + characteristic.required + '"> ' +

                '<input  id= "characteristic-valueDataType" name="characteristics[' + i + '].valueDataType" ' +
                ' hidden="hidden" value="' + characteristic.valueDataType + '"> ' +

                '<input id= "characteristic-name" type="text" name="characteristics[' + i + '].name" ' +
                'hidden="hidden" value="' + characteristic.name + '"> ' +
                '    <div id="1" class="row">\n' +
                '                <div id="2" class="input-field col s10 offset-s1">\n' +
                '                <input id= "characteristic-value" name="characteristics[' + i + '].value" type="text">\n' +
                '                    <label for="value">' + characteristic.name + '</label>\n' +
                ' <span class="helper-text">' + characteristic.measureUnit + '</span>' +
              '  <div name="characteristics[' + i + ']-errors"></div>' +
                '                </div>' +
                '            </div></div>';
            characteristicDiv.append(newInput);
            i++;
        });

    });
}


function showError(field, message) {
    $("[name='" + field + "-errors']").append('<span class="cl-c62828">' + message+". " + '</span>');
}

$(document).ready(function () {
    //file type validation
    $("#file").change(function () {
        var fileMaxSize=1048575;
        var file = this.files[0];
        var imagefile = file.type;
        var match = ["image/jpeg", "image/png", "image/jpg"];
        if (!((imagefile === match[0]) || (imagefile === match[1]) || (imagefile === match[2]))) {
            alert('Please select a valid image file (JPEG/JPG/PNG).');
            $("#file").val('');
            return false;
        }else if (file.size>fileMaxSize){
            donotUploadFile('File size must be not more than 1 Mb');
        }
    });
});

function donotUploadFile(message) {
    alert(message);
    $("#file").val('');
    return false;
}