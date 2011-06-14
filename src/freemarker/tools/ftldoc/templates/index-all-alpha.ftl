<html>
<head>
<script language="javascript">
function setTitle() {
	parent.document.title="ftldoc - Index";
}
</script>
</head>
<body onLoad="setTitle();">
 <#include "nav.ftl">
 <br>
 <#assign lastLetter = "" />
                <#list macros as macro>
                
<#if macro.name[0]?cap_first != lastLetter>
<#assign lastLetter = macro.name[0]?cap_first />
<a href="#${lastLetter}">${lastLetter}</a>
</#if>
                            
                </#list>
 <hr>
 
 
<#assign lastLetter = "" />
                <#list macros as macro>
                
<#if macro.name[0]?cap_first != lastLetter>
<#assign lastLetter = macro.name[0]?cap_first />
<a name="${lastLetter}" /><h3>${lastLetter}</h3>
</#if>
                            <b><a href="${macro.filename}.html#${macro.name}">
                                            ${macro.name}</a>
                                        </b> - ${macro.type?cap_first} in file
<a href="${macro.filename}.html">${macro.filename}</a>
                <br>
                </#list>
                
                
                </body>
                </html>
