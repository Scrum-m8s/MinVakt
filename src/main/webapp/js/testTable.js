/**
 * Created by asdfLaptop on 12.01.2017.
 */

// The root URL for the RESTful services
var rootURL = "http://localhost:8080/api/employees/";

function ChangeColor(tableRow, highLight)
{
    if (highLight)
    {
        tableRow.style.backgroundColor = '#89ae37';
    }
    else if (this.class == alt)
    {
        tableRow.style.backgroundColor = '#EAF2D3';
    }
    else
    {
        tableRow.style.backgroundColor = '#A7C942';
    }
}

function DoNav(theUrl)
{
    document.location.href = theUrl;
}

$(document).ready(function(){

    $.get(rootURL,function(data){
        var container = $("#testTable tbody");
        var html = container.html("");

        for(var i=0;i<data.length;i++) {
            container.append(

                '<tr id="rowId' + (i+1) + '" class="clickable-row"><td><input type="checkbox" id="checkboxId' + (i+1) + '"></td><td>' + (i+1) + '</td>' + '<td>' + data[i].firstname + '</td>' + '<td>' + data[i].surname + '</td>' + '<td>' + data[i].userId + '</td></tr>'
            );
        console.log(data[i].userId);

        }
        $(".clickable-row").click(function() {
            if($(this).hasClass('info')){
                $(this).removeClass('info');
            }else {
                $(this).addClass('info').siblings().removeClass('info');
                console.log(this.getElementsByTagName("td")[4].textContent);
            }
        });
    });


    /*
    $("#addKunde").click(function () {
        $.ajax({
            url: 'app/kunder/',
            type: 'POST',
            data: JSON.stringify({
                id: $("#newId").val(),
                name: $("#newName").val(),
            }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function (result) {
                console.log("User added!");
            }
        });
    });

    $.getJSON('app/rooms/1/sounds/1', function(result) {
        $("#progress-sound-1").attr("aria-valuenow", result.volume).css("width", (result.volume*2));
    });
    */

    //Filter functions
    $('.filterable .btn-filter').click(function(){
        var $panel = $(this).parents('.filterable'),
            $filters = $panel.find('.filters input'),
            $tbody = $panel.find('.table tbody');
        if ($filters.prop('disabled') == true) {
            $filters.prop('disabled', false);
            $filters.first().focus();
        } else {
            $filters.val('').prop('disabled', true);
            $tbody.find('.no-result').remove();
            $tbody.find('tr').show();
        }
    });

    $('.filterable .filters input').keyup(function(e){
        /* Ignore tab key */
        var code = e.keyCode || e.which;
        if (code == '9') return;
        /* Useful DOM data and selectors */
        var $input = $(this),
            inputContent = $input.val().toLowerCase(),
            $panel = $input.parents('.filterable'),
            column = $panel.find('.filters th').index($input.parents('th')),
            $table = $panel.find('.table'),
            $rows = $table.find('tbody tr');
        /* Dirtiest filter function ever ;) */
        var $filteredRows = $rows.filter(function(){
            var value = $(this).find('td').eq(column).text().toLowerCase();
            return value.indexOf(inputContent) === -1;
        });
        /* Clean previous no-result if exist */
        $table.find('tbody .no-result').remove();
        /* Show all rows, hide filtered ones (never do that outside of a demo ! xD) */
        $rows.show();
        $filteredRows.hide();
        /* Prepend no-result row if all rows are filtered */
        if ($filteredRows.length === $rows.length) {
            $table.find('tbody').prepend($('<tr class="no-result text-center"><td colspan="'+ $table.find('.filters th').length +'">No result found</td></tr>'));
        }
    });

});