<#macro foot_copyright_macro >
        Design: <a href="${foot_copyright_design_url}">${foot_copyright_design}</a> Author:<a href="${foot_copyright_author_url}">${foot_copyright_author}</a>
</#macro>

<#macro foot_friendlinks list=[] left="" right="">
        <#list list as item>
		<#if item[left]?exists && item[right]?exists>
			<li><a href="${item[left]}">${item[right]}</a></li>
		</#if>
	</#list>
</#macro>