package com.abin.lee.soap.model.rpc;

import com.google.common.base.MoreObjects;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 16-5-15
 * Time: 下午5:32
 * To change this template use File | Settings | File Templates.
 */
public class ProvinceBean {
    private String byProvinceName;

    public String getByProvinceName() {
        return byProvinceName;
    }

    public void setByProvinceName(String byProvinceName) {
        this.byProvinceName = byProvinceName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("byProvinceName", byProvinceName)
                .toString();
    }
}
