var geocoder;
var map;
var marker;
var address;
var postcode;
var organisation;
var latlng;

function initialize() {
	var address = $("#address").text();
	var postcode = $("#postcode").text();
	var fullAddress = address + " " + postcode;
	postcode = document.getElementById('postcode').innerHTML.replace("Postcode","");
	postcode = postcode.replace(":","");
	organisation = document.getElementById('organisationName').innerHTML.replace("Name","");
	organisation = organisation.replace(":","");
	var contentString = '<div id="content">' +
		'<div id="siteNotice">' +
		'</div>' +
		'<h4 id="firstHeading" class="firstHeading">' + organisation + '</h1>' +
		'<div id="bodyContent">' +
		'<p>' + address +'</p>' +
		'<p>' + postcode + '</p>' +
		'</div>' +
		'</div>';


	geocoder = new google.maps.Geocoder();
	geocoder.geocode({
		'address': fullAddress
	}, function(results, status) {
		latlng = results[0].geometry.location;
		if (status == google.maps.GeocoderStatus.OK) {
			var mapOptions = {
				zoom: 10,
				center: latlng,
				mapTypeId: google.maps.MapTypeId.ROADMAP
			}
			map = new google.maps.Map(document.getElementById('map_canvas'), mapOptions);

			marker = new google.maps.Marker({
				map: map,
				position: latlng
			});

			var infoWindow = new google.maps.InfoWindow({
				content: contentString,
				maxWidth: 300
			});

			google.maps.event.addListener(marker, 'click', function() {
				infoWindow.open(map, marker);
			});

		} else {
			alert('Geocode was not successful for the following reason: ' + status);
			latlng = new google.maps.LatLng(51.508742, -0.131836);
		}
	});
}

google.maps.event.addDomListener(window, 'load', initialize);