package com.example.House_Hoot.Common.MakerChecker;

import com.example.House_Hoot.Common.CommonExtends.CommonExtend;

public class MakerRequest<T> extends CommonExtend {
    private Object request;
    private String module;
    private String approvalStatus;
    private String tag;

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}

