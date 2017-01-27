$(document).ready(function() {

    var table = $('#swapTable').DataTable( {

        "select" : {
            style: 'single'
        },
        "responsive": true,
        "autoWidth": true,
        "paging": false,
        "info" : false,
        "columns" : [
            { data: 'user_id'},
            { data: 'category'},
            { data: 'my_date'},
            { data: 'shift_id'}
        ],
        ajax: {

            url: 'api/shift_lists/want_swap/true',
            "dataSrc": function (json) {
                doAjax(json, function(return_data) {
                    return return_data;
                });
            }
        }
    });

    table.draw();



    function getCurrentUserId(callback) {
        $.getJSON('api/users/current', function(json)  {
            callback(json.user_id);
        });
    }

    function getWantSwapUserId(callback) {
        $.getJSON('api/users/current', function(json)  {
            callback(json.user_id);
        });
    }

    function doAjax(json, callback) {
        var return_data = [];
        $.each(json, function(index, value) {
            getCategory(value.user_id, function(category) {
                if (value.shift_id === 1) {
                    value.shift_id = 'Nattevakt';
                } else if (value.shift_id === 2) {
                    value.shift_id = 'Dagvakt';
                } else if (value.shift_id === 3){
                    value.shift_id = 'Aftenvakt';
                } if (value.on_duty === true){
                    value.on_duty = 'Ja';
                } if (value.on_duty == false){
                    value.on_duty = 'Nei';
                }

                return_data.push({
                    'shift_id': value.shift_id,
                    'user_id': value.user_id,
                    'my_date': value.my_date,
                    'category': category
                });

                console.log("Liste inni callback: " + return_data);
            });
        });
        console.log("Liste utfor callback: " + return_data);
        callback(return_data);
    }

    function getSwapList(callback) {
        //callback(return_data);
    }

    function getCategory(user, callback) {
        $.getJSON('api/employees/' + user, function(json) {
            callback(json.category);
        });
    }
});


