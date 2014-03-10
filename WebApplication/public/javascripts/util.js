$(document).ready(function() {
$(".panel-heading").click(function (event) {
	if($(this).find("span").hasClass('glyphicon glyphicon-plus'))
	{
		$(this).find(".glyphicon").remove(); 
		$(this).find(".panel-title").append('<span class="glyphicon glyphicon-minus"></span>');
	}
	else
	{      
		$(this).find(".glyphicon").remove(); 
		$(this).find(".panel-title").append('<span class="glyphicon glyphicon-plus"></span>');
	}
}); 
});