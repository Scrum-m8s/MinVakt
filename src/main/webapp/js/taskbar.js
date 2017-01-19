/**
 * Created by KristofferLaptop on 17-Jan-17.
 */

var sidebar;

function openCloseNav() {
    sidebar = $("#sidebar-wrapper-id");
    if(sidebar.width()=="0"){
        $("#menu-icon-id").html('close');
        sidebar.css('width', '300');
        alignCards();
    }else {
        $("#menu-icon-id").html('menu');
        sidebar.css('width', '0');
        alignCards();
    }
}

$(document).ready(function(){
    sidebar = $("#sidebar-wrapper-id");
    if(screen.height > "750"){
        $("#menu-icon-id").html('close');
        sidebar.css('width', '300');
    }
    alignCards();
});

function alignCards(){
    sidebar = $("#sidebar-wrapper-id");
    var leftPush = sidebar.width()+8;
    console.log(leftPush);
    $("#content-wrapper-id").css('padding-left', leftPush);

}
