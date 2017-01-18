/**
 * Created by mariyashchekanenko on 16/01/2017.
 * Edited by asdfLaptop on 17/01/2017.
 */

// The root URL for the RESTful services
var rootURL = "http://localhost:8080/api/employees/";

$(document).ready(function() {

    // Get the modal
    var modalCreate = document.getElementById('createModal');
    var modalUpdate = document.getElementById('updateModal');

// Get the button that opens the modal
    var btnCreate = document.getElementById("createEmployee");
    var btnUpdate = document.getElementById("updateEmployee");

// Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

    // When the user clicks on the create button, open the modal
    btnCreate.onclick = function() {
        modalCreate.style.display = "block";
    }

// When the user clicks on the update button, open the modal
    btnUpdate.onclick = function() {
        modalUpdate.style.display = "block";
        $("#username_filler").html('<b>Edit user: ' + $('tr.selected td:eq(5)').text() + '</b>');
        $("#inputFirstname").attr('value', $('tr.selected td:eq(1)').text());
        $("#inputLastname").attr('value', $('tr.selected td:eq(0)').text());
        $("#inputPhone").attr('value', $('tr.selected td:eq(2)').text());
        $("#inputEmail").attr('value', $('tr.selected td:eq(3)').text());
        $("#inputRole").val($('tr.selected td:eq(4)').text());
    }

// When the user clicks on <span> (x), close the modal
    span.onclick = function() {
        modalUpdate.style.display = "none";

        modalCreate.style.display = "none";
    }

// When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modalCreate) {
            modalCreate.style.display = "none";
        }
        if (event.target == modalUpdate) {
            modalUpdate.style.display = "none";
        }
    }
    
    function editEmployee() {
        console.log('editEmployee with user_id: ' + $('tr.selected td:eq(5)').text());
        console.log($("#inputRole").prop('selectedIndex'));
        $.ajax({
            type: 'PUT',
            contentType: 'application/json',
            url: rootURL + $('tr.selected td:eq(5)').text(),
            dataType: "json",
            data: JSON.stringify({
                "user_id": $('tr.selected td:eq(5)').text(),
                "firstname": $("#inputFirstname").val(),
                "surname": $("#inputLastname").val(),
                "email": $("#inputEmail").val(),
                "phone_number": $("#inputPhone").val(),
                "category": ($("#inputRole").prop('selectedIndex')+1)
            }),
            success: function(data, textStatus, jqXHR){
                console.log("Employee updated.");
                window.location.reload();
            },
            error: function(data, textStatus, jqXHR){
                console.log("Error: " + textStatus);
            }
        });
    }

    function createEmployee() {
        if($("#inputUsername").val() == "") {
            alert("Error: Username cannot be blank!");
            $("#inputUsername").focus();
            return false;
        }
        if($("#inputPassword").val() == "") {
            alert("Error: Password cannot be blank!");
            $("#inputPassword").focus();
            return false;
        }
        if($("#inputPassword").val().length < 8) {
            alert("Error: Password must contain at least eight characters!");
            $("#inputPassword").focus();
            return false;
        }
        re = /[0-9]/;
        if(!re.test($("#inputPassword").val())) {
            alert("Error: password must contain at least one number (0-9)!");
            $("#inputPassword").focus();
            return false;
        }


        console.log('registerUser and empty employee with user_id: ' + $("#inputUsername").val());
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: 'api/users',
            dataType: "json",
            data: JSON.stringify({
                "user_id": $("#inputUsername").val(),
                "password": $("#inputPassword").val(),
                "role": -1
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
                        "user_id": $("#inputUsername").val(),
                        "firstname": " ",
                        "surname": " ",
                        "email": " ",
                        "phone_number": " ",
                        "category": -1
                    }),
                    success: function(data, textStatus, jqXHR){
                        console.log("Empty employee added.");
                        window.location.reload();
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

    $("#submitCreate").click(function () {
        createEmployee();
        modalCreate.style.display = "none";
        return false;
    });


    $("#submitUpdate").click(function () {
        editEmployee();
        modalUpdate.style.display = "none";
        return false;
    });

    //delete employee
    $("#deleteEmployee").click(function () {
        var user_id = $('tr.selected td:eq(5)').text();
        console.log(user_id);
        $.ajax({
            url: rootURL + user_id,
            type: 'DELETE',
            success: function(result) {
                console.log("Employee was deleted with user_id: " + user_id);
                window.location.reload();
            },
            error: function(data, textStatus, jqXHR){
                console.log("Error: " + textStatus);
            }
        });
    });


});
