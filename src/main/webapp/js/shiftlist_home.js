$(document).ready(function() {
    getCurrentUserId(function(user_id) {
        $('#calendar_home').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            editable: false,
            timeFormat: 'H:mm',
            weekNumbers: true,
            firstDay: 1,
            events: function (start, end, timezone, callback) {
                $.ajax({
                    url: 'api/shift_lists/get_by_id/' + user_id,
                    type: "GET",
                    datatype: 'json',
                    success: function ( doc ) {
                        var events = [];
                        var color;
                        var startTime;
                        var endTime;
                        var shiftTypeString;
                        var date;
                        var d;
                        var m;
                        var y;

                        if ( doc != undefined && doc.length > 0 ) {
                            doc.forEach( function( entry ) {
                                date = new Date(entry.my_date);
                                d = date.getDate();
                                m = date.getMonth();
                                y = date.getFullYear();
                                if (entry.shift_id == 1) {
                                    color = 'MidnightBlue';
                                    shiftTypeString = "Nattevakt";
                                    startTime = "0";
                                    endTime = "8"
                                } else if (entry.shift_id == 2) {
                                    color = 'LimeGreen';
                                    shiftTypeString = "Dagvakt";
                                    startTime = "8";
                                    endTime = "16"
                                } else if (entry.shift_id == 3) {
                                    color = 'DarkOrange';
                                    shiftTypeString = "Aftenvakt";
                                    startTime = "16";
                                    endTime = "24"
                                }

                                events.push({
                                    title: user_id,
                                    //start: new Date(entry.my_date, startTime, 0),
                                    //end: new Date(entry.my_date, endTime, 0),
                                    date: new Date(y, m, d+1).toISOString().slice(0, 10),
                                    startTime: startTime + ":00",
                                    endTime: endTime + ":00",
                                    start: new Date(y, m, d, startTime, 0),
                                    end: new Date(y, m, d, endTime, 0),
                                    allDay: false,
                                    shiftType: shiftTypeString,
                                    shiftId: entry.shift_id,
                                    color: color
                                });
                            });
                        }
                        callback(events);
                    }
                });
            }
        });
    });

    function getCurrentUserId(callback) {
        $.getJSON('api/users/current', function(json)  {
            callback(json.user_id);
        });
    }
});