package com.lzh.qes.enums;

/**
 * Created by liuzhihao on 2017/4/7.
 */
public enum IsEnableState {
    启用(0), 停用(1);

    private int index;

    private IsEnableState(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
