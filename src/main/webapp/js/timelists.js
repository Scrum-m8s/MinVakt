<!--
   Hente ut liste med antall timer jobbet, og antall timer fravær/overtid gitt bruker og mnd
   dette er for admin. på slutten av mnd ser alle timer
   Backend ferdig (?) hent timelist
   -->


$(document).ready(function() {
    // Bind opp tabellen mot rest-ressursen
    var user = "nina"; //test
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
                        'month': json[i].month,
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
