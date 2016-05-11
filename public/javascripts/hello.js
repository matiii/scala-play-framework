var markers = [];

function initialize() {
  var mapOptions = {
    center: { lat: 52.2297700, lng: 21.0117800},
    zoom: 13
  };
  var map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);

  loadData(map);

  setInterval(function(){ loadData(map);}, 10000);
}

function loadData(map){
  var data = JSON.parse(httpGet('api/data'));

  for(var i=0; i < data.length; i++) {
    var e = data[i];

    var marker = objectFindByKey(markers, 'id', e.line);
    var p = new google.maps.LatLng(e.latitude, e.longitude);
    if (marker == null) {

      var m = new google.maps.Marker({
        position: p,
        map: map,
        title: e.line.toString()
      });

      markers.push({id: e.line, lat: e.latitude, lng: e.longitude, map: m});
    } else {
      if(e.latitude != marker.lat && e.longitude != marker.lng) {
        console.log("Update pozycji tramwaju o numerze: " + e.line);
        marker.map.setMap(null);
        marker.map.position = p;
        marker.map.setMap(map);
      }
    }
  }
}

function httpGet(theUrl)
{
  var xmlHttp = new XMLHttpRequest();
  xmlHttp.open( "GET", theUrl, false );
  xmlHttp.send( null );
  return xmlHttp.responseText;
}

function objectFindByKey(array, key, value) {
  for (var i = 0; i < array.length; i++) {
    if (array[i][key] === value) {
      return array[i];
    }
  }
  return null;
}

google.maps.event.addDomListener(window, 'load', initialize);
