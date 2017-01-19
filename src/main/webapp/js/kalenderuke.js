
$(document).ready(function initialize() {
//current date
    var today = new Date();
    var mnd = today.getMonth();
    var last = new Date(today.getYear(), mnd + 1, 0);

//get week of the year by date
    Date.prototype.getWeek = function () {
        var onejan = new Date(this.getFullYear(), 0, 1);
        var millisecsInDay = 86400000; // 1000*60*60*24 ms-s-min-hr
        return Math.ceil((((this - onejan) / millisecsInDay) + onejan.getDay() + 1) / 7);
    };

    var first = new Date(today.getFullYear(), mnd, 1);
    var firstWeek = first.getWeek();

//TODO: switch week
//creates an empty 1x8 grid with IDs
    var newRow;
    newRow = document.createElement("div");
    newRow.className = "row";
    newRow.id = "r1";
    document.getElementById("bscal").appendChild(newRow);
    for (var m = 1; m < 9; ++m) { //days
        var newDateDiv = document.createElement("div");
        newDateDiv.className = "col-sm-1";
        newDateDiv.setAttribute("id", "r1" + "c" + m);
        document.getElementById("r1").appendChild(newDateDiv);
    }
    var newEndCol = document.createElement("div");
    newEndCol.className = "col-sm-4";
    document.getElementById("r1").appendChild(newEndCol);


    var currentDay = first;

//TODO: write in a smaller date format
//populate a div for each day of the month. last.getDate is the amount of days current month
    for (var k = 0; k < 7; ++k) {
        var newDay = document.createElement("div");
        var newDayContent = document.createTextNode(currentDay.toDateString());
        newDay.appendChild(newDayContent);
        var currentDiv;

        if (currentDay.getDay() != 0) {
            currentDiv = document.getElementById("r1c" + (currentDay.getDay() + 1));
        } else {
            currentDiv = document.getElementById("r1c8");
        }
        currentDiv.insertBefore(newDay, null);
        currentDay.setDate(currentDay.getDate() + 1); //increment currentDay -> tomorrow
        currentDiv.style.backgroundColor = "AliceBlue";
    }


//generate the week number and insert it in the grid
        var newWeekDiv = document.createElement("div");
        var newWeekContent = document.createTextNode(firstWeek);
        newWeekDiv.appendChild(newWeekContent); //add the text node to newly created div

        // add the newly created element and its content into the DOM
        var currentWeekDiv = document.getElementById("r1c1");
        currentWeekDiv.insertBefore(newWeekDiv, null);
});