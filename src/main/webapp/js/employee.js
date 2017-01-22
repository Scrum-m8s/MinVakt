/**
 * Created by mariyashchekanenko on 16/01/2017.
 * Edited by asdfLaptop on 17/01/2017.
 */

// The root URL for the RESTful services
var rootURL = "http://localhost:8080/api/employees/";

$(document).ready(function() {
/*
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
    */
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


    $("#availableEmployeesTable").DataTable({
        data:[],
        columns: [
            { data: 'firstname' },
            { data: 'surname' },
            { data: 'email' },
            { data: 'phone_number' },
            { data: 'category' }
        ],
        rowCallback: function (row, data) {},
        filter: false,
        info: false,
        ordering: false,
        processing: true,
        searching: false,
        paging: false,
        retrieve: true
    });

    //function to get employees qualified for a shift
    //is used in kvalifisert_for_vakt.html
    function getAvailableEmployees(shift_id, my_date, category){
        var table = $('#availableEmployeesTable').DataTable();

        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: 'api/availableemployees',
            data: JSON.stringify({
                shift_id: shift_id,
                my_date: my_date,
                category: category
            }),
            dataType: "json",
            success: function(data, textStatus, jqXHR){
                console.log("Available employees."+data);
                table.clear();
                table.rows.add(data);
                table.draw();
            },
            error: function(data, textStatus, jqXHR){
                console.log("Error: " + textStatus);
            }
        });

    }
    $("#availableEmployees").click(function () {
        var category = $("#category").val();
        var my_date = $("#my_date").val();
        var shift_id = $("#shift_id").val();

        console.log("Before function. Category: "+category+" My_date: "+my_date+" Shift_id: "+shift_id);

        getAvailableEmployees(shift_id, my_date, category);
    });




    function registerOvertimeAbsence(user_id, shift_id, my_date, deviance) {

        $.ajax({
            type: 'PUT',
            contentType: 'application/json',
            url: "/api/shift_lists/deviance/" + user_id+"/"+ shift_id,
            data: '{"user_id": "' + user_id + '", "shift_id" : "' + shift_id + '", "my_date" : "' + my_date + '", "deviance" : "' + deviance + '"}',
            dataType: "json",

            success: function(data, textStatus, jqXHR){
                console.log("deviance updated.");
            },
            error: function(data, textStatus, jqXHR){
                console.log("Error: " + textStatus);
            }
        });
        alert("Deviance ble registert");
    }


    $("#submitAbsenceOvertime").click(function () {
        var user_id = $("#user_id").val();
        var shift_id = $("#shift_id").val();
        var my_date = $("#my_date").val();
        var deviance;
        var selector = document.getElementById("selector")
        var value_selector = selector.options[selector.selectedIndex].value;

        if (value_selector == "absence"){
            deviance = -($("#deviance").val());
        } else {
            deviance = $("#deviance").val();
        }

        registerOvertimeAbsence(user_id, shift_id, my_date, deviance);


    });






})





