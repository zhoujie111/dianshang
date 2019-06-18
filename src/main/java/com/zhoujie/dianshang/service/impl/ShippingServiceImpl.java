package com.zhoujie.dianshang.service.impl;

import com.google.common.collect.Maps;
import com.zhoujie.dianshang.common.ServerResponse;
import com.zhoujie.dianshang.dao.ShippingMapper;
import com.zhoujie.dianshang.pojo.Shipping;
import com.zhoujie.dianshang.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int effectNum = shippingMapper.insert(shipping);
        if(effectNum>0){
            Map map = Maps.newHashMap();
            map.put("shippingId",shipping.getId());
            return ServerResponse.createBySuccess("新建地址成功",map);
        }
        return ServerResponse.createByError("新建收货地址失败");
    }

    @Override
    public ServerResponse del(Integer userId, Integer shippingId) {
        int result = shippingMapper.deleteByUserIdAndShippingId(userId,shippingId);
        if(result>0){
            return ServerResponse.createBySuccess("删除收货地址成功");
        }
        return ServerResponse.createByError("删除收货地址失");
    }
}
