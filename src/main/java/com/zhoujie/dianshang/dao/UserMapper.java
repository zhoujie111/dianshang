package com.zhoujie.dianshang.dao;

import com.zhoujie.dianshang.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByName(String username);

    User selectByEmail(String email);
}