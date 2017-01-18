
$(document).ready(function initialize() {
//fetch the shifts for all users and insert into correct dates in the calendar
    $.get("/api/shift_lists/", function(data){
        //data er her en liste over alle shiftlistene

            for(var j = 0; j < data.length; ++j) { //shift counter

                var newDate = new Date(data[j].my_date); //the date of each shift
                var first = new Date(newDate.getFullYear(), newDate.getMonth(), 1);
                var currentWOM = newDate.getWeek() - first.getWeek() + 2;
                var calDate = new Date();

                if(newDate.getDay() != 0) {
                    calDate = new Date(document.getElementById("r" + currentWOM + "c" + newDate.getDay() + 1));
                } else{
                    calDate = new Date(document.getElementById("r" + currentWOM + "c8"));
                }

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
                var newDayContent;

                //TODO: modal when clicked
                if(calDate.getDay() != 0){
                    currentDiv = document.getElementById("r" + currentWOM + "c" + (newDate.getDay() + 1));
                    currentDiv.style.backgroundColor = color;
                    newDayContent = document.createTextNode(newDate.toDateString());
                    currentDiv.appendChild(newDayContent);
                    currentDiv.insertBefore(newDayContent, null);
                } else {
                    currentDiv = document.getElementById("r" + currentWOM + "c8");
                    currentDiv.style.backgroundColor = color;
                    newDayContent = document.createTextNode(newDate.toDateString());
                    currentDiv.appendChild(newDayContent);
                    currentDiv.insertBefore(newDayContent, null);
                    ++currentWOM;
                }
            }
    });
});

/*
 "I suggest you use drop-downs or some similar constrained form of date entry rather than text boxes,
  though, lest you find yourself in input validation hell."
  - stackoverflow user moonshadow
 */