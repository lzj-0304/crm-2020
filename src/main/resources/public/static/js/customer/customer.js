layui.use(['table','layer',"form"],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table,
        form = layui.form;

    //客户列表展示
    var  tableIns = table.render({
        elem: '#customerList',
        url : ctx+'/customer/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "customerListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'name', title: '客户名',align:"center"},
            {field: 'fr', title: '法人',  align:'center'},
            {field: 'khno', title: '客户编号', align:'center'},
            {field: 'area', title: '地区', align:'center'},
            {field: 'cusManager', title: '客户经理',  align:'center'},
            {field: 'myd', title: '满意度', align:'center'},
            {field: 'level', title: '客户级别', align:'center'},
            {field: 'xyd', title: '信用度', align:'center'},
            {field: 'address', title: '详细地址', align:'center'},
            {field: 'postCode', title: '邮编', align:'center'},
            {field: 'phone', title: '电话', align:'center'},
            {field: 'webSite', title: '网站', align:'center'},
            {field: 'fax', title: '传真', align:'center'},
            {field: 'zczj', title: '注册资金', align:'center'},
            {field: 'yyzzzch', title: '营业执照', align:'center'},
            {field: 'khyh', title: '开户行', align:'center'},
            {field: 'khzh', title: '开户账号', align:'center'},
            {field: 'gsdjh', title: '国税', align:'center'},
            {field: 'dsdjh', title: '地税', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center'},
            {field: 'updateDate', title: '更新时间', align:'center'},
            {title: '操作', templet:'#customerListBar',fixed:"right",align:"center", minWidth:150}
        ]]
    });

    // 多条件搜索
    $(".search_btn").on("click",function(){
        table.reload("customerListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                cusName: $("input[name='name']").val(),  //客户名
                cusNo: $("input[name='khno']").val(),  //客户编号
                level: $("#level").val()  //客户等级
            }
        })
    });

    //头工具栏事件
    table.on('toolbar(customers)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case "add":
                openAddOrUpdateCustomerDialog();
                break;
            case "order":
                openOrderInfoDialog(checkStatus.data);
                break;
        };
    });


    /**
     * 行监听
     */
    table.on("tool(customers)", function(obj){
        var layEvent = obj.event;
        if(layEvent === "edit") {
            openAddOrUpdateCustomerDialog(obj.data.id);
        }else if(layEvent === "del") {
            layer.confirm('确定删除当前数据？', {icon: 3, title: "客户管理"}, function (index) {
                $.post(ctx+"/customer/delete",{id:obj.data.id},function (data) {
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


    // 打开添加客户数据页面
    function openAddOrUpdateCustomerDialog(id){
        var url  =  ctx+"/customer/addOrUpdateSaleChancePage";
        var title="客户管理-添加";
        if(id){
            url = url+"?id="+id;
            title="客户管理-更新";
        }
        layui.layer.open({
            title : title,
            type : 2,
            area:["700px","500px"],
            maxmin:true,
            content : url
        });
    }



    // 打开订单查看页面
    function openOrderInfoDialog(datas) {
        if(datas.length==0){
            layer.msg("请选择待查看订单对应客户!", {icon: 5});
            return;
        }
        if(datas.length>1){
            layer.msg("暂不支持批量查看!", {icon: 5});
            return;
        }
        var url  =  ctx+"/customer/orderInfoPage?cid="+datas[0].id;
        layui.layer.open({
            title : "客户管理-订单信息展示",
            type : 2,
            area:["700px","500px"],
            maxmin:true,
            content : url
        });
    }


});
