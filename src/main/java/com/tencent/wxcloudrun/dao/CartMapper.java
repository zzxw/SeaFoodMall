package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    List<Cart> queryCart(@Param("userId") String userId);

    void createCart(Cart cart);

    void updateCart(Cart cart);

    void deleteCart(@Param("userId") String userId, @Param("goodsID") String goodsID);
}
