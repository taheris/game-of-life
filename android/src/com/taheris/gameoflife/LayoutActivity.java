package com.taheris.gameoflife;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * <dl>
 *   <dt> Purpose:
 *   <dd> Display the Layout view
 *   
 *   <dt> Description:
 *   <dd> Layout view allows the user to select from a pre-defined layout
 * </dl>
 *
 * @author Shaun Taheri
 * @version 23 Jan 2013
 */

public class LayoutActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] layouts = getResources().getStringArray(R.array.layouts);
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.layout_item, layouts));
        
        ListView listView = getListView();
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String layout = ((TextView) view).getText().toString(); // selected layout
                Intent intent = new Intent(LayoutActivity.this, GameOfLifeActivity.class);
                intent.putExtra("layout", layout); // send layout to new activity
                startActivity(intent);
            }
        });
    }
}
