<#if item.photoName??>
                <img  id="img" width="60" height="200" src="../img/${item.photoName}">
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


<#--<script>-->
    <#--$("#img")-->
            <#--.load(function(){-->
                <#--alert("cooll");-->
            <#--})-->
            <#--.error(function(){-->
                <#--alert("coole");-->
            <#--});-->

<#--</script>-->


<#--<script>-->
    <#--$("#container").waitForImages(function() {-->
    <#--}, function(loaded, count, success) {-->
        <#--alert(loaded + ' of ' + count + ' images has ' + (success ? 'loaded' : 'failed to load') +  '.');-->
        <#--$(this).addClass('loaded');-->
    <#--});-->
<#--</script>-->