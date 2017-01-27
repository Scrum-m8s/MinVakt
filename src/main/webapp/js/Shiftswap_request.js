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
            url: 'api/function/getwantswap',
            "dataSrc": function (json) {
                var return_data = new Array();
                var category = 1;
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
                        'category': json[i].category,
                        })

                }
                return return_data;
            }
        },
        "columns" : [
            { data: 'my_date'},
            { data: 'shift_id', orderable: false},
            { data: 'user_id', orderable: false},
            { data: 'on_duty', orderable: false},
            { data: 'category', orderable: false}

        ]
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
            url: 'api/function/setwantswap/' + table.rows( { selected: true } ).data()[0].my_date + '/' + table.rows( { selected: true } ).data()[0].shift_id + '/' + table.rows( { selected: true } ).data()[0].user_id + '/false',
            type: 'PUT',
            success: function(result){
                table.ajax.reload();
            },
        });
    });

    $("#availableEmployeesTable").DataTable({
        select: true,
        columns: [
            { data: 'firstname' },
            { data: 'surname' },
            { data: 'email' },
            { data: 'phone_number' },
            { data: 'category' },
            { data: 'user_id' }
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
        var my_date = $('tr.selected td:eq(0)').text();
        var shift_id = $('tr.selected td:eq(1)').text();
        var category = $('tr.selected td:eq(4)').text();
        if(shift_id === ("Nattevakt")){
            shift_id = 1;
        } else if(shift_id === ("Dagvakt")){
            shift_id = 2;
        } else{
            shift_id = 3;
        }
        getAvailableEmployees(shift_id, my_date, category);
    });



    $("#change").click(function () {
        var table1selected = $(".selected")[0];
        var table2selected = $(".selected")[1];
        var new_user_id = $('tr.selected td:eq(5)').text();
        var user_id = $('tr.selected td:eq(2)').text();
        shiftSwap(new_user_id, my_date, shift_id, user_id);
    });

    function shiftSwap(new_user_id, my_date, shift_id, user_id) {
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: 'api/shift_lists/' + new_user_id + '/' + my_date + '/' + shift_id + '/' + user_id,
        data: JSON.stringify({
            new_user_id: new_user_id,
            my_date: my_date,
            Shift_id: shift_id,
            category: user_id
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


    /*

    $('#avaialbleEmployees').click(function() {

        var date = $('tr.selected td:eq(0)').text();
        var shiftString = $('tr.selected td:eq(1)').text();
        var category = $('tr.selected td:eq(4)').text();
        if(shiftString === ("Nattevakt")){
            shift = 1;
        } else if(shiftString === ("Dagvakt")){
            shift = 2;
        } else{
            shift = 3;
        }
    });

    */
});






