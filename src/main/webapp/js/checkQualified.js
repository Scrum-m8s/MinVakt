/**
 * Created by espen on 19.01.2017.
 */
var rootURL = "http://localhost:8080/api/employees/";

// TODO: eksempelkrav, byttes med ekte krav til vakt
// antar minst ti på jobb, min 3 fagarbeidere (30%) og 2 sykepleiere (20%)

const MIN_EMPLOYEES = 10;
const MIN_NURSES = Math.floor(MIN_EMPLOYEES * 0.2);
const MIN_SKILLED = Math.floor(MIN_EMPLOYEES * 0.3);

function countRoles(employees){
    var roles = {
        "nurses": 0,
        "skilled": 0,
        "assistants": 0,
        "total:" : 0
    };

    for(var i=0; i<employees.length; i++){
        var category = employees[0].category;
        if(category == 1){
            roles.nurses++;
        }else if(category == 2){
            roles.skilled++;
        }else if(category == 3){
            roles.assistants++;
        }
    }

    roles.total = employees.length;

    return roles;
}

function checkSufficientEmployeesString(employees){

    var employeeString = {};

    if(employees.nurses <= MIN_NURSES)employeeString[0] = "Ikke nok sykepleiere, mangler " + (MIN_NURSES - employees.nurses) + " sykepleier(e)\n";
    else employeeString[0] = "Det er " + employees.nurses + " sykepleiere på dette skiftet\n";
    if(employees.skilled <= MIN_SKILLED)employeeString[1] = "Ikke nok fagarbeidere, mangler " + (MIN_SKILLED - employees.skilled) + " fagarbeider(e)\n";
    else employeeString[1] = "Det er " + employees.skilled + " fagarbeidere på dette skiftet\n";
    if(employees.total <= MIN_EMPLOYEES) employeeString[2] = "Ikke nok ansatte, mangler " + (MIN_EMPLOYEES - employees.total) + " ansatt(e)\n";
    else employeeString[2] = "Det er " + employees.total + " ansatt(e) på dette skiftet\n";

    return employeeString;
}

// returnerer liste over ansatte som jobber enten en dag eller et skift
// første parameter er funksjonen som kjøres når antall ansatte er hentet, se testScript.html for eksempel på hvordan den brukes
// andre parameter er dato
// tredje er shift_id, utelates hvis man vil ha for hele dag
function checkQualified(callback, date, shift){

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

            callback(data);

        },
        error: function(data, textStatus, jqXHR){
            console.log("Error: " + textStatus);
        }
    });
}

function checkCollision(callback, date1, shift1, date2, shift2, role1, role2){

    var employees1;
    var employees2;

    checkQualified(function(data){
        employees1 = data;
        checkQualified(function(data){
            employees2 = data;

            var rolecount1 = countRoles(employees1);
            var rolecount2 = countRoles(employees2);

            console.log(rolecount1);
            console.log(rolecount2);

            var acceptable = true;

            if(role1 == 1){
                if(rolecount1.nurses <= MIN_NURSES){
                    acceptable = false;
                }
            }else if(role1 == 2){
                if(rolecount1.skilled <= MIN_SKILLED){
                    acceptable = false;
                }
            }

            if(role2 == 1){
                if(rolecount2.nurses <= MIN_NURSES){
                    acceptable = false;
                }
            }else if(role2 == 2){
                if(rolecount2.skilled <= MIN_SKILLED){
                    acceptable = false;
                }
            }

            if(role1 == role2) acceptable = true;

            callback(acceptable);

        }, date2, shift2);
    }, date1, shift1);
}