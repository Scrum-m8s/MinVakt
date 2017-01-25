/**
 * Created by mariyashchekanenko on 16/01/2017.
 * Edited by asdfLaptop on 17/01/2017.
 */

// The root URL for the RESTful services
var rootURL = "http://localhost:8080/api/employees/";

$(document).ready(function() {
  //FIXME: make this work without commenting out. Prob just move the functions on the bottom to another js file.
/* 
    // When the user clicks on the create button, open the modal
    $("#createEmployee").on('click', function() {
        $("#createModal").modal('show');
    });

    // When the user clicks on the update button, open the modal
    $("#updateEmployee").on('click', function() {
        $("#updateModal").modal('show');
        $(".username_filler").html('<b>Edit user: ' + $('tr.selected td:eq(5)').text() + '</b>');
        $("#inputFirstname").attr('value', $('tr.selected td:eq(1)').text());
        $("#inputLastname").attr('value', $('tr.selected td:eq(0)').text());
        $("#inputPhone").attr('value', $('tr.selected td:eq(2)').text());
        $("#inputEmail").attr('value', $('tr.selected td:eq(3)').text());
        $("#inputCategory").val($('tr.selected td:eq(4)').text());

    });

    $("#updateRoleAndPassword").on('click', function() {
        $.getJSON('api/users/' + $('tr.selected td:eq(5)').text(), function(result) {
            $("#updateRoleAndPasswordModal").modal('show');
            $(".username_filler").html('<b>Edit user: ' + $('tr.selected td:eq(5)').text() + '</b>');
            $("#inputRole1").val(result.role + "Sel");
        });
    });

    // When the user clicks on <span> (x), close the modal
     $("#closeUpdate").on('click', function() {
        console.log("update");
        $("#updateModal").modal('hide');
    });

    $("#closeCreate").on('click', function() {
        console.log("create");
        $("#createModal").modal('hide');
    });

    $("#closeUpdateRoleAndPassword").on('click', function() {
        $("#updateRoleAndPasswordModal").modal('hide');
    });

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == $("#createModal")) {
            $("#createModal").modal('hide');
        }
        if (event.target == $("#updateModal")) {
            $("#updateModal").modal('hide');
        }
        if (event.target == $("#updateRoleAndPasswordModal")) {
            $("#updateRoleAndPasswordModal").modal('hide');
        }
    }
*/
    function editEmployee() {
        var user_id = $('tr.selected td:eq(5)').text();
        console.log('editEmployee with user_id: ' + user_id);

        $.ajax({
            type: 'PUT',
            contentType: 'application/json',
            url: rootURL + user_id,
            dataType: "json",
            data: JSON.stringify({
                "user_id": user_id,
                "firstname": $("#inputFirstname").val(),
                "surname": $("#inputLastname").val(),
                "email": $("#inputEmail").val(),
                "phone_number": $("#inputPhone").val(),
                "category": ($("#inputCategory").prop('selectedIndex')+1)
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

    //Hvis en bruker med samme brukernavn allerede finnes vil bare kontoen oppdateres med nytt passord og rolle(bruker/admin/inactive)
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

        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: 'api/users',
            dataType: "json",
            data: JSON.stringify({
                "user_id": $("#inputUsername").val(),
                "password": $("#inputPassword").val(),
                "role": ($("#inputRole").prop('selectedIndex')-1)
            }),
            success: function(data, textStatus, jqXHR){
                console.log("User added.");

                //creating empty employee with same user_id
                $.ajax({
                    type: 'PUT',
                    contentType: 'application/json',
                    url: 'api/users/' + $("#inputUsername").val(),
                    dataType: "json",
                    data: JSON.stringify({
                        "user_id": $("#inputUsername").val(),
                        "firstname": "",
                        "surname": "",
                        "email": "",
                        "phone_number": "",
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
            } else {
                $.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: 'api/users',
                    dataType: "json",
                    data: JSON.stringify({
                        "user_id": $("#inputUsername").val(),
                        "password": $("#inputPassword").val(),
                        "role": ($("#inputRole").prop('selectedIndex')-1)
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
                                "firstname": "",
                                "surname": "",
                                "email": "",
                                "phone_number": "",
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
        });
    }

    function updateRoleAndPassword() {
        if($("#inputPassword1").val() == "") {
            alert("Error: Password cannot be blank!");
            $("#inputPassword1").focus();
            return false;
        }
        if($("#inputPassword1").val().length < 8) {
            alert("Error: Password must contain at least eight characters!");
            $("#inputPassword1").focus();
            return false;
        }
        re = /[0-9]/;
        if(!re.test($("#inputPassword1").val())) {
            alert("Error: password must contain at least one number (0-9)!");
            $("#inputPassword1").focus();
            return false;
        }

        var user_id = $('tr.selected td:eq(5)').text();
        $.ajax({
            type: 'PUT',
            contentType: 'application/json',
            url: "api/users/" + user_id,
            dataType: "json",
            data: JSON.stringify({
                "user_id": user_id,
                "password": $("#inputPassword1").val(),
                "role": ($("#inputRole1").prop('selectedIndex')-1)
            }),
            success: function(data, textStatus, jqXHR){
                console.log("Updated password and role for user.");
                window.location.reload();
            },
            error: function(data, textStatus, jqXHR){
                console.log("Error: " + textStatus);
            }
        });
    }

    $("#submitCreate").click(function () {
        createEmployee();
        $("#createModal").modal('hide');
        return false;
    });

    $("#submitUpdate").click(function () {
        editEmployee();
        $("#updateModal").modal('hide');
        return false;
    });

    $("#submitUpdateRoleAndPassword").click(function () {
        updateRoleAndPassword();
        $("#updateRoleAndPasswordModal").modal('hide');
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
            url: "api/shift_lists/deviance",
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
        var selector = document.getElementById("selector");
        var value_selector = selector.options[selector.selectedIndex].value;
        if (value_selector == "absence") {
            deviance = -($("#deviance").val());
        } else {
            deviance = $("#deviance").val();
        }
        registerOvertimeAbsence(user_id, shift_id, my_date, deviance);
    })

    function getPhoneNumbers(){

    }

});