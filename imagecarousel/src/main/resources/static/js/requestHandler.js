$(document).ready(function(){

});

var getImage = function(){
    var name = $("#imagesSelector").val();
    doGetImage(name)
}

var doGetImage = function(name){
    $.ajax({
        url: '/images/'+name,
        method: 'GET',
        contentType: "application/octet-stream",
        xhr: function() {
            var xhr = new XMLHttpRequest();
            xhr.responseType= 'blob'
            return xhr;
        },
        success: function(data) {
            var img = document.getElementById('img');
            var url = window.URL || window.webkitURL;
            img.src = url.createObjectURL(data);
        },
        error: function(error) {
            console.log(error);
        }
    });
}

