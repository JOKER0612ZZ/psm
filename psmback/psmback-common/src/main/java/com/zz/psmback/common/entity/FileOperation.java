package com.zz.psmback.common.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("file_operation")
public class FileOperation {
    @TableId(type= IdType.AUTO)
    @ExcelIgnore
    private Integer operationId;
    @ExcelIgnore
    private Integer fileId;
    @ExcelIgnore
    private Integer operatorId;
    @ColumnWidth(20)
    @ExcelProperty("操作时间")
    private String operationTime;
    @ColumnWidth(15)
    @ExcelProperty("操作类型")
    private String operationType;
    @ColumnWidth(20)
    @ExcelProperty("文件名")
    private String fileName;
    @ExcelIgnore
    private int projectId;
    public FileOperation(){

    }
}
