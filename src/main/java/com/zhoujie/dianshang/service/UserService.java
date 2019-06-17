package com.zhoujie.dianshang.service;


import com.zhoujie.dianshang.common.ServerResponse;
import com.zhoujie.dianshang.pojo.User;

public interface UserService {

    ServerResponse login(String username, String password);

    ServerResponse register(User user);

    ServerResponse checkValid(String str, String type);

    ServerResponse selectQuestion(String username);
}
