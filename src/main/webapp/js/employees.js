/**
 * Created by mariyashchekanenko on 13/01/2017.
 */
$(document).ready(function(){

    $('#employeeTable').DataTable( {
        "order": [[ 1, "asc" ]],
        searching: false,
        paging: false,
        info:  false,
        ajax: {
            url: 'api/employees',
            dataSrc: '',

        },
        columns: [
            { data: 'firstname'},
            { data: 'surname' },
            { data: 'email' },
            { data: 'phone_number' },
            { data: 'category'}

        ]
    });




     $("#createEmployee").click(function () {
        $.ajax({
            url: 'api/employees',
            type: 'POST',
            data: JSON.stringify({
                 //user_id: $("#new_user_id").val(),
                 firstname: $("#new_firstname").val(),
                 surname: $("#new_surname").val(),
                 email: $("#new_email").val(),
                 phone_number: $("#new_phone_number").val(),
                 category: $("#new_category").val()

            }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function() {
            alert("New employee was registered");
            },
            error: function() {
            alert("New employee was not registered")
             }
         });
     });
 })