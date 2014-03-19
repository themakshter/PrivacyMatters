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
	var median,size,natMed,label1,label2,label3;
	label1 = label2 = label3 ="(not set)";
	switch(elementID){
	case "dataClasses-graph":
		$("#dataClasses-heading").show();
		$("#sensitiveData-graph svg").remove();
		size = parseInt($("#dataClass-size").text());
		median = parseInt($("#dataClass-median").text());
		natMed = parseInt($("#dataClass-natMed").text());
		label1 = "Number of data classes collected";
		label2 = "Overall Median"
		label3 = "Median for this nature of work"
		break;
	case "sensitiveData-graph":
		$("#dataClasses-graph svg").remove();
		size = parseInt($("#sensitiveData-size").text());
		median = parseInt($("#sensitiveData-median").text());
		natMed = parseInt($("#sensitiveData-natMed").text());
		label1 = "Number of sensitive data classes collected";
		label2 = "Overall Median"
		label3 = "Median for this nature of work"
		break
	case "dataSubjects-graph":
		size = parseInt($("#dataSubject-size").text());
		median = parseInt($("#dataSubject-median").text());
		natMed = parseInt($("#dataSubject-natMed").text());
		label1 = "Number of data subjects collected information from";
		label2 = "Overall Median"
		label3 = "Median for this nature of work"
		break;
	case "dataDisclosees-graph":
		size = parseInt($("#dataDisclosee-size").text());
		median = parseInt($("#dataDisclosee-median").text());
		natMed = parseInt($("#dataDisclosee-natMed").text());
		label1 = "Number of data disclosees information shared with";
		label2 = "Overall Median"
		label3 = "Median for this nature of work"
		break;
	}
	Morris.Bar({
		element:elementID,
		data:[
		 {y:'',n:size,x:median,z:natMed}     
		],
		xkey:'y',
		ykeys:['n','x','z'],
		labels:[label1,label2,label3],
		hideHover:'auto'
	})
}


var clearAll = function(){
	$(".graph svg").remove();
	$(".hidden").hide();
}

