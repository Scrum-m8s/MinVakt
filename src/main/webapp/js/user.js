/**
 * Created by mariyashchekanenko on 13/01/2017.
 */

$(document).ready(function() {

        $('#UserTable').DataTable( {
            ajax: {
                url: 'api/users',
                dataSrc: ''
            },
            columns: [
                { data: 'user_id' },
                { data: 'password' },
                { data: 'role' }
            ]
        });


        function registerUser(user_id, password, role) {
        var ok = false;
        $.ajax({
            type: "POST",
            url: "api/users",
            data: '{"userId": "' + user_id + '", "password" : "' + password + '", "role" : "' + role + '"}',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function (result) {
                $('#UserTable').DataTable().ajax.reload();
                alert("New user was registered");

            }

        });
    }


    $("#regButton").click(function () {
        var user_id = $("#user_id").val();
        var password = $("#password").val();
        var role = $("#role").val();

        registerUser(user_id, password, role);

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
    // Rediger
    $("#editUser").click(function () {
        $.ajax({
            url: 'api/users' + $("#editUserId").val(),
            type: 'PUT',
            data: JSON.stringify({
                user_id: $("#edit_user_id").val(),
                password: $("#edit_password").val(),
                role: $("#edit_role").val()

            }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function(result) {
                $('#EmployeeTable').DataTable().ajax.reload();
            },
            error: function () {
                alert("Something went wrong");
            }
        });
    });

});
































