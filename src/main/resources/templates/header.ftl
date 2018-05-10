<div class="white hoverable">
    <div class="row">
         <#if currentUser??>

             <div class="input-field col s2 offset-s9">
                 <span class="cl-4db6ac truncate"><b>${currentUser.email}</b></span>
             </div>
             <div class="input-field col">
                 <a href="/logout" class="cl-4db6ac">logout</a>
             </div>

         <#else>

        <div class="input-field col s1 offset-s10">
            <a href="/login" class="cl-4db6ac">log in</a>
        </div>
        <div class="input-field col">
            <a href="/signup" class="cl-4db6ac">register</a>
        </div>
         </#if>
    </div>

    <div class="row">

        <div class="input-field col s1 offset-s3">
            <div class="right"><a href="/items" class="flow-text cl-4db6ac fs-40">Volt</a></div>
        </div>
        <div class="input-field col s3">
            <input placeholder="Search" id="search" type="text" class="left">
        </div>
        <div style="margin-top: 23px;" class="input-field col s1">
            <a href="javascript: findItems()" class="btn left"><i class="material-icons">search</i></a>
        </div>
        <#if currentUser??>
        <div class="input-field col s1 offset-s2">
            <div class="right"><a class="cl-4db6ac" href="/orders">my orders</a></div>
        </div>

        <div class="input-field col">
            <div class="right"><a href="/basket"> <i class="material-icons cl-4db6a sz-36">shopping_cart</i></a></div>
        </div>
        <#else>
         <div class="input-field col s1 offset-s2">
             <div class="right"><a href="/basket"> <i class="material-icons cl-4db6a sz-36">shopping_cart</i></a></div>
         </div>
        </#if>

    </div>
    <div class="divider z-depth-3"></div>
</div>
