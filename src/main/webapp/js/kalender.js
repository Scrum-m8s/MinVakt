
$(document).ready(function initialize() {
//current date
    var today = new Date();
    var mnd = today.getMonth();

// find last day of month
    var last = new Date(today.getYear(), mnd + 1, 0);

//get week of the year by date
    Date.prototype.getWeek = function () {
        var onejan = new Date(this.getFullYear(), 0, 1);
        var millisecsInDay = 86400000; // 1000*60*60*24 ms-s-min-hr
        return Math.ceil((((this - onejan) / millisecsInDay) + onejan.getDay() + 1) / 7);
    };

//get first day of current month to know when to initialize calendar
    var first = new Date(today.getFullYear(), mnd, 1);
    var firstWeek = first.getWeek();

//TODO: add listener on each week. onclick: go to big view of that week
//TODO: switch month
//creates an empty 6x8 grid with IDs
    var newRow;
    for (var l = 1; l < 7; ++l) { //weeks
        newRow = document.createElement("div");
        newRow.className = "row";
        newRow.id = "r" + l;
        document.getElementById("bscal").appendChild(newRow);
        for (var m = 1; m < 9; ++m) { //days
            var newDateDiv = document.createElement("div");
            newDateDiv.className = "col-sm-1";
            newDateDiv.setAttribute("id", "r" + l + "c" + m);
            document.getElementById("r" + l).appendChild(newDateDiv);
        }
        var newEndCol = document.createElement("div");
        newEndCol.className = "col-sm-4";
        document.getElementById("r" + l).appendChild(newEndCol);

    }

    var currentDay = first; //variable for counting days when looping through days of the month
    var currentWOM = 1; //variable for counting weeks when looping through days of the month

//TODO: write in a smaller date format
//populate a div for each day of the month. last.getDate is the amount of days current month
    for (var k = 0; k < last.getDate(); ++k) {
        var newDay = document.createElement("div");
        var newDayContent = document.createTextNode(currentDay.toDateString());
        newDay.appendChild(newDayContent);
        var currentDiv;

        if (currentDay.getDay() != 0) {
            currentDiv = document.getElementById("r" + (currentWOM) + "c" + (currentDay.getDay() + 1));
            currentDiv.style.backgroundColor = "AliceBlue";
            currentDiv.insertBefore(newDay, null);

        } else {
            currentDiv = document.getElementById("r" + (currentWOM) + "c8");
            currentDiv.style.backgroundColor = "AliceBlue";
            currentDiv.insertBefore(newDay, null);
            ++currentWOM; //update week of month
        }
        currentDay.setDate(currentDay.getDate() + 1); //increment currentDay -> tomorrow
    }


//generate the week numbers and insert them in the grid
    for (var n = 0; n < 6; ++n) {
        var newWeekDiv = document.createElement("div");
        var newWeekContent = document.createTextNode(firstWeek + n);
        newWeekDiv.appendChild(newWeekContent); //add the text node to newly created div

        // add the newly created element and its content into the DOM
        var currentWeekDiv = document.getElementById("r" + (n + 1) + "c1");
        currentWeekDiv.insertBefore(newWeekDiv, null);
    }
});