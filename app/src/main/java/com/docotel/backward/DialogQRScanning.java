package com.docotel.backward;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.docotel.scanner.model.MerchantQr;

/**
 * Created by Ari on 1/5/2018.
 */

public class DialogQRScanning extends AlertDialog implements
        android.view.View.OnClickListener  {



    public Activity c;
    public Dialog d;
    public Button yes, no;
    public String lastText;
    private TextView txtMerchantName, txtMvisaPan, txtEmoneyPan, txtMasterPan, txtDebitPan;

    public DialogQRScanning(Activity a, String lastText) {
        super(a);
        this.c = a;
        this.lastText = lastText;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_qrscanning);

        yes = (Button) findViewById(R.id.btn_yes);

        txtMerchantName = (TextView) findViewById(R.id.txtMerchantName);
        txtMvisaPan = (TextView) findViewById(R.id.txtMvisaPan);
        txtEmoneyPan = (TextView) findViewById(R.id.txtEmoneyPan);
        txtMasterPan = (TextView) findViewById(R.id.txtMasterPan);
        txtDebitPan = (TextView) findViewById(R.id.txtDebitPan);

        yes.setOnClickListener(this);

        MerchantQr merchantQr = new MerchantQr(lastText);

        txtMerchantName.setText(merchantQr.getMerchantName());

        if (!merchantQr.getVisa_id().equalsIgnoreCase("")){

            txtMvisaPan.setText("Visa Pan : "+merchantQr.getVisa_id());
        }

        if (!merchantQr.getMastercard_id().equalsIgnoreCase("")){

            txtMasterPan.setText("Master Pan : "+merchantQr.getMastercard_id());
        }

        if (!merchantQr.getDebitId().equalsIgnoreCase("")){

            txtDebitPan.setText("Debit Pan : "+merchantQr.getDebitId());
        }

        if (!merchantQr.getEmoneyId().equalsIgnoreCase("")){

            txtEmoneyPan.setText("Emoney : "+merchantQr.getEmoneyId());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                dismiss();
                ((MainActivity) c).onResume();
                break;
            default:
                break;
        }
        dismiss();
    }
}
