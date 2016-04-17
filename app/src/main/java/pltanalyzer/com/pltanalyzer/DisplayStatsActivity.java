package pltanalyzer.com.pltanalyzer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class DisplayStatsActivity extends Activity {
    RelativeLayout chart_layout = null;
    private static int COUNT = 7;
    public static final String TAG = "DisplayStatsActivity";
    public static final String LOOKUP_TIME = "pltanalyzer.com.pltanalyzer.DisplayStatsActivity.LOOKUP_TIME";
    public static final String CONNECT_TIME = "pltanalyzer.com.pltanalyzer.DisplayStatsActivity.CONNECT_TIME";
    public static final String PRE_TRANSFER_TIME = "pltanalyzer.com.pltanalyzer.DisplayStatsActivity.PRE_TRANSFER_TIME";
    public static final String START_TRANSFER_TIME = "pltanalyzer.com.pltanalyzer.DisplayStatsActivity.START_TRANSFER_TIME";
    public static final String DOWNLOADED_SIZE = "pltanalyzer.com.pltanalyzer.DisplayStatsActivity.DOWNLOADED_SIZE";
    public static final String DOWNLOADED_SPEED = "pltanalyzer.com.pltanalyzer.DisplayStatsActivity.DOWNLOADED_SPEED";
    public static final String TOTAL_TIME = "pltanalyzer.com.pltanalyzer.DisplayStatsActivity.TOTAL_TIME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_stats);
        chart_layout = (RelativeLayout)findViewById(R.id.chart_ui);
        Intent intent = getIntent();
        if(intent == null){
            Log.e(TAG, " Intent is null");
            finish();
        }
        double[] values = new double[COUNT];
        String[] keys = new String[]{ LOOKUP_TIME, CONNECT_TIME, PRE_TRANSFER_TIME,
                START_TRANSFER_TIME, DOWNLOADED_SIZE, DOWNLOADED_SPEED, TOTAL_TIME
        };

        for(int i=0; i < COUNT; i++ ){
            values[i] = Double.valueOf(intent.getStringExtra(keys[i]));
        }

        // Color of each Pie Chart Sections
        int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED,
                Color.YELLOW };

        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries(" Page Load Statistic ");
        for(int i=0 ;i < values.length;i++){
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(keys[i], values[i]);
        }

        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer  = new DefaultRenderer();
        for(int i = 0 ;i<values.length;i++){
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            //seriesRenderer.setDisplayChartValues(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        defaultRenderer.setChartTitle(" Page Load Statistic ");
        defaultRenderer.setChartTitleTextSize(20);
        defaultRenderer.setZoomButtonsVisible(false);

        // Creating an intent to plot bar chart using dataset and multipleRenderer
        Intent i = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries , defaultRenderer, "AChartEnginePieChartDemo");

        // Start Activity
        startActivity(i);

    }
}
