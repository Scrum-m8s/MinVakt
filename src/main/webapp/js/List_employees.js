/**
 * Created by Nina on 15.01.2017.
 */
$(document).ready(function() {
    // Bind opp tabellen mot rest-ressursen
    $('#table_employees').DataTable( {
        "paging": false,
        "info" : false,
        //"ordering": false,
        ajax: {
            url: 'api/employees',
            "dataSrc": function (json) {
                var return_data = new Array();
                for (var i = 0; i < json.length; i++) {
                    if (json[i].category === 1) {
                        json[i].category = 'Sykepleier';
                    } else if (json[i].category === 2) {
                        json[i].category = 'Fagarbeider';
                    } else {
                        json[i].category = 'Assistent';
                    }
                    return_data.push({
                        'surname': json[i].surname,
                        'firstname': json[i].firstname,
                        'phoneNumber': json[i].phoneNumber,
                        'email': json[i].email,
                        'category': json[i].category,
                    })
                }
                return return_data;
            }
        },
        "columns" : [
            { data: 'surname'},
            { data: 'firstname', orderable: false},
            { data: 'phoneNumber', orderable: false},
            { data: 'email', orderable: false},
            { data: 'category', orderable: false}
        ],
    });

    var table = $('#table_employees').DataTable();
    table.draw();
});