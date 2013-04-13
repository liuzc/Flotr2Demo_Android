package com.liuzhichao.flotr2demo;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	
	private String[] charts={"折线图","柱状图","饼状图","点状图","雷达图","气泡图"};
	private String[] types = {"lines","bars","pie","points","radar","bubble"};
	private Context ctx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ctx = this;
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,charts);
		
		ListView listView= getListView();
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent = new Intent(ctx, ChartActivity.class);
				intent.putExtra("type", types[position]);
				startActivity(intent);
				
			}
		});
	}


	
	
}
