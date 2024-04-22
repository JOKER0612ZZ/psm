package com.zz.psmback.common.utils;

import com.alibaba.excel.EasyExcel;
import com.zz.psmback.common.entity.vo.ProjectPlan;
import com.zz.psmback.common.listen.DataListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class ExcelUtils {
    @Autowired
    RedisUtils  redisUtils;
    public byte[] exportExcel(List<?> list){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes =null;
        try {
            EasyExcel.write(outputStream, list.get(0).getClass())
                    .sheet("Sheet1")
                    .doWrite(list);
            bytes= outputStream.toByteArray();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bytes;
    }
    public boolean setPlan(int projectId, MultipartFile file){
        List<ProjectPlan> projectPlans = null;
        try{
            DataListener<ProjectPlan> listener = new DataListener<>();
            EasyExcel.read(file.getInputStream(), ProjectPlan.class, listener).sheet().doRead();
            projectPlans = listener.getData();
            redisUtils.set("plan:"+projectId,projectPlans);
            return true;
        }catch (Exception e){
            System.out.println(projectPlans);
            e.printStackTrace();
            return false;
        }
    }
    @SuppressWarnings("unchecked")
    public List<ProjectPlan> getPlan(int projectId){
        if(redisUtils.hasKey("plan:"+projectId)){
            return (List<ProjectPlan>) redisUtils.get("plan:"+projectId);
        }else{
            return null;
        }
    }
}

