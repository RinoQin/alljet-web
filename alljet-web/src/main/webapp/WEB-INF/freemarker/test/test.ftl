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
<div id="chartsBar" style="height:400px;width:1000px"></div>
<script type="text/javascript" src="${request.contextPath}/assets/plugins/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/assets/echarts/echarts.js"></script>
<script type="text/javascript" src="${request.contextPath}/assets/echarts/esl.js"></script>
<script>
$(document).ready(function(){
    initCharts();

});
function initCharts(){
	var activityId="12312";
	$.ajax({
		       url: "${request.contextPath}/test/statChartsResult.json",
		       type: "post",
		       dataType: "json",
		       data: {
		            activityId : activityId 
		       },
		       success: function(data){
					if(data){
		       			if(data.flag=="true"){
		       				
		       				 // 使用
       						require(
           						[
               						'echarts',
               						'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
           						],
           						function (ec) {
               						// 基于准备好的dom，初始化echarts图表
               						var myChart = ec.init(document.getElementById('chartsBar')); 
               
               						var option = {
                   						tooltip: {
                       						show: true
                   						},
                   						legend: {
                   							x:'center',
                   							y:'bottom',
                       						data:data.legends
                   						},
                  						 xAxis : [
                       						{
                           						type : 'category',
                          						data : data.xAxisRes
                       						}
                   						],
                   						yAxis : [
                      						{
                           						type : 'value'
                       						}
                   						],
                   						series : [
                       						{
                           						"name":data.legends[0],
                           						"type":"bar",
                           						"barGap":10,
                           						"barCategoryGap":90,
                           						"barWidth":30,
                           						"barMaxWidth":30,
                           						"itemStyle": {
    												normal: {
        												color:'#4F81BD'
    												}
												},
                           						"data":data.yAxisExpect
                       						},{
                           						"name":data.legends[1],
                           						"type":"bar",
                           						"barGap":10,
                           						"barCategoryGap":90,
                           						"barWidth":30,
                           						"barMaxWidth":30,
                           						"itemStyle": {
    												normal: {
        												color:'#C0504D'
    												}
												},
                           						"data":data.yAxisStat
                       						}
                   						]
                  					};
       
                  					// 为echarts对象加载数据 
                  					myChart.setOption(option); 
                  					window.onresize = myChart.resize;
           		 				}
       						);
		       			}
		       		}
		       	}
		    });

 
}
</script>
