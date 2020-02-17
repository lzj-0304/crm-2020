package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import com.xxxx.crm.model.ResultInfo;
import com.xxxx.crm.model.UserModel;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;



    @RequestMapping("login")
    @ResponseBody
    public ResultInfo login(String userName, String userPwd) {
        UserModel userModel = userService.login(userName, userPwd);
        return success("用户登录成功",userModel);
    }


    @RequestMapping("exit")
    @ResponseBody
    public ResultInfo exit() {
        return success("用户退出成功");
    }


    @RequestMapping("toSettingPage")
    public String toSettingPage(HttpServletRequest request){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        request.setAttribute("user",userService.selectByPrimaryKey(userId));
        return "user/setting";
    }

    @RequestMapping("toPasswordPage")
    public String toPasswordPage(){
        return "user/password";
    }


    @RequestMapping("index")
    public String index(){
        return "user/user";
    }


    @RequestMapping("updatePassword")
    @ResponseBody
    public ResultInfo updatePassword(HttpServletRequest request, String oldPassword, String newPassword, String confirmPassword) {
        userService.updateUserPassword(LoginUserUtil.releaseUserIdFromCookie(request), oldPassword, newPassword, confirmPassword);
        return success("密码更新成功");
    }


    @RequestMapping("addOrUpdateUserPage")
    public String addUserPage(Integer id, Model model){
        if(null !=id){
            model.addAttribute("user",userService.selectByPrimaryKey(id));
        }
        return "user/add_update";
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> userList(UserQuery userQuery){
       return userService.userList(userQuery);
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveUser(User user){
        userService.saveUser(user);
        return success("用户记录添加成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateUser(User user){
        userService.updateUser(user);
        return success("用户记录更新成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(@RequestParam(name = "id") Integer userId){
        userService.deleteUser(userId);
        return success("用户记录删除成功");
    }

    @RequestMapping("deleteBatch")
    @ResponseBody
    public ResultInfo deleteBatch(Integer[] ids){
        userService.deleteBatch(ids);
        return success("用户记录删除成功");
    }


}
