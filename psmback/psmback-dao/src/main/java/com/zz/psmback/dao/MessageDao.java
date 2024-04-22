package com.zz.psmback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zz.psmback.common.entity.Message;
import com.zz.psmback.common.entity.vo.MessageView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageDao extends BaseMapper<Message> {
    List<MessageView> getMessages(int receiverId);
}
