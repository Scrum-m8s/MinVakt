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
        firstDay: 1,
        eventClick: function(calEvent, jsEvent, view) {
            $("#buttonsShift").html("");
            $("#employeesOnShiftList").html("<hr><h4>På vakt:</h4>");
            $("#shiftModal").modal('show');
            $("#username_filler_shift").html("Brukernavn: <span>" + calEvent.title + "</span>");
            $("#date_shift").html("Dato: <span>" + calEvent.date + "</span>");
            $("#time_shift").html("Tid: " + calEvent.startTime + " - " + calEvent.endTime + " (<span>" + calEvent.shiftType + "</span>)");

            $.getJSON('api/shift_lists/' + calEvent.date + "/" + calEvent.shiftId, function(data) {
                    $.each(data, function(index, item) {
                        if (item.on_duty) {
                            $("#employeesOnShiftList").append('<li id="' + item.user_id + '" class="list-group-item justify-content-between">' + item.user_id + '<span style="float: right; margin-right: 0.5%;" class="label label-success">Ansvar</span></li>');
                        } else {
                            $("#employeesOnShiftList").append('<li id="' + item.user_id + '" class="list-group-item justify-content-between">' + item.user_id + '</li>');
                        }

                        if (item.want_swap === true) {
                            $("#employeesOnShiftList li:last").append('<span id="wantSwapLabel" style="float: right; margin-right: 0.5%;" class="label label-danger">Vil bytte</span>');
                        }

                        getCategory(item.user_id, function(category) {
                            $('#' + item.user_id).append('<span style="float: right; margin-right: 0.5%;" class="label label-primary">' + category + '</span>');
                        })
                    });
                })
                .success(function() {
                    console.log("successfully fetched shifts!");
                    isEmployeeOnShift(function(amt) {
                        if (amt === 1) {
                            $("#buttonsShift").html('<button style="margin-right: 2%" class="btn btn-primary" id="wantSwapBtn">Toggle bytte vakt</button><button class="btn btn-primary" id="regAbsenceBtn">Registrer fravær</button>');
                        } else {
                            $("#buttonsShift").html('<button class="btn btn-primary" id="setBusyBtn">Toggle opptatt</button>');
                        }
                    });
                });
        },
        events: function(start, end, timezone, callback) {
            $.ajax({
                url: 'api/shift_lists',
                type: "GET",
                datatype: 'json',
                success: function(doc) {
                    var events = [];
                    var color;
                    var startTime;
                    var endTime;
                    var shiftTypeString;
                    var date;
                    var d;
                    var m;
                    var y;

                    if (doc != undefined && doc.length > 0) {
                        doc.forEach(function(entry) {
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
                                title: entry.user_id,
                                //start: new Date(entry.my_date, startTime, 0),
                                //end: new Date(entry.my_date, endTime, 0),
                                date: new Date(y, m, d + 1).toISOString().slice(0, 10),
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

    getCurrentUserId(function(user_id) {
        $(document).on("click", "#wantSwapBtn", function() {
            if ($("#" + user_id).children("span").is("#wantSwapLabel")) {
                setWantSwap(false, user_id);
            } else {
                setWantSwap(true, user_id);
            }
        });
    });


    function setWantSwap(swap, user_id) {
        var date = $("#date_shift span").text();
        var shift_type = $("#time_shift span").text();
        var shift_id = -1;
        if (shift_type === "Nattevakt") {
            shift_id = 1;
        } else if (shift_type === "Dagvakt") {
            shift_id = 2;
        } else if (shift_type === "Aftenvakt") {
            shift_id = 3;
        }
        var swapString = Boolean(swap).toString();
        $.ajax({
            type: 'PUT',
            contentType: 'application/json',
            accept: 'application/json',
            url: 'api/function/setwantswap/' + date + "/" + shift_id + "/" + user_id + "/" + swapString,
            dataType: "json",
            data: JSON.stringify({
                want_swap: swap
            }),
            success: function(data, textStatus, jqXHR) {
                console.log("Want swap status updated.");
                $("#shiftModal").modal('hide');
                if (swap) {
                    alert("Ønsket om å bytte bort vakten er registrert.");
                } else {
                    alert("Ønsket om å bytte bort vakten er fjernet.");
                }
            },
            error: function(data, textStatus, jqXHR) {
                console.log("Error with swap change.");
                $("#shiftModal").modal('hide');
                if (swap) {
                    alert("Ønsket om å bytte bort vakten er registrert.");
                } else {
                    alert("Ønsket om å bytte bort vakten er fjernet.");
                }
            }
        });
    }

    $("#closeShift").on('click', function() {
        $("#shiftModal").modal('hide');
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
        $.getJSON('api/users/current', function(json) {
            var amt = 0;
            $("#employeesOnShiftList li").each(function() {
                if ($(this).is('#' + json.user_id)) {
                    amt++;
                }
            });
            callback(amt);
        });
    }

    function getCurrentUserId(callback) {
        $.getJSON('api/users/current', function(json) {
            callback(json.user_id);
        });
    }

});
