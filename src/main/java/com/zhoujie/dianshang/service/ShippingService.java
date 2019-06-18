package com.zhoujie.dianshang.service;

import com.zhoujie.dianshang.common.ServerResponse;
import com.zhoujie.dianshang.pojo.Shipping;

public interface ShippingService {

    ServerResponse add(Integer userId, Shipping shipping);

    ServerResponse del(Integer userId, Integer shippingId);
}
