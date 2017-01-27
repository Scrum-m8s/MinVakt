
$(document).ready(function() {
    // Bind opp tabellen mot rest-ressursen
    var user = window.location.href.split("/")[5];

    var monthNames = ["Januar", "Februar", "Mars", "April", "Mai", "Juni",
        "Juli", "August", "September", "Oktober", "November", "Desember"
    ];
    var totOver = 0;
    var totAbs = 0;
    var totOrd = 0;

    var table = $('#table_timelists').DataTable( {
        "paging": false,
        "info" : false,
        "select" : {
            style: 'single'
        },
        "responsive": true,
        "autoWidth": true,

        ajax: {
            url: 'api/timelists/user/' + user,
            "dataSrc": function (json) {
                var return_data = new Array();
                for (var i = 0;  i < json.length; ++i) {
                    json[i].month = monthNames[(json[i].month)];

                    return_data.push({
                        'year': json[i].year,
                        'month': json[i].month,
                        'overtime': json[i].overtime,
                        'absence': json[i].absence,
                        'ordinary': json[i].ordinary,
                        'total': json[i].overtime + json[i].absence + json[i].ordinary,
                        'user_id': json[i].user_id
                    });
                    totOver += json[i].overtime;
                    totAbs += json[i].absence;
                    totOrd += json[i].ordinary;
                }
                return_data.push({
                    'year': "Totalt",
                    'month': "-",
                    'overtime': totOver,
                    'absence': totAbs,
                    'ordinary': totOrd,
                    'total': totOver + totAbs + totOrd
                });
                return return_data;
            }
        },

        "columns" : [
            { data: 'year'},
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