/**
 * Created by espen on 23.01.2017.
 */


function getRole(callback){
    if(sessionStorage.getItem("role") == null){
        $.get("/api/users/current", function(user){
            if(user.role == 0){
                sessionStorage.setItem("role", "admin");
            }else{
                sessionStorage.setItem("role", "user");
            }
            callback();
        });
    }else{
        callback();
    }
}