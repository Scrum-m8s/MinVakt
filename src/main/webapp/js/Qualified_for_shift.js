/**
 * Created by Nina on 13.01.2017.
 */

//employee category
function drawTable(data) {
    $("#qualifiedTable").empty();
    var head = $("<tr><th>Navn</th><th>Stilling</th><th>Mer data</th></tr>");
    $("#sushiTable").append(head);
    drawRow(data[i]);
}

function drawRow(rowData) {
    var row = $("<tr />");
    $("#qualifiedTable").append(row); //this will append tr element to table
    if(rowData.type !== 99){
        row.append($("<td id='sushi11'>" + formatSushi(rowData.type, rowData.fish) + "</td>"));
        row.append($("<td>" + rowData.price + "</td>"));
    }
}

$(document).ready(function() {
    $('#viewport').DataTable( {

        ajax: {
            url: 'api/users',
            dataSrc: ''
        },
        columns: [
            { data: '' },
            { data: 'parkeringsplass'},
            { data: 'batteri'},
            { data: 'ledig'},
            { data: 'tid'}
        ]
    });
}
