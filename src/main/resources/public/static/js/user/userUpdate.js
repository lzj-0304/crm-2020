layui.config({
    base : "../static/js/"
}).extend({
    "formSelects":"formSelects-v4"
});
layui.use(['form','layer',"formSelects"],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        formSelects = layui.formSelects;

    var userId=$("input[name='id']").val();
    formSelects.config('selectId',{
        type:"post",
        searchUrl:ctx+"/role/allRoles?userId="+userId,
        keyName: 'roleName',            //自定义返回数据中name的key, 默认 name
        keyVal: 'id'            //自定义返回数据中value的key, 默认 value
    },true);

    //server
    /*formSelects.data('selectId', 'server', {
        url: ctx+"/role/allRoles"
    });*/
//se

    form.on("submit(addUser)",function(data){
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //弹出loading
        $.post(ctx+"/user/saveOrUpdate",data.field,function(res){
            if(res.code==200){
                setTimeout(function(){
                    top.layer.close(index);
                    top.layer.msg("操作成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                },500);
            }else{
                layer.msg(res.msg, {icon: 5});
            }
        });
        return false;
    });

})