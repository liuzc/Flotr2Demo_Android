package com.liuzhichao.flotr2demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ChartActivity extends Activity {

	private String type;
	private WebView webView;
	private String data;
	private String option;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chart);
		initViews();
		type = getIntent().getStringExtra("type");
		initChartData(type);
	}

	
	//根据传入的图表类型,初始化数据和设置
	private void initChartData(String type) {
		if ("lines".equals(type)) {
			data = "[[0, 3], [4, 8], [8, 5], [9, 13]]";
			option = "{ xaxis: { minorTickFreq: 4}, grid: {minorVerticalLines: true}}";
		}else if ("bars".equals(type)) {
			String bj = " [[1, -9], [2, 1], [3, 12], [4, 20],[5, 26], [6, 30], [7, 32], [8, 29],[9, 22], [10, 12], [11, 0], [12, -6]]";
			data = "{ data : "+bj+", label :'北京' ,bars : { show : true }}";
			//我们也可以不设置option,当option为空时,将使用flotr2的默认配置
		}else if ("pie".equals(type)) {
			data = " { data : [[0, 4]], label : 'Comedy' }, { data :[[0, 3]], label : 'Action' },{ data : [[0, 1.03]], label : 'Romance',pie : {explode : 50} }, { data : [[0, 3.5]], label : 'Drama' }";
			option = "{mouse : { track : true },pie : {show : true, explode : 6}}";
		}else if ("points".equals(type)) {
			String sz = "[[1, 15], [2, 16], [3, 19], [4, 22],[5, 26], [6, 27], [7, 28], [8, 28],[9, 27], [10, 25], [11, 20], [12, 16]]";
		    data = " { data : "+sz+", label :'深圳' , points : { show : true }}";
			
		}else if ("radar".equals(type)) {
			String s1 ="{ label : 'Actual', data : [[0, 3], [1, 8], [2, 5], [3, 5], [4, 3], [5, 9]] }";
			String s2 = "{ label : 'Target', data : [[0, 8], [1, 7], [2, 8], [3, 2], [4, 4], [5, 7]] }";
			// Radar Labels
			String ticks = "[[0, 'Statutory'],[1, 'External'], [2, 'Videos'],[3, 'Yippy'],[4, 'Management'],[5, 'oops']]";
			
			data = s1+","+s2;
			option = "{radar : { show : true}, grid  : { circular : true, minorHorizontalLines : true}, yaxis : { min : 0, max : 10, minorTickFreq : 2}, xaxis : { ticks : "+ticks+"}}";
			
		}else if ("bubble".equals(type)) {
			
			String [] d1 = new String[10];
			String [] d2 = new String[10];
			
			for(int i=0;i<10;i++){
				d1[i] = "["+i+","+Math.ceil(Math.random()*10)+","+Math.ceil(Math.random()*10)+"]";
				d2[i] = "["+i+","+Math.ceil(Math.random()*10)+","+Math.ceil(Math.random()*10)+"]";
			}
			
			String str1="[";
			String str2 = "[";
			
			for(String str:d1){
				str1+=str+",";
			}
			for(String str:d2){
				str2+= str+",";
			}
			data = str1.substring(0, str1.length()-1)+"],\n"+str2.substring(0, str2.length()-1)+"]";
			
			option = "{ bubbles : { show : true, baseRadius : 5 },xaxis   : { min : -4, max : 14 },yaxis   : { min : -4, max : 14 }}";
		}
		
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initViews() {
		webView = (WebView) findViewById(R.id.webView);
		//因为要调用页面的javascript,所以要启用javascript支持
		webView.getSettings().setJavaScriptEnabled(true);
		//在我实现的过程中,没有设置setWebChromeClient时无法调用页面的javascript
		webView.setWebChromeClient(new WebChromeClient());
		
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				//在网页加载完成之后,再调用网页上的javascript方法
				webView.loadUrl("javascript:drawChart(["+data+"],"+option+")");
			}
			
		});
		
		webView.loadUrl("file:///android_asset/chart.html");
	}

}
