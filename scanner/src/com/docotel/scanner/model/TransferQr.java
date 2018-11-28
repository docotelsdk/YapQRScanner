package com.docotel.scanner.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import static com.docotel.scanner.model.CreditCardValidator.format;

public class TransferQr {
    private String qr;
    private int currentIdx = 0;
    private boolean isValid = false;
    private List<MerchantQrTag> qrByTag = new ArrayList<>();
    private String qrVersion = "";
    private QrType type = null;
    private String codetag = "";
    private String pan_debit_tujuan = "";
    private String norek_tujuan = "";
    private String expired_date = "";
    private String token = "";
    private String currency_code = "";
    private String country_code = "";
    private String nama_tujuan = "";
    private String ref_id = "";
    private String amount = "";
    private String crc = "";
    private String merchantData = "";
    private boolean isAdditionalField = false;

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public int getCurrentIdx() {
        return currentIdx;
    }

    public void setCurrentIdx(int currentIdx) {
        this.currentIdx = currentIdx;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public List<MerchantQrTag> getQrByTag() {
        return qrByTag;
    }

    public void setQrByTag(List<MerchantQrTag> qrByTag) {
        this.qrByTag = qrByTag;
    }

    public String getQrVersion() {
        return qrVersion;
    }

    public void setQrVersion(String qrVersion) {
        this.qrVersion = qrVersion;
    }

    public QrType getType() {
        return type;
    }

    public void setType(QrType type) {
        this.type = type;
    }
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isAdditionalField() {
        return isAdditionalField;
    }

    public void setAdditionalField(boolean additionalField) {
        isAdditionalField = additionalField;
    }

    public String getCodetag() {
        return codetag;
    }

    public void setCodetag(String codetag) {
        this.codetag = codetag;
    }

    public String getPan_debit_tujuan() {
        return pan_debit_tujuan;
    }

    public void setPan_debit_tujuan(String pan_debit_tujuan) {
        this.pan_debit_tujuan = pan_debit_tujuan;
    }

    public String getNorek_tujuan() {
        return norek_tujuan;
    }

    public void setNorek_tujuan(String norek_tujuan) {
        this.norek_tujuan = norek_tujuan;
    }

    public String getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(String expired_date) {
        this.expired_date = expired_date;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getNama_tujuan() {
        return nama_tujuan;
    }

    public void setNama_tujuan(String nama_tujuan) {
        this.nama_tujuan = nama_tujuan;
    }

    public String getRef_id() {
        return ref_id;
    }

    public void setRef_id(String ref_id) {
        this.ref_id = ref_id;
    }

    public TransferQr(String qr) {
        this.qr = qr;
        crcCheck();
    }

    private TransferQr(String qr, boolean isAdditionalField) {
        this.qr = qr;
        this.isAdditionalField = isAdditionalField;
        parseByTag();
    }

    private void crcCheck() {
        try {
            String plain = qr.substring(0, qr.length() - 4);
            String crc = qr.substring(qr.length() - 4, qr.length()).toLowerCase();
            String validCrc = CrcHelper.crc(plain).toLowerCase();
            if (validCrc.length() == 3) validCrc = "0" + validCrc;
            if (validCrc.length() == 2) validCrc = "00" + validCrc;
            if (validCrc.length() == 1) validCrc = "000" + validCrc;
            LoggerHelper.info(validCrc);
            if (validCrc.equals(crc)) parseByTag();
        } catch (Exception ignored) {
        }
    }

    private List<MerchantQrTag> getFields() {
        if (isAdditionalField) return qrByTag;
        else return null;
    }

    private void parseByTag() {
        try {
            if (currentIdx == qr.length()) {
                isValid = true;
                if (!isAdditionalField) readEachTag();
                return;
            }
            MerchantQrTag qrTag = new MerchantQrTag();
            qrTag.setTag(qr.substring(currentIdx, currentIdx + 2));
            if (TextUtils.isDigitsOnly(qr.substring(currentIdx + 2, currentIdx + 4)))
                qrTag.setLength(Integer.valueOf(qr.substring(currentIdx + 2, currentIdx + 4)));
            qrTag.setValue(qr.substring(currentIdx + 4, currentIdx + 4 + qrTag.getLength()));
            currentIdx = currentIdx + 4 + qrTag.getLength();
            qrByTag.add(qrTag);
            parseByTag();
        } catch (Exception e) {
            e.printStackTrace();
            isValid = false;
        }
    }

    private void readEachTag() {
        for (MerchantQrTag qrTag : qrByTag) {
            setValue(qrTag.getTag(), qrTag.getValue());
        }
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    private void setValue(String tag, String value) {
        switch (tag) {
            case "00":
                qrVersion = value;
                break;
            case "01":
                if (value.equals("19")) type = QrType.DYNAMIC;
                break;
            case "26":
                pan_debit_tujuan = value;
                break;
            case "35":
                norek_tujuan = value;
                break;
            case "36":
                expired_date = value;
                break;
            case "44":
                token = value;
                break;
            case "53":
                currency_code = value;
                break;
            case "58":
                country_code = value;
                break;
            case "59":
                nama_tujuan = value;
                break;
            case "62":
                merchantData = value;
                readAdditionalField();
                break;
            case "63":
                crc = value;
        }
    }

    private void readAdditionalField() {
        TransferQr additionalField = new TransferQr(merchantData, true);
        List<MerchantQrTag> additionalTags = additionalField.getFields();
        if (additionalTags != null) {
            for (MerchantQrTag additionalTag : additionalTags) {
                switch (additionalTag.getTag()) {
                    case "05":
                        ref_id = additionalTag.getValue();
                        break;
                }
            }
        }
    }

    public boolean isValid() {
        return isValid;
    }

    public enum QrType {STATIC, DYNAMIC};
}

