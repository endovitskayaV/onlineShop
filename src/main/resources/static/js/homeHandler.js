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
    if ($('#search').val() !== "") {
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
    var e = document.getElementById('chosenCharacteristics');
    if (document.getElementById('chosenCharacteristics') !== null) {
        location.href = document.location.origin + '/items/filter?category=' + categoryName + '&' + getSortingParams() + '&' + getChosenCharacteristicParams();
    } else {
        location.href = document.location.origin + '/items?category=' + categoryName + '&' + getSortingParams();
    }

}

function getChosenCharacteristicParams() {
    var params = '';
    $("li[id*='characteristics_values']").each(function () {
        var characterisricName = $("#characteristic_name").text();
        var div = document.getElementById("values");
        div.childrenNodes.each(function (index, child) {
                if (child.id === "characteristic_value" && child.prop('checked')) {
                    params.append(characterisricName + '=' + child.value);
                }
            }
        )
    });

    return params;
}