$(document).ready(function() {


    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        editable: false,
        timeFormat: 'H:mm',
        weekNumbers: true,
        eventOverlap: function(stillEvent, movingEvent) {
            console.log(stillEvent.title && movingEvent.title);
        },
        eventClick: function(calEvent, jsEvent, view) {
            $("#buttonsShift").html("");
            $("#employeesOnShiftList").html("<hr><h4>På vakt:</h4>");
            $("#shiftModal").modal('show');
            $("#username_filler_shift").html("Brukernavn: " + calEvent.title);
            $("#date_shift").html("Dato: " + calEvent.date);
            $("#time_shift").html("Tid: " + calEvent.startTime + " - " + calEvent.endTime + " (" + calEvent.shiftType + ")");

            $.getJSON('api/function/getshifttotal/' + calEvent.date + "/" + calEvent.shiftId, function(data){
                $.each(data, function(index, item) {
                    if(item.on_duty) {
                        $("#employeesOnShiftList").append('<li id="' + item.user_id + '" class="list-group-item justify-content-between">' + item.user_id + '<span style="float: right; margin-right: 0.5%;" class="label label-success">Ansvar</span></li>');
                    } else {
                        $("#employeesOnShiftList").append('<li id="' + item.user_id + '" class="list-group-item justify-content-between">' + item.user_id + '</li>');
                    }

                    if(item.want_swap === true) {
                        $("#employeesOnShiftList li:last").append('<span style="float: right; margin-right: 0.5%;" class="label label-danger">Vil bytte</span>');
                    }

                    getCategory(item.user_id, function(category){
                        $('#' + item.user_id).append('<span style="float: right; margin-right: 0.5%;" class="label label-primary">' + category + '</span>');
                    })
                });
            })
            .success(function(){
                console.log("successfully fetched shifts!");
                isEmployeeOnShift(function(amt) {
                    if(amt === 1) {
                        $("#buttonsShift").html('<button style="margin-right: 2%" type="submit" class="btn btn-primary" id="changeShiftBtn">Bytte vakt?</button><button type="submit" class="btn btn-primary" id="regAbsence">Registrer fravær</button>');
                    } else {
                        $("#buttonsShift").html('<button type="submit" class="btn btn-primary" id="setBusyBtn">Opptatt?</button>');
                    }
                });
            });
        },
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

    function getCategory(user_id, callback) {
        $.getJSON('api/employees/' + user_id, function(json) {
            if (json.category === 1) {
                callback('Sykepleier');
            } else if (json.category === 2) {
                callback('Fagarbeider');
            } else if (json.category === 3) {
                callback('Assistent');
            } else {
                callback('Uregistrert');
            }
        });
    }

    function isEmployeeOnShift(callback) {
        $.getJSON('api/users/current', function(json)  {
            var amt = 0;
            $("#employeesOnShiftList li").each(function() {
                if($(this).is('#' + json.user_id)) {
                    amt++;
                }
            });
            callback(amt);
        });
    }

});