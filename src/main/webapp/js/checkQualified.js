/**
 * Created by espen on 19.01.2017.
 */
var rootURL = "http://localhost:8081/api/employees/";

function checkQualified(date, shift){

    var employees;
    var queryUrl = 'shift/' + date;

    if(shift != null){
        queryUrl += '/' + shift;
    }

    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: rootURL + queryUrl,
        dataType: "json",
        success: function(data, textStatus, jqXHR){
            if(shift != null){
                console.log("Success loading shift");
            }else{
                console.log("Success loading date");
            }

            employees = data;
            var nurses = 0;
            var skilled = 0;
            var assistants = 0;
            for(var i=0; i<employees.length; i++){
                var category = employees[0].category;
                if(category == 1){
                    nurses++;
                }else if(category == 2){
                    skilled++;
                }else if(category == 3){
                    assistants++;
                }
            }

            console.log("Sykepleiere: " + nurses);
            console.log("Fagarbeidere: " +skilled);
            console.log("Assistenter: " + assistants);

        },
        error: function(data, textStatus, jqXHR){
            console.log("Error: " + textStatus);
        }
    });
}