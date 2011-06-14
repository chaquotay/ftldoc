<#import "ftl_highlight.ftl" as ftl>

<html>
<head>
<title>
ftldoc
</title>
<link rel="stylesheet" type="text/css" href="eclipse.css" />
<style>
table {
    width: 100%;
}
td {
    background-color: White;
}
td.heading {
    padding: 3px;
    font-weight: bold;
    font-size: 18px;
    background-color: #CCCCFF;
}
td.category {
    padding: 3px;
    font-weight: bold;
    font-size: 14px;
    background-color: #DDDDFF;
}
div.sourcecode {
    display: none;
    border : 1px solid Black;
    background-color : #DDDDDD; /* #E8E8E8; */
    padding : 3px;
    margin-top : 8px;
}


span {font-family:Courier; font-size:12px}
span.directive {color:blue}
span.userdirective {color:red}
span.interpolation {color:green}
span.textblock {color:black}
span.comment {color:brown}

</style>
<script language="javascript">
function toggle(id) {
    elem = document.getElementById(id);
    if(elem.style.display=="block") {
        elem.style.display="none";
    } else {
        elem.style.display="block";
    }
}

function setTitle() {
	parent.document.title="${filename}";
}
</script>
</head>
<body onLoad="setTitle();">
<#include "nav.ftl">

<#-- start prolog -->
<h3>${filename}</h3>
<#if comment.comment?has_content>
    ${comment.comment}<br>
</#if>
<dl>
    <@printOptional comment.@author?if_exists, "Author" />
    <@printOptional comment.@version?if_exists, "Version" />
</dl>
<#-- end prolog -->

<#-- start summary -->
<table border="1" cellpadding="4">
    <tr><td colspan="2" class="heading">Macro and Function Summary</td></tr>
        <#list categories?keys as category>
            <#if categories[category]?has_content>
                <tr><td colspan="2" class="category">
                <#if category?has_content>
                    Category ${category}
                <#else>
                    no category
                </#if>
                </td></tr>
                <#list categories[category] as macro>
                    <tr>
                        <td width="100px" valign="top">
                            <code>${macro.type}</code>
                        </td>
                        <td>
                            <dl>
                                <dt>
                                    <code>
                                        <b><a href="#${macro.name}">
                                            ${macro.name}</a>
                                        </b>
                                        <@signature macro />
                                    </code>
                                </dt>
                                <dd>
                                    ${macro.short_comment?if_exists}
                                </dd>
                            </dl>
                        </td>
                    </tr>
                </#list>
            </#if>
        </#list>
</table>

<#-- end summary -->
<br>
<#-- start details -->

<table border="1" cellpadding="4">
    <tr><td colspan="2" class="heading">Macro and Function Detail</td></tr>
</table>
<#list macros as macro>
    <dl>
        <dt><code>${macro.type} <b><a name="${macro.name}">${macro.name}</a></b>
                <@signature macro />
        </code></dt>
        <dd>
            <br>
            <#if macro.comment?has_content>
                ${macro.comment}<br><br>
            </#if>
            <dl>
                <@printOptional macro.category?if_exists, "Category" />
                <@printParameters macro />
                <@printOptional macro.@nested?if_exists, "Nested" />
                <@printOptional macro.@return?if_exists, "Return value" />
                <@printSourceCode macro />
            </dl>
        </dd>
    </dl>
    <#if macro_has_next><hr></#if>
</#list>

<#-- end details -->

</body>
</html>

<#macro printParameters macro>
<#if macro.@param?has_content>
<dt><b>Parameters</b></dt>
<dd>
<#list macro.@param as param>
<code>${param.name}</code> - ${param.description}<br>
</#list>
</dd>
</#if>
</#macro>

<#macro printSourceCode macro>
<dt><a href="javascript:toggle('sc_${macro.name}');">Source Code</a></dt>
<dd>
<div class="sourcecode" id="sc_${macro.name}">
<@ftl.print root=macro.node/>
</div>
</dd>
</#macro>

<#macro printOptional value label>
<#if value?has_content>
<dt><b>${label}</b></dt>
<dd>${value}</dd>
</#if>
</#macro>

<#macro signature macro>
    <#if macro.isfunction>
        (
        <#list macro.arguments as argument>
            ${argument}
            <#if argument_has_next>,</#if>
        </#list>
        )
    <#else>
        <#list macro.arguments as argument>
            ${argument}
        </#list>
    </#if>
</#macro>
