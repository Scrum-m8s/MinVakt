/**
 * Created by mariyashchekanenko on 16/01/2017.
 */

$(document).ready(function() {

    $('#EmployeeTable').DataTable({
        //"order": [[ 1, "asc" ]],
        searching: false,
        paging: false,
        info: false,
        ajax: {
            url: 'api/employees',
            dataSrc: ''

        },
        columns: [
            {data: 'user_id'},
            {data: 'fornavn'},
            {data: 'etternavn'},
            {data: 'telefon'},
            {data: 'epost'},
            {data: 'kategori'}

        ]
    });
//registrer en ny ansatt
    $("#registerEmployee").click(function () {
        $.ajax({
            url: 'api/employees',
            type: 'POST',
            data: JSON.stringify({
                user_id:$("#user_id").val(),
                fornavn: $("#fornavn").val(),
                etternavn: $("#etternavn").val(),
                telefon: $("#telefon").val(),
                epost: $("#epost").val(),
                kategori: $("#kategori").val()
            }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function(result) {
                $('#EmployeeTable').DataTable().ajax.reload();
                alert("Employee was registered");

            },
            error: function () {
                alert("Something went wrong");
            }
        });
    });

    // Slett
    $("#deleteEmployee").click(function () {
        $.ajax({
            url: 'api/employees/' + $("#deleteEmployeeId").val(),
            type: 'DELETE',
            success: function(result) {
                $('#EmployeeTable').DataTable().ajax.reload();
            },
            error: function () {
                alert("Something went wrong");
            }
        });
    });

    // Rediger
    $("#editEmployee").click(function () {
        $.ajax({
            url: 'api/employees' + $("#editEmployeeId").val(),
            type: 'PUT',
            data: JSON.stringify({
                user_id:$("#edit_user_id").val(),
                fornavn: $("#edit_fornavn").val(),
                etternavn: $("#edit_etternavn").val(),
                telefon: $("#edit_telefon").val(),
                epost: $("#edit_epost").val(),
                kategori: $("#edit_kategori").val()

            }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function(result) {
                $('#EmployeeTable').DataTable().ajax.reload();
            },
            error: function () {
                alert("Something went wrong");
            }
        });
    });

})