package com.lzh.qes.search;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzhihao on 2017/4/13.
 */
public class PageList<T extends Serializable> implements Serializable {
    /**
     * 返回的数据集合
     */
    private List<T> dataList;

    /**
     * 返回的分页对象信息
     */
    private Map<String, Long> pagersInfo;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Map<String, Long> getPagersInfo() {
        return pagersInfo;
    }

    public void setPagersInfo(Map<String, Long> pagersInfo) {
        this.pagersInfo = pagersInfo;
    }
}
