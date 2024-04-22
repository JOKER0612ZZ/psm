package com.zz.psmback.common.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectPlan {
    @ExcelProperty("阶段")
    private String phaseName;
    @ExcelProperty("开始时间")
    private Date startTime;
    @ExcelProperty("结束时间")
    private Date endTime;
}
