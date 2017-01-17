/**
 * Created by mariyashchekanenko on 13/01/2017.
 */


$(document).ready(function() {


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


    $("#changePasswordButton").click(function () {
        var user_id = $("#user_id").val();
        var new_password1 = $("#new_password1").val();
        var new_password2 = $("#new_password2").val();
        changePassword(user_id, new_password1, new_password2);

    });

    // Slett brukeren
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

//endre passord
function changePassword(user_id, new_password1, new_password2) {
    if(user_id == "") {
        alert("Error: Username cannot be blank!");
        user_id.focus();
        return false;
    }
    if(new_password1 != "" && new_password1 == new_password2) {
        if(new_password1.length < 8) {
            alert("Error: Password must contain at least eight characters!");
            $("#new_password1").focus();
            return false;
        }
        re = /[0-9]/;
        if(!re.test(new_password1)) {
            alert("Error: password must contain at least one number (0-9)!");
            $("#new_password1").focus();
            return false;
        }
    } else {
        alert("Error: Please check that you've entered and confirmed your password!");
        $("#new_password1").focus();
        return false;
    }

    alert("You entered a valid password: " + new_password1);

    $.ajax({
        type: "PUT",
        url: "api/users",
        data: '{"userId": "' + user_id + '", "password" : "' + new_password1 + '", "role" : "' + 1 + '"}',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',

    });
    return true;


}

function registerUser(user_id, password, role, firstname, surname, phone_number, email, category) {

    $.ajax({
        type: "POST",
        url: "api/users",
        data: '{"userId": "' + user_id + '", "password" : "' + password + '", "role" : "' + role + '"}',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (data) {
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

        }
    });
}














