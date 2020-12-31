<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <!--如果一个技术基于另一个技术，则这个技术必须在其后面-->
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#bth1").click(function () {
                $.ajax({
                    "url": "send/array/one.html",   // 请求目标资源的地址
                    "type": "post",             // 请求方式
                    "data":  {
                        "array": [5,8,12]
                    },                          // 要发送的请求参数
                    "dataType": "text",         // 如何对待服务器端返回的数据
                    "success": function (response) { // 服务器端成功请求后调用的回调函数，response是响应体
                        alert(response);
                    },
                    "error": function (response) {   // 服务器端请求失败后调用的回调函数
                        alert(response);
                    }
                });
            });

            $("#bth2").click(function () {
                $.ajax({
                    "url": "send/array/two.html",   // 请求目标资源的地址
                    "type": "post",             // 请求方式
                    "data":  {
                        "array[0]": 5,
                        "array[1]": 8,
                        "array[2]": 12
                    },                          // 要发送的请求参数
                    "dataType": "text",         // 如何对待服务器端返回的数据
                    "success": function (response) { // 服务器端成功请求后调用的回调函数，response是响应体
                        alert(response);
                    },
                    "error": function (response) {   // 服务器端请求失败后调用的回调函数
                        alert(response);
                    }
                });
            });

            $("#bth3").click(function () {
                // 准备好要发送到服务器的数组
                var array = [5,8,12];
                console.log(array.length);
                // 将JSON数据转换为JSON字符串
                var requestBody = JSON.stringify(array);
                // "['5','8','12']"
                console.log(requestBody.length);
                $.ajax({
                    "url": "send/array/three.html",   // 请求目标资源的地址
                    "type": "post",             // 请求方式
                    "data":  requestBody,       // 要发送的请求体
                    "contentType": "application/json;charset=UTF-8",//请求体的内容类型，告诉服务端本次请求的请求体是JSON数据
                    "dataType": "text",         // 如何对待服务器端返回的数据
                    "success": function (response) { // 服务器端成功请求后调用的回调函数，response是响应体
                        alert(response);
                    },
                    "error": function (response) {   // 服务器端请求失败后调用的回调函数
                        alert(response);
                    }
                });
            });

            $("#bth4").click(function () {
                // 准备好要发送到服务器的数组
                var student = {
                    "stuId": 5,
                    "stuName": "tom",
                    "address": {
                        "province":"广东",
                        "city":"深圳",
                        "street":"后瑞"
                    },
                    "subjectList": [
                        {
                            "subjectName": "JavaSE",
                            "subjectScore": 100
                        }, {
                            "subjectName": "SSM",
                            "subjectScore": 99
                        }
                    ],
                    "map": {
                        "k1": "v1",
                        "k2": "v2"
                    }
                };
                // 将JSON数据转换为JSON字符串
                var requestBody = JSON.stringify(student);
                $.ajax({
                    "url": "send/complex/object.json",   // 请求目标资源的地址
                    "type": "post",             // 请求方式
                    "data":  requestBody,       // 要发送的请求体
                    "contentType": "application/json;charset=UTF-8",//请求体的内容类型，告诉服务端本次请求的请求体是JSON数据
                    "dataType": "json",         // 如何对待服务器端返回的数据
                    "success": function (response) { // 服务器端成功请求后调用的回调函数，response是响应体
                        console.log(response);
                    },
                    "error": function (response) {   // 服务器端请求失败后调用的回调函数
                        console.log(response);
                    }
                });
            });

            $("#bth5").click(function () {
                layer.msg("Layer的弹框");
            });
        });
    </script>
</head>
<body>
    <a href="test/ssm.html">SSM整合环境测试</a>

    <br/>
    <br/>
    <br/>
    <button id="bth1">Test RequestBody Send [5,8,12] One</button>
    <br/>
    <br/>
    <br/>
    <button id="bth2">Test RequestBody Send [5,8,12] Two</button>
    <br/>
    <br/>
    <br/>
    <button id="bth3">Test RequestBody Send [5,8,12] Three</button>
    <br/>
    <br/>
    <br/>
    <button id="bth4">Test RequestBody Send Complex Object</button>
    <br/>
    <br/>
    <br/>
    <button id="bth5">点我弹框</button>
</body>
</html>
