layui.use(['element', 'layer', 'layuimini'], function () {
    var $ = layui.jquery,
        layer = layui.layer;
    layuimini.init(ctx+'/static/api/init.json');
    $('.login-out').on("click", function () {
        layer.msg('退出登录成功', function () {
            window.location = 'index';
        });
    });
});