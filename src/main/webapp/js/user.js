/**
 * Created by mariyashchekanenko on 13/01/2017.
 */

$(document).ready(function() {


    function registerUser(user_id, password, role, firstname, surname, phone_number, email, category) {

        $.ajax({
            type: "POST",
            url: "api/users",
            data: '{"userId": "' + user_id + '", "password" : "' + password + '", "role" : "' + role + '"}',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function (data) {
                $('#UserTable').DataTable().ajax.reload();
                alert("New user was registered");
            }
        });
        $.ajax({
            type: 'POST',
            url: 'api/employees',
            data: '{"userId": "' + user_id + '", "firstname" : "' + firstname + '", "surname" : "' + surname + '", "phone_number" : "' + phone_number + '", "email" : "' + email + '", "category" : "' + category + '"}',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function (data) {
                alert("Employee was registered");

            },
            error: function() {
            alert("Something went wrong");
        }
        });
    }

    $("#regButton").click(function () {
        var user_id = $("#user_id").val();
        var password = $("#password").val();
        var role = $("#role").val();
        var firstname = $("").val();
        var surname = $("").val();
        var phone_number = $("").val();
        var email = $("").val();
        var category = $("").val();

        registerUser(user_id, password, role, firstname, surname, phone_number, email, category);

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




















