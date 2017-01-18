/**
 * Created by mariyashchekanenko on 13/01/2017.
 * Edited by asdfLaptop on 17/01/2017.
 */

// The root URL for the RESTful services
var rootURL = "http://localhost:8080/api/users/";

$(document).ready(function() {

    //register user
    function registerUser() {
        console.log('registerUser and empty employee with user_id: ' + $("#user_id").val());
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: rootURL,
            dataType: "json",
            data: JSON.stringify({
                "user_id": $("#user_id").val(),
                "password": $("#password").val(),
                "role": $("#role").val(),
            }),
            success: function(data, textStatus, jqXHR){
                console.log("User added.");

                //creating empty employee with same user_id
                $.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: "api/employees",
                    dataType: "json",
                    data: JSON.stringify({
                        "user_id": $("#user_id").val(),
                        "firstname": " ",
                        "surname": " ",
                        "email": " ",
                        "phone_number": " ",
                        "category": -1
                    }),
                    success: function(data, textStatus, jqXHR){
                        console.log("Empty employee added.");
                    },
                    error: function(data, textStatus, jqXHR){
                        console.log("Error: " + textStatus);
                    }
                });
            },
            error: function(data, textStatus, jqXHR){
                console.log("Error: " + textStatus);
            }
        });
    }

    $("#regButton").click(function () {
        registerUser();
        return false;
    });

    //delete user
    $("#deleteUser").click(function () {
        $.ajax({
            url: rootURL + $("#deleteUserId").val(),
            type: 'DELETE',
            success: function(result) {
                console.log("User was deleted with user_id: " + $("#deleteUserId").val());
                $('#UserTable').DataTable().ajax.reload();
            },
            error: function(data, textStatus, jqXHR){
                console.log("Error: " + textStatus);
            }
        });
    });
});




















