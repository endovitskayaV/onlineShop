var query="";

$('#search').keypress(function (e) {
    if (e.which === 13) {
        findItems();
    }
});

function getSaveQuery(){
    return String($('#search').val()).replace(/&/g, '').replace(/</g, '').replace(/>/g, '').replace(/"/g, '');
}

function findItems() {
    var search = getSaveQuery();
    if (search !== "") {
        var url = document.location.origin + '/items/find?query=' + search;
        var element = document.getElementById('sort_criteria');
        if (element !== null) {
           url+='&' + getSortingParams();
        }
        location.href = url;
    }
}

function getByCategoryAndQuery(categoryId) {
    var search = getSaveQuery();
    var url = document.location.origin + '/items/find?query=' + search;
    var element = document.getElementById('sort_criteria');
    if (element !== null) {
        url+='&' + getSortingParams() ;
    }

    url+='&' + 'categoryId=' + categoryId;
    location.href = url;
}