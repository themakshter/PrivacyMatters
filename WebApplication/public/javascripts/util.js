$(document).ready(
		function() {
			 $(function() {
				$(".pop-over").popover();
			});
						
			
			$(".purpose").click(
					function() {
						if ($(this).find("span").hasClass('glyphicon-plus')) {
							$(".purpose span").removeClass("glyphicon-minus")
									.addClass("glyphicon-plus");
							$(this).find("span").removeClass("glyphicon-plus")
									.addClass("glyphicon-minus");
						} else {
							$(this).find("span").removeClass("glyphicon-minus")
									.addClass("glyphicon-plus");
						}
					});
		});
