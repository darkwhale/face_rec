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

    // document.getElementById("result_list").innerHTML = "";

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
                    $("#result_table").show();

                    var result_table = document.getElementById("result_tbody");
                    result_table.innerText = "";
                    message.data.forEach(function (item) {
                        var row = document.createElement('tr');
                        var rectangle_cell = document.createElement('td');
                        rectangle_cell.style.fontSize = "16px";
                        rectangle_cell.innerHTML = "rectangle: (" +
                            item.rectangle.left + "\t" +
                            item.rectangle.right + "\t" +
                            item.rectangle.top + "\t" +
                            item.rectangle.bottom + ")";
                        row.appendChild(rectangle_cell);

                        var person_cell = document.createElement("td");
                        person_cell.innerHTML = (item.personId == null? "未匹配": item.personId);
                        person_cell.style.fontSize = "16px";
                        row.appendChild(person_cell);

                        result_table.appendChild(row);
                    });

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