package com.zz.psmback.common.listen;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DataListener<T> extends AnalysisEventListener<T> {
    private List<T> data =new ArrayList<T>();
    public DataListener() {
    }
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        log.info("t:"+t.toString());
        data.add(t);
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("加载excel数据完成");
    }
}
