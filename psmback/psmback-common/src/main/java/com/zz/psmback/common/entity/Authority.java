package com.zz.psmback.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@TableName("authority")
public class Authority  {
    @TableId(type = IdType.AUTO)
    private Integer authorityId;
    private String authorityName;
    private Integer authorityCode;
}
