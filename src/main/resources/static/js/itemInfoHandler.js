function dropdown_div(id) {
    //$('#' + id).toggle();
     var element = document.getElementById(id);
     element.style.display = ( element.style.display === 'block' )? 'none' : 'block';
}