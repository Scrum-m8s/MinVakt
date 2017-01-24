$(document).ready(function() {


    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        editable: true,
        timeFormat: 'H:mm',
        events: function (start, end, timezone, callback) {
            $.ajax({
                url: 'api/shift_lists',
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
                                shiftTypeString = "Dagsvakt";
                                startTime = "8";
                                endTime = "16"
                            } else if (entry.shift_id == 3) {
                                color = 'DarkOrange';
                                shiftTypeString = "Kveldsvakt";
                                startTime = "16";
                                endTime = "24"
                            }
                            events.push({
                                title: entry.user_id,
                                //start: new Date(entry.my_date, startTime, 0),
                                //end: new Date(entry.my_date, endTime, 0),
                                start: new Date(y, m, d, startTime, 0),
                                end: new Date(y, m, d, endTime, 0),
                                allDay: false,
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


/*
 events: {
 url: 'api/shift_lists/nina',
 type: 'GET',
 data: function(json) {
 // a function that returns an object
 var date;
 var d;
 var m;
 var y;

 var color;
 var shiftTypeString;
 var user_id;
 var startTime;
 var endTime;

 for (var i = 0; i < json.length; i++) {
 user_id = json[i].user_id;
 date = new Date(json[i].my_date);
 d = date.getDay();
 m = date.getMonth();
 y = date.getFullYear();

 if (json[i].shift_id == 1) {
 color = "DarkSlateBlue";
 shiftTypeString = "Nattevakt";
 startTime = "0";
 endTime = "8"
 } else if (json[i].shift_id == 2) {
 color = "GoldenRod";
 shiftTypeString = "Dagsvakt";
 startTime = "8";
 endTime = "16"
 } else if (json[i].shift_id == 3) {
 color = "LightSlateGrey";
 shiftTypeString = "Kveldsvakt";
 startTime = "16";
 endTime = "24"
 }
 }

 return {
 //dummy data
 'title': 'test',
 'start': new Date(2017, 1, 23, 16, 0),
 //'end': new Date(2017, 1, 23, 24, 0),
 'allDay': true,
 'id': 'test'

 //'title': shiftTypeString,
 //'start': new Date(y, m, d, startTime, 0),
 //'end': new Date(y, m, d, endTime, 0),
 //'eventColor': color,
 //'allDay': false,
 //'id': user_id
 };
 }
 }
 */