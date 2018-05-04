function deleteItem(id) {
    if (confirm("Are you sure?")) {
        $.ajax({
            url: document.location.origin + '/items/delete/' + id,
            type: "DELETE",
            contentType: "application/x-www-form-urlencoded",
            success: function () {
                $('#' + id).html("");
                showModal('<div class="row">' +
                    '          <div class="card-content">' +
                    '             <p class="center-align">Deleted</p>' +
                    '      </div></div></div>');

                var url=window.location.href;
                var f=document.location.origin + '/items/' + id;
                if (url.search(f)!==-1) {
                    $('#categories').html("");
                    $('#' + id).append('<div style="margin-top: 50px" class="row">\n' +
                        '  <div class="col s3 offset-s4 card hoverable"><div class="card-content">\n' +
                        '<p class="center-align">No such item</p> </div></div></div>');
                }
            },
            error: function (data) {
                showModal('<div class="row">' +
                    '          <div class="card-content">' +
                    '             <p class="center-align">' + data.responseText +
                    '</p></div></div>');
            }
        });
    }
}

function showModal(message) {
    var modalDiv = $('#info-modal');
    modalDiv.html("");
    modalDiv.append(message);
    var elem = document.getElementById('info-modal');
    var instance = M.Modal.init(elem);
    instance.open();
}

function getSortingParams() {
    var element = document.getElementById('sort_criteria');
    var params = '';
    if (element === null) {
        params += 'sortBy=PRODUCER&acs=true';
    } else {
        switch (element.selectedOptions[0].id) {
            case 'producerAcs':
                params += 'sortBy=PRODUCER&acs=true';
                break;
            case 'producerDes':
                params += 'sortBy=PRODUCER&acs=false';
                break;
            case 'priceAcs':
                params += 'sortBy=PRICE&acs=true';
                break;
            case 'priceDes':
                params += 'sortBy=PRICE&acs=false';
                break;
            case 'popularityDes':
                params += 'sortBy=POPULARITY&acs=false';
                break;
            default:
                params += 'sortBy=POPULARITY&acs=true';
                break;
        }
    }
    return params;
}

function sortItems() {
    if (query !== "") {
        if (document.getElementById('specifiedCategory') === null || $('#specifiedCategory').val() === "") {
            location.href = document.location.origin + window.location.pathname + '?query=' + getSaveQuery() + '&' + getSortingParams();
        } else {
            location.href = document.location.origin + '/items/find?query=' + query + '&categoryId=' + $('#specifiedCategory').val() + '&' + getSortingParams() + getChosenCharacteristicParams();
        }

    } else if (document.location.href.includes("category")) {
        if (anyCharacteristicWasChosen()) {
            location.href = document.location.origin + '/items/filter?category=' + $('#chosenCategory').val() + '&' + getSortingParams() + getChosenCharacteristicParams();
        } else {
            location.href = document.location.origin + '/items/filter?category=' + $('#chosenCategory').val() + '&' + getSortingParams();
        }
    } else {
        location.href = document.location.origin + '/items?' + getSortingParams();
    }
}

function getByCategory(categoryName) {
    location.href = document.location.origin + '/items?category=' + categoryName + '&' + getSortingParams();
}

function findByCharacteristics() {
    if (anyCharacteristicWasChosen()) {
        if (query !== "") {
            location.href = document.location.origin + '/items/find?query=' + query + '&categoryId=' + $('#specifiedCategory').val() + '&' + getSortingParams() + getChosenCharacteristicParams();
        } else {
            location.href =
                document.location.origin + '/items/filter?category=' + $('#chosenCategory').val() + '&' + getSortingParams() + getChosenCharacteristicParams();
        }
    }
}

function anyCharacteristicWasChosen() {
    var chosen = false;
    $("li[id*='characteristics_values']").each(function (index, li) {
        var values = '';
        var characteristicName = ((li.children)[1]).value;
        var valuesDiv = li.children[2];
        $.each(valuesDiv.children, function (key, ch) {
            var checkBox = ((ch.children)[0].children)[0];
            if (checkBox.checked) {
                chosen = true;
            }
        });

    });
    return chosen;
}

function getChosenCharacteristicParams() {
    var params = '';
    $("li[id*='characteristics_values']").each(function (index, li) {
        var values = '';
        var characteristicName = ((li.children)[1]).value;
        var valuesDiv = li.children[2];
        $.each(valuesDiv.children, function (key, ch) {
            var checkBox = ((ch.children)[0].children)[0];
            if (checkBox.checked) {
                values += ',' + checkBox.value;
            }
        });

        if (values.length > 1) {
            params += '&' + characteristicName + '=' + values.substring(1, values.length);
        }
    });
    return params;
}