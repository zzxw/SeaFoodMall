package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Cart;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.Order;
import com.tencent.wxcloudrun.model.User;

import java.util.List;
import java.util.Optional;

public interface CounterService {

  Optional<Counter> getCounter(Integer id);

  void upsertCount(Counter counter);

  void clearCount(Integer id);

  boolean hasUser(String userID);

  void createUser(User userInfo);

  void createCart(Cart cart);

  void updateCart(Cart cart);

  void deleteCart(String userID, String goodsID);

  List<Cart> queryCart(String userID);

  void createOrder(Order order);

  void updateOrder(Order order);

  void deleteOrder(String orderID);

  List<Order> queryOrderByUserID(String userID);

  Order queryOrderByID(String orderID);

}
