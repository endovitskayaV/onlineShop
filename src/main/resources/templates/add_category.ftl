<form>
    <div class="row">
        <div class="col s8 center">
            <div class="row">
                <div class="input-field col s10 offset-s3">
                    <input id="category_name" required="required" name="category_name" type="text"
                           value="${category.name}">
                    <label for="category_name">Name</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s10 offset-s3">
                    <input id="category_description" name="category_description" type="text"
                           value="${category.description}">
                    <label for="category_description">Description</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s10 offset-s3">
                    <input id="category_rating" name="category_rating" type="number" value="${category.rating}">
                    <label for="category_rating">Rating</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s10 offset-s3">
                    <select id="category_select" required="required" name="characteristicIdArray[0]" multiple>
                        <option value="" disabled selected>Choose your option</option>
                    <#assign i = 1>
                  <#list characteristics as characteristic>
                     <option id="characteristicIdArray[${i}].id" name="characteristicIdArray[${i}].id"
                             value="${characteristic.id}">${characteristic.name}</option>
                      <#assign i++>
                  </#list>
                    </select>
                    <label for="category_select">Characteristics</label>
                </div>
            </div>

            <div class="row">
                <div class="input-field col s2 offset-s3">
                    <a href="javascript: addCategory('category_select')" class="waves-effect waves-light btn">Save</a>
                </div>
                <div class="input-field col s2 offset-s5">
                    <button class="waves-effect waves-light btn" type="reset">Reset</button>
                </div>
            </div>
        </div>
</form>
