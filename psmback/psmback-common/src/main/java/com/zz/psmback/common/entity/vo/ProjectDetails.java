package com.zz.psmback.common.entity.vo;

import com.zz.psmback.common.entity.Project;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectDetails extends Project {
    private String userName;
}
