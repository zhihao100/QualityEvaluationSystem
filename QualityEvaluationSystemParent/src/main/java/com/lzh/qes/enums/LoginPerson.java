package com.lzh.qes.enums;

/**
 * 登录人
 * Created by liuzhihao on 2017/4/12.
 */
public enum LoginPerson {
    用户(0), 管理员(1);

    private int index;

    private LoginPerson(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
