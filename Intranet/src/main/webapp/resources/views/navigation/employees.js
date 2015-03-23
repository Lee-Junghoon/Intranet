$(function(){
	$(".list-group-item").click(function(){
		var empNo = $(this).find("#empNo")[0].value;
		$(location).attr("href","employees-detail?empNo="+empNo);
	});
});