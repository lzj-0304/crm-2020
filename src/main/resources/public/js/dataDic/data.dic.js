layui.use(['table','layer',"form"],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    //用户列表展示
    var  tableIns = table.render({
        elem: '#dataDicList',
        url : ctx+'/data_dic/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "dataDicListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: "id", title:'编号',fixed:"true", width:80},
            {field: 'dataDicName', title: '字典名', minWidth:50, align:"center"},
            {field: 'dataDicValue', title: '字典值', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center',minWidth:150},
            {field: 'updateDate', title: '更新时间', align:'center',minWidth:150},
            {title: '操作', minWidth:150, templet:'#dataDicListBar',fixed:"right",align:"center"}
        ]]
    });


    // 多条件搜索
    $(".search_btn").on("click",function(){
        table.reload("dataDicListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                dicName: $("input[name='dicName']").val()
            }
        })
    });

    //头工具栏事件
    table.on('toolbar(dataDics)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case "add":
                openAddOrUpdateDataDicDialog();
                break;
        };
    });


    /**
     * 行监听
     */
    table.on("tool(dataDics)", function(obj){
        var layEvent = obj.event;
        if(layEvent === "edit") {
            openAddOrUpdateDataDicDialog(obj.data.id);
        }else if(layEvent === "del") {
            layer.confirm('确定删除当前字典记录？', {icon: 3, title: "字典管理"}, function (index) {
                $.post(ctx+"/data_dic/delete",{id:obj.data.id},function (data) {
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
    function openAddOrUpdateDataDicDialog(id){
        var url  =  ctx+"/data_dic/addOrUpdateDataDicPage";
        var title="字典管理-字典添加";
        if(id){
            url = url+"?id="+id;
            title="字典管理-字典更新";
        }
        layui.layer.open({
            title : title,
            type : 2,
            area:["700px","420px"],
            maxmin:true,
            content : url
        });
    }




});
