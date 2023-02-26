package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.CountersMapper;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CounterServiceImpl implements CounterService {

  final CountersMapper countersMapper;
  final UserMapper userMapper;

  public CounterServiceImpl(@Autowired CountersMapper countersMapper, UserMapper userMapper) {
    this.countersMapper = countersMapper;
    this.userMapper = userMapper;
  }

  @Override
  public Optional<Counter> getCounter(Integer id) {
    return Optional.ofNullable(countersMapper.getCounter(id));
  }

  @Override
  public void upsertCount(Counter counter) {
    countersMapper.upsertCount(counter);
  }

  @Override
  public void clearCount(Integer id) {
    countersMapper.clearCount(id);
  }

  @Override
  public boolean hasUser(String userId) {
    User user = userMapper.getUser(userId);
    if(user != null) {
      return true;
    }
    return user == null ? false : true;
  }

  @Override
  public void createUser(User user) {
    userMapper.createUser(user);
  }

}
