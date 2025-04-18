package com.orangehrm.tests.api.pojos;

public class GetUserResponse {
    private UserData data;
    private Support support;

    // Getter & Setter for data
    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }
}
