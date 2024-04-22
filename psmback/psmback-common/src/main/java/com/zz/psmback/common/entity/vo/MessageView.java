package com.zz.psmback.common.entity.vo;

import com.zz.psmback.common.entity.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MessageView extends Message {
    private String publisher;
    private String receiver;
}
