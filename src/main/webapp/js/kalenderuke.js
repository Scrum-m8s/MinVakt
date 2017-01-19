
$(document).ready(function initialize() {
//current date
    var today = new Date();

//get week of the year by date
    Date.prototype.getWeek = function () {
        var onejan = new Date(this.getFullYear(), 0, 1);
        var millisecsInDay = 86400000; // 1000*60*60*24 ms-s-min-hr
        return Math.ceil((((this - onejan) / millisecsInDay) + onejan.getDay() + 1) / 7);
    };

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

    var firstOfWeek = today.getDate() - today.getDay();
    var tomorrow;

//populate grid
    for (var k = 1; k < 8; ++k) {
        tomorrow = new Date(today.getFullYear(), today.getMonth(), (firstOfWeek + k));
        var newDay = document.createElement("div");
        var newDayContent = document.createTextNode(tomorrow.toDateString());
        newDay.appendChild(newDayContent);

        var currentDiv = document.getElementById("r1c" + (k + 1));
        currentDiv.insertBefore(newDay, null);
        currentDiv.style.backgroundColor = "AliceBlue";
    }


//generate the week number and insert it in the grid
        var newWeekDiv = document.createElement("div");
        var newWeekContent = document.createTextNode(today.getWeek() + 1);
        newWeekDiv.appendChild(newWeekContent);
        var currentWeekDiv = document.getElementById("r1c1");
        currentWeekDiv.insertBefore(newWeekDiv, null);
});