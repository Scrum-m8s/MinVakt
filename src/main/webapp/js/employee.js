/**
 * Created by mariyashchekanenko on 16/01/2017.
 */

$(document).ready(function() {

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
                alert("Something went wrong");
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
    $("#deleteEmployee").click(function () {
        $.ajax({
            url: 'api/employees/' + $("#user_id").val(),
            type: 'DELETE',
            success: function(result) {
                alert("Employee was deleted");
            },
            error: function () {
                alert("Something went wrong");
            }
        });
    });


})