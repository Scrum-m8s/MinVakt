/**
 * Created by mariyashchekanenko on 16/01/2017.
 * Edited by asdfLaptop on 17/01/2017.
 */

// The root URL for the RESTful services
var rootURL = "http://localhost:8080/api/employees/";

$(document).ready(function() {

    // Get the modal
    var modal = document.getElementById('updateModal');

// Get the button that opens the modal
    var btn = document.getElementById("updateEmployee");

// Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

// When the user clicks on the button, open the modal
    btn.onclick = function() {
        modal.style.display = "block";
        $("#username_filler").html('<b>Edit user: ' + $('tr.selected td:eq(5)').text() + '</b>');
        $("#inputFirstname").attr('value', $('tr.selected td:eq(1)').text());
        $("#inputLastname").attr('value', $('tr.selected td:eq(0)').text());
        $("#inputPhone").attr('value', $('tr.selected td:eq(2)').text());
        $("#inputEmail").attr('value', $('tr.selected td:eq(3)').text());
        $("#inputRole").val($('tr.selected td:eq(4)').text());
    }

// When the user clicks on <span> (x), close the modal
    span.onclick = function() {
        modal.style.display = "none";
    }

// When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
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
            },
            error: function(data, textStatus, jqXHR){
                console.log("Error: " + textStatus);
            }
        });
    }


    $("#submitUpdate").click(function () {
        editEmployee();
        modal.style.display = "none";
        //window.location.reload();
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
