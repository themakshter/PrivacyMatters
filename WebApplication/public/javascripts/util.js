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

});


var makePanelChart = function(element){
	
	var elementID = "#"+element;
	var graph = element+"-graph";
	var graphID = "#"+graph;
	$(graphID + " svg").remove();
	
	var median = parseInt($(elementID+"-median").text());
	var size = parseInt($(elementID+"-size").text());
	var natMed = parseInt($(elementID+"-natMed").text());
	
	var label1 = "(not set)";
	var label2 = "Overall Median";
	var label3 = "Median for this nature of work";
	
	switch(element){
	case "dataClasses":
		
		$("#sensitiveData-graph svg").remove();
		label1 = "Number of data classes collected";
		break;
	case "sensitiveData":
		$("#dataClasses-graph svg").remove();
		label1 = "Number of sensitive data classes collected";
		break
	case "dataSubjects":
		label1 = "Number of data subjects collected information from";
		break;
	case "dataDisclosees":
		label1 = "Number of data disclosees information shared with";
		break;
	}

	Morris.Bar({
		element:graph,
		data:[
		 {y:'',n:size,x:median,z:natMed}     
		],
		xkey:'y',
		ykeys:['n','x','z'],
		labels:[label1,label2,label3],
		hideHover:'auto'
	})
}


var clearAllCharts = function(){
	$(".graph svg").remove();
	$(".hidden").hide();
}

var clearPanel = function(panel){
	
}

