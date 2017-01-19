
var user = "nina"; //test value

$(document).ready(function initialize() {
//fetch the shifts for this user and insert into correct dates in the calendar
    $.get("/api/shift_lists/" + user, function(data){
        for(var i = 0; i < data.length; ++i){ //shift counter

            var newDate = new Date(data[i].my_date); //the date of each shift
            var first = new Date(newDate.getFullYear(), newDate.getMonth(), 1);
            var currentWOM = newDate.getWeek() - first.getWeek() + 2;
            var shiftType = data[i].shift_id;
            var color;
            var shiftTypeString;
            var currentDiv;

            if(shiftType == 1){
                color = "DarkSlateBlue ";
                shiftTypeString = "Natt";
            } else if(shiftType ==2){
                color = "GoldenRod";
                shiftTypeString = "Dag";
            } else if(shiftType == 3){
                color = "LightSlateGrey";
                shiftTypeString = "Kveld";
            }

            if(newDate.getDay() == 0) {
                currentDiv = document.getElementById("r" + currentWOM + "c8");
                ++currentWOM;
            } else {
                currentDiv = document.getElementById("r" + currentWOM + "c" + (newDate.getDay() + 1));
            }

            currentDiv.style.backgroundColor = color;
            currentDiv.innerHTML = newDate.toDateString(); // can add data[i].want_swap

            currentDiv.onclick = function(){$("#timelisteModal").modal();};

            document.getElementById("modalHead").innerHTML = "Skift den: " + currentDiv.innerHTML;
            document.getElementById("modalBody").innerHTML = "Type skift: " + shiftTypeString;
        }
    });
});
