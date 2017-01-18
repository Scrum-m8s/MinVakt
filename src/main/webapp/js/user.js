/**
 * Created by mariyashchekanenko on 13/01/2017.
 * Edited by asdfLaptop on 17/01/2017.
 */

// The root URL for the RESTful services
var rootURL = "http://localhost:8080/api/users/";

$(document).ready(function() {
    
    //register user
    function registerUser() {
      if($("#user_id").val() == "") {
        alert("Error: Username cannot be blank!");
        user_id.focus();
        return false;
    } 
      if($("#password").val() == "") {
        alert("Error: Password cannot be blank!");
        password.focus();
        return false;
    }
    if($("#password").val().length < 8) {
        alert("Error: Password must contain at least eight characters!");
        $("#password").focus();
        return false;
    }
    re = /[0-9]/;
    if(!re.test($("#password").val())) {
        alert("Error: password must contain at least one number (0-9)!");
        $("#password").focus();
        return false;
    }
      
      
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


    $("#changePasswordButton").click(function () {
        var user_id = $("#user_id").val();
        var new_password1 = $("#new_password1").val();
        var new_password2 = $("#new_password2").val();
        changePassword(user_id, new_password1, new_password2);

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















