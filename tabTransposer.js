/***************************************************************************************************/
/***************************************************************************************************/
/////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////MISC LOGIC FOR SITE////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
/***************************************************************************************************/
/***************************************************************************************************/
//Listener on tabs
$(document).ready(function() {
    $('.tabs .tab-links a').on('click', function(e) {
		//get previous tab and new tab
		//var prevAttrValue = $(".tabs .tab-links .active a").attr('href'); <-- this line took too long to figure out for me to completely delete it
        var currentAttrValue = $(this).attr('href');
		
		//show current tab, hide others
		$('.tabs ' + currentAttrValue).show("slow").siblings().hide("slow");
        // Change current tab to active, remove active class from other tabs
        $(this).parent('li').addClass('active').siblings().removeClass('active');
 
        e.preventDefault();
    });
	$('textarea').scroll(function(e) {
		if(e.target.id == "fromText") {
			document.getElementById("toText").scrollTop = e.target.scrollTop;
		} else if(e.target.id == "toText") {
			document.getElementById("fromText").scrollTop = e.target.scrollTop;
		}
	});
});

function clearText() {
	fromText.value = "";
	toText.value = "";
}

function printTabToPage() {
	if(toText.value.length > 0) {
		document.getElementById('tabPageText').innerHTML = toText.value;
	} else {
		alert("A tab must be transposed before it can be displayed on the tab page.");
	}
}