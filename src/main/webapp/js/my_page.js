/**
 * Created by espen on 24.01.2017.
 */

function userCallback(userdata){



}


$(document).ready(function(){
    $.get("/api/users/current", userCallback(data));
});

