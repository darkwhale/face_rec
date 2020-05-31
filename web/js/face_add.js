
function image_change() {
    var file = document.getElementById("input_image").files[0];
    var reader = new FileReader();
    reader.onloadend = function () {
        $("#image_window").attr("style", "display:inline-block");
        $("#image_window").attr("src", reader.result);
    };
    if (file) {
        reader.readAsDataURL(file);
    }
}


function ajax_face_add() {
    var person_id = $("#person_id").val();
    var image = document.getElementById("image_window").src;
    var api = getCookie("api");

    $.ajax({
        type: "post",
        url: "face/facial/add",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({"api": api, "personId": person_id, "image": image}),
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    console.log(message);
                    window.location="http://127.0.0.1/database.html"
                }else if (message.code === 9){
                    alert("person不存在");
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
