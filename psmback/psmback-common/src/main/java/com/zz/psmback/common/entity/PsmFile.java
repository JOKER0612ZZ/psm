package com.zz.psmback.common.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("file")
public class PsmFile {
    @TableId(type = IdType.AUTO)
    private Integer fileId;
    private String fileName;
    private String filePath;
    private Integer uploaderId;
    private String uploaderTime;
    private Integer projectId;
    private String type;
    private Integer parentId;
    private Integer size;
    private Integer taskId;
    public PsmFile() {}
    public PsmFile(String fileName, String filePath, Integer uploaderId,
                   String uploaderTime, Integer projectId, String type, Integer parentId) {
        this.fileName=fileName;
        this.filePath=filePath;
        this.uploaderId=uploaderId;
        this.uploaderTime=uploaderTime;
        this.projectId=projectId;
        this.type=type;
        this.parentId=parentId;

    }
}
