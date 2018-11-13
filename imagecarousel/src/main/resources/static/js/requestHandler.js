$(document).ready(function(){
    var image = new Image;
    $.ajax({
        url: '/images/test.jpg',
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
});
