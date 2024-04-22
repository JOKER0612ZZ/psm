package com.zz.psmback.common.entity.vo;
import com.zz.psmback.common.entity.PsmFile;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PsmFileView extends PsmFile {
    private String userName;
}
