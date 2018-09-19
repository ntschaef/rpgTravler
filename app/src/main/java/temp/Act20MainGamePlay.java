package happyface.rpgtraveler.com;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.content.SharedPreferences;

public class Act20MainGamePlay extends Activity {

	SharedPreferences metadata;
    SharedPreferences.Editor editor;
    RelativeLayout rlBase;
    RelativeLayout rlBack;
    RelativeLayout rlMap;
    
    LinearLayout llMaster;
    LinearLayout llLists;
    TextView tvChange;
    
    Button but;
    public static int [] tileColor = new int[3];
	public static int mapType;
	boolean[] multitouch = new boolean[1];
	
	final ArrayList<int[]> alColorIndex = new ArrayList<int[]>();
    final ArrayList<int[]> alColor = new ArrayList<int[]>();
	final ArrayList<int[]> alTileIndex = new ArrayList<int[]>();
    
	final int[] shift = new int[2];
    final int[] viewSize = new int[2];
    final public static int[] parSize = new int[2];
    final public static boolean[] oglFlag = new boolean[2];
    final double[] tileSpan = new double[1];
    
    int widthCount;
    int heightCount;
	double heightOverlap;
	double widthShift;
	double heightShift;
	double [] intTilePos = new double[2];
	
    double[] startXY1 = new double[2];
    double[] startXY2 = new double[2];
	int[] intCenter = new int[2];
	int[] curCenter = new int[2];
	double[] intIndex = new double[2];
	double[] curIndex = new double[2];
	double[] intPos = new double[2];
	double inflate = 0;
	
	boolean move;
	
