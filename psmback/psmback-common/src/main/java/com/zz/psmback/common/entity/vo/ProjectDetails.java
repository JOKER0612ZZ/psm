package com.zz.psmback.common.entity.vo;

import com.zz.psmback.common.entity.Project;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zouzan
 * @date 2024/04/04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectDetails extends Project {
    private String userName;
    private Integer teamId;
}
