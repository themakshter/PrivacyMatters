$(function() {
	$(".pop-over").popover();
});

$(function(){
	$(".tool-tip").tooltip();
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
				} else {
					$(this).find("span").removeClass("glyphicon-minus").addClass(
							"glyphicon-plus");
				}
			});
	
	$(".inner-panel").click(
			function() {
				var purpose = $(this).parent().parent().parent().parent().parent().attr('id');
				var id= this.id;
				switch(id){
				case "dataClass-panel":
					makePanelChart(purpose,"dataClasses");
					break;
				case "sensitiveData-panel":
					purpose = $(this).parent().parent().parent().parent().parent().parent().parent().attr('id');
					makePanelChart(purpose,"sensitiveData");
					break;
				case "dataSubject-panel":
					makePanelChart(purpose,"dataSubjects");
					break;
				case "dataDisclosee-panel":
					makePanelChart(purpose,"dataDisclosees");
				
				}
			});
	
	$(".stat-object").click(
			function(){
				var purpose = $(this).parent().parent().parent().parent().parent().parent().attr('id');
				if($(this).hasClass('dataClass-item')){
					makeItemChart("dataClass",this.id,purpose);
				}else if($(this).hasClass('sensitiveData-item')){
					purpose = $(this).parent().parent().parent().parent().parent().parent().parent().parent().attr('id');
					makeItemChart("sensitiveData",this.id,purpose);
				}else if($(this).hasClass('dataSubject-item')){
					makeItemChart("dataSubject",this.id,purpose);
				}else if($(this).hasClass('dataDisclosee-item')){
					makeItemChart("dataDisclosee",this.id,purpose);
				}else if($(this).hasClass('purpose-item')){
					var purposeId=$(this).parent().parent().parent().parent().attr('id');
					makePurposeChart(purposeId);
				}
			});

});



var makePurposeChart = function(purposeId){
	var count = parseInt($("#count").text());
	var number = parseInt($("#"+purposeId+"-purpose-number").text());
	console.log(number);
	console.log(count);
	var percentage = (number/count) * 100;
	percentage = percentage.toFixed(2);
	var diff = 100 - percentage;
	var link = $("#"+purposeId+"-link-purpose").attr("href");
	var item=$("#"+purposeId+"-title").text();
	var graph = purposeId+"-purpose-graph";
	var heading = purposeId+"-purpose-heading";
	$("#"+heading).empty().append("<h4>Popularity of Purpose("+item+")</h4><p class\"text-justify\">How many controllers collect data for this purpose and how many do not</p><a href=\" "+link+"\">View those collecting for this purpose ("+(number-1)+")</a>");
	var label1="Controllers which collect(%)";
	var label2="Controllers which do not(%)";
	clearPanel(graph);
	
	Morris.Donut({
		  element: graph,
		  data: [
		    {label: label1, value: percentage},
		    {label: label2, value: diff}
		  ],
		  redraw:true
		});
	
	focusOn("#"+graph);
};


var focusOn = function(element){
	$(window).scrollTop($(element).parent().parent().parent().parent().offset().top);
};

