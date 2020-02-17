layui.use(['form','layuimini'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;

    //监听提交
    form.on('submit(saveBtn)', function (data) {
        $.ajax({
            type:"post",
            url:ctx+"/user/update",
            data:data.field,
            dataType:"json",
            success:function (data) {
                layer.msg(data.msg);
            }
        });
        return false;
    });
});