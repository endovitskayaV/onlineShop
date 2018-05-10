<div class="row" style="margin-top: 30px">
    <div class="col s2">
        <#if currentUser??>
                  <#if currentUser.roleId==1>
              <br>

              <a style="margin-left: 10px" href="/items/add">
                  <i class="material-icons cl-4db6a sz-30">add</i><span class="cl-4db6ac">New item<span>
              </a>
                  </#if>
        </#if>

    </div>
    <div class="row">
        <div class="col s5 offset-s1">
            <div class="card horizontal hoverable">
                <div class="card-content">
                    <div id="message_div"></div>
                    <div id="sort_criteria_div">
                        <select id="sort_criteria" name="categoryId" onchange="sortItems()">
                            <#if selectedSortCriteria??>
                                <#switch selectedSortCriteria>
                                    <#case "producerAcs">
                                    <option id="producerAcs" selected>Name(A-z)</option>
                            <option id="producerDes">Name(Z-a)</option>
                            <option id="popularityDes">Popularity</option>
                            <option id="priceAcs">Cheap first</option>
                            <option id="priceDes">Expensive first</option>
                                        <#break>
                                    <#case "producerDes">
                                    <option id="producerDes" selected>Name(Z-a)</option>
                                    <option id="producerAcs">Name(A-z)</option>
                                     <option id="popularityDes">Popularity</option>
                                     <option id="priceAcs">Cheap first</option>
                             <option id="priceDes">Expensive first</option>
                                        <#break>
                                    <#case "priceAcs">
                                         <option id="priceAcs" selected>Cheap first</option>
                                         <option id="priceDes">Expensive first</option>
                                          <option id="popularityDes">Popularity</option>
                                    <option id="producerDes">Name(Z-a)</option>
                                    <option id="producerAcs">Name(A-z)</option>

                                        <#break>
                                    <#case "priceDes">
                                         <option id="priceDes" selected>Expensive first</option>
                                         <option id="priceAcs">Cheap first</option>
                                         <option id="popularityDes">Popularity</option>
                                    <option id="producerDes">Name(Z-a)</option>
                                    <option id="producerAcs">Name(A-z)</option>

                                        <#break>
                                    <#case "popularityDes">
                                         <option id="popularityDes">Popularity</option>
                                    <option id="producerAcs">Name(A-z)</option>
                            <option id="producerDes">Name(Z-a)</option>
                            <option id="priceAcs">Cheap first</option>
                            <option id="priceDes">Expensive first</option>
                                        <#break>
                                </#switch>

                            <#else>
                            <option id="popularityDes" selected>Popularity</option>
                                <option id="producerAcs">Name(A-z)</option>
                            <option id="producerDes">Name(Z-a)</option>
                            <option id="priceAcs">Cheap first</option>
                            <option id="priceDes">Expensive first</option>
                            </#if>

                        </select>
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>

