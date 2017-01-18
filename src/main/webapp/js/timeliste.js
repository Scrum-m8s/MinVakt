/*
legg til event listener på hver vakt som blir opprettet. når vakten klikkes skal modal vises med
relevant informasjon om vakten

fargekode vaktene etter type vakt. feks gul = dag, mørkeblå = natt og grønn = kveld
 */
var user = "anders"; //test value

$(document).ready(function initialize() {
//fetch the shifts for this user and insert into correct dates in the calendar
    $.get("/api/shift_lists/" + user, function(data){
        for(var i = 0; i < data.length; ++i){ //shift counter
            var newDate = new Date(data[i].my_date); //the date of each shift

            var first = new Date(newDate.getFullYear(), newDate.getMonth(), 1);
            var currentWOM = newDate.getWeek() - first.getWeek() + 2;
            var calDate = new Date();
            if(newDate.getDay() != 0) {
                calDate = new Date(document.getElementById("r" + currentWOM + "c" + newDate.getDay() + 1));
            } else{
                calDate = new Date(document.getElementById("r" + currentWOM + "c8"));
            }

            var shiftType = data[i].shift_id;
            var color;
            if(shiftType == 1){
                color = "DarkSlateBlue ";
            } else if(shiftType ==2){
                color = "GoldenRod";
            } else if(shiftType == 3){
                color = "LightSlateGrey";
            }
            var currentDiv;

            //TODO: modal when clicked
            //TODO: change color depending on shift type
            if(calDate.getDay() != 0){
                currentDiv = document.getElementById("r" + currentWOM + "c" + (newDate.getDay() + 1));
                currentDiv.innerHTML = newDate.toDateString(); // can add data[i].want_swap);
                currentDiv.style.backgroundColor = color;
            } else {
                currentDiv = document.getElementById("r" + currentWOM + "c8");
                currentDiv.innerHTML = newDate.toDateString();
                currentDiv.style.backgroundColor = color;
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