var makePanelChart = function(purpose,type){
	var typeID = "#"+purpose+"-"+type;
	var graph;
	var median = parseInt($(typeID+"-median").text());
	var size = parseInt($(typeID+"-size").text());
	var natMed = parseInt($(typeID+"-specMed").text());
	var label = $("#specMed-label").text()
	var label1 = "This data controller";
	var label2 = "Overall Median";
	var label3 = "Median for this "+label;
	var heading;
	switch(type){
	case "dataClasses":
		graph = purpose+"-left-graph";
		heading = "#"+purpose+"-left-heading";
		$("#"+purpose+"-left-heading").empty().append("<h4>Data Classes Count Comparison</h4>");
		$("#"+purpose+"-left-body").empty().append("<p class=\"text-justify\">This compares the number of data classes of this data controller compared to the average amount and the average amount for this "+label+"</p>");
		break;
	case "sensitiveData":
		heading = "#"+purpose+"-left-heading";
		graph = purpose+"-left-graph";
		$("#"+purpose+"-left-heading").empty().append("<h4>Sensitive Data Count Comparison</h4>");
		$("#"+purpose+"-left-body").empty().append("<p class=\"text-justify\">This compares the number of sensitive data classes of this data controller compared to the average amount and the average amount for this "+label+"</p>");
		break
	case "dataSubjects":
		heading = "#"+purpose+"-middle-heading";
		graph=purpose+"-middle-graph";
		$("#"+purpose+"-middle-heading").empty().append("<h4>Data Subjects Count Comparison</h4>");
		$("#"+purpose+"-middle-body").empty().append("<p class=\"text-justify\">This compares the number of data subjects of this data controller compared to the average amount and the average amount for this "+label+"</p>");
		break;
	case "dataDisclosees":
		heading = "#"+purpose+"-right-heading";
		graph=purpose+"-right-graph";
		$("#"+purpose+"-right-heading").empty().append("<h4>Data Disclosees Count Comparison</h4>");
		$("#"+purpose+"-right-body").empty().append("<p class=\"text-justify\">This compares the number of data disclosees of this data controller compared to the average amount and the average amount for this "+label+"</p>");
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
		hideHover:'true',
		redraw:true
	})
	focusOn(heading);
};

var makeItemChart = function(type,id,purpose){
	var count = parseInt($("#count").text());
	var number = parseInt($("#"+purpose+"-"+type+"-"+id).text());
	var item=$("#"+id).text();
	var percentage = (number/count) * 100;
	percentage = percentage.toFixed(2);
	var diff = (100 - percentage).toFixed(2);
	var heading;
	var link = $("#"+purpose+"-" + type + "-link-"+id).attr("href");
	var label1,label2;
	label1="Controllers which collect (%)";
	label2="Controllers which do not (%)";
	var graph;
	switch(type){
	case "dataClass":
		heading = "#"+purpose+"-left-heading";
		graph=purpose+"-left-graph";	
		$("#"+purpose+"-left-heading").empty().append("<h4>Popularity of Data Class ("+item+")</h4>");
		$("#"+purpose+"-left-body").empty().append("<p class=\"text-justify\">How many controllers collect this data class and how many do not</p><a href=\" "+link+"\">View those collecting this data class ("+(number-1)+")</a>");
		break;
	case "sensitiveData":
		heading = "#"+purpose+"-left-heading";
		graph=purpose+"-left-graph";
		$("#"+purpose+"-left-heading").empty().append("<h4>Popularity of Sensitive Data Class ("+item+")</h4>");
		$("#"+purpose+"-left-body").empty().append("<p>How many controllers collect this sensitive data class and how many do not</p><a href=\" "+link+"\">View those collecting this sensitive data class ("+(number-1)+")</a>");
		break;
	case "dataSubject":
		heading = "#"+purpose+"-middle-heading";
		graph=purpose+"-middle-graph";
		$("#"+purpose+"-middle-heading").empty().append("<h4>Popularity of Data Subject ("+item+")</h4>");
		$("#"+purpose+"-middle-body").empty().append("<p>How many controllers collect information from this data subject and how many do not</p><a href=\" "+link+"\">View those collecting from this data subject ("+(number-1)+")</a>");
		break;
	case "dataDisclosee":
		heading = "#"+purpose+"-right-heading";
		graph=purpose+"-right-graph";
		$("#"+purpose+"-right-heading").empty().append("<h4>Popularity of Data Disclosee ("+item+")</h4>");
		$("#"+purpose+"-right-body").empty().append("<p>How many controllers share information with this data disclosee and how many do not</p><a href=\" "+link+"\">View those sharing information with this disclosee ("+(number-1)+")</a>");
		label1="Controllers which share (%)";
		label2="Controllers which do not (%)";
		break;	 
	}
	
	clearPanel(graph);
	
	Morris.Donut({
		  element: graph,
		  data: [
		    {label: label1, value: percentage},
		    {label: label2, value: diff}
		  ],
		  redraw:true
		});
		focusOn(heading);
};




var clearAllCharts = function(){
	$(".graph svg").remove();
	$(".graph-heading").empty();
};

var clearPanel = function(panel){
	$("#"+panel + " h3").remove();
	$("#"+panel + " svg").remove();
	$("#"+panel + " .morris-hover").remove();
};

