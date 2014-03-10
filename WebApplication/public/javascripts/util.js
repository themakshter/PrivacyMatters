$(document).ready(function() {
$(".purpose").click(function() {
	//alert($(this).find("span").length);
	//if($(this).find("span").length){
	if($(this).find("span").hasClass('glyphicon glyphicon-plus'))
	{
		$(".purpose-title span").remove()
		$(".purpose-title").append('<span class="glyphicon glyphicon-plus"></span>');
		$(this).find(".glyphicon").remove(); 
		$(this).find(".panel-title").append('<span class="glyphicon glyphicon-minus"></span>');
	}
	else
	{   
		$(this).find(".glyphicon").remove(); 
		$(this).find(".panel-title").append('<span class="glyphicon glyphicon-plus"></span>');
		
	}
	//}
}); 
});