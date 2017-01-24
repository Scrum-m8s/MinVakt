/**
 * Created by mariyashchekanenko on 23/01/2017.
 */
$(document).ready(function() {

    function createTimelist(year, month){
        $.ajax({
            type: 'GET',
            contentType: 'application/json',
            url: 'api/function/updatedeviances/'+ year + "/"+month,

            success: function(data, textStatus, jqXHR){
                alert("Timelesten for "+ month + ". " + year + " ble registert");
            },
            error: function(data, textStatus, jqXHR){
                console.log("Error: " + textStatus);
            }
        });

    }
    $("#generateTimelistButton").click(function () {
        var year = $("#year").val();
        var month = $("#month").val();
        console.log("Year: "+year+" month: "+month);

        createTimelist(year, month);
        getTimelist();
    });

    $("#timelistTable").DataTable({
        data:[],
        columns: [
            { data: 'user_id' },
            { data: 'month' },
            { data: 'year' },
            { data: 'ordinary' },
            { data: 'overtime' },
            { data: 'absence' }
        ],
        rowCallback: function (row, data) {},
        filter: false,
        info: false,
        ordering: false,
        processing: true,
        searching: false,
        paging: false,
        retrieve: true
    });

    //function to list all timelists
    function getTimelist(){
        var table = $('#timelistTable').DataTable();
        $.ajax({
            type: 'GET',
            contentType: 'application/json',
            url: 'api/timelists',
            success: function(data, textStatus, jqXHR){
                console.log("Timelisten."+data);
                table.clear();
                table.rows.add(data);
                table.draw();
            },
            error: function(data, textStatus, jqXHR){
                console.log("Error: " + textStatus);
            }
        });

    }

})