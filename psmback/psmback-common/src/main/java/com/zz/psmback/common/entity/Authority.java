package com.zz.psmback.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@TableName("authority")
public class Authority  {
    private Integer authorityId;
    private String authorityName;
    private Integer authorityCode;

}
