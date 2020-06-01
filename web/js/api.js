$(document).ready(function() {
    var api = getCookie("api");

    if (api !== "") {
        $("#api_show_content").show();
        $("#api_show_content").text("你的api是:" + api);
        $("#api_show_hr").show();
    }

    $(".url_prefix").text(get_main_url());
});