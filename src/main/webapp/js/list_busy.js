/**
 * Created by Nina on 23.01.2017.
 */

$(document).ready(function() {

    var span = document.getElementsByClassName("close")[0];

    var table = $('#busy_table').DataTable( {
        "paging": false,
        "info" : false,
        "select" : true,
        "searching" : false,
        ajax: {
            url: 'api/busy',
            "dataSrc": function (json) {
                var return_data = new Array();
                for (var i = 0; i < json.length; i++) {
                    if (json[i].shift_id === 1) {
                        json[i].shift_id = 'Nattevakt';
                    } else if (json[i].shift_id === 2) {
                        json[i].shift_id = 'Dagvakt';
                    } else if (json[i].shift_id === 3) {
                        json[i].shift_id = 'Kveldsvakt';
                    }
                    return_data.push({
                        'user_id': json[i].user_id,
                        'shift_id': json[i].shift_id,
                        'my_date': json[i].my_date
                    })
                }
                return return_data;
            }
        },
        "columns" : [
            { data: 'user_id', orderable: false},
            { data: 'shift_id'},
            { data: 'my_date'}
        ]
    });
    table.draw();

    $("#register").click(function(){
        $("#registerModal").modal();
    });

    function createBusy() {
        if($("#inputUserId").val() == "") {
            alert("Error: Vennligst fyll ut brukernavn!");
            $("#inputUserId").focus();
            return false;
        }if($("#inputDate").val() == "") {
            alert("Error: Vennligst velg dato!");
            $("#inputDate").focus();
            return false;
        }
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: 'api/busy',
            dataType: "json",
            data: JSON.stringify({
                "user_id": $("#inputUserId").val(),
                "shift_id": ($("#shiftId").prop('selectedIndex')+1),
                "my_date": $("#inputDate").val()

            }),
            success: function(data, textStatus, jqXHR){
                console.log("Busy added.");
                window.location.reload();
            },
            error: function(data, textStatus, jqXHR){
                console.log("Error: " + textStatus);
            }
        });
    }

    $("#confirmNew").click(function () {
        createBusy();
        return false;
    });

    $("#update").click(function(){
        $("#updateModal").modal();
        $("#updateShiftId").attr('value', $('tr.selected td:eq(1)').text());
        $("#updateInputDate").attr('value', $('tr.selected td:eq(2)').text());
    });

    $("#confirmChange").click(function () {
        var user_id = $('tr.selected td:eq(0)').text();
        var shift_id = $('tr.selected td:eq(1)').text();
        var my_date = $('tr.selected td:eq(2)').text();
        if(shift_id == "Nattevakt"){
            shift_id = 1;
        }else if(shift_id == 'Dagvakt'){
            shift_id = 2;
        }else{
            shift_id = 3;
        }

        $.ajax({
            type: 'PUT',
            contentType: 'application/json',
            url: 'api/busy/',
            dataType: "json",
            data: JSON.stringify({
                "my_date": $("#updateInputDate").val(),
                "shift_id": ($("#updateShiftId").prop('selectedIndex')+1),
                "user_id": user_id
            }),
            success: function(data, textStatus, jqXHR){
                console.log("Busy added.");
                window.location.reload();
            },
            error: function(data, textStatus, jqXHR){
                console.log("Error: " + textStatus);
            }
        })
    });
});




