package happyface.rpgtraveler.com;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ViewMap extends View {
	    private Path mapPath, mapBorderPath;
	    private double width, height, radius, minCells;
	    public int maskColor, backgroundColor, defaultColor;

	public ViewMap(Context context) {
	    super(context);
	    init();
	}

	public ViewMap(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    init();
	}

	public ViewMap(Context context, AttributeSet attrs, int defStyleAttr) {
	    super(context, attrs, defStyleAttr);
	    init();
	}

	private void init() {
	    mapPath = new Path();
	    mapBorderPath = new Path();
	    maskColor = 0x00000000;
	    backgroundColor = 0x00000000;
	    defaultColor = 0x0000000;
	    minCells = 1;
	}

	public void setColor(int[] color) {
		this.defaultColor = color[0];
	    this.maskColor = color[1];
	    this.backgroundColor = color[2];
	    invalidate();
	}
	
	public void setSpan(double cells){
		minCells = cells;
	}
	
	private void calculatePath() {
	    double triangleHeight = (float) (Math.sqrt(3) * radius / 2);
	    double centerX = width/2;
	    double centerY = height/2;
    	double radiusBorder = radius - 2;  
	    if (Act20MainGamePlay.mapType == 1){
	    	mapPath.moveTo((float)(centerX), (float)(centerY + radius));
	    	mapPath.lineTo((float)(centerX - triangleHeight), (float) (centerY + radius/2));
	    	mapPath.lineTo((float)(centerX - triangleHeight), (float) (centerY - radius/2));
	    	mapPath.lineTo((float)(centerX), (float) (centerY - radius));
	    	mapPath.lineTo((float)(centerX + triangleHeight), (float) (centerY - radius/2));
	    	mapPath.lineTo((float)(centerX + triangleHeight), (float) (centerY + radius/2));
	    	mapPath.lineTo((float)(centerX), (float) (centerY + radius));
  
	    	float triangleBorderHeight = (float) (Math.sqrt(3) * radiusBorder / 2);
	    	mapBorderPath.moveTo((float)(centerX), (float) (centerY + radiusBorder));
	    	mapBorderPath.lineTo((float)(centerX - triangleBorderHeight), (float) (centerY + radiusBorder/2));
	    	mapBorderPath.lineTo((float)(centerX - triangleBorderHeight), (float) (centerY - radiusBorder/2));
	    	mapBorderPath.lineTo((float)(centerX), (float) (centerY - radiusBorder));
	    	mapBorderPath.lineTo((float)(centerX + triangleBorderHeight), (float) (centerY - radiusBorder/2));
	    	mapBorderPath.lineTo((float)(centerX + triangleBorderHeight), (float) (centerY + radiusBorder/2));
	    	mapBorderPath.lineTo((float)(centerX), (float) (centerY + radiusBorder));
	    } else {
	    	mapPath.moveTo((float)(centerX + radius / Math.sqrt(2)), (float)(centerY + radius / Math.sqrt(2)));
	    	mapPath.lineTo((float)(centerX + radius / Math.sqrt(2)), (float)(centerY - radius / Math.sqrt(2)));
	    	mapPath.lineTo((float)(centerX - radius / Math.sqrt(2)), (float)(centerY - radius / Math.sqrt(2)));
	    	mapPath.lineTo((float)(centerX - radius / Math.sqrt(2)), (float)(centerY + radius / Math.sqrt(2)));
	    	mapPath.lineTo((float)(centerX + radius / Math.sqrt(2)), (float)(centerY + radius / Math.sqrt(2)));

	    	mapBorderPath.moveTo((float)(centerX + radiusBorder / Math.sqrt(2)), (float)(centerY + radiusBorder / Math.sqrt(2)));
	    	mapBorderPath.lineTo((float)(centerX + radiusBorder / Math.sqrt(2)), (float)(centerY - radiusBorder / Math.sqrt(2)));
	    	mapBorderPath.lineTo((float)(centerX - radiusBorder / Math.sqrt(2)), (float)(centerY - radiusBorder / Math.sqrt(2)));
	    	mapBorderPath.lineTo((float)(centerX - radiusBorder / Math.sqrt(2)), (float)(centerY + radiusBorder / Math.sqrt(2)));
	    	mapBorderPath.lineTo((float)(centerX + radiusBorder / Math.sqrt(2)), (float)(centerY + radiusBorder / Math.sqrt(2)));
	    }
	    invalidate();
	}
	
	// getting the view size and default radius
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    if (Act20MainGamePlay.mapType == 1){
	    	radius = Math.min(Act20MainGamePlay.parSize[0] / (minCells * (float)Math.sqrt(3)), 2 * Act20MainGamePlay.parSize[1] / (3 * minCells + 1));
	    	width = Math.max((int) (Math.sqrt(3) * radius), 7);
		    height = Math.max((int)(2 * radius), (int) (7 * 2 / Math.sqrt(3)));
	    } else {
	    	radius = (float) Math.min(Math.sqrt(2) * Act20MainGamePlay.parSize[0] / (minCells * 2), Math.sqrt(2) * Act20MainGamePlay.parSize[1] / (minCells * 2));
	    	width = Math.max((int) (Math.sqrt(2) * radius),7);
	    	height = Math.max((int) (Math.sqrt(2) * radius), 7);
	    }
	    setMeasuredDimension((int) width, (int) height);
	    calculatePath();
	}
	
	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);
		c.drawColor(defaultColor);
		c.clipPath(mapPath, Region.Op.REPLACE);
		c.clipPath(mapBorderPath,Region.Op.DIFFERENCE);
		c.drawColor(maskColor);
		c.clipPath(mapBorderPath,Region.Op.REPLACE);
		c.drawColor(backgroundColor);
	}
}