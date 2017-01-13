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

function formatCategory(user_id, category){
    var cat;
    if(category == 1){
        cat = "Sykepleier ";
    }else if(type == 2){
        cat = "Helsefagarbeider ";
    }else if(type == 3){
        cat = "Assistent ";
    }
    if(fish == 1){
        name = name + "Salmon";
    }else if(fish == 2){
        name = name + "Tuna";
    }else if (fish == 3){
        name = name + "Shrimp";
    }
    return name;
}

function drawRow(rowData) {
    var row = $("<tr />");
    $("#qualifiedTable").append(row); //this will append tr element to table
    if(rowData.category == 1)
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
            { data: 'firstname' },
            { data: 'surname'},
            { data: 'role'}
        ]
    });
})
