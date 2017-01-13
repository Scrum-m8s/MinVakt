/*
legg til event listener på hver vakt som blir opprettet. når vakten klikkes skal modal vises med
relevant informasjon om vakten

fargekode vaktene etter type vakt. feks gul = dag, mørkeblå = natt og grønn = kveld
 */

$(document).ready(function initialize() {

    var currd = new Date();
    var mnd = currd.getMonth();

//get week of the year by date
    Date.prototype.getWeek = function() {
        var onejan = new Date(this.getFullYear(),0,1);
        var millisecsInDay = 86400000; // 1000*60*60*24 ms-s-min-hr
        return Math.ceil((((this - onejan) /millisecsInDay) + onejan.getDay()+1)/7);
    };

//get first day of current month to know when to initialize calendar
    var first = new Date(currd.getFullYear(), mnd,1);
    var firstWeek = first.getWeek();


//id of first element on each row in timeliste grid
    var calid = ["r2c1", "r3c1", "r4c1", "r5c1", "r6c1", "r7c1", "r8c1"];

    for (var j = 0; j < calid.length; ++j) {
        var newDiv = document.createElement("div");
        var newContent = document.createTextNode(firstWeek + j);
        newDiv.appendChild(newContent); //add the text node to newly created div

        // add the newly created element and its content into the DOM
        var currentDiv = document.getElementById(calid[j]);
        currentDiv.insertBefore(newDiv, null);
    }

});



/*
/*
function listVakter() {
    // uke = rad og kolonne = dag
    $.get('/shifts'), function(data){
        data.forEach(function(elem){
            var elemD = new Date(elem.my_date); //for every shift, get date
            if(elemD.getMonth == mnd) { //make sure it's this month's shifts
                var diff = elemD.getDate() - currd.getDate();

                if(diff == 0) {
                    //legg inn skift i dag
                } else if(diff < 0){
                    //legg inn skift før i dag
                } else{
                    //legg inn skift etter i dag
                }
            }
            /*
            for every shift elem:
            - check date
            - compare date to current date
            - find correct place in array
            - create event listener
             */
/*
function initialize() {
    $.get('/rest/plass', function (data) {
        data.forEach(function (elem) {
            var marker = new google.maps.Marker({
                position: {lat: elem.latitude, lng: elem.longitude},
                title: elem.lokasjon,
                map: map
            });
            marker.plassId = elem.id;

            google.maps.event.addListener(marker, 'click', function (event) {
                selectPlass(this.plassId)
            });
        });
    });
}
*/
    /*
    sjekk denne måneden
    for hver uke:
        lag rad
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
