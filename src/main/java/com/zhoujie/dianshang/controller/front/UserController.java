package com.zhoujie.dianshang.controller.front;

import com.zhoujie.dianshang.common.Const;
import com.zhoujie.dianshang.common.ServerResponse;
import com.zhoujie.dianshang.pojo.User;
import com.zhoujie.dianshang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录接口
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("/login.do")
    @ResponseBody
    public ServerResponse<User> login(String username, String password,HttpSession session){
        // todo service <- dao

        ServerResponse response = userService.login(username,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 用户登出
     * @param session
     * @return
     */
    @RequestMapping("/logout.do")
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        if(session!=null){
            session.removeAttribute(Const.CURRENT_USER);
            return ServerResponse.createBySuccess("退出成功！");
        }
        return ServerResponse.createByError("服务端异常！");
    }

    /**
     * 用户注册
     * params username password email phone question answer
     * @return ServerResponse
     */
    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse register(User user){
        return userService.register(user);
    }

    /**
     * 检查用户名是否有效
     * @return
     */
    @RequestMapping("/check_valid.do")
    @ResponseBody
    public ServerResponse check_valid(String str,String type){
        return userService.checkValid(str,type);
    }

    /**
     * 获取登录状态的用户信息
     * @param session
     * @return
     */
    @RequestMapping("/get_user_info.do")
    @ResponseBody
    public ServerResponse getUserInfo(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user!=null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByError("用户未登录");
    }

    @RequestMapping("/forget_get_question.do")
    @ResponseBody
    public ServerResponse forgetGetQuestion(String username){
        return userService.selectQuestion(username);
    }


    @RequestMapping("/forget_check_answer.do")
    @ResponseBody
    public ServerResponse forgetCheckAnswer(String username,String question,String answer){
         return userService.checkAnswer(username,question,answer);

    }


}
