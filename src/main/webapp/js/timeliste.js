/*
legg til event listener på hver vakt som blir opprettet. når vakten klikkes skal modal vises med
relevant informasjon om vakten

fargekode vaktene etter type vakt. feks gul = dag, mørkeblå = natt og grønn = kveld
 */
var user = "haakonrp"; //test

$(document).ready(function initialize() {
//fetch the shifts for this user and insert into correct dates in the calendar
    $.get("/api/shift_lists/" + user, function(data){
        for(var i = 0; i < data.length; ++i){ //shift counter
            var newDate = new Date(data[i].my_date); //the date of each shift

            var first = new Date(newDate.getFullYear(), newDate.getMonth(), 1);
            var currentWOM = newDate.getWeek() - first.getWeek() + 2;
            var calDate = new Date();
            if(newDate.getDay() != 0) {
                calDate = new Date(document.getElementById("r" + currentWOM + "c" + newDate.getDay() + 1)); //toDateString?
            } else{
                calDate = new Date(document.getElementById("r" + currentWOM + "c8")); //toDateString?
            }
            var newCalDiv = document.createElement("div");
            var newCalContent = document.createTextNode(newDate.toDateString() +" " + data[i].want_swap);
            newCalDiv.appendChild(newCalContent);
            var currentDiv;

            if(calDate.getDay() != 0){
                currentDiv = document.getElementById("r" + currentWOM + "c" + (newDate.getDay() + 1));
                currentDiv.style.backgroundColor = "Brown";
                currentDiv.insertBefore(newCalDiv, null);
            } else {
                currentDiv = document.getElementById("r" + currentWOM + "c8");
                currentDiv.style.backgroundColor = "Brown";
                currentDiv.insertBefore(newCalDiv, null);
                ++currentWOM;
            }
        }
    });
});

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