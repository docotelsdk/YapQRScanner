package com.docotel.scanner.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BankData implements Serializable {

    @SerializedName("acquirer_domain")
    private String acquirer_domain;

    @SerializedName("acquirer_mpan")
    private String acquirer_mpan;

    @SerializedName("acquirer_mid")
    private String acquirer_mid;

    @SerializedName("acquirer_mcriteria")
    private String acquirer_mcriteria;

    public String getAcquirer_domain() {
        return acquirer_domain;
    }

    public void setAcquirer_domain(String acquirer_domain) {
        this.acquirer_domain = acquirer_domain;
    }

    public String getAcquirer_mpan() {
        return acquirer_mpan;
    }

    public void setAcquirer_mpan(String acquirer_mpan) {
        this.acquirer_mpan = acquirer_mpan;
    }

    public String getAcquirer_mid() {
        return acquirer_mid;
    }

    public void setAcquirer_mid(String acquirer_mid) {
        this.acquirer_mid = acquirer_mid;
    }

    public String getAcquirer_mcriteria() {
        return acquirer_mcriteria;
    }

    public void setAcquirer_mcriteria(String acquirer_mcriteria) {
        this.acquirer_mcriteria = acquirer_mcriteria;
    }
}
