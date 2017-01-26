/**
 * Created by espen on 24.01.2017.
 */


$(document).ready(function(){
    $.get("/api/users/current", function(data){
        userCallback(data)
    });
    $.get("/api/employees/current", function(data){
        employeeCallback(data)
    });

    $("#submitnewpassword").click(function(){
        var newpassword = $("#newpassword");
        $.ajax({
            type: 'PUT',
            url: '/api/users/current/updatepassword',
            contentType: 'text/plain',
            data: newpassword,
            success: function(){
                $("#passwordsuccess").html("Passord endret");
            },
            error: function(data, textStatus, jqXHR){
                $("#passwordsuccess").html("Feil i endring av passord");
            }
        });
    });
});

