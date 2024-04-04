package com.zz.psmback.common.entity.vo;

import com.zz.psmback.common.entity.Task;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author zouzan
 * @date 2024/04/04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskView extends Task {
    private String creatorName;
    private String assignName;
    private Integer assignerId;
    private String projectName;
}
