var query="";

$('#search').keypress(function (e) {
    if (e.which === 13) {
        findItems();
    }
});

function findItems() {
    var search = String($('#search').val()).replace(/&/g, '').replace(/</g, '').replace(/>/g, '').replace(/"/g, '');
    if (search !== "") {
        query=search;
        $('#characteristrics_filter').html("");
        var url = document.location.origin + '/items/search?query=' + search;
        var urlForUser = document.location.origin + '/items/find?query=' + search;
        var element = document.getElementById('sort_criteria');
        if (element !== null) {
           url+=  '&' + getSortingParams();
           urlForUser+='&' + getSortingParams();
        }

        history.replaceState('', 'VOLT-Home', urlForUser);
        setCategories(url);
    }
}

function setCategories(req) {
    $.get(req, function (data) {
        var select = document.getElementById("sort_criteria_div");

        if (data.items.length === 0) {
            $('#verifyCategory').html("");
            $('#categories').html("");
            $('#items_div').html("");
            if (select !== null) {
                select.style.visibility = "hidden";
            }
            $('#message_div').append('<span>No items on request &laquo;' + query + '&raquo; found</span>');

        } else {
            $('#categories').html("");
            if (select !== null) {
                select.style.visibility = "visible";
            }
            $('#message_div').html("");
            var categoriesDiv = $('#verifyCategory').html("");


            if (data.categories.length > 1) {
                var div = '<div class="col s2"> <div class="collection z-depth-1">' +
                    '<div style="background-color: white">' +
                    '<span>Specify category:</span></div>';

                $.each(data.categories, function (index, category) {
                    div += '<div>' +
                        '<a href="javascript: getByCategoryAndQuery(\'' + category.id + '\', \'query\')" ' +
                        'class="collection-item">' + category.name + '</a></div>';
                });

                div += '</div></div>';
                categoriesDiv.append(div);
            } else {
                categoriesDiv.append('<div class="col s3"></div>');
            }
            setItemCards(data.items);
        }
    });
}

function getByCategoryAndQuery(categoryId) {
    $('#verifyCategory').html("").append('<div class="col s2"></div>');
    $('#specifiedCategory').remove();
    $('<input id="specifiedCategory" hidden value="' + categoryId + '">').appendTo(document.body);
    var search = $('#search').val();

    var url = document.location.origin + '/items/search?query=' + search;
    var urlForUser = document.location.origin + '/items/find?query=' + search;
    var element = document.getElementById('sort_criteria');
    if (element !== null) {
        url+=  '&' + getSortingParams() ;
        urlForUser+='&' + getSortingParams() ;
    }
    url+= '&' + 'categoryId=' + categoryId;
    urlForUser+='&' + 'categoryId=' + categoryId;
    setNewSortedItemsFilterCharacteristics(url);

    history.replaceState('', 'VOLT-Home', urlForUser);

}

function setNewSortedItemsFilterCharacteristics(query) {
    $.get(query, function (data) {
        setItemCards(data);

    });
}

function setItemCards(data) {
    var itemsDiv = $('#items_div').html("");

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
}