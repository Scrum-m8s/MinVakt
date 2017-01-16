/**
 * Created by mariyashchekanenko on 13/01/2017.
 */
/*
$(document).ready(function() {

        $('#myTable').DataTable( {
            ajax: {
                url: 'api/users',
                dataSrc: ''
            },
            columns: [
                { data: 'user_id' },
                { data: 'password1' },
                { data: 'role' }
            ]
        });


        function registerUser(user_id, password1, role) {
        var ok = false;
        $.ajax({
            type: "POST",
            url: "api/users",
            data: '{"userId": "' + user_id + '", "password" : "' + password1 + '", "rolle" : "' + role + '"}',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function (result) {
                ok = true;
                alert("New user was registered");

            },
            error: function () {
                alert("Something went wrong");
            }

        });
    }


    $("#regButton").click(function () {
        var user_id = $("#user_id").val();
        var password1 = $("#password1").val();
        var role = $("#role").val();

        registerUser(user_id, password1, role);

    });
});

*/

$(document).ready(function() {

    $('#UserTable').DataTable({
        //"order": [[ 1, "asc" ]],
        searching: false,
        paging: false,
        info: false,
        ajax: {
            url: 'api/users',
            dataSrc: ''

        },
        columns: [
            {data: 'user_id'},
            {data: 'password'},
            {data: 'role'}

        ]
    });

    $("#registerUser").click(function () {
        $.ajax({
            url: 'api/users',
            type: 'POST',
            data: JSON.stringify({
                user_id: $("#user_id").val(),
                password: $("#password").val(),
                role: $("#role").val()
            }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function(result) {
                $('#UserTable').DataTable().ajax.reload();
                alert("New user was registered");

            },
            error: function () {
                alert("Something went wrong");
            }
        });
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
})



































