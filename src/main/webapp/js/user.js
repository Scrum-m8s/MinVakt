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
    $('#EmployeeTable').DataTable({
        //"order": [[ 1, "asc" ]],
        searching: false,
        paging: false,
        info: false,
        ajax: {
            url: 'api/employees',
            dataSrc: ''

        },
        columns: [
            {data: 'user_id'},
            {data: 'fornavn'},
            {data: 'etternavn'},
            {data: 'telefon'},
            {data: 'epost'},
            {data: 'kategori'}

        ]
    });


        function registerUser(user_id, password, role, fornavn, etternavn, telefon, epost, kategori) {

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
            $.ajax({
                url: 'api/employees',
                type: 'POST',
                data: '{"userId": "' + user_id + '", "fornavn" : "' + fornavn + '", "etternavn" : "' + etternavn + '", "telefon" : "' + telefon + '", "epost" : "' + epost + '", "kategori" : "' + kategori + '"}',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (result) {
                    $('#EmployeeTable').DataTable().ajax.reload();
                    alert("Employee was registered");

                }
            });
        }

    $("#regButton").click(function () {
        var user_id = $("#user_id").val();
        var password = $("#password").val();
        var role = $("#role").val();
        var fornavn = $("");
        var etternavn = $("");
        var telefon = $("");
        var epost = $("");
        var kategori = $("");

        registerUser(user_id, password, role, fornavn, etternavn, telefon, epost, kategori);

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
































