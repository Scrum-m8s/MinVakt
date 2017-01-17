/**
 * Created by mariyashchekanenko on 16/01/2017.
 * Edited by asdfLaptop on 17/01/2017.
 */

// The root URL for the RESTful services
var rootURL = "http://localhost:8080/api/employees/";

$(document).ready(function() {
    function editEmployee() {
        console.log('editEmployee with user_id: ' + user_id);
        $.ajax({
            type: 'PUT',
            contentType: 'application/json',
            url: rootURL + $("#user_id").val(),
            dataType: "json",
            data: JSON.stringify({
                "user_id": $("#user_id").val(),
                "firstname": $("#firstname").val(),
                "surname": $("#surname").val(),
                "email": $("#email").val(),
                "phone_number": $("#phone_number").val(),
                "category": $("#category").val()
            }),
            success: function(data, textStatus, jqXHR){
                console.log("Employee updated.");
            },
            error: function(data, textStatus, jqXHR){
                console.log("Error: " + textStatus);
            }
        });
    }


    $("#editEmployee").click(function () {
        editEmployee();
        return false;
    });

    //delete employee
    $("#deleteEmployee").click(function () {
        var user_id = $('tr.selected td:eq(5)').text();
        console.log(user_id);
        $.ajax({
            url: rootURL + user_id,
            type: 'DELETE',
            success: function(result) {
                console.log("Employee was deleted with user_id: " + user_id);
                window.location.reload();
            },
            error: function(data, textStatus, jqXHR){
                console.log("Error: " + textStatus);
            }
        });
    });
});