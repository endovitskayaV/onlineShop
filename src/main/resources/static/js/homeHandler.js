function deleteItem(id) {
    if (confirm("Are you sure?")) {
        $.ajax({
            url: document.location.origin + '/items/delete/' + id,
            type: "DELETE",
            contentType: "application/x-www-form-urlencoded",
            success: function (data) {
                $('#' + id).html("");
                showModal('<div class="row">' +
                    '          <div class="card-content">' +
                    '             <p class="center-align">Deleted</p>' +
                    '      </div></div></div>');
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
            default:
                params += 'sortBy=PRODUCER&acs=true';
                break;
        }
    }
    return params;
}

function sortItems() {
    if (query !== "") {
        if (document.getElementById('specifiedCategory') === null || $('#specifiedCategory').val() === "") {
            setCategories(document.location.origin + '/items/search?query=' + $('#search').val() + '&' + getSortingParams());
        } else {
            getByCategoryAndQuery($('#specifiedCategory').val(), $('#search').val());
        }

    } else if (document.location.href.includes("category")) {
        location.href = document.location.origin + '/items?category=' + $('#chosenCategory').val() + '&' + getSortingParams();
    } else {
        location.href = document.location.origin + '/items?' + getSortingParams();
    }
}

function getByCategory(categoryName) {
    location.href = document.location.origin + '/items?category=' + categoryName + '&' + getSortingParams();
}

function findByCharacteristics() {
    if (anyCharacteristicWasChosen) {
        location.href =
            document.location.origin + '/items?category=' + $('#chosenCategory').val() + '&' + getSortingParams();

    } else {
        location.href =
            document.location.origin + '/items/filter?category=' + $('#chosenCategory').val() + '&' + getSortingParams() + getChosenCharacteristicParams();
    }
}

function anyCharacteristicWasChosen() {
    $("li[id*='characteristics_values']").each(function (index, li) {
        var values = '';
        var characteristicName = ((li.children)[1]).value;
        var valuesDiv = li.children[2];
        $.each(valuesDiv.children, function (key, ch) {
            var characteristic = ((ch.children)[0].children)[0]; //checkBox
            if ((((ch.children)[0].children)[0]).checked) {
                return true;
            }
        });

    });
//     $("li[id*='characteristics_values']").each(function () {
//         var characterisricName = $("#characteristic_name").text();
//         var children = $("div[id*='values']");
//         $.each(children, function (key, child) {
//             $.each(child.children, function (key, ch) {
//                 if ((((ch.children)[0].children)[0]).checked) return true;
//             })
//         });
//     });
    return false;
}

function getChosenCharacteristicParams() {
    var params = '';

        $("li[id*='characteristics_values']").each(function (index, li) {
            var values = '';
            var characteristicName = ((li.children)[1]).value;
            var valuesDiv = li.children[2];
            $.each(valuesDiv.children, function (key, ch) {
                var characteristic = ((ch.children)[0].children)[0]; //checkBox
                if ((((ch.children)[0].children)[0]).checked) {
                    values += ',' + characteristic.value;
                }
            });

            if (values.length > 1) {
                params += '&' + characteristicName + '=' + values.substring(1, values.length);
            }
        });

    return params;
}