/**
 * Created by KristofferLaptop on 17-Jan-17.
 */
function openCloseNav() {
    var sidebar = $("#sidebar-wrapper-id");

    if(sidebar.width()=="0"){
        $("#menu-icon-id").html('close');
        sidebar.css('width', '300');
    }else {
        $("#menu-icon-id").html('menu');
        sidebar.css('width', '0');
    }
}

$(document).ready(function(){
    if(screen.height > "750"){
        $("#menu-icon-id").html('menu');
        $("#sidebar-wrapper-id").css('width', '0');
    }
});