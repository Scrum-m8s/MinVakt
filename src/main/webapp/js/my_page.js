/**
 * Created by espen on 24.01.2017.
 */

function passwordChangeSuccess(data){
    if(data == 'true'){
        $("#passwordsuccess").html("Passord endret");
    }else{
        $("#passwordsuccess").html("Feil i endring av passord");
    }
}

function passwordChangeError(data){
    $("#passwordsuccess").html("Feil i endring av passord");
}

function infoChangeSuccess(data){
    $("#infosuccess").html("Info endret");
}

function infoChangeError(data){
    $("#infosuccess").html("Feil i endring av info");
}

function changeEmployeeInfo(successCallback, errorCallback, userid,
                            firstname, surname, email, phonenumber, category){
    $.ajax({
        type: 'PUT',
        url: '/api/employees/',
        contentType: 'application/json',
        data: JSON.stringify({
            user_id: userid,
            firstname: firstname,
            surname: surname,
            email: email,
            phone_number: phonenumber,
            category: category
        }),
        success: function(data){
            successCallback(data);
        },
        error: function(data){
            errorCallback(data);
        }
    });
}

function changePassword(successCallback, errorCallback, oldpassword, newpassword){

    $.ajax({
        type: 'PUT',
        url: '/api/users/current/updatepassword',
        contentType: 'application/json',
        data: JSON.stringify({
            oldpassword: oldpassword,
            newpassword: newpassword
        }),
        success: function(data){
            successCallback(data);
        },
        error: function(data){
            errorCallback(data);
        }

    });
}

$(document).ready(function(){
    $.get("/api/users/current", function(data){
        userCallback(data)
    });
    $.get("/api/employees/current", function(data){
        employeeCallback(data)
    });

    $("#submitnewpassword").click(function(){
        var oldpassword = $("#oldpassword").val();
        var newpassword = $("#newpassword").val();
        var newpasswordreapeat = $("#newpasswordrepeat").val();

        if(newpasswordreapeat != newpassword){
            $("#passwordsuccess").html("Passordene er ikke like");
            return;
        }

        changePassword(passwordChangeSuccess, passwordChangeError, oldpassword, newpassword);

    });

    $("#submitnewinfo").click(function(){
        var userid = $("#username").html();
        var firstname = $("#firstname").val();
        var surname = $("#surname").val();
        var email = $("#email").val();
        var phonenumber = $("#phonenumber").val();
        var category = $("#category").html();

        changeEmployeeInfo(infoChangeSuccess, infoChangeError,
            userid, firstname, surname, email, phonenumber, category);

    });

});

