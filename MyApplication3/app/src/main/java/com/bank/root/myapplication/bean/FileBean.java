package com.bank.root.myapplication.bean;

import com.bank.root.myapplication.ListView.TreeNodeId;
import com.bank.root.myapplication.ListView.TreeNodeLabel;
import com.bank.root.myapplication.ListView.TreeNodePid;

/**
 * Created by root on 15-3-3.
 */
public class FileBean {
    @TreeNodeId
    private int id;

    @TreeNodePid
    private int pId;

    @TreeNodeLabel
    private String label;

    private String desc;

    public FileBean(int id, int pId, String label) {
        this.id = id;
        this.pId = pId;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
