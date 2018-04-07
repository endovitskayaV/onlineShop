function post(id) {
    var element = document.getElementById(id);
    var instance = M.FormSelect.getInstance(element);
    var selectedValues = instance.getSelectedValues();
    var query=document.location.origin+"/categories/add?id="+$("#id").val()+"&name="+$("#name").val()+"&description="+$("#description").val()+"&rating="+$("#rating").val();
    var i=0;
    $.each(selectedValues, function (key, value) {
        query+='&characteristicIds['+i+']='+selectedValues[i];
        i++;
    });

    $.post(query);


}