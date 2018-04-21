function findItems() {
    var search = $('#search').val();
    location.href =document.location.origin + '/items/filter?query=' + search;

}