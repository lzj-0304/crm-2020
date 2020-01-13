layui.use(['table','layer',"form"],function(){
       var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table,
        form = layui.form;

    //用户列表展示
    var  tableIns = table.render({
        elem: '#userList',
        url : ctx+'/user/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 20,
        id : "userListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: "id", title:'编号',fixed:"true", width:80},
            {field: 'userName', title: '用户名', minWidth:50, align:"center"},
            {field: 'email', title: '用户邮箱', minWidth:100, align:'center',templet:function(d){
                    return '<a class="layui-blue" href="mailto:'+d.email+'">'+d.email+'</a>';
                }},
            {field: 'trueName', title: '真实姓名', align:'center'},
            {field: 'isValid', title: '用户状态',  align:'center',templet:function(d){
                    return d.isValid == "1" ? "启用" : "禁用";
                }},
            {field: 'createDate', title: '创建时间', align:'center',minWidth:150},
            {field: 'updateDate', title: '更新时间', align:'center',minWidth:150},
            {title: '操作', minWidth:150, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });

    // 多条件搜索
    $(".search_btn").on("click",function(){
        table.reload("userListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: $("input[name='userName']").val(),  //用户名
                email: $("input[name='email']").val(),  //邮箱
                phone: $("input[name='phone']").val()  //手机号
            }
        })
    });


    //打开添加用户页面
    $(".addNews_btn").click(function(){
        updateUser();
    });

    // 批量删除操作
    /*$(".delAll_btn").click(function () {
        var checkStatus = table.checkStatus('userListTable');
        if(checkStatus.data.length==0){
            layer.msg("请选择待删除用户记录",{icon:5});
        }else{
            // 获取选中数据id
            var ids=[];
            var data = checkStatus.data;
            for(var i=0;i<data.length;i++){
               ids[i]=data[i].id;
            }
            console.log(ids);
            layer.confirm('确定删除选中的用户?', {icon: 3, title: '用户管理'}, function (index) {
                $.ajax({
                    type:"post",
                    url:ctx+"/user/del",
                    data:{
                       ids:ids
                    },
                    dataType:"json",
                    success:function (data) {
                        tableIns.reload();
                        layer.close(index);
                    }
                })
            })
        }

    });*/


    /**
     * 行监听
     */
    table.on('tool(users)', function(obj){
        var layEvent = obj.event;
        if(layEvent === 'edit') {
            updateUser(obj.data.id);
        }else if(layEvent === 'del') {
            layer.confirm('确定删除当前用户？', {icon: 3, title: '用户管理'}, function (index) {
                $.get(ctx+"/user/del",{userId:obj.data.id},function (data) {
                        if(data.code==200){
                            layer.msg("操作成功！");
                            tableIns.reload();
                        }else{
                            layer.msg(data.msg, {icon: 5});
                        }
                });
            })
        }
    });


    // 打开添加用户页面
    function updateUser(uid){
        var url  =  ctx+"/user/userUpdate";
        if(uid){
            url = url+"?uid="+uid;
        }
        layui.layer.open({
            title : "用户管理-用户信息更新",
            type : 2,
            area:["750px","500px"],
            maxmin:true,
            content : url
        });
    }


});
