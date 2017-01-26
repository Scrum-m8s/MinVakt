/**
 * Created by Nina on 16.01.2017.
 */

var editor;
$(document).ready(function() {
    // Bind opp tabellen mot rest-ressursen
    $('#table_wantSwap').DataTable( {
        select: true,
        "paging": false,
        "info" : false,
        //"ordering": false,
        ajax: {
            url: 'api/shift_lists/want_swap/true',
            "dataSrc": function (json) {
                var return_data = new Array();
                for (var i = 0; i < json.length; i++) {
                    if (json[i].shift_id === 1) {
                        json[i].shift_id = 'Nattevakt';
                    } else if (json[i].shift_id === 2) {
                        json[i].shift_id = 'Dagvakt';
                    } else if (json[i].shift_id === 3){
                        json[i].shift_id = 'Aftenvakt';
                    } if (json[i].on_duty === true){
                        json[i].on_duty = 'Ja ';
                    } if (json[i].on_duty == false){
                        json[i].on_duty = 'Nei ';
                    }
                    return_data.push({
                        'user_id': json[i].user_id,
                        'shift_id': json[i].shift_id,
                        'on_duty': json[i].on_duty,
                        'my_date': json[i].my_date
                    })
                }
                return return_data;
            }
        },
        "columns" : [
            { data: 'user_id', orderable: false},
            { data: 'shift_id', orderable: false},
            { data: 'on_duty', orderable: false},
            { data: 'my_date'}
        ],
    });

    var table = $('#table_wantSwap').DataTable();
    table.draw();

    $("#avslaa").click(function () {
        if (table.rows( { selected: true } ).data()[0].shift_id === 'Nattevakt') {
            table.rows( { selected: true } ).data()[0].shift_id = 1;
        } else if (table.rows( { selected: true } ).data()[0].shift_id === 'Dagvakt') {
            table.rows( { selected: true } ).data()[0].shift_id = 2;
        } else if (table.rows( { selected: true } ).data()[0].shift_id === 'Aftenvakt'){
            table.rows( { selected: true } ).data()[0].shift_id = 3;
        }
        alert(JSON.stringify(table.rows( { selected: true } ).data()[0].user_id + "  er valgt"));
        $.ajax({
            url: 'api/shift_lists/' + table.rows( { selected: true } ).data()[0].user_id + '/' + table.rows( { selected: true } ).data()[0].shift_id,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({
                user_id: table.rows( { selected: true } ).data()[0].user_id,
                shift_id: table.rows( { selected: true } ).data()[0].shift_id,
                my_date: table.rows( { selected: true } ).data()[0].my_date,
                want_swap: false
            }),
            success: function(result){
                table.ajax.reload();
            },
        });
    });

    $('#deny').click(function() {
        var date = $('tr.selected td:eq(0)').text();
        var shift = $('tr.selected td:eq(1)').text();
        var user = $('tr.selected td:eq(2)').text();

        var category = 0;
        $.get("/api/employee/)" + user, function(data){
            category = data.category;
        });

        if(user!= null) {
            window.location = "http://www.localhost:8080/kvalifisert_for_vakt.html?date=" + date + "/shi=" + shift + "/cat=" + category;
        }
    });
});


