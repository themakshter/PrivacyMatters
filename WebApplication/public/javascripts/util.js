$(function() {
	$(".pop-over").popover();
});

$(document).ready(function(){
	$(".purpose").click(
			function() {
				if ($(this).find("span").hasClass('glyphicon-plus')) {
					$(".purpose span").removeClass("glyphicon-minus").addClass(
							"glyphicon-plus");
					$(this).find("span").removeClass("glyphicon-plus").addClass(
							"glyphicon-minus");
					var purposeId = $(this);
					console.log(purposeId);
				} else {
					$(this).find("span").removeClass("glyphicon-minus").addClass(
							"glyphicon-plus");
				}
			});
	
	$(".inner-panel").click(
			function() {
				var id= this.id;
				switch(id){
				case "dataClass-panel":
					makePanelChart("dataClasses");
					break;
				case "sensitiveData-panel":
					makePanelChart("sensitiveData");
					break;
				case "dataSubject-panel":
					makePanelChart("dataSubjects");
					break;
				case "dataDisclosee-panel":
					makePanelChart("dataDisclosees");
				
				}
			});
	
	$(".stat-object").click(
			function(){
				var purpose = $(this).parent().parent().parent().parent().parent().parent().get(0).id;
				if($(this).hasClass('dataClass-item')){
					makeItemChart("dataClass",this.id,purpose);
				}else if($(this).hasClass('sensitiveData-item')){
					purpose = $(this).parent().parent().parent().parent().parent().parent().parent().parent().get(0).id;
					makeItemChart("sensitiveData",this.id,purpose);
				}else if($(this).hasClass('dataSubject-item')){
					makeItemChart("dataSubject",this.id,purpose);
				}else if($(this).hasClass('dataDisclosee-item')){
					makeItemChart("dataDisclosee",this.id,purpose);
				}
			});

});


var makePanelChart = function(element){
	var elementID = "#general-"+element;
	var graph;
	
	var median = parseInt($(elementID+"-median").text());
	var size = parseInt($(elementID+"-size").text());
	var natMed = parseInt($(elementID+"-natMed").text());
	
	var label1 = "(not set)";
	var label2 = "Overall Median";
	var label3 = "Median for this nature of work";
	
	switch(element){
	case "dataClasses":
		graph = "general-left-graph";
		$("#general-left-heading").empty().append("<h4>Data Classes Count Comparison</h4><p class=\"text-justify\">This compares the number of data classes of this data controller compared to the average amount and the average amount for this nature of work</p>");
		label1 = "This data controller";
		break;
	case "sensitiveData":
		graph = "general-left-graph";
		$("#general-left-heading").empty().append("<h4>Sensitive Data Count Comparison</h4><p class=\"text-justify\">This compares the number of sensitive data classes of this data controller compared to the average amount and the average amount for this nature of work</p>");
		label1 = "This data controller";
		break
	case "dataSubjects":
		graph="general-middle-graph";
		$("#general-middle-heading").empty().append("<h4>Data Subjects Count Comparison</h4><p class=\"text-justify\">This compares the number of data subjects of this data controller compared to the average amount and the average amount for this nature of work</p>");
		label1 = "This data controller";
		break;
	case "dataDisclosees":
		graph="general-right-graph";
		$("#general-right-heading").empty().append("<h4>Data Disclosees Count Comparison</h4><p class=\"text-justify\">This compares the number of data disclosees of this data controller compared to the average amount and the average amount for this nature of work</p>");
		label1 = "This data controller";
		break;
	}
	
	clearPanel(graph);
	
	Morris.Bar({
		element:graph,
		data:[
		 {y:'',n:size,x:median,z:natMed}     
		],
		xkey:'y',
		ykeys:['n','x','z'],
		labels:[label1,label2,label3],
		hideHover:'auto',
		redraw:true
	})
}

var makeItemChart = function(type,id,purpose){
	var count = parseInt($("#count").text());
	var number = parseInt($("#"+purpose+"-"+type+"-"+id).text());
	var item=$("#"+id).text();
	var diff = count - number;
	var link = $("#"+purpose+"-" + type + "-link-"+id).attr("href");
	var label1,label2;
	label1="Controllers which collect";
	label2="Controllers which do not";
	var graph;
	switch(type){
	case "dataClass":
		graph=purpose+"-left-graph";	
		$("#"+purpose+"-left-heading").empty().append("<h4>Popularity of Data Class ("+item+")</h4>");
		$("#"+purpose+"-left-body").empty().append("<p class=\"text-justify\">This shows how many controllers collect this data class and how many do not</p><a href=\" "+link+"\">View controllers collecting this data class</a>");
		break;
	case "sensitiveData":
		graph=purpose+"-left-graph";
		$("#"+purpose+"-left-heading").empty().append("<h4>Popularity of Sensitive Data Class ("+item+")</h4>");
		$("#"+purpose+"-left-body").empty().append("<p class=\"text-justify\">This shows how many controllers collect this sensitive data class and how many do not</p><a href=\" "+link+"\">View controllers collecting this sensitive data class</a>");
		break;
	case "dataSubject":
		graph=purpose+"-middle-graph";
		$("#"+purpose+"-middle-heading").empty().append("<h4>Popularity of Data Subject ("+item+")</h4>");
		$("#"+purpose+"-middle-body").empty().append("<p class=\"text-justify\">This shows how many controllers collect information from this data subject and how many do not</p><a href=\" "+link+"\">View controllers collecting from this data subject</a>");
		break;
	case "dataDisclosee":
		graph=purpose+"-right-graph";
		$("#"+purpose+"-right-heading").empty().append("<h4>Popularity of Data Disclosee ("+item+")</h4>");
		$("#"+purpose+"-right-body").empty().append("<p class=\"text-justify\">This shows how many controllers share information with this data disclosee and how many do not</p><a href=\" "+link+"\">View controllers collecting sharing information with this disclosee</a>");
		label1="Controllers which share";
		label2="Controllers which do not";
		break;	 
	}
	
	clearPanel(graph);
	
	Morris.Donut({
		  element: graph,
		  data: [
		    {label: label1, value: number},
		    {label: label2, value: diff}
		  ],
		  redraw:true
		});
}




var clearAllCharts = function(){
	$(".graph svg").remove();
	$(".graph-heading").empty();
}

var clearPanel = function(panel){
	$("#"+panel + " svg").remove();
	$("#"+panel + " .morris-hover").remove();
}

