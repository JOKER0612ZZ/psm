package com.zz.psmback.common.entity.vo;

import com.zz.psmback.common.entity.Task;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class TaskView extends Task {
    private String userName;
    private String assignName;
    private String projectName;
}
