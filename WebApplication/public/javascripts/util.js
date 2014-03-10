$(document).ready(function() {
$(".purpose").click(function() {
	if($(this).find("span").hasClass('glyphicon glyphicon-plus'))
	{
		$(".icon").removeClass("glyphicon glyphicon-minus").addClass("glyphicon glyphicon-plus");
		$(this).find("span").removeClass("glyphicon glyphicon-plus").addClass("glyphicon glyphicon-minus");
//		$(this).find(".glyphicon").remove(); 
//		$(this).find(".panel-title").append('<span class="glyphicon glyphicon-minus"></span>');
	}
	else
	{   
		console.log("closing");
		$(this).find("span").removeClass("glyphicon glyphicon-minus").addClass("glyphicon glyphicon-plus");
//		$(this).find(".glyphicon").remove(); 
//		$(this).find(".panel-title").append('<span class="glyphicon glyphicon-plus"></span>');
	}
});
});