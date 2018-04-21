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
    }
    return params;
}

function sortItems() {
    if (document.location.href.includes("category")) {
        var c=document.location.origin+'/items/catalogue'+document.location.search+'&' + getSortingParams();
        setNewSortedItems(document.location.origin+'/items/catalogue'+document.location.search+'&' + getSortingParams());
    } else {
        setNewSortedItems(document.location.origin + '/items/catalogue?' + getSortingParams());
    }
}

function setNewSortedItems(query) {
    $.get(query, function (data) {
        var itemsDiv = $('#items_div');
        itemsDiv.html("");
        $.each(data, function (index, item) {
            itemsDiv.append('<div id="' + item.id + '" class="card horizontal hoverable">\n' +
                '            <div class="card-image">\n' +
                ' <img width="60" height="200" src="../img/' + item.photoName + '">' +
                '            </div>\n' +
                '            <div class="card-stacked">\n' +
                '                <div class="card-content">\n' +
                '                    <p class="flow-text"><a href="/items/' + item.id + '">' + item.producer + ' ' + item.name + '</a></p>\n' +
                '                    <p>' + item.price + ' rub</p>\n' +
                '                    <p><span id="storage-' + item.id + '">' + item.storage + '</span> ps</p>\n' +
                '                </div>\n' +
                '                <div class="card-action">\n' +
                '                    <div class="row">\n' +
                '                        <div class="input-field col s1">\n' +
                '                            <a href="javascript: addItemToBasket(' + item.id + ')"\n' +
                '                               class="waves-effect waves-light btn">buy</a>\n' +
                '                        </div>\n' +
                '                        <div class="input-field col s1 offset-s7">\n' +
                '                            <a href="/items/edit/' + item.id + '"> <i class="material-icons cl-4db6a sz-30">edit</i></a>\n' +
                '                        </div>\n' +
                '                        <div class="input-field col s1">\n' +
                '                            <a href="javascript: deleteItem(' + item.id + ')"> <i\n' +
                '                                    class="material-icons cl-4db6a sz-30 modal-trigger">delete</i>\n' +
                '                            </a>\n' +
                '                        </div>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </div>');
        })
    });

}