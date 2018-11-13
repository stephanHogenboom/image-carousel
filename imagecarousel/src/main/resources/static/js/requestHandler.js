$(document).ready(function(){
    $.ajax({
        url: '/images/test.jpg',
        method: 'GET',
        contentType: "application/octet-stream",
        success: function(response) {
            console.log(response);
        },
        error: function(error) {
            console.log(error);
        }
    });
});
