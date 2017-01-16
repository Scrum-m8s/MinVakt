/*
legg til event listener på hver vakt som blir opprettet. når vakten klikkes skal modal vises med
relevant informasjon om vakten

fargekode vaktene etter type vakt. feks gul = dag, mørkeblå = natt og grønn = kveld
 */

$(document).ready(function initialize() {
//current date
    var today = new Date();
    var mnd = today.getMonth();

// find last day of month
    var last = new Date(today.getYear(), mnd + 1, 0);

//get week of the year by date
    Date.prototype.getWeek = function() {
        var onejan = new Date(this.getFullYear(),0,1);
        var millisecsInDay = 86400000; // 1000*60*60*24 ms-s-min-hr
        return Math.ceil((((this - onejan) /millisecsInDay) + onejan.getDay()+1)/7);
    };

//get first day of current month to know when to initialize calendar
    var first = new Date(today.getFullYear(), mnd, 1);
    var firstWeek = first.getWeek();

//creates an empty 6x8 grid with IDs
    var currRow = document.getElementById("row1id");
    for(var l = 0; l < 6; ++l){
        var newRow = document.createElement("div");
        newRow.className = "row";
        newRow.setAttribute("id", "r" + l);
        for(var m = 0; m < 8; ++m){
            var newDateDiv = document.createElement("div");
            newDateDiv.className = "col-sm-1";
            newDateDiv.setAttribute("id", "r" + l + "c" + m)
        }
        var newEndRow = document.createElement("div");
        newEndRow.className = "col-sm-4";
        currRow.insertBefore(newRow, null);
        currRow = newRow;
    }

    var days = new Array(last.getDate()); // array containing the days of this month
    var currentDay = new Date(); //variable for counting days when looping through days of the month

    //generate a div for each day of the month. last.getDate is the amount of days current month
    for(var k = 0; k < last.getDate(); ++k){
        var newDay = document.createElement("div");
        var newDateContent = document.createTextNode(first.toDateString());

        newDateDiv.appendChild(newDateContent);
        days[k] = newDateDiv;
        currentDay.setDate(currentDay.getDate() + 1); //increment currentDay -> tomorrow
    }

    /*
     this inserts into existing div:
     var currentShift = document.getElementById(); //[week][day]
     currentShift.insertBefore(newShiftDiv, null);
     */


//generate the week numbers and insert them in the grid
//TODO: make sure week 1 is whole (1/1 could be part of week 52)
    for (var j = 0; j < c1id.length; ++j) {
        var newDiv = document.createElement("div");
        var newContent = document.createTextNode(firstWeek + j);
        newDiv.appendChild(newContent); //add the text node to newly created div

        // add the newly created element and its content into the DOM
        var currentDiv = document.getElementById(grID[j][0]);
        currentDiv.insertBefore(newDiv, null);
    }

//fetch the shifts for this user and insert into correct dates in the calendar
    $.get('/users/shifts/', function (data) {
        data.forEach(function (elem){
            //fill array with [week][day]. if shiftWDiff = 0, insert into first week, 1 = second week etc.
            //check that diff !> c1id.length
           var shiftDate = new Date(elem.getMyDate());
            var shiftDOW = shiftDate.getDay();
            var shiftWDiff = firstWeek - shiftDate.getWeek();
           if(shiftDate.getMonth() == mnd){

           } else{
               //feil måned
           }

        });
    });
});



/*
/*
    /*

    for hver vakt:
        sjekk mnd, uke, dag
        lag kolonne med modal
        legg inn tid (fargekod kolonne)
     */
/*

function selectVakt(vaktID){

}
*/

/*var cal = [
 [0, 1, 2, 3, 4, 5, 6, 7],
 [0, 1, 2, 3, 4, 5, 6, 7],
 [0, 1, 2, 3, 4, 5, 6, 7],
 [0, 1, 2, 3, 4, 5, 6, 7],
 [0, 1, 2, 3, 4, 5, 6, 7],
 [0, 1, 2, 3, 4, 5, 6, 7],
 [0, 1, 2, 3, 4, 5, 6, 7]
 ];*/
