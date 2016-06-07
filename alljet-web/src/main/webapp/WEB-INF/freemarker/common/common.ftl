<#ftl strip_whitespace=true><!-- 剥离空白 -->
<#macro drawOptionList list=[] left="" right="" value="" default="">
	<#if default!=""><option value="">${default}</option></#if>
	<#list list as item>
		<#if item[left]?exists && item[right]?exists>
			<option value="${item[left]}" <#if item[left]==value?string>selected="selected"</#if>>${item[right]}</option>
		</#if>
	</#list>
</#macro>

<#macro drawOptionListJs list=[] left="" right="" value="" default="">
	<#if default!="">'<option value="">${default}</option>'+</#if>
	<#list list as item>
		<#if item[left]?exists && item[right]?exists>
			'<option value="${item[left]}" <#if item[left]==value?string>selected="selected"</#if>>${item[right]}</option>'+
		</#if>
	</#list>
</#macro>

<#macro drawOptionMap map={} value="" default="">
	<#if default!=""><option value="">${default}</option></#if>
	<#list map?keys as key>
		<#if key?exists && map[key]?exists>
			<option value="${key}"  <#if key==value>selected="selected"</#if>>${map[key]}</option>
		</#if>
	</#list>
</#macro>

<#macro drawJsObject map={}>{<#list map?keys as key>"${key}":"${map[key]}"<#if key_has_next>,</#if></#list>}</#macro>