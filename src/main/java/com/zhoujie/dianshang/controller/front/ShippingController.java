package com.zhoujie.dianshang.controller.front;

import com.zhoujie.dianshang.common.Const;
import com.zhoujie.dianshang.common.ResponseCode;
import com.zhoujie.dianshang.common.ServerResponse;
import com.zhoujie.dianshang.pojo.Shipping;
import com.zhoujie.dianshang.pojo.User;
import com.zhoujie.dianshang.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;
    /**
     * 添加收货信息/在登陆状态情况下
     * @param shipping
     * @return
     */
    @RequestMapping("/add.do")
    @ResponseBody
    public ServerResponse add(HttpSession session,Shipping shipping){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.add(user.getId(),shipping);
    }

    @RequestMapping("/del.do")
    @ResponseBody
    public ServerResponse del(HttpSession session,Integer shippingId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.del(user.getId(),shippingId);
    }
}
