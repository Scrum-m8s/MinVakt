/*
legg til event listener på hver vakt som blir opprettet. når vakten klikkes skal modal vises med
relevant informasjon om vakten

fargekode vaktene etter type vakt. feks gul = dag, mørkeblå = natt og grønn = kveld
 */
var user = "anders"; //test
$(document).ready(function initialize() {
//fetch the shifts for this user and insert into correct dates in the calendar
    var childRows = document.getElementById("bscal").childNodes;
    var shiftCounter = 1;
    for(var i = 1; i < childRows.length - 2; ++i){
        var childCols = document.getElementById("r" + i).childNodes;
        for(var j = 1; j < childCols.length; ++j){
            var calDate = new Date(document.getElementById("r" + i + "c" + (j + 1)));
            $.get("/api/shift_lists/" + user, function(data){
                var newDate = data.my_date.toDateString();
                while(newDate.getTime() == calDate.getTime()){
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
 $.get("/api/shift_lists/anders/" + 2, function(data){
 console.log(data.my_date);
 });
 */

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