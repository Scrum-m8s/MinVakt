/**
 * Created by mariyashchekanenko on 16/01/2017.
 */

$(document).ready(function() {

    $('#EmployeeTable').DataTable({
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
    //rediger ansatt fungerer ikke
    function editEmployee(user_id, fornavn, etternavn,telefon, epost, kategori) {
        $.ajax({
            url: 'api/employees' + $("#user_id").val(),
            type: 'PUT',
            data: '{"userId": "' + user_id + '", "fornavn" : "' + fornavn + '", "etternavn" : "' + etternavn + '", "telefon" : "' + telefon + '", "epost" : "' + epost + '", "kategori" : "' + kategori + '"}',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function (result) {
                $('#EmployeeTable').DataTable().ajax.reload();
            },
            error: function () {
                alert("Something went wrong");
            }
        });
    }
    $("#editButton").click(function () {
        var user_id = $("#user_id").val();
        var fornavn = $("#fornavn").val();
        var etternavn = $("#etternavn").val();
        var telefon = $("#telefon").val();
        var epost = $("#epost").val();
        var kategori = $("#kategori").val();
        v

        editEmployee(user_id, fornavn, etternavn, telefon, epost, kategori);

    });

    // Slett
    $("#deleteEmployee").click(function () {
        $.ajax({
            url: 'api/employees/' + $("#user_id").val(),
            type: 'DELETE',
            success: function(result) {
                $('#EmployeeTable').DataTable().ajax.reload();
            },
            error: function () {
                alert("Something went wrong");
            }
        });
    });


})