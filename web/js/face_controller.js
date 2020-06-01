$(document).ready(function(){
    var username = getCookie("username");
    if (username === "") {
        $("#username").hide();
        $("#login_symbol").text("登录");
        $("#register_symbol").text("注册");
        $("#logout_symbol").hide();
    }else {
        $("#username").text(username);
        $("#login_symbol").hide();
        $("#register_symbol").hide();
        $("#logout_symbol").text("退出");
    }

});

function ajax_register() {
    var user_id = $("#user_id").val();
    var username = $("#user_name").val();
    var password = $("#password").val();

    $.ajax({
        type: "post",
        url: "face/user/register",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({"id": user_id, "username": username, "password": password}),
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    // 写cookie
                    document.cookie = 'userId=' + message.data.id;
                    document.cookie = 'username=' + message.data.username;
                    document.cookie = 'api=' + message.data.api;

                    // window.location="http://127.0.0.1"
                    window.location=get_main_url();

                }else if (message.code === 1){
                    alert("用户已存在");
                }else{
                    alert("数据错误")
                }
            }else {
                alert("数据错误")
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });
}

function ajax_login() {
    var user_id = $("#user_id").val();
    var password = $("#password").val();

    $.ajax({
        type: "post",
        url: "face/user/login",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({"id": user_id, "password": password}),
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    // 写cookie
                    document.cookie = 'userId=' + message.data.id;
                    document.cookie = 'username=' + message.data.username;
                    document.cookie = 'api=' + message.data.api;

                    // window.location="http://127.0.0.1";
                    window.location=get_main_url();
                }else{
                    alert("用户名或密码错误");
                }
            }else {
                alert("数据错误")
            }
        },
        error: function(message){
            alert("访问错误");
        }
    });
}


function ajax_logout() {
    var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
    if(keys) {
        for(var i = keys.length; i--;) {
            document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString();
        }
    }
    // window.location="http://127.0.0.1";
    window.location=get_main_url();
}

function getCookie(name)
{
    var start;
    var end;
    if (document.cookie.length > 0) {
        start = document.cookie.indexOf(name + "=");
        if (start !== -1) {
            start = start + name.length + 1;
            end = document.cookie.indexOf(";", start);
            if (end === -1){
                end = document.cookie.length;
            }
            return document.cookie.substring(start, end);
        }
    }
    return ""
}