    OnClickListener tileClick;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity20_maingameplay);
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	metadata = getSharedPreferences("metadata", 0);
    	editor = metadata.edit();
        mapType = metadata.getInt("Map",0);
        rlBase = (RelativeLayout) findViewById(R.id.a20_rlBase);
        rlBack = (RelativeLayout) findViewById(R.id.a20_rlBack);
        rlMap = (RelativeLayout) findViewById(R.id.a20_rlMap);
        
        llMaster = (LinearLayout) findViewById(R.id.a20_llMaster);
        llLists = (LinearLayout) findViewById(R.id.a20_llLists);
        tvChange = (TextView) findViewById(R.id.a20_tvChange);
        
        but = (Button)findViewById(R.id.a20_bMenu);
        intTilePos = new double[]{0,0};
        oglFlag[0] = true;
        oglFlag[1] = true;
		tileSpan[0] = 5;
		tileColor[0] = 0x00000000;
		tileColor[1] = 0xFFAAAAAA;
		tileColor[2] = 0xFF333333;
        if(!metadata.getBoolean("GM?", false)){
        	but.setVisibility(View.GONE);
        }
        
        tileClick = new OnClickListener() {
			@Override
			public void onClick(View view) {
				Act00LogonMaster.centerToast(getBaseContext(), "This is cell index: " + alTileIndex.get((int) (intIndex[0] + intIndex[1] * widthCount + 1))[0] + "x" + alTileIndex.get((int) (intIndex[0] + intIndex[1] * widthCount + 1))[1]);
			}
		};
        
        but.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				this button will allow you to edit ... something - figure this out later.
			}
		});
        
        tvChange.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutParams lpMaster = (LayoutParams) llMaster.getLayoutParams();
				LinearLayout.LayoutParams lpLists = (LinearLayout.LayoutParams) llLists.getLayoutParams();
				LinearLayout.LayoutParams lpChange = (LinearLayout.LayoutParams) tvChange.getLayoutParams();
				if(tvChange.getText().equals(">")){
					lpMaster.width = 600;
					lpLists.weight = 5f;
					tvChange.setText("<");
				} else {
					lpMaster.width = 100;
					lpLists.weight = 0f;
					tvChange.setText(">");
				}
				llMaster.setLayoutParams(lpMaster);
				llLists.setLayoutParams(lpLists);
			}
        });
        
        rlMap.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        	@Override
			public void onGlobalLayout() {
        		if (oglFlag[0]){
        			oglFlag[0] = false;
        			parSize[0] = rlMap.getWidth();
        			parSize[1] = rlMap.getHeight();
        			final ViewMap tempHex = new ViewMap(getBaseContext());
//        			tempHex.setId(1);
        			tempHex.setSpan(tileSpan[0]);
        			LayoutParams lpTHex = new LayoutParams(parSize[0],parSize[1]);
        			tempHex.setLayoutParams(lpTHex);
        			rlMap.addView(tempHex); 
        			tempHex.measure(0, 0);
        			viewSize[1] = tempHex.getMeasuredHeight();	
        			viewSize[0] = tempHex.getMeasuredWidth();
        			widthCount = ((int) ((parSize[0]/viewSize[0]) + 3));
        			heightCount = ((int) ((parSize[1]/viewSize[1]) + 3));
        			if (metadata.getInt("Map", 0) == 1){
        				heightCount = (int) ((4 * parSize[1] / (3 * viewSize[1])) + 3);
        			}
        			final int[] baseCoordinate = new int[2];
					try {
						baseCoordinate[0] = alTileIndex.get(1)[0] - shift[0];
						baseCoordinate[1] = alTileIndex.get(1)[1] - shift[1];
					} catch (Exception e){
						baseCoordinate[0] = 0;
						baseCoordinate[1] = 0;
					}
					alTileIndex.clear();
					alTileIndex.add(null);
					heightOverlap = 0;
					widthShift = 0;
					heightShift = 0;
					if (metadata.getInt("Map",0) == 1){
						heightOverlap =  .25;
						heightShift = -.25;
						widthShift = -.5;
					}
        			for (int i = 0; i < heightCount ; i++){
        				for(int j = 0; j < widthCount; j++){
        					if (metadata.getInt("Map", 0) == 1){
        						alTileIndex.add(new int[]{(int)(baseCoordinate[0] + j - (i / 2)), baseCoordinate[1] + i});
        					} else {
        						alTileIndex.add(new int[]{(int)(baseCoordinate[0] + j), baseCoordinate[1] + i});
        					}
        					if(j == 0 && i == 0){
        						LayoutParams lpTH = (LayoutParams) tempHex.getLayoutParams();
        						lpTH.setMargins((int)(viewSize[0] * (intTilePos[0] + widthShift)), (int) (viewSize[1] * (intTilePos[1] + heightShift)) , 0, 0);
        						lpTH.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        	        			lpTH.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        						tempHex.setColor(tileColor);
        	        			tempHex.setLayoutParams(lpTH);
        	        			tempHex.setOnClickListener(tileClick);
        					} else {
        						ViewMap tempHex1 = new ViewMap(getBaseContext());
        						tempHex1.setId(j + i * widthCount + 1);
        						tempHex1.setSpan(tileSpan[0]);
        						LayoutParams lpTHex1 = new LayoutParams(parSize[0], parSize[1]);
        						lpTHex1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        						lpTHex1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        						lpTHex1.setMargins((int)(viewSize[0] * (intTilePos[0] + (widthShift + (i % 2 * .5)) * (metadata.getInt("Map", 0) % 2) + j)), (int) (viewSize[1] * (intTilePos[1] + heightShift + i * (1 - heightOverlap))), 0, 0);
        						tempHex1.setLayoutParams(lpTHex1);
        						rlMap.addView(tempHex1); 
        						tempHex1.setColor(tileColor);
        						tempHex1.setOnClickListener(tileClick);
        					}
//        			begin ------------ temp coding
        					if (Math.abs((alTileIndex.get(j + i * widthCount + 1)[0] + alTileIndex.get(j + i * widthCount + 1)[1]) % 7) == 2 && !alColorIndex.contains(alTileIndex.get(j + i * widthCount + 1))){
        						alColorIndex.add(alTileIndex.get(j + i * widthCount + 1));
        						alColor.add(new int[]{tileColor[0], tileColor[1], 0xFF654321});
        					}
//        			end -------------- temp coding
        					if (alColorIndex.contains(alTileIndex.get(j + i * widthCount + 1))){
        						((ViewMap)findViewById(j + i * widthCount + 1)).setColor(alColor.get(alColorIndex.indexOf(alTileIndex.get(j + i * widthCount + 1))));
        					}
        				}
        			}
        			but.bringToFront();
        			llMaster.bringToFront();
        			llLists.bringToFront();
        			tvChange.bringToFront();
        		}
        	}
        });
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
    	if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
    		move = false;
    		inflate = 0;
    		multitouch[0] = false;
       		startXY1[0] = startXY2[0] = event.getX(0);
           	startXY1[1] = startXY2[1] = event.getY(0);
           	intCenter = new int[]{(int) startXY1[0],(int) startXY1[1]};
           	double [][] intStats = findStats(intCenter[0],intCenter[1], 1, intTilePos[0], intTilePos[1]);
           	intIndex = intStats[0];
           	intPos = intStats[1];
           	intPos[0] = intPos[0] * viewSize[0];
            intPos[1] = intPos[1] * viewSize[1];
    	} else if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_POINTER_DOWN){
           	multitouch[0] = true;
           	startXY1[0] = event.getX(0);
           	startXY1[1] = event.getY(0);
    		startXY2[0] = event.getX(1);
            startXY2[1] = event.getY(1);
            intCenter = new int[]{(int)((startXY1[0] + startXY2 [0])/2), (int)((startXY1[1] + startXY2 [1])/2)};
            double [][] intStats = findStats(intCenter[0],intCenter[1], 1, intTilePos[0], intTilePos[1]);
            intIndex = intStats[0];
            intPos = intStats[1];
            intPos[0] = intPos[0] * viewSize[0];
            intPos[1] = intPos[1] * viewSize[1];
        } else if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_MOVE){
        	if (event.getPointerCount() > 1 || !multitouch[0]){
        		if (!multitouch[0]){
        			inflate = 1;
        			curCenter = new int[]{(int) event.getX(),(int) event.getY()};
        		} else if (multitouch[0]){
        			inflate = Math.sqrt(Math.pow((double)(event.getX(0) - event.getX(1)),2) + Math.pow((double)(event.getY(0) - event.getY(1)), 2)) / Math.sqrt(Math.pow((double)(startXY1[0] - startXY2[0]),2) + Math.pow((double)(startXY1[1] - startXY2[1]),2));
        			curCenter = new int[]{(int) ((event.getX(0) + event.getX(1)) / 2), (int) ((event.getY(0) + event.getY(1)) / 2)}; 
        		}
        		if (multitouch[0] || Math.sqrt(Math.pow(startXY1[0] - event.getX(),2) + Math.pow(startXY1[1] - event.getY(),2)) > 10 || move){
        			move = true;
        			ViewMap vmOriginal = (ViewMap)findViewById((int) (intIndex[0] + intIndex[1] * ((int) ((parSize[0]/viewSize[0]) + 3)) + 1));
        			ViewMap vmTemp = new ViewMap(getBaseContext());
        			vmTemp.setColor(new int[]{vmOriginal.defaultColor,vmOriginal.maskColor,vmOriginal.backgroundColor});
        			vmTemp.setSpan(Math.min(Math.max((float) (tileSpan[0] / inflate), 3), Math.min(rlBack.getWidth(), rlBack.getHeight())/50));
        			LayoutParams lpTemp = new LayoutParams(0,0);
        			int rawHeightIndex = (int)((curCenter[1] - inflate * (intPos[1] + viewSize[1]) - heightShift * viewSize[1] * inflate) % (viewSize[1] * (1 - heightOverlap)) + 1);
        			lpTemp.setMargins((int)(curCenter[0] - inflate * (intPos[0] + viewSize[0]) + (widthShift + (rawHeightIndex % 2) * .5) * (metadata.getInt("Map", 0) % 2)),(int)(curCenter[1] - inflate * (intPos[1] + viewSize[1])), 0, 0);
        			vmTemp.setLayoutParams(lpTemp);
        			rlBack.removeAllViews();
        			rlBack.addView(vmTemp);
        			rlBack.bringToFront();
        		}
        	}
    	} else if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_UP){
        	double[] newIndex = new double[2];
        	if (!multitouch[0]){
        		inflate = 1;
        		curCenter = new int[]{(int) event.getX(),(int) event.getY()};
        	} else {
        		if (event.getPointerCount()>1){
        			inflate = Math.sqrt(Math.pow((double)(event.getX(0) - event.getX(1)),2) + Math.pow((double)(event.getY(0) - event.getY(1)), 2)) / Math.sqrt(Math.pow((double)(startXY1[0] - startXY2[0]),2) + Math.pow((double)(startXY1[1] - startXY2[1]),2));
        			curCenter = new int[]{(int) ((event.getX(0) + event.getX(1)) / 2), (int) ((event.getY(0) + event.getY(1)) / 2)}; 
        		}
        	}
        	double curStats[][] = findStats(curCenter[0],curCenter[1],inflate, intPos[0]/viewSize[0], intPos[1]/viewSize[1]);
       		newIndex = curStats[0];
        	if (move){
        		tileSpan[0] = Math.min(Math.max((float) (tileSpan[0] / inflate), 3), Math.min(rlBack.getWidth(), rlBack.getHeight())/50);
        		shift[1] = (int) (newIndex[1] - intIndex[1]);
        		shift[0] = (int) (newIndex[0] - intIndex[0] + ((int)(intIndex[1]/2) - (int)(newIndex[1] / 2)) * (metadata.getInt("Map", 0) % 2));
        		intTilePos = curStats[1];
        		LayoutParams lpMap = (LayoutParams) rlMap.getLayoutParams();
        		lpMap.height = rlBack.getHeight();
        		lpMap.width = rlBack.getWidth();
        		rlMap.setLayoutParams(lpMap);
        		rlMap.bringToFront();
        		oglFlag[0] = true;
        		rlMap.removeAllViews();
        		rlBack.removeAllViews();
        		rlMap.requestLayout();
        	}
    	}
    	return super.dispatchTouchEvent(event);
    }
    
    private double[][] findStats (int x, int y, double scale, double posX, double posY){
		double[] rawIndex = new double[]{(double)(x) / (viewSize[0] * scale) - posX, ((double)(y) / (viewSize[1] * scale) - posY - heightShift) / (1 - heightOverlap)}; 
		double [] tempIndex = new double[]{(int)(rawIndex[0] - (widthShift + .5 * ((int)rawIndex[1] % 2)) * (metadata.getInt("Map", 0) % 2)),(int)(rawIndex[1])};    	
    	double[] rawPos = new double[]{(double)(x) / (viewSize[0] * scale) - posX, ((double)(y) / (viewSize[1] * scale) - posY - heightShift)};
   		double [] tempPos = new double[]{((rawPos[0]  - (widthShift + .5 * ((int)(rawPos[1] / (1 - heightOverlap)) % 2)) * (metadata.getInt("Game",0) % 2)) % 1) - 1, (rawPos[1] % (1 - heightOverlap)) - 1};
   		if (Math.abs(.5 * tempPos[0] + .25) > tempPos[1] + 1 && metadata.getInt("Map", 0) == 1){
   			tempIndex[1] = tempIndex[1] - 1;
   			tempPos[1] = tempPos[1] + .75;
   			if(tempPos[0] < - .5){
   				tempPos[0] = tempPos[0] + .5;
   				if (tempIndex[1] % 2 == 1){
   					tempIndex[0] = tempIndex[0] - 1;
   				}
   			}else{
   				tempPos[0] = tempPos[0] - .5; 
   				if (tempIndex[1] % 2 == 0){
   					tempIndex[0] = tempIndex[0] + 1;
   				}
   			}
   		}
    	return new double[][]{tempIndex,tempPos};
    }
}