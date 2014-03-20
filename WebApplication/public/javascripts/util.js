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
				if($(this).hasClass('dataClass-item')){
					makeItemChart("dataClass",this.id);
				}else if($(this).hasClass('sensitiveData-item')){
					makeItemChart("sensitiveData",this.id);
				}else if($(this).hasClass('dataSubject-item')){
					makeItemChart("dataSubject",this.id);
				}else if($(this).hasClass('dataDisclosee-item')){
					makeItemChart("dataDisclosee",this.id);
				}
			});

});


var makePanelChart = function(element){
	var elementID = "#"+element;
	var graph;
	
	var median = parseInt($(elementID+"-median").text());
	var size = parseInt($(elementID+"-size").text());
	var natMed = parseInt($(elementID+"-natMed").text());
	
	var label1 = "(not set)";
	var label2 = "Overall Median";
	var label3 = "Median for this nature of work";
	
	switch(element){
	case "dataClasses":
		graph = "left-graph";
		$("#left-heading").empty().append("<h4>Data Classes Count Comparison</h4><p>This compares the number of data classes of this data controller compared to the average amount and the average amount for this nature of work</p>");
		label1 = "This data controller";
		break;
	case "sensitiveData":
		graph = "left-graph";
		$("#left-heading").empty().append("<h4>Sensitive Data Count Comparison</h4><p>This compares the number of sensitive data classes of this data controller compared to the average amount and the average amount for this nature of work</p>");
		label1 = "This data controller";
		break
	case "dataSubjects":
		graph="middle-graph";
		$("#middle-heading").empty().append("<h4>Data Subjects Count Comparison</h4><p>This compares the number of data subjects of this data controller compared to the average amount and the average amount for this nature of work</p>");
		label1 = "This data controller";
		break;
	case "dataDisclosees":
		graph="right-graph";
		$("#right-heading").empty().append("<h4>Data Disclosees Count Comparison</h4><p>This compares the number of data disclosees of this data controller compared to the average amount and the average amount for this nature of work</p>");
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

var makeItemChart = function(type,id){
	var count = parseInt($("#count").text());
	var number = parseInt($("#"+type+"-"+id).text());
	var item=$("#"+id).text();
	var diff = count - number;
	var label1,label2;
	label1="Controllers which collect";
	label2="Controllers which do not";
	var graph;
	switch(type){
	case "dataClass":
		graph="left-graph";	
		var link=$("#lala").attr('href');
		 $("#left-heading").empty().append("<h4>Popularity of Data Class ("+item+")</h4><p>This shows how many controllers collect this data class and how many do not</p>");
		 break;
	case "sensitiveData":
		graph="left-graph";
		$("#left-heading").empty().append("<h4>Popularity of Sensitive Data Class ("+item+")</h4><p>This shows how many controllers collect this data class and how many do not</p>");
		break;
	case "dataSubject":
		graph="middle-graph";
		$("#middle-heading").empty().append("<h4>Popularity of Sensitive Data Subject ("+item+")</h4><p>This shows how many controllers collect this information from this data subject and how many do not</p>");
		break;
	case "dataDisclosee":
		graph="right-graph";
		$("#right-heading").empty().append("<h4>Popularity of Data Disclosee ("+item+")</h4><p>This shows how many controllers share information with this data disclosee and how many do not</p>");
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

