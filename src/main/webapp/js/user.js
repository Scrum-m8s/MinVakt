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
                $('#EmployeeTable').DataTable().ajax.reload();
                alert("Employee was registered");

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
    function editEmployee(user_id, firstname, surname, phone_number, email, category) {
        $.ajax({
            url: 'api/employees/' + $("#user_id").val(),
            type: 'PUT',
            data: '{"userId": "' + user_id + '", "firstname" : "' + firstname + '", "surname" : "' + surname +  '", "email" : "' + email + '", "phoneNumber" : "' + phone_number + '", "category" : "' + category + '"}',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function (data) {
                alert("Suksess!");
//                $('#EmployeeTable').DataTable().ajax.reload();
            },
            error: function() {
                console.log('Feil!');
            }
        });
    }
    $("#editButton").click(function () {
        var user_id = $("#user_id").val();
        var firstname = $("#firstname").val();
        var surname = $("#surname").val();
        var phone_number = $("#phone_number").val();
        var email = $("#email").val();
        var category = $("#category").val();


        editEmployee(user_id, firstname, surname, phone_number, email, category);

    });


    // Slett
    $("#deleteUser").click(function () {
        $.ajax({
            url: 'api/users/' + $("#deleteUserId").val(),
            type: 'DELETE',
            success: function(result) {
                $('#UserTable').DataTable().ajax.reload();
            },
            error: function () {
                alert("Something went wrong");
            }
        });
    });


});




















