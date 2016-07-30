/***************************************************************************************************/
/***************************************************************************************************/
/////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////MAIN FUNCTIONALITY FOR NOTE CONVERSION///////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
/***************************************************************************************************/
/***************************************************************************************************/
function printNotesToPage() {
	if(fromText.value.length > 0) {
		document.getElementById('tabPageText').innerHTML = toText.value;
	} else {
		alert("A tab must be transposed before it can be converted to notes.");
		return;
	}
	/*drawing stuff goes here
	var c = document.getElementById("notesCanvas");
	var ctx = c.getContext("2d");
	var imageObj = new Image();
	imageObj.src = './images/clefs.png';
	imageObj.onload = function() {
        ctx.drawImage(imageObj, 0, 0);
    };
	*/
	var tabText = fromText.value;
	$.ajax( {
		type:"POST",
		produces:"MediaType/TEXT_PLAIN",
		url:"http://localhost:8080/tabToNotes/core",
		data: tabText,
		success:function(data) {
			alert(data);
		}
	})
}