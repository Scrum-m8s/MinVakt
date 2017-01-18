
$(document).ready(function initialize() {
//fetch all shifts and insert into correct dates in the calendar
    var childRows = document.getElementById("bscal").childNodes;
    var shiftCounter = 1;
    for(var i = 1; i < childRows.length - 2; ++i){
        var childCols = document.getElementById("r" + i).childNodes;
        for(var j = 1; j < childCols.length; ++j){
            var calDate = new Date(document.getElementById("r" + i + "c" + (j + 1)));
            $.get("/api/shift_lists/" + shiftCounter, function(data){
                var myDate = new Date(data.my_date);
                while(myDate.getTime() == calDate.getTime()){
                    var newCalDiv = document.createElement("div");
                    var newCalContent = document.createTextNode(calDate.toDateString());
                    newCalDiv.appendChild(newCalContent);
                    var currentDiv;

                    currentDiv = document.getElementById("r" + i + "c" + (j + 1));
                    currentDiv.style.backgroundColor = "Brown";
                    currentDiv.insertBefore(newCalDiv, null);
                    ++shiftCounter;
                }
            });
    }
    }
});

/*
 "I suggest you use drop-downs or some similar constrained form of date entry rather than text boxes,
  though, lest you find yourself in input validation hell."
  - stackoverflow user moonshadow
 */