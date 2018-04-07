function addCategory(id) {
    var element = document.getElementById(id);
    var instance = M.FormSelect.getInstance(element);
    var selectedValues = instance.getSelectedValues();
    // var query=document.location.origin+"/categories/add?id="+$("#id").val()+"&name="+$("#name").val()+"&description="+$("#description").val()+"&rating="+$("#rating").val();
    var i = 0;
    var query = "name=" + $("#category_name").val() + "&description=" + $("#category_description").val() + "&rating=" + $("#category_rating").val();

    $.each(selectedValues, function (key, value) {
        query += '&characteristicIds[' + i + ']=' + selectedValues[i];
        i++;
    });

    $.ajax({
        url: document.location.origin + '/categories/add',
        type: "POST",
        contentType: "application/x-www-form-urlencoded",
        data: query,
        success: function (data) {
            $.get("/categories/" + data, function (data) {
                var append='<option  selected id="'+data.id+'"'+
                    ' name="'+data.name+'"' +
                    ' value="'+data.id+'">ddddd</option>';
                $('#category').append(append);
                $('#category').focus();
                $(data.id).select();
                var elem = document.getElementById('category_modal');
                var instance = M.Modal.getInstance(elem);
                instance.close();

            })
        },
        error: function (data) {
            var elem = document.getElementById('error-modal');
            elem.append(data.responseText);
            var instance = M.Modal.getInstance(elem);
            instance.open();
        }
    })
}