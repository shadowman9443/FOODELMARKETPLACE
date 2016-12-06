package com.digitechlabs.neat.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import com.dgtech.neat.R;
import com.digitechlabs.neat.adapter.ReastaurentListAdapter;
import com.digitechlabs.neat.fragments.FilterSheetDialogFragment;
import com.digitechlabs.neat.fragments.SortSheetDialogFragment;


public class RestaurentListActivity extends HeaderFooterActivity{
    ListView list;
    String[] web = {
            "Bangkok Eatery",
            "Kitchen Madras",
            "Green Chilis",

    } ;
    Integer[] imageId = {
            R.drawable.logo_bombay,
            R.drawable.logomadras,
            R.drawable.logo_bombay


    };
    RelativeLayout filtersRelative,sortRelative;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurent_list);
        list= (ListView) findViewById(R.id.listView);
        filtersRelative= (RelativeLayout) findViewById(R.id.relFilters);
        sortRelative= (RelativeLayout) findViewById(R.id.relsort);
        final BottomSheetDialogFragment myBottomSheet = FilterSheetDialogFragment.newInstance("Filter Bottom Sheet");

        llcheckout.setVisibility(View.GONE);
        filtersRelative.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myBottomSheet.show(getSupportFragmentManager(), myBottomSheet.getTag());

            }
        });
        final BottomSheetDialogFragment sortBottomSheet = SortSheetDialogFragment.newInstance("Sort Bottom Sheet");


        sortRelative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sortBottomSheet.show(getSupportFragmentManager(), sortBottomSheet.getTag());
                    }
                });


        ReastaurentListAdapter adapter = new
                ReastaurentListAdapter(RestaurentListActivity.this, web, imageId);
      /*  LinearLayout relative1 = (LinearLayout) findViewById(R.id.tell_a_frnd_rel);
        relative1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(RestaurentListActivity.this, TellAFrndActvity.class) );
            }
        });*/

        list.setAdapter(adapter);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

}
