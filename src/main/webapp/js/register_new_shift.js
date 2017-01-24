/**
 * Created by Nina on 15.01.2017.
 */
$(document).ready(function() {
    // Bind opp tabellen mot rest-ressursen
    var table = $('#role_table').DataTable( {
        "paging": false,
        "info" : false,
        "select" : true,
        "searching" : false,
        ajax: {
            url: 'api/employees',
            "dataSrc": function (json) {
                var return_data = new Array();
                for (var i = 0; i < json.length; i++) {
                    if (json[i].category === 1) {
                        json[i].category = 'Sykepleier';
                    } else if (json[i].category === 2) {
                        json[i].category = 'Fagarbeider';
                    } else if (json[i].category === 3) {
                        json[i].category = 'Assistent';
                    } else {
                        json[i].category = 'Uregistrert';
                    }
                    return_data.push({
                        'surname': json[i].surname,
                        'firstname': json[i].firstname,
                        'category': json[i].category,
                        'user_id': json[i].user_id
                    })
                }
                return return_data;
            }
        },

        "columns" : [
            { data: 'surname'},
            { data: 'firstname', orderable: false},
            { data: 'category'},
            { data: 'user_id', orderable: false}
        ]
    });
    table.on( 'select', function ( ) {
        if(table.rows( { selected: true } ).data()[0].category!= 'Sykepleier') {
            $("#ansvarsvakt").hide();
        }else{
            $("#ansvarsvakt").show();
        }
        $("#datediv").show();
    });
    table.on( 'deselect', function ( ) {
        $("#datediv").hide();
    });

    table.draw();

    $("#sykepleier").click(function () {
        table.ajax.url("/api/employees/category/1");
        table.ajax.reload();
        $('#role_table').show();
        $("#datediv").hide();
        $('#info1').show();
    });

    $("#fag").click(function () {
        table.ajax.url("/api/employees/category/2");
        table.ajax.reload();
        $('#role_table').show();
        $("#datediv").hide();
        $('#info1').show();
    });

    $("#ass").click(function () {
        table.ajax.url("/api/employees/category/3");
        table.ajax.reload();
        $('#role_table').show();
        $("#datediv").hide();
        $('#info1').show();
    });

    $("#nyVakt").click(function () {
        var ansvarCheck = 0;
        if($("#ansvar").is(':checked')){
            ansvarCheck = 1;
        }
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: 'api/shift_lists',
            dataType: "json",
            data: JSON.stringify({
                "user_id": table.rows( { selected: true } ).data()[0].user_id,
                "my_date": $("#datepick").val(),
                "shift_id": $("#myselect").val(),
                "on_duty": ansvarCheck,
                "deviance": 0,
                "want_swap":0
            }),
            success: function(data, textStatus, jqXHR){
                $('#regSuccess').show().delay(2000).fadeOut();
                $('#role_table').hide();
                $("#datediv").hide();
                $('#info1').hide();
            },
            error: function(data, textStatus, jqXHR){
                $('#regFail').show().delay(2000).fadeOut();

            }
        });
    });
});

