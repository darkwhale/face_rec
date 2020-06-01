
function ajax_person_add() {
    var person_id = $("#person_id").val();
    var person_name = $("#person_name").val();
    var api = getCookie("api");

    $.ajax({
        type: "post",
        url: "face/person/add",
        contentType: "application/json;charset=utf-8",
        async: false,
        data: JSON.stringify({"api": api, "personId": person_id, "personName": person_name}),
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {

                    window.location=get_main_url() + "database.html";
                    // window.location="http://127.0.0.1/database.html"
                }else if (message.code === 6){
                    alert("person已存在");
                }else{
                    alert("数据错误")
                }
            }else {
                alert("数据错误")
            }
        },
        error: function(message){
            console.log(message);
            alert("访问错误");
        }
    });
}
