package com.zz.psmback.common.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.zz.psmback.common.entity.FileOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileOperationView extends FileOperation {
    @ColumnWidth(10)
    @ExcelProperty("操作者")
    private String operatorName;
}
