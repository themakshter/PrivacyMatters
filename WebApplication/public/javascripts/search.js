$(document).ready(function(){
	highlightQuery();
});

var highlightQuery = function(){
	console.log("hello");
	var searchTerm = $("#query").text();
	var listItems = $("#searchResults li");
	
	listItems.each(function(){
		var item = $(this);
		item.html(item.html().replace(searchTerm,"<b>"+searchTerm+"</b>"));
	});
}