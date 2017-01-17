/*
legg til event listener på hver vakt som blir opprettet. når vakten klikkes skal modal vises med
relevant informasjon om vakten

fargekode vaktene etter type vakt. feks gul = dag, mørkeblå = natt og grønn = kveld
 */

$(document).ready(function initialize() {
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
