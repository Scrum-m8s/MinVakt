
//fetch the shifts for all users and insert into correct dates in the calendar
$(document).ready(function initialize() {
    $.get("/api/shift_lists/", function(data){

        //empties the grid and fills in 3 divs in different colours for types of shifts
        var calChildren = document.getElementById("bscal").childNodes; //rows
        for(var i = 3; i < calChildren.length; ++i){ // count rows
            var rowChildren = calChildren[i].childNodes; //columns
            for(var k = 1; k < rowChildren.length - 1; ++k){ //count columns
                var currDiv = rowChildren[k];
                currDiv.innerHTML = ""; //clears div
                currDiv.style.backgroundColor = "White"; //clears style
                for(var l = 0; l < 3; ++l){
                    var newShiftDiv = document.createElement("div");
                    var newShiftContent = document.createTextNode(""); //test value
                    newShiftDiv.appendChild(newShiftContent);
                    newShiftDiv.onclick = function(){
                        $("#shiftModal").modal();
                    };
                    currDiv.insertBefore(newShiftDiv, null);
                }
            }
        }
        for(var j = 0; j < data.length; ++j) { //shift counter
            var newDate = new Date(data[j].my_date); //the date of each shift
            var first = new Date(newDate.getFullYear(), newDate.getMonth(), 1);
            var currentWOM = newDate.getWeek() - first.getWeek() + 2;

            var shiftType = data[j].shift_id;
            var currentDiv;
            var currentChildren;


            document.getElementById("modalHead").innerHTML = "Skift den: " + newDate; //TODO: date format

            if(newDate.getDay() != 0) {
                currentDiv = document.getElementById("r" + currentWOM + "c" + (newDate.getDay() + 1));
            } else {
                currentDiv = document.getElementById("r" + currentWOM + "c8");
                ++currentWOM;
            }
            currentChildren = currentDiv.childNodes;

            if(shiftType == 1){
                currentChildren[0].style.backgroundColor = "DarkSlateBlue";
            }else if(shiftType == 2){
                currentChildren[1].style.backgroundColor = "GoldenRod";
            }else if(shiftType == 3){
                currentChildren[2].style.backgroundColor = "LightSlateGrey";
            }

            currentChildren[shiftType - 1].innerHTML = newDate.toDateString();
            document.getElementById("modalHead").innerHTML =  currentChildren[shiftType - 1].innerHTML + "\n";

            if(data[j].want_swap){
                currentChildren[shiftType - 1].style.backgroundColor = "Crimson"; //undermanned
            }
        }
    });
});