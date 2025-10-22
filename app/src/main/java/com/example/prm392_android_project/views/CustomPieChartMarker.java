package com.example.prm392_android_project.views;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.example.prm392_android_project.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;

public class CustomPieChartMarker extends MarkerView {

    private final TextView tvContent;
    private final DecimalFormat format = new DecimalFormat("0");

    public CustomPieChartMarker(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e instanceof PieEntry) {
            PieEntry pe = (PieEntry) e;

            String label = pe.getLabel();
            float value = pe.getValue();

            String content = label + ": " + format.format(value);
            Log.d("CustomMarker", "Content: " + content);
            tvContent.setText(content);
        }

        super.refreshContent(e, highlight);
    }

    // Tùy chỉnh vị trí của Marker (để nó không bị cắt khỏi màn hình)
    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight() - 10);
    }
}
