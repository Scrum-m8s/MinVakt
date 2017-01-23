/**
 * Created by espen on 23.01.2017.
 */

function getMenu(){
    if(sessionStorage.getItem("role") == "admin"){
        // TODO: koble til ui, hent meny for admin
    }else{
        // TODO: koble til ui, hent meny for bruker
    }
}

$(document).ready(function(){
    if(sessionStorage.getItem("role") == null){
        $.get("/api/users/current", function(user){
            if(user.role == 0){
                sessionStorage.setItem("role", "admin");
            }else{
                sessionStorage.setItem("role", "user");
            }
            getMenu();
        });
    }else{
        getMenu();
    }
});