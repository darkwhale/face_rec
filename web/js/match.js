$(document).ready(function(){

    var api = getCookie("api");

    if (api === "") {
        $("#image_show_bar").hide();
    }
});


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



function ajax_face_match() {
    var api = getCookie("api");
    var image = document.getElementById("image_window").src;

    document.getElementById("result_list").innerHTML = "";

    $.ajax({
        type: "post",
        url: "face/facial/multi_match",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({"api": api, "image": image}),
        dataType: "json",
        success: function(message){
            if (message){
                if (message.code === 0) {
                    $("#result_label").show();
                    var result_content = document.getElementById("result_list");
                    message.data.forEach(function (item) {
                        var sub_result = document.createElement("div");
                        sub_result.style.fontSize = "18px";

                        sub_result.textContent = "rectangle: (" +
                            item.rectangle.left + "\t" +
                            item.rectangle.right + "\t" +
                            item.rectangle.top + "\t" +
                            item.rectangle.bottom + ")\t"
                            + (item.personId == null? "未匹配": item.personId);

                        result_content.appendChild(sub_result);
                    })

                    // window.location="http://127.0.0.1";
                }else{
                    alert("未检测到人脸");
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