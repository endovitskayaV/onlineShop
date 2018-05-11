<#if item.photoNameCompressed??>
                <img  style="margin-top: 12px" id="img" width="60" height="200" src="../img/${item.photoNameCompressed}">
<#else>
                    <img id="img" width="60" height="200" src="../img/no_photo.png">
</#if>

<!--<script>
    $(document).ready(function(){
        $('img').each(function(){
            $(this).error(function(){ $(this).attr('src', '../../img/no_photo.png'); });
        });
    });
</script>-->