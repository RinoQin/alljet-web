<h3>${hello}</h3>
<table>
<#list resultlist as results>
<tr>
<td>${results.id}</td><td>${results.testName}</td><td>${results.testValue}</td>
</tr>
</#list>
</table>
<span>${testVo.id}:${testVo.testName}:${testVo.testValue}</span>
<#--><table>
<#list pageList as pageData>
<tr>
<td>${pageData.id}</td><td>${pageData.testName}</td><td>${pageData.testValue}</td>
</tr>
</#list>
</table>
<span>每页${pagination.pagesize}条，第${pagination.currentPage}页，共${pagination.pageCount}页，共${pagination.totalRows}条</span>
<#-->