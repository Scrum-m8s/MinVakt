/**
 * Created by mariyashchekanenko on 13/01/2017.
 */
$(document).ready(function() {

    function registerUser(user_id, password1, role) {
        var ok = false;
            $.ajax({
                type: "POST",
                url: "api/users",
                data: '{"userName": "' + user_id + '", "password" : "' + password1 + '", "rolle" : "' + role + '"}',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',

                success: function (data) {
                    ok = true;
                    alert("New user was registered");

                },
                error: function () {
                    alert("Something went wrong");
                }


            });
        //}
    }


    $("#regButton").click(function () {
        var user_id = $("#user_id").val();
        var password1 = $("#password1").val();
        var role = $("#role").val();

        registerUser(user_id, password1, role);

    });
})






