<div class="row" style="margin-top: 30px">
    <div class="col s3"></div>
    <div  class="col s8">
        <div class="row">
            <div class="col s8 offset-s1">
                <div class="card horizontal hoverable">
                    <div class="card-content">
                        <div id="message_div"></div><div id="sort_criteria_div">
                        <select  id="sort_criteria" name="categoryId" onchange="sortItems()">
                            <#if selectedSortCriteria??>
                                <#switch selectedSortCriteria>
                                    <#case "producerAcs">
                                    <option id="producerAcs" selected>Name(A-z)</option>
                            <option id="producerDes">Name(Z-a)</option>
                            <option id="priceAcs">Cheap first</option>
                            <option id="priceDes">Expensive first</option>
                                        <#break>
                                    <#case "producerDes">
                                    <option id="producerDes" selected>Name(Z-a)</option>
                                    <option id="producerAcs">Name(A-z)</option>
                                     <option id="priceAcs">Cheap first</option>
                             <option id="priceDes">Expensive first</option>
                                        <#break>
                                    <#case "priceAcs">
                                         <option id="priceAcs" selected>Cheap first</option>
                                    <option id="producerDes">Name(Z-a)</option>
                                    <option id="producerAcs">Name(A-z)</option>
                                     <option id="priceDes">Expensive first</option>
                                        <#break>
                                    <#case "priceDes">
                                         <option id="priceDes" selected>Expensive first</option>
                                    <option id="producerDes">Name(Z-a)</option>
                                    <option id="producerAcs">Name(A-z)</option>
                                     <option id="priceAcs">Cheap first</option>
                                        <#break>
                                </#switch>

                            <#else>
                                <option id="producerAcs" selected>Name(A-z)</option>
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
</div>
