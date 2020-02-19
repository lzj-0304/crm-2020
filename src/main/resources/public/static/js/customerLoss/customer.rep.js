layui.use(['table','layer',"form"],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table,
        form = layui.form;
    //暂缓列表展示
    var  tableIns = table.render({
        elem: '#customerRepList',
        url : ctx+'/customer_rep/list?lossId='+$("input[name='id']").val(),
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "customerRepListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'measure', title: '暂缓措施',align:"center"},
            {field: 'createDate', title: '创建时间',align:"center"},
            {field: 'updateDate', title: '更新时间',align:"center"},
            {title: '操作',fixed:"right",align:"center", minWidth:150,templet:"#customerRepListBar"}
        ]]
    });



    //头工具栏事件
    table.on('toolbar(customerReps)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case "add":
                openAddOrUpdateCustomerRepDialog();
                break;
            case "confirm":
                updateCustomerLossState();
                break;
        };
    });


    // 客户确认流失
    function updateCustomerLossState() {
        layer.confirm('当前客户确认流失？', {icon: 3, title: "客户流失管理"}, function (c1) {
            layer.close(c1);
            layer.prompt({title: "请输入客户流失原因", formType: 2}, function(text, c2){
                layer.close(c2);
                $.post(ctx+"/customer_loss/updateCustomerLossState",
                    {
                        id:$("input[name='id']").val(),
                        reason:text
                    },function (data) {
                        if(data.code==200){
                            layer.msg("操作成功！");
                            layer.closeAll("iframe");
                            //刷新父页面
                            parent.location.reload();
                        }else{
                            layer.msg(data.msg, {icon: 5});
                        }
                    });
            });
        })
    }



    /**
     * 行监听
     */
    table.on("tool(customerReps)", function(obj){
        var layEvent = obj.event;
        if(layEvent === "edit") {
            openAddOrUpdateCustomerRepDialog(obj.data.id);
        }else if(layEvent === "del") {
            layer.confirm('确定删除当前暂缓记录？', {icon: 3, title: "暂缓管理"}, function (index) {
                $.post(ctx+"/customer_rep/delete",{id:obj.data.id},function (data) {
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
    // 打开添加计划项数据页面
    function openAddOrUpdateCustomerRepDialog(id){
        var url  =  ctx+"/customer_rep/addOrUpdateCustomerRepPage?lossId="+$("input[name='id']").val();
        var title="暂缓管理-添加暂缓措施";
        if(id){
            url = url+"&id="+id;
            title="暂缓管理-更新暂缓措施";
        }
        layui.layer.open({
            title : title,
            type : 2,
            area:["400px","260px"],
            maxmin:true,
            content : url
        });
    }



});
