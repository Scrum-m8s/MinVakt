/**
 * Created by Nina on 23.01.2017.
 */

$(document).ready(function() {
    var modalNew = document.getElementById('createmodal');
    var modalUpdt = document.getElementById('updatemodal');

    var btnNew = document.getElementById("register");
    var btnUpdt = document.getElementById("update");
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

    btnNew.onclick = function() {
        modalNew.style.display = "block";
    }

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
        createBusy()
        modalNew.style.display = "none";
        return false;
    });

    btnUpdt.onclick = function() {
        modalUpdt.style.display = "block";
        $("#updateShiftId").attr('value', $('tr.selected td:eq(1)').text());
        $("#updateInputDate").attr('value', $('tr.selected td:eq(2)').text());
    }

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
            url: '/api/busy/',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify([
            {
                "user_id": user_id,
                "shift_id": shift_id,
                "my_date": my_date
            },
            {
                "user_id": user_id,
                "shift_id": ($("#updateShiftId").prop('selectedIndex')+1),
                "my_date": $("#updateInputDate").val()
            }])
        });
    });
});




