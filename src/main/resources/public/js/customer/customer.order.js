layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //订单列表展示
    var  tableIns = table.render({
        elem: '#customerOrderList',
        url : ctx+'/order/list?cid='+$("input[name='id']").val(),
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "customerOrderListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'orderNo', title: '订单编号',align:"center"},
            {field: 'orderDate', title: '下单日期',align:"center"},
            {field: 'address', title: '收货地址',align:"center"},
            {field: 'state', title: '支付状态',align:"center",templet:function (d) {
                    if(d.state==1){
                        return "已支付;"
                    }else{
                        return "未支付";
                    }
                }},
            {field: 'createDate', title: '创建时间',align:"center"},
            {field: 'updateDate', title: '更新时间',align:"center"},
            {title: '操作',fixed:"right",align:"center", minWidth:150,templet:"#customerOrderListBar"}
        ]]
    });





    /**
     * 行监听
     */
    table.on("tool(customerOrders)", function(obj){
        var layEvent = obj.event;
        if(layEvent == "info") {
            openOrderDetailDialog(obj.data.id);
        }

    });
    // 打开添加计划项数据页面
    function openOrderDetailDialog(id){
        var url  =  ctx+"/customer/orderDetailPage?orderId="+id;
        layui.layer.open({
            title : "订单详情查看",
            type : 2,
            area:["700px","400px"],
            maxmin:true,
            content : url
        });
    }






    // 打开开发计划对话框
    function openCusDevPlanDialog(title,sid){
        layui.layer.open({
            title : title,
            type : 2,
            area:["700px","500px"],
            maxmin:true,
            content : ctx+"/cus_dev_plan/toCusDevPlanDataPage?sid="+sid
        });
    }


    function updateSaleChanceDevResult(sid,devResult) {
        layer.confirm('确定执行当前操作？', {icon: 3, title: "计划项维护"}, function (index) {
            $.post(ctx+"/sale_chance/updateSaleChanceDevResult",
                {
                    id:sid,
                    devResult:devResult
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
        })
    }




});
