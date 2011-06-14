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
        <#list categories?keys as category>
            <#if categories[category]?has_content>
                <#if category?has_content>
                <h3>Category ${category}</h3>
                <#else>
                <h3>no category</h3>
                </#if>
                <#list categories[category] as macro>
                
                
                            <b><a href="${macro.filename}.html#${macro.name}">
                                            ${macro.name}</a>
                                        </b> - ${macro.type?cap_first} in file
<a href="${macro.filename}.html">${macro.filename}</a>
                <br>
                </#list>
                <br>
                <#if category_has_next><hr></#if>
            </#if>
        </#list>
</body>
</html>
