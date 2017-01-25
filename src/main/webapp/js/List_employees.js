/**
 * Created by Nina on 15.01.2017.
 */
$(document).ready(function() {
    // Bind opp tabellen mot rest-ressursen
    var table = $('#table_employees').DataTable( {
        "paging": false,
        "info" : false,
        "select" : {
            style: 'single'
        },
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
                        'phone_number': json[i].phone_number,
                        'email': json[i].email,
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
            { data: 'phone_number', orderable: false},
            { data: 'email', orderable: false},
            { data: 'category'},
            { data: 'user_id', orderable: false}
        ]
    });

    //write table
    table.draw();
});