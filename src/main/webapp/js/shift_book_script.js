
//fetch the shifts for all users and insert into correct dates in the calendar
$(document).ready(function initialize() {
    $.get("/api/shift_lists/", function(data){
        for(var j = 0; j < data.length; ++j) { //shift counter

            var newDate = new Date(data[j].my_date); //the date of each shift
            var first = new Date(newDate.getFullYear(), newDate.getMonth(), 1);
            var currentWOM = newDate.getWeek() - first.getWeek() + 2;

            var shiftType = data[j].shift_id;
            var color;
            if(shiftType == 1){
                color = "DarkSlateBlue ";
            } else if(shiftType ==2){
                color = "GoldenRod";
            } else if(shiftType == 3){
                color = "LightSlateGrey";
            }

            var currentDiv;
            var newDayDiv;
            var newDayContent;

            //TODO: modal when clicked
            if(newDate.getDay() != 0){
                currentDiv = document.getElementById("r" + currentWOM + "c" + (newDate.getDay() + 1));
                newDayDiv = document.createElement("div");
                newDayContent = document.createTextNode(newDate.toDateString());
                newDayDiv.appendChild(newDayContent);
                newDayDiv.style.backgroundColor = color;
                currentDiv.insertBefore(newDayDiv, null);
            } else {
                currentDiv = document.getElementById("r" + currentWOM + "c8");
                newDayDiv = document.createElement("div");
                newDayContent = document.createTextNode(newDate.toDateString());
                newDayDiv.appendChild(newDayContent);
                newDayDiv.style.backgroundColor = color;
                currentDiv.insertBefore(newDayDiv, null);
                ++currentWOM;
            }
        }
    });
});
//currentDiv.childNodes[0].innerHTML = "";

/*
 "I suggest you use drop-downs or some similar constrained form of date entry rather than text boxes,
  though, lest you find yourself in input validation hell."
  - stackoverflow user moonshadow
 */