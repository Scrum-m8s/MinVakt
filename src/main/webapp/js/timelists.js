
$(document).ready(function() {
    // Bind opp tabellen mot rest-ressursen
    var user = "nina"; //test
    var monthNames = ["January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ];

    var table = $('#table_timelists').DataTable( {
        "paging": false,
        "info" : false,
        "select" : true,

        ajax: {
            url: 'api/timelists/' + user,
            "dataSrc": function (json) {
                var return_data = new Array();
                for (var i = 0; i < json.length; i++) {
                    return_data.push({
                        'month': monthNames[json[i].month - 1],
                        'overtime': json[i].overtime,
                        'absence': json[i].absence,
                        'ordinary': json[i].ordinary,
                        'total': json[i].overtime + json[i].absence + json[i].ordinary,
                        'user_id': json[i].user_id
                    })
                }
                return return_data;
            }
        },

        "columns" : [
            { data: 'month'},
            { data: 'overtime', orderable: false},
            { data: 'absence', orderable: false},
            { data: 'ordinary', orderable: false},
            { data: 'total'}
        ]
    });

    //write table
    table.draw();
});
