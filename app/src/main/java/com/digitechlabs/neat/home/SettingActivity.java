package com.digitechlabs.neat.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.dgtech.neat.R;

public class SettingActivity extends HeaderFooterActivity {
    RelativeLayout contactdetail,mngLocations,savedcrds,lolpts,rtordrs,rtApp,help,notific,eularel,signout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        restaurentSearch.setVisibility(View.GONE);
        backArroow.setVisibility(View.GONE);
        restaurentText.setVisibility(View.GONE);
        initUI();
    }
    private void initUI(){
        contactdetail= (RelativeLayout) findViewById(R.id.contctDetailRel);
        mngLocations= (RelativeLayout) findViewById(R.id.manageLoaRel);
        savedcrds= (RelativeLayout) findViewById(R.id.svdcardRel);
        lolpts= (RelativeLayout) findViewById(R.id.loyalitypntRel);
        rtordrs= (RelativeLayout) findViewById(R.id.rateordRel);
        rtApp= (RelativeLayout) findViewById(R.id.ratappRel);
        help= (RelativeLayout) findViewById(R.id.helpRel);
        notific= (RelativeLayout) findViewById(R.id.notficRel);
        eularel= (RelativeLayout) findViewById(R.id.eulaRel);
        signout= (RelativeLayout) findViewById(R.id.signoutRel);

    }


    public void contactDetail(View view) {
    }

    public void manageLocation(View view) {
    }

    public void savedCard(View view) {
    }

    public void loayalityPoint(View view) {
    }

    public void rateOrders(View view) {
    }

    public void rateApps(View view) {
    }

    public void help(View view) {
    }

    public void notify(View view) {
    }

    public void eula(View view) {
    }

    public void signout(View view) {
    }
}
