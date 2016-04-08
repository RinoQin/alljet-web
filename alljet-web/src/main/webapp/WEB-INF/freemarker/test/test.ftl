<h3>${hello}</h3>
<table>
<#list resultlist as results>
<tr>
<td>${results.id}</td><td>${results.testName}</td><td>${results.testValue}</td>
</tr>
</#list>
</table>