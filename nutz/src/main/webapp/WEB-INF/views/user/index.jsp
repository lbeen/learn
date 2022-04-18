<html>
<body>
<h2>Hello World!</h2>
<button id="send">send</button>
</body>
</html>
<script src="js/common/jquery-3.3.1.js"></script>
<script>
    $(function () {
        var WS_URL = window.location.host + "/nutz/websocket";
        var ws = new WebSocket("ws://" + WS_URL);
        ws.onopen = function(event) {
            console.log("websocket onopen ...");
            // 加入home房间
            ws.send(JSON.stringify({room:'home',"action":"join"}));
        };
        ws.onmessage = function(event) {
            console.log("websocket onmessage", event.data);
            var re = JSON.parse(event.data);
            if (re.action == "notify") {
                // 弹个浏览器通知
            } else if (re.action == "msg") {
                // 插入到聊天记录中
            }
        };

        $('#send').on("click", function () {
           ws.send('{"title":"test"}')
        });

    });
    
    
    
</script>
