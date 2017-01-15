/**
 * Created by Nina on 13.01.2017.
 */

function drawTable(data) {
    $("#qualifiedTable").empty();
    var head = $("<tr><th>Navn</th><th>Etternavn</th><th>Stilling</th></tr>");
    $("#qualifiedTable").append(head);
    drawRow(data[i]);
}

function formatCategory(category){
    var cat;
    if(category == 1){
        cat = "Sykepleier ";
    }else if(category == 2){
        cat = "Helsefagarbeider ";
    }else if(category == 3){
        cat = "Assistent ";
    }
    return cat;
}

$.ajax({
    url: 'api/employees',
    type: 'POST',
    data: JSON.stringify({
        firstname: $(this).data('firstname'),
        surname: $(this).data('surname'),
        category: $(this).data('category')
    }),
    contentType: 'application/json; charset=utf-8',
    dataType: 'json',
    success: function(result) {
        $.ajax({
            type: 'GET',
            dataType: 'json',
            url: 'api/employees',
            success: function(data){
                drawTable(data);
            }
        });
    }
});

function drawRow(rowData) {
    var row = $("<tr />");
    $("#qualifiedTable").append(row); //this will append tr element to table
        row.append($("<td>" + rowData.firstname + "</td>"));
        row.append($("<td>" + rowData.surname + "</td>"));
    row.append($("<td>" + formatCategory(rowData.category) + "</td>"));
}

$(document).ready(function() {
    //tegn tabellen
})
