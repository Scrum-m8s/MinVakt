/**
 * Created by mariyashchekanenko on 25/01/2017.
 */

$(document).ready(function() {

    function setEmployeeAsBusy(user_id, shift_id, my_date){
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: 'api/busy',
            data: '{"user_id": "' + user_id + '", "shift_id" : "' + shift_id + '", "my_date" : "' + my_date + '"}',
            dataType: 'json',
            success: function(data, textStatus, jqXHR){
                alert(" Ansatt med bruker_id "+ user_id + " er ikke tilgjengelig " + my_date + " for vakt " + shift_id);
            },
            error: function(data, textStatus, jqXHR){
                console.log("Error: " + textStatus);
            }
        });
    }

    $("#busyButton").click(function () {
        var user_id = $("#user_id").val();
        var shift_id = $("#shift_id").val();
        var my_date = $("#my_date").val();
        console.log("user_id: "+user_id+" shift_id: "+shift_id+ " my_date: "+my_date);
        setEmployeeAsBusy(user_id, shift_id, my_date);
    });
})
