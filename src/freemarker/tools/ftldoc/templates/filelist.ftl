<html>
<body>
<h3>Macro Libraries</h3>
<#list files as file>
<a href="${file.name}${suffix}" target="main">${file.name}</a><br>
</#list>
</html>
