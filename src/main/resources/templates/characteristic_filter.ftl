<#if items??>
    <#if characteristics??>
          <div id="characteristrics_filter" class="col s3 offset-s1">
              <div><ul class="collapsible expandable">

            <#list characteristics as characteristic>
                <li id="characteristics_values">
                    <div id="characteristic_name" class="collapsible-header">${characteristic.name}</div>
                    <input hidden id="characteristic_code" value="${characteristic.code}"/>
                    <div id="values" class="collapsible-body">

                <#list characteristic.values as characteristicValue>

                    <p><label id="3">
                        <input type="checkbox" id="characteristic_value"  ${characteristicValue.checked?then('checked="checked"', '')}
                               value="${characteristicValue.value}"/>
                        <span>${characteristicValue.value}</span>
                    </label></p>

                </#list>

                    </div>
                </li>
            </#list>


              </ul></div>


              <a style="visibility: hidden" id="apply_btn" href="javascript: findByCharacteristics()"
                 class="waves-effect waves-light btn">apply</a>
          </div>
    </#if>
</#if>