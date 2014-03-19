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
					makeChart("dataClasses-graph");
					break;
				case "sensitiveData-panel":
					makeChart("sensitiveData-graph");
					break;
				case "dataSubject-panel":
					makeChart("dataSubjects-graph");
					break;
				case "dataDisclosee-panel":
					makeChart("dataDisclosees-graph");
				
				}
			});

});


var makeChart = function(elementID){
	var id = "#" + elementID;
	$(id + " svg").remove();
	var median,size,natMed;
	var label ="(not set)";
	switch(elementID){
	case "dataClasses-graph":
		$("#sensitiveData-graph svg").remove();
		size = parseInt($("#dataClass-size").text());
		median = parseInt($("#dataClass-median").text());
		natMed = parseInt($("#dataClass-natMed").text());
		label = "Number of data classes"
		break;
	case "sensitiveData-graph":
		$("#dataClasses-graph svg").remove();
		size = parseInt($("#sensitiveData-size").text());
		median = parseInt($("#sensitiveData-median").text());
		natMed = parseInt($("#sensitiveData-natMed").text());
		label = "Number of sensitive data classes"
		break
	case "dataSubjects-graph":
		size = parseInt($("#dataSubject-size").text());
		median = parseInt($("#dataSubject-median").text());
		natMed = parseInt($("#dataSubject-natMed").text());
		label = "Number of data subjects"
		break;
	case "dataDisclosees-graph":
		size = parseInt($("#dataDisclosee-size").text());
		median = parseInt($("#dataDisclosee-median").text());
		natMed = parseInt($("#dataDisclosee-natMed").text());
		label = "Number of data disclosees"
		break;
	}
	Morris.Bar({
		element:elementID,
		data:[
		 {y:'Size',n:size},
		 {y: 'Median',n:median},
		 {y:'Median for this nature of work',n:natMed}     
		],
		xkey:'y',
		ykeys:['n'],
		labels:[label],
		hideHover:'auto'
	})
}

