layui.use(['form','layuimini','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        layuimini = layui.layuimini,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);


    //监听提交
    form.on('submit(saveBtn)', function (data) {
        console.log(data.field);

        /*var index = layer.alert(JSON.stringify(data.field), {
            title: '最终的提交信息'
        }, function () {
            layer.close(index);
            layuimini.closeCurrentTab();
        });*/
        $.ajax({
            type:"post",
            url:ctx+"/user/updatePassword",
            data:{
                oldPassword:data.field.old_password,
                newPassword:data.field.new_password,
                confirmPassword:data.field.again_password
            },
            dataType:"json",
            success:function (data) {
                if(data.code==200){
                    layer.msg("密码修改成功,系统将在3秒后自动退出...", function () {
                        $.removeCookie("userIdStr");
                        $.removeCookie("userName");
                        $.removeCookie("trueName");
                        setTimeout(function () {
                            window.parent.location.href=ctx+"/index";
                        },3000);
                    });
                }else{
                    layer.msg(data.msg);
                }
            }
        });

        return false;
    });

});