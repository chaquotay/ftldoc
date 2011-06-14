<html>
<head>
<script language="javascript">
function setTitle() {
	parent.document.title="ftldoc - Overview";
}
</script>
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
</style>
</head>
<body onLoad="setTitle();">
<#include "nav.ftl">
<h3>Overview</h3>

<table border="1" cellpadding="4">
    <tr><td colspan="2" class="heading">Library Summary</td></tr>
	<#list files as file>
	<tr>
		<td width="160px"><a href="${file.filename}.html"><b>${file.filename}</b></a></td>
		<td>${file.comment.short_comment?if_exists}&nbsp;</td>
	</tr>
	</#list>
</table>
</body>
</html>
