package com.zhoujie.dianshang.service.impl;

import com.zhoujie.dianshang.common.Const;
import com.zhoujie.dianshang.common.ServerResponse;
import com.zhoujie.dianshang.common.TokenCache;
import com.zhoujie.dianshang.dao.UserMapper;
import com.zhoujie.dianshang.pojo.User;
import com.zhoujie.dianshang.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse login(String username, String password) {
        // todo 判断的逻辑
        //查询数据库中是否有username这个数据
       User user =  userMapper.selectByName(username);
       if(user == null){
           return ServerResponse.createByError("用户名不存在！");
       }
        //如果有，验证密码是否正确
       if(!user.getPassword().equals(password)){
           return ServerResponse.createByError("密码错误！");
       }
       return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse register(User user) {
        //名字不能相同
        User u = userMapper.selectByName(user.getUsername());
        if(u!=null){
            return ServerResponse.createByError("用户名已经存在！");
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        int effectNum = userMapper.insert(user);
        if(effectNum<0){
            return ServerResponse.createByError("插入数据失败！");
        }
        return ServerResponse.createBySuccess("插入成功！");
    }

    @Override
    public ServerResponse checkValid(String str, String type) {
        if(StringUtils.isNotEmpty(type)){
            if(type.equals(Const.USERNAME)){
                User u = userMapper.selectByName(str);
                if(u!=null){
                    return ServerResponse.createByError("用户名已存在！");
                }
            }
            if(type.equals(Const.EMAIL)){
                User u = userMapper.selectByEmail(str);
                if(u!=null){
                    return ServerResponse.createByError("邮箱已存在！");
                }
            }
        }else{
            return ServerResponse.createByError("参数错误！");
        }
        return ServerResponse.createBySuccess("校验成功！");
    }

    @Override
    public ServerResponse selectQuestion(String username) {
        //判断username是否有效
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            return ServerResponse.createByError("用户不存在！");
        }
        String question = userMapper.selectByName(username).getQuestion();
        if(StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByError("该用户未设置找回密码问题");
    }

    @Override
    public ServerResponse<String> checkAnswer(String username,String question,String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if(resultCount>0){
            //问题、问题答案是这个用户的
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey("token_"+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByError("问题的答案错误！");
    }
}
