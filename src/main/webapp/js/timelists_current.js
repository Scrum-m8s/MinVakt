
$(document).ready(function() {


    getCurrentUserId(function(user_id) {

        $("#username_filler_timelist").html("");
        $("#username_filler_timelist").append("Timeliste for " + user_id);

        var monthNames = ["Januar", "Februar", "Mars", "April", "Mai", "Juni",
            "Juli", "August", "September", "Oktober", "November", "Desember"
        ];
        var totOver = 0;
        var totAbs = 0;
        var totOrd = 0;

        var table = $('#table_timelists_current').DataTable( {
            "paging": false,
            "info" : false,
            "select" : {
                style: 'single'
            },
            "responsive": true,

            ajax: {
                url: 'api/timelists/user/' + user_id,
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
                            'user_id': user_id
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



    function getCurrentUserId(callback) {
        $.getJSON('api/users/current', function(json)  {
            callback(json.user_id);
        });
    }
});