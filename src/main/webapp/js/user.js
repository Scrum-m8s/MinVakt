/**
 * Created by mariyashchekanenko on 13/01/2017.
 */

// The root URL for the RESTful services
var rootURL = "http://localhost:8080/api/";

$(document).ready(function() {

    function addUser() {
        var user_id = $("#user_id").val();
        var password = $("#password").val();
        var role = $("#role").val();

        console.log('addUser and empty employee: ' + user_id);
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: rootURL + "users",
            dataType: "json",
            data: JSON.stringify({
                "user_id": user_id == "" ? null : user_id,
                "password": password,
                "role": role,
            }),
            success: function(data, textStatus, jqXHR){
                console.log("User added.")
                $.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: rootURL + "employees",
                    dataType: "json",
                    data: JSON.stringify({
                        "user_id": user_id == "" ? null : user_id,
                        "firstname": null,
                        "surname": null,
                        "email": null,
                        "phone_number": null,
                        "category": 0
                    }),
                    success: function(data, textStatus, jqXHR){
                        console.log("Empty employee added.")
                        return true;
                    },
                    error: function(data, textStatus, jqXHR){
                        console.log("Error: " + textStatus);
                    }
                });
                return true;
            },
            error: function(data, textStatus, jqXHR){
                console.log("Error: " + textStatus);
            }
        });
    }


    $("#regButton").click(function () {
        addUser();
        return false;
    });


    // Slett
    $("#deleteUser").click(function () {
        $.ajax({
            url: 'api/users/' + $("#deleteUserId").val(),
            type: 'DELETE',
            success: function(result) {
                alert("User was deleted");
                $('#UserTable').DataTable().ajax.reload();
            },
            error: function () {
                alert("Something went wrong");
            }
        });
    });


});




















