/**
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
                        'my_date': json[i].my_date,
                        'shift_id': json[i].shift_id,
                        'user_id': json[i].user_id,
                        'on_duty': json[i].on_duty,
                    })
                }
                return return_data;
            }
        },
        "columns" : [
            { data: 'my_date'},
            { data: 'shift_id', orderable: false},
            { data: 'user_id', orderable: false},
            { data: 'on_duty', orderable: false}
        ],
    });

    var table = $('#table_wantSwap').DataTable();
    table.draw();

    $("#avslaa").click(function () {
        var shift;
        if (table.rows( { selected: true } ).data()[0].shift_id === 'Nattevakt') {
            table.rows( { selected: true } ).data()[0].shift_id = 1;
        } else if (table.rows( { selected: true } ).data()[0].shift_id === 'Dagvakt') {
            table.rows( { selected: true } ).data()[0].shift_id = 2;
        } else if (table.rows( { selected: true } ).data()[0].shift_id === 'Aftenvakt'){
            table.rows( { selected: true } ).data()[0].shift_id = 3;
        }
        alert(JSON.stringify(table.rows( { selected: true } ).data()[0].user_id + "  er valgt"));
        $.ajax({
            url: 'api/function/setwantswap/' + table.rows( { selected: true } ).data()[0].my_date + '/' + shift + '/' + table.rows( { selected: true } ).data()[0].user_id + '/false',
            //url: 'api/shift_lists/' + table.rows( { selected: true } ).data()[0].user_id + '/' + table.rows( { selected: true } ).data()[0].shift_id,
            type: 'PUT',
            success: function(result){
                table.ajax.reload();
            },
        });
    });

    $('#se_ledige').click(function() {
        var date = $('tr.selected td:eq(0)').text();
        var shiftString = $('tr.selected td:eq(1)').text();
        var user = $('tr.selected td:eq(2)').text();
        var shift;
        if(shiftString === ("Nattevakt")){
            shift = 1;
        } else if(shiftString === ("Dagvakt")){
            shift = 2;
        } else{
            shift = 3;
        }
        var category = 1;
        $.get("/api/employees/" + user, function(data){
            category = data.category;
        });
        if(user!= null) {
            window.location = "http://www.localhost:8080/kvalifisert_for_vakt.html?var1=" + date + "&var2=" + shift + "&var3=" + category;
        }
    });
});



