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
});

