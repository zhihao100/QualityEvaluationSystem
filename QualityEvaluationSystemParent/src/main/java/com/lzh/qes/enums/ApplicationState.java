package com.lzh.qes.enums;

/**
 * Created by liuzhihao on 2017/5/15.
 */
public enum ApplicationState {
    待审核(0), 审核通过(1), 审核未通过(2);

    private int index;

    private ApplicationState(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
