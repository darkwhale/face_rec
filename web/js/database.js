$(document).ready(function(){
    var api = getCookie("api");

    if (api === "") {
        $("#person-add").hide();
        $("#face-add").hide();
    }

    if (api !== "") {
        $.ajax({
            type: "post",
            url: "face/user/listForImage",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({"api": api}),
            dataType: "json",
            success: function (message) {
                if (message) {
                    if (message.code === 0) {
                        var face_num = 0;

                        var image_content = document.getElementById("image_content");
                        for (var key in message.data) {

                            var sub_content = document.createElement("strong");
                            sub_content.style.fontSize = "18px";
                            sub_content.innerText = "person_id: " + key;
                            image_content.appendChild(sub_content);

                            var sub_content_delete = document.createElement("button");
                            sub_content_delete.type = "button";
                            sub_content_delete.textContent = "删除person";
                            sub_content_delete.setAttribute("class", "btn btn-default pull-right");
                            sub_content_delete.setAttribute("person_id", key);
                            sub_content_delete.onclick = function () {
                                $.ajax({
                                    type: "post",
                                    url: "face/person/delete",
                                    contentType: "application/json;charset=utf-8",
                                    data: JSON.stringify({"api": api, "personId": this.getAttribute("person_id")}),
                                    dataType: "json",
                                    success: function (message) {
                                        console.log(message);
                                        if (message) {
                                            if (message.code === 0) {
                                                // 刷新
                                                location.reload();
                                            } else if (message.code === 1) {
                                                alert("参数错误");
                                            } else {
                                                alert("数据错误")
                                            }
                                        } else {
                                            alert("数据错误")
                                        }
                                    },
                                    error: function (message) {
                                        alert("访问错误");
                                    }
                                });
                            };
                            image_content.appendChild(sub_content_delete);


                            var sub_image_content = document.createElement("div");
                            image_content.appendChild(sub_image_content);

                            var hr_div = document.createElement("hr");
                            image_content.appendChild(hr_div);


                            message.data[key].forEach(function (item) {
                                face_num += 1;

                                var sub_image_href = document.createElement("a");
                                sub_image_href.setAttribute("person_id", key);
                                sub_image_href.setAttribute("face_id", item.id);
                                sub_image_content.appendChild(sub_image_href);

                                var sub_image = document.createElement("img");
                                sub_image.id = item.id;
                                sub_image.src = item.imageName;
                                sub_image.style.height = "80px";
                                sub_image.style.width = "80px";
                                sub_image.style.marginRight = "5px";
                                sub_image.style.marginBottom = "5px";

                                sub_image_href.appendChild(sub_image);

                                sub_image_href.onclick = function () {
                                    var delete_confirm = window.confirm("确定要删除人脸？");
                                    if (delete_confirm === true) {

                                        $.ajax({
                                            type: "post",
                                            url: "face/facial/delete",
                                            contentType: "application/json;charset=utf-8",
                                            data: JSON.stringify({
                                                "api": api,
                                                "personId": this.getAttribute("person_id"),
                                                "faceId": this.getAttribute("face_id")
                                            }),
                                            dataType: "json",
                                            success: function (message) {
                                                console.log(message);
                                                if (message) {
                                                    if (message.code === 0) {
                                                        // 刷新
                                                        location.reload();
                                                    } else if (message.code === 1) {
                                                        alert("参数错误");
                                                    } else {
                                                        alert("数据错误")
                                                    }
                                                } else {
                                                    alert("数据错误")
                                                }
                                            },
                                            error: function (message) {
                                                alert("访问错误");
                                            }
                                        });
                                    }
                                }

                            });



                        }
                        $("#user_info").text("你一共注册了" + Object.keys(message.data).length + "个person对象, " +
                            face_num + "张人脸");


                    } else if (message.code === 1) {
                        alert("用户不存在");
                    } else {
                        alert("数据错误")
                    }
                } else {
                    alert("数据错误")
                }
            },
            error: function (message) {
                alert("访问错误");
            }
        });
    }
});

