package com.zz.psmback.common.entity.vo;

import com.zz.psmback.common.entity.Authority;
import lombok.Data;

import java.util.List;

@Data
public class ProjectAuthorities {
    private Integer projectId;
    private List<Authority> authorities;
}
