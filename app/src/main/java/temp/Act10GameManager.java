package happyface.rpgtraveler.com;

import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collection;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.content.res.Configuration;
import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
//import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
//import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
//import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
//import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
//import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class Act10GameManager extends Activity {
	SharedPreferences metadata;
	SharedPreferences.Editor editor;
	DBAdapter dbAdapter = new DBAdapter(this);
	DBTables dbTables  = new DBTables(this);
	int tempInt;
	String tempString;
	Cursor curTemp1 = null;
	Cursor curTemp2 = null;
	Cursor curTemp3 = null;
	Cursor curTemp4 = null;
	String key;
//	int inherCount = 0;
//	int iDelCheck = 0;
//	int rotationFlag = 0;
	ArrayList<String> alRequiredComponents;
	ArrayList<String> alRequiredParent;
	ArrayList<Integer> alRequiredTrait;
	ArrayList<Integer> alRequiredHidden;
	ArrayList<String> alRequiredScore;
	ArrayList<String> alRequiredType;
	ArrayList<String> alRequiredPrereqComp;
	ArrayList<String> alRequiredPrereqTarget;
	ArrayList<Integer> alRequiredPrereqScore;
	ArrayList<String> alRequiredResComp;
	ArrayList<String> alRequiredResScore;

	TextView tvTitle;
	TextView tvBreakdown;
//	LinearLayout llBreakdown;
	ListView lvBreakdown;
	String sBreakdown = "";
	HorizontalScrollView hsvMap;
	ListView lvGame;
	ListView lvComp;
	ListView lvElement;
	ArrayList<String> alBreakdown;
	ArrayList<Integer> alBreakdown_id;
	ArrayList<Integer> alBreakdown_color;
	ArrayList<String> alGame0;
	ArrayList<String> alComp0;
	ArrayList<String> alElement0;
	ArrayList<Integer> alGame0_color;
	ArrayList<Integer> alComp0_color;
	ArrayList<Integer> alElement0_color;

	Button bReset;

	ScrollView svMain;
	EditText etName;
	EditText etDesc;
	Button bStore;
	Button bRemove;

	CheckBox cbSetInGame;
	
	RelativeLayout rlComp;
	TextView tvCompTitle;
	LinearLayout llCompLink;
	Spinner sCompParent;
	ArrayList<String> alCompParent;
	ArrayList<Integer> alCompParent_id;
	ArrayAdapter aaCompParent;
	CheckBox cbAction;
	Button bPosChange;
	RadioGroup rgReq;
	RadioButton rbReqNo;
	RadioButton rbReqYes;
	RadioButton rbReq1;
	Spinner sCompType;
	ArrayList<String> alCompType;
	ArrayList<String> alCompScore;
	ArrayList<String> alCompHint;
	ArrayAdapter aaCompType;
	CheckBox cbSame;
	EditText etCompScore;
	Button bCompGameChange;

	RelativeLayout rlElement;
	TextView tvElInstructions;
	Button bElPosChange;
	CheckBox cbEditable;
	CheckBox cbHidden;
	RelativeLayout rlScore;
	RelativeLayout rlInherited;
	ArrayList<Object[]> alElInher;
	RelativeLayout rlOptional;
	RelativeLayout rlPrereq;
	RelativeLayout rlRestric;

	Button bNext;

	TextWatcher twTest;
//	TextWatcher twLink;
	TextWatcher twScore;
	TextWatcher twCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity10_gamemanager);

		metadata = getSharedPreferences("metadata", 0);
		editor = metadata.edit();
		
		key = "";

		tvTitle = (TextView) findViewById(R.id.a10_tvTitle);
		tvBreakdown = (TextView) findViewById(R.id.a10_tvBreakdown);
//		llBreakdown = (LinearLayout) findViewById(R.id.a10_llBreakdown);
		lvBreakdown = (ListView) findViewById(R.id.a10_lvBreakdown);
		hsvMap = (HorizontalScrollView) findViewById(R.id.a10_hsvMap);
		lvGame = (ListView) findViewById(R.id.a10_lvGame);
		lvComp = (ListView) findViewById(R.id.a10_lvComponent);
		lvElement = (ListView) findViewById(R.id.a10_lvSub);
		alBreakdown = new ArrayList<String>();
		alBreakdown_id = new ArrayList<Integer>();
		alBreakdown_color = new ArrayList<Integer>();
		alGame0 = new ArrayList<String>(Arrays.asList(""));
		alComp0 = new ArrayList<String>(Arrays.asList(""));
		alElement0 = new ArrayList<String>(Arrays.asList(""));
		alGame0_color = new ArrayList<Integer>();
		alComp0_color = new ArrayList<Integer>();
		alElement0_color = new ArrayList<Integer>();

		bReset = (Button) findViewById(R.id.a10_bReset);

		svMain = (ScrollView) findViewById(R.id.a10_svMain);
		etName = (EditText) findViewById(R.id.a10_etName);
		etDesc = (EditText) findViewById(R.id.a10_etDescription);
		bStore = (Button) findViewById(R.id.a10_bStore);
		bRemove = (Button) findViewById(R.id.a10_bRemove);
		
		cbSetInGame = (CheckBox) findViewById(R.id.a10_cbSetInGame);
		
		rlComp = (RelativeLayout) findViewById(R.id.a10_rlComp);
		tvCompTitle = (TextView) findViewById(R.id.a10_tvCompTitle);
		llCompLink = (LinearLayout) findViewById(R.id.a10_llCompLink);
		sCompParent = (Spinner) findViewById(R.id.a10_sCompParent);
		alCompParent = new ArrayList<String>();
		alCompParent_id = new ArrayList<Integer>();
		aaCompParent = new ArrayAdapter(getBaseContext(), R.layout.my_list_items, alCompParent);
		sCompParent.setAdapter(aaCompParent);
		cbAction = (CheckBox) findViewById(R.id.a10_cbAction);
		bPosChange = (Button) findViewById(R.id.a10_bCompOrderUp);
		rgReq = (RadioGroup) findViewById(R.id.a10_rgReq);
		rbReqNo = (RadioButton) findViewById(R.id.a10_rbReqNo);
		rbReqYes = (RadioButton) findViewById(R.id.a10_rbReqYes);
		rbReq1 = (RadioButton) findViewById(R.id.a10_rbReq1);
		sCompType = (Spinner) findViewById(R.id.a10_sCompType);
		alCompType = new ArrayList<String>(Arrays.asList("Select Type", "Object", "Equation", "Psudo-random Score", "Dynamic Score", "Action"));
		alCompScore = new ArrayList<String>(Arrays.asList("Select Type", "c", "e", "d", "r", "a"));
		alCompHint = new ArrayList<String>(Arrays.asList("Select Type", "", "constant", "##of##D##", "##D##", ""));
		aaCompType = new ArrayAdapter(this, R.layout.my_list_items, alCompType);
		cbSame = (CheckBox) findViewById(R.id.a10_cbSame);
		etCompScore = (EditText) findViewById(R.id.a10_etCompScore);
		sCompType.setAdapter(aaCompType);
		bCompGameChange = (Button) findViewById(R.id.a10_bCompGameChange);

		rlElement = (RelativeLayout) findViewById(R.id.a10_rlElement);
		tvElInstructions = (TextView) findViewById(R.id.a10_tvElInsructions);
		bElPosChange = (Button) findViewById(R.id.a10_bElOrderUp);
		cbEditable = (CheckBox) findViewById(R.id.a10_cbEditable);
		cbHidden = (CheckBox) findViewById(R.id.a10_cbHidden);
		rlScore = (RelativeLayout) findViewById(R.id.a10_rlScores);
		rlInherited = (RelativeLayout) findViewById(R.id.a10_rlInherited);
		rlOptional = (RelativeLayout) findViewById(R.id.a10_rlOptional);
		rlPrereq = (RelativeLayout) findViewById(R.id.a10_rlPrereq);
		rlRestric = (RelativeLayout) findViewById(R.id.a10_rlRestric);

		bNext = (Button) findViewById(R.id.a10_bNext);

		twTest = new TextWatcher() {
			public void afterTextChanged(Editable arg0) {
				if (!key.equals("")) {
					dbTables.open();
					int restart = 0;
					etName.getBackground().clearColorFilter();
					bStore.setVisibility(View.VISIBLE);
					bRemove.setVisibility(View.GONE);
					tvBreakdown.setText(sBreakdown);
					Boolean dupflag = false;
					curTemp1 = dbTables.db.rawQuery("SELECT _ID FROM LOCAL WHERE NAME LIKE \"" + etName.getText().toString() + "\"",null);
					Log.e("LogFlag", "count = " + curTemp1.getCount() + ", query = SELECT _ID FROM LOCAL WHERE NAME LIKE \"" + etName.getText().toString() + "\"");
					if(curTemp1.getCount()>0){
						dupflag = true;
					}
					curTemp1.close();

					if(metadata.getInt(key,0)==0){
						Log.e("LogFlag", "checker, dupflag = " + dupflag + ", etName = " + etName.getText().toString() + ", etDesc = " + etDesc.getText().toString());
						if(key.equals("Game")){
							tvBreakdown.setText("copy this game");
						}
						if(dupflag || etName.getText().toString().equals("") || etDesc.getText().toString().equals("")){
							bStore.setVisibility(View.GONE);
							if(dupflag) {
								etName.getBackground().setColorFilter(0xFFD06E6E, PorterDuff.Mode.SRC_ATOP);
							}
 						}
					} else {
						bStore.setText("Update");
						bStore.setVisibility(View.VISIBLE);
						bRemove.setVisibility(View.VISIBLE);
						curTemp1 = dbTables.getRow(metadata.getInt(key, 0),"LOCAL");
						if((!curTemp1.getString(2).equals(etName.getText().toString()) && dupflag) || (curTemp1.getString(2).equals(etName.getText().toString()) && curTemp1.getString(3).equals(etDesc.getText().toString()))){
							bStore.setVisibility(View.GONE);
							if(!curTemp1.getString(2).equals(etName.getText().toString()) && dupflag){
								etName.getBackground().setColorFilter(0xFFD06E6E, PorterDuff.Mode.SRC_ATOP);
							}
						}
					}


					if (etName.getText().toString().equals("") || etDesc.getText().toString().equals("")) {
						bStore.setVisibility(View.GONE);
					} else {
						if (metadata.getInt(key, 0) == 0){
							if(key.equals("Game")) {
								tvBreakdown.setText("copy this game");
							}
						} else {
							bStore.setVisibility(View.VISIBLE);
							bRemove.setVisibility(View.GONE);
							curTemp1 = dbTables.getRow(metadata.getInt(key, 0), "LOCAL");
							if (etName.getText().toString().equals(curTemp1.getString(2))
									&& etDesc.getText().toString().equals(curTemp1.getString(3))) {
								restart = 1;
							} else if (Act00LogonMaster.dbToList(getBaseContext(),0, key, 0, 0, 0, 0, 0, "ALL", 0, 0, 2).contains(etName.getText().toString())
									&& !etName.getText().toString().equals(curTemp1.getString(2))) {
								bStore.setVisibility(View.GONE);
								etName.getBackground().setColorFilter(0xFFD06E6E, PorterDuff.Mode.SRC_ATOP);
							}
							curTemp1.close();
						}
					}
					if (restart == 1) {
						if (key == "Game") {
							createView();
						} else if (key == "Component") {
							gameView();
						} else {
							compView();
						}
					}
					dbTables.close();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}
		};

		etName.addTextChangedListener(twTest);
		etDesc.addTextChangedListener(twTest);

		bReset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editor.remove(key);
				editor.commit();
				clear();
				if(key.equals("Game")) {
					createView();
				} else if(key.equals("Component")) {
					gameView();
				} else {
					compView();
				}
			}
		});
//	need to correct the creation from the arrays
		bStore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			dbTables.open();
			if (bStore.getText().toString().equals("Create")) {
				int iGameId = (int) dbTables.createLocal(key, etName.getText().toString(), etDesc.getText().toString(), -1);
				editor.putInt(key,iGameId);
				editor.commit();
				if (key == "Game") {
					for (int i = 1; i < 1000; i++){
						if (Act00LogonMaster.dbToList(getBaseContext(), 0, "Game", 0, 0, 0, 0, i, "ALL", 0, 0, 0).size() == 0){
							for(int j = i; j > 1; j--){
								int iLinkId = (Integer) Act00LogonMaster.dbToList(getBaseContext(), 0, "Game", 0, 0, 0, 0, j-1, "ALL", 0, 0, 0).get(0);
								Cursor tempCur = dbTables.getRow(iLinkId, "LINKS");
								dbTables.updateLink(iLinkId, tempCur.getInt(2), tempCur.getInt(3), tempCur.getInt(4), tempCur.getInt(5), j, tempCur.getString(7), tempCur.getInt(8), tempCur.getInt(9));
								tempCur.close();
							}
							i = 1000;
						}
					}
					dbTables.createLink(iGameId, 0, 0, -1, iGameId, 1, "0", 1, 1, -1);
					editor.putInt(key,iGameId);
					editor.commit();

//					initiate required information
					alRequiredComponents = Act00LogonMaster.alRequiredComponents;
					alRequiredParent = Act00LogonMaster.alRequiredParent;
					alRequiredTrait = Act00LogonMaster.alRequiredTrait;
					alRequiredHidden = Act00LogonMaster.alRequiredHidden;
					alRequiredScore = Act00LogonMaster.alRequiredScore;
					alRequiredType = Act00LogonMaster.alRequiredType;
					alRequiredPrereqComp = Act00LogonMaster.alRequiredPrereqComp;
					alRequiredPrereqTarget = Act00LogonMaster.alRequiredPrereqTarget;
					alRequiredPrereqScore = Act00LogonMaster.alRequiredPrereqScore;
					alRequiredResComp = Act00LogonMaster.alRequiredResComp;
					alRequiredResScore = Act00LogonMaster.alRequiredResScore;

					//		chainging the names in the required arrays to id's
					dbTables.open();
					Boolean endFlag;
					curTemp1 = dbTables.getAllLocal("ALL");
					while (!curTemp1.isAfterLast()) {
						int iTemp = 0;
						endFlag = true;
						while (endFlag) {
							endFlag = false;
							if (iTemp < alRequiredComponents.size()) {
								if (alRequiredComponents.get(iTemp).equals(curTemp1.getString(2))) {
									alRequiredComponents.set(iTemp, curTemp1.getString(0));
								}
								endFlag = true;
							}
							if (iTemp < alRequiredParent.size()) {
//								if (alRequiredParent.get(iTemp).equals("Game")) {
//									alRequiredParent.set(iTemp, iGameId + "");
//								} else
 								if (alRequiredParent.get(iTemp).equals(curTemp1.getString(2))) {
									alRequiredParent.set(iTemp, curTemp1.getString(0));
								}
								endFlag = true;
							}
							if (iTemp < alRequiredPrereqComp.size()) {
								if (alRequiredPrereqComp.get(iTemp).equals(curTemp1.getString(2))) {
									alRequiredPrereqComp.set(iTemp, curTemp1.getString(0));
								}
								endFlag = true;
							}
							if (iTemp < alRequiredPrereqTarget.size()) {
								if (alRequiredPrereqTarget.get(iTemp).equals(curTemp1.getString(2))) {
									alRequiredPrereqTarget.set(iTemp, curTemp1.getString(0));
								}
								endFlag = true;
							}
							if (iTemp < alRequiredResComp.size()) {
								if (alRequiredResComp.get(iTemp).equals(curTemp1.getString(2))) {
									alRequiredResComp.set(iTemp, curTemp1.getString(0));
								}
								endFlag = true;
							}
							if (iTemp < alRequiredResScore.size()) {
								Log.e("LogFlag", "index = " + iTemp + " | alRequiredResScore(iTemp) = " + alRequiredResScore.get(iTemp));
								if(alRequiredResScore.get(iTemp).length() >= 5) {
									if (alRequiredResScore.get(iTemp).substring(3, 5).equals("id")) {
										if (alRequiredResScore.get(iTemp).substring(5).equals(curTemp1.getString(2))) {
											alRequiredResScore.set(iTemp, alRequiredResScore.get(iTemp).substring(0, 5) + curTemp1.getString(0));
										}
									}
								}
								endFlag = true;
							}
							iTemp++;
						}
						curTemp1.moveToNext();
					}
					curTemp1.moveToFirst();
					// create necessary components and elements
//					ArrayList<String> alCompName = new ArrayList<String>();
//					ArrayList<String> alCompCat = new ArrayList<String>();
//					ArrayList<Integer> alCompId = new ArrayList<Integer>();
//					while (!curTemp1.isAfterLast()){
//						if(Act00LogonMaster.alRequiredComponents.contains(curTemp1.getString(2)) || Act00LogonMaster.alRequiredParent.contains(curComp.getString(2))){
//							alCompName.add(curTemp1.getString(2));
//							alCompCat.add(curTemp1.getString(1));
//							alCompId.add(curTemp1.getInt(0));
//						}
//						curTemp1.moveToNext();
//					}
//					curTemp1.close();
					for(int i=0; i<alRequiredComponents.size();i++){
						int iCount = 0;
						for (int j=0; j<=i; j++){
							Log.e("LogFlag", "index - " + j + ", alReqComp(i) - " + alRequiredComponents.get(i) + ", alReqComp(j) - " + alRequiredComponents.get(j));
							if(alRequiredParent.get(j) == alRequiredParent.get(i)){
								iCount = iCount + 1;
							}
						}
//						int parentId = alCompId.get(alCompName.indexOf(Act00LogonMaster.alRequiredParent.get(i)));
//						if(Act00LogonMaster.alRequiredParent.get(i).equals("Game")){
//							parentId = metadata.getInt("Game",0);
//						}
						int linkId = (int)dbTables.createLink(metadata.getInt("Game", 0),0,0,Integer.parseInt(alRequiredParent.get(i)),Integer.parseInt(alRequiredComponents.get(i)),iCount,alRequiredScore.get(i), alRequiredTrait.get(i), alRequiredHidden.get(i), -1);
						if (Act00LogonMaster.alRequiredType.get(i).equals("ALL")){
							dbTables.createInherit(linkId, 0, -1);
						} else if (Act00LogonMaster.alRequiredType.get(i).equals("ONE")){
							dbTables.createInherit(linkId, 1, -1);
						}
					}
//					include all the prerequisits and restrictions.
					for(int j = 0; j<alRequiredPrereqComp.size(); j++){
						dbTables.createPrereq(metadata.getInt("Game", 0),Integer.parseInt(alRequiredPrereqComp.get(j)), Integer.parseInt(alRequiredPrereqTarget.get(j)),alRequiredPrereqScore.get(j),-1);
					}
					for (int j=0; j<alRequiredResComp.size(); j++){
						dbTables.createRes(metadata.getInt("Game", 0), Integer.parseInt(alRequiredResComp.get(j)), alRequiredResScore.get(j), -1);
					}
					gameView();
				} else if (key == "Component") {
					gameView();
				} else if (key != "") {
					compView();
				}
			} else {
				dbTables.updateLocal(metadata.getInt(key, 0), etName.getText().toString(), etDesc.getText().toString());
				if (key == "Game") {
					createView();
				} else if (key == "Component") {
					gameView();
				} else if (key != "") {
					compView();
				}
			}
			dbTables.close();
			}
		});

		bRemove.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			dbTables.open();

			Cursor curTempA = dbTables.getAllLink(0, 0, metadata.getInt(key, 0));
			Cursor curTempB = dbTables.getAllLink(metadata.getInt("Game", 0), 0, metadata.getInt(key, 0));
			int iOtherGames = curTempA.getCount() - curTempB.getCount();
			curTempA.close();
			curTempB.close();
			if (iOtherGames > 0){
				Act00LogonMaster.centerToast(getBaseContext(), "Cannot be deleted.  Currently this element is used in " + iOtherGames + " other games.");
			} else {
				final Object[] view = Act00LogonMaster.verify(getBaseContext(),svMain);
				((Button)view[1]).setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						dbTables.open();
						Cursor curTempB =  dbTables.getAllLink(metadata.getInt("Game", 0), 0, metadata.getInt(key, 0));
						((PopupWindow)view[0]).dismiss();
						Log.e("LogFlag", "link count: " + curTempB.getCount());
						if (curTempB.getCount() > 0){
							Act00LogonMaster.removeFromGame(getBaseContext(),curTempB.getInt(0));
						} else {
							dbTables.deleteRow(metadata.getInt(key, 0), "LOCAL");
						}
						Cursor curTempA = dbTables.getRow(metadata.getInt(key, 0), "LOCAL");
						if(curTempA.getCount() == 0){
							editor.remove(key);
							editor.commit();
						}
						curTempA.close();
						curTempB.close();
						clear();
						if (key.equals("Game") || key.equals("")){
							createView();
						} else if (key.equals("Component")) {
							gameView();
						} else {
							compView();
						}
					}
				});
			}
			dbTables.close();
			}
		});

		twScore = new TextWatcher() {
			String oldString = "";

			@Override
			public void afterTextChanged(Editable current) {
				if (!key.equals("")){
					if (!key.equals("")) {
						int HintIndex = alCompTypeHint.indexOf(((EditText) getCurrentFocus()).getHint());
						if ((int)getCurrentFocus().getId() == R.id.a10_etCompScore) {
							gameView();
						} else {
							int Id = (int)getCurrentFocus().getId();
							if (HintIndex == 3 || HintIndex == 4) {
								try {
									Button but = (Button) findViewById(Id + 1);
									but.setVisibility(View.GONE);
									if ((current.length() == ((EditText) getCurrentFocus()).getHint().length())) {
										but.setVisibility(View.VISIBLE);
									}
								} catch (Exception e) {
									Log.e("LogFlag", "button counldn't be altered due to " + e);
								}
							}
						}
						if (oldString.length() == current.length() - 1) {
							if ((HintIndex == 4 && current.length() == 2) || (HintIndex == 3 && current.length() == 6)) {
								((EditText) getCurrentFocus()).setInputType(InputType.TYPE_CLASS_TEXT);
								current.replace(current.length(), current.length(), "D");
								((EditText) getCurrentFocus()).setInputType(InputType.TYPE_CLASS_NUMBER);
							} else if (HintIndex == 3 && current.length() == 2) {
								((EditText) getCurrentFocus()).setInputType(InputType.TYPE_CLASS_TEXT);
								current.replace(current.length(), current.length(), "of");
								((EditText) getCurrentFocus()).setInputType(InputType.TYPE_CLASS_NUMBER);
							}
						} else if (oldString.length() == current.length() + 1) {
							if ((HintIndex == 4 && current.length() == 2) || (HintIndex == 3 && current.length() == 6)) {
								current.replace(current.length() - 1, current.length(), "");
							} else if (HintIndex == 3 && current.length() == 3) {
								current.replace(1, 3, "");
							}
						}
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				oldString = s.toString();
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		};

		etCompScore.addTextChangedListener(twScore);

		cbSame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (!key.equals("")) {
					gameView();
				}
			}
		});


		cbEditable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				if (!key.equals("")) {
					dbTables.open();
					Cursor curTempA = dbTables.getAllLink(metadata.getInt("Game", 0), metadata.getInt("Component", 0), metadata.getInt(key,0));
					Cursor curTempB = dbTables.getRow(metadata.getInt(key,0), "LOCAL");
					if(Act00LogonMaster.alRequiredComponents.contains(curTempB.getString(2))){
					    String key0 = key;
                        key = "";
                        cbEditable.setChecked(!isChecked);
                        key = key0;
						Act00LogonMaster.centerToast(getBaseContext(), "This property cannot be altered.");
					} else {
						if (isChecked) {
							dbTables.updateLink(curTempA.getInt(0), curTempA.getInt(1), curTempA.getInt(2), curTempA.getInt(3), curTempA.getInt(4), curTempA.getInt(5), curTempA.getString(6), 1, curTempA.getInt(8));
						} else {
							dbTables.updateLink(curTempA.getInt(0), curTempA.getInt(1), curTempA.getInt(2), curTempA.getInt(3), curTempA.getInt(4), curTempA.getInt(5), curTempA.getString(6), 0, curTempA.getInt(8));
						}
					}
					curTempB.close();
					curTempA.close();
					dbTables.close();
				}
			}
		});

		cbHidden.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				if (!key.equals("")) {
					dbTables.open();
					Cursor curTempA = dbTables.getAllLink(metadata.getInt("Game", 0), metadata.getInt("Component", 0), metadata.getInt(key,0));
					Cursor curTempB = dbTables.getRow(metadata.getInt(key,0), "LOCAL");
					if (Act00LogonMaster.alRequiredComponents.contains(curTempB.getString(2))) {
                        String key0 = key;
                        key = "";
                        cbHidden.setChecked(!isChecked);
                        key = key0;
                        Act00LogonMaster.centerToast(getBaseContext(), "this property cannot be altered.");
                    } else {
                        if (isChecked) {
							dbTables.updateLink(curTempA.getInt(0), curTempA.getInt(1), curTempA.getInt(2), curTempA.getInt(3), curTempA.getInt(4), curTempA.getInt(5), curTempA.getString(6), curTempA.getInt(7), 0);
						} else {
							dbTables.updateLink(curTempA.getInt(0), curTempA.getInt(1), curTempA.getInt(2), curTempA.getInt(3), curTempA.getInt(4), curTempA.getInt(5), curTempA.getString(6), curTempA.getInt(7), 1);
						}
					}
					curTempB.close();
					curTempA.close();
					dbTables.close();
				}
			}
		});

		cbSetInGame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
			if (!key.equals("")){
				dbTables.open();
				Cursor curTempA = dbTables.getRow(metadata.getInt(key,0), "LOCAL");
                if(Act00LogonMaster.alRequiredComponents.contains(curTempA.getString(2))){
                    String key0 = key;
                    key = "";
                    cbSetInGame.setChecked(!isChecked);
                    key = key0;
                    Act00LogonMaster.centerToast(getBaseContext(), "this property cannot be altered");
                    curTempA.close();
                } else {
                    if (isChecked) {
                        curTempA = dbTables.getAllLink(metadata.getInt("Game", 0), 0, metadata.getInt("Game", 0));
                        int majVer = curTempA.getInt(2);
                        int nextVer = curTempA.getInt(3) + 1;
                        int focusId = metadata.getInt(key, 0);
                        int baseId;
                        int orderMax = 1;
                        if (key.equals("Component")) {
                            baseId = 0;
                        } else {
                            baseId = metadata.getInt("Component", 0);
                        }
                        curTempA = dbTables.getAllLocal(key);
                        if (curTempA.getCount() > 0) {
                            curTempA.moveToFirst();
                        }
                        while (!curTempA.isAfterLast()) {
                            Cursor curTempB = dbTables.getAllLink(metadata.getInt("Game", 0), 0, curTempA.getInt(0));
                            if (curTempB.getCount() > 0) {
                                orderMax = Math.max(orderMax, curTempB.getInt(6));
                            }
                            curTempB.close();
                            curTempA.moveToNext();
                        }
                        int newOrder = curTempA.getCount() + 1;
                        for (int i = orderMax; i > 0; i--) {
                            if (Act00LogonMaster.dbToList(getBaseContext(), metadata.getInt("Game", 0), key, 0, 0, 0, 0, i, "ALL", 0, 0, 0).size() > 0) {
                                curTempA = dbTables.getRow((Integer) Act00LogonMaster.dbToList(getBaseContext(), metadata.getInt("Game", 0), key, 0, 0, 0, 0, i, "ALL", 0, 0, 0).get(0), "LINKS");
                                dbTables.updateLink(curTempA.getInt(0), curTempA.getInt(2), curTempA.getInt(3), curTempA.getInt(4), curTempA.getInt(5), newOrder, curTempA.getString(7), curTempA.getInt(8), curTempA.getInt(9));
                                newOrder = newOrder - 1;
                            }
                        }
                        dbTables.createLink(metadata.getInt("Game", 0), majVer, nextVer, baseId, focusId, 1, "", 0, 0, -1);
                    } else {
                        int curCount;
                        if (key.equals("Component")) {
                            curTempA = dbTables.getAllLink(metadata.getInt("Game", 0), metadata.getInt(key, 0), 0);
                            curCount = curTempA.getCount() + 1;
                        } else {
                            curTempA = dbTables.getAllLink(metadata.getInt("Game", 0), 0, metadata.getInt(key, 0));
                            curCount = curTempA.getCount();
                        }
                        if (curCount > 1) {
                            Act00LogonMaster.centerToast(getBaseContext(), "Currently being used. Cannot be removed from game.");
                        } else {
                            Cursor curTempB = dbTables.getAllLink(metadata.getInt("Game", 0), 0, metadata.getInt(key, 0));
                            Act00LogonMaster.removeFromGame(getBaseContext(), curTempB.getInt(0));
                            curTempB.close();
                        }
                        curTempA = dbTables.getRow(metadata.getInt(key, 0), "LOCAL");
                        if (curTempA.getCount() == 0) {
                            editor.remove(key);
                            editor.commit();
                        }
                    }
					curTempA.close();
                    if (key.equals("Component")) {
                        gameView();
                    } else {
                        compView();
                    }
                }
                dbTables.close();
			}
			}
		});

		sCompParent.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				dbTables.open();
				gameDetailsSetter();
				dbTables.close();
//				gameView();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		sCompType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (key != "") {
					dbTables.open();
					gameDetailsSetter();
					dbTables.close();
//					gameView();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		rgReq.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int checked) {
				Log.e("LogFlag", "game change should be here: key " + key);
				if (!key.equals("")) {
//					gameView();
					dbTables.open();
					Log.e("LogFlag", "game change should be here");
					gameDetailsSetter();
					dbTables.close();
				}
			}
		});
		
		lvBreakdown.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			Log.e("LogFlag", "alBreakdown is nothing test = " + alBreakdown.get(position).equals("") + " | key = " + key);
			dbTables.open();
			if (key == "Game" && !tvBreakdown.getText().toString().equals("copy a game")) {
				editor.putInt("Game", alBreakdown_id.get(position));
				editor.commit();
				createView();
			} else if (key == "Game") {
				Cursor curTempA = dbTables.getAllLink(alBreakdown_id.get(position), -1, alBreakdown_id.get(position));
				editor.putInt("Game", (int) dbTables.createLocal("Game", etName.getText().toString(), etDesc.getText().toString(), -1));
				editor.commit();
				int majVer = curTempA.getInt(2);
				int minVer = curTempA.getInt(3);
				Cursor TemplateLink = dbTables.getAllLink(alBreakdown_id.get(position), 0, 0);
				while (!TemplateLink.isAfterLast()) {
					if (majVer >= TemplateLink.getInt(2) && minVer >= TemplateLink.getInt(3)) {
						int linkid = 0;
						if (TemplateLink.getInt(4) == alBreakdown_id.get(position)) {
							linkid = (int) dbTables.createLink(metadata.getInt("Game", 0), 0, 0,
									metadata.getInt("Game", 0), TemplateLink.getInt(5), TemplateLink.getInt(6), TemplateLink.getString(7), TemplateLink.getInt(8), TemplateLink.getInt(9), TemplateLink.getInt(8));
						} else if (TemplateLink.getInt(5) == alBreakdown_id.get(position)) {
							linkid = (int) dbTables.createLink(metadata.getInt("Game", 0), 0, 0,
									TemplateLink.getInt(4), metadata.getInt("Game", 0), TemplateLink.getInt(6), "0", TemplateLink.getInt(8), TemplateLink.getInt(9), -1);
						} else {
							linkid = (int) dbTables.createLink(metadata.getInt("Game", 0), 0, 0,
									TemplateLink.getInt(4), TemplateLink.getInt(5), TemplateLink.getInt(6), TemplateLink.getString(7), TemplateLink.getInt(8),  TemplateLink.getInt(9), TemplateLink.getInt(8));
						}
						curTempA = dbTables.getAllInherit(TemplateLink.getInt(0));
						if (curTempA.getCount() == 1) {
							dbTables.createInherit(linkid, curTempA.getInt(2), -1);
						}
					}
					TemplateLink.moveToNext();
				}
				TemplateLink.close();
				curTempA.close();
				gameView();
			} else if (key.equals("Component")){
				Log.e("LogFlag", "item selected = " + alBreakdown.get(position) + " | id selected = " + alBreakdown_id.get(position));
				if(!alBreakdown.get(position).equals("")){
					Log.e("LogFlag", "Game Id = " + metadata.getInt("Game",0));
					key = "";
					if (alBreakdown.get(position).equals("Game")){
						editor.remove("Component");
					} else {
						editor.putInt("Component", alBreakdown_id.get(position));
					}
					editor.commit();
					key = "Component";
					gameView();
				}
			} else if (!key.equals("")) {
				String key0 = key;
				key = "";
				editor.putInt(key0, alBreakdown_id.get(position));
				editor.commit();
				key = key0;
				compView();
			}
			dbTables.close();
			}
		});
		
		bPosChange.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				dbTables.open();
				Cursor curTempA = dbTables.getAllLink(metadata.getInt("Game", 0), 0, metadata.getInt("Component",0));
				Cursor curTempB = dbTables.getAllLink(metadata.getInt("Game", 0), curTempA.getInt(4), 0);
				while(curTempB.getInt(0)!=curTempA.getInt(0)){
					curTempB.moveToNext();
				}
				curTempB.moveToPrevious();
				Act00LogonMaster.moveInOrderTo(getBaseContext(), curTempA.getInt(0), curTempB.getInt(6));
				curTempA.close();
				curTempB.close();
				goToView();
				dbTables.close();
			}
		});

		bElPosChange.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				dbTables.open();
				Cursor curTempA = dbTables.getAllLink(metadata.getInt("Game",0),metadata.getInt("Component",0),metadata.getInt(key,0));
				Cursor curTempB = dbTables.getAllLink(metadata.getInt("Game", 0), curTempA.getInt(4), 0);
				while(curTempB.getInt(0)!=curTempA.getInt(0)){
					curTempB.moveToNext();
				}
				curTempB.moveToPrevious();
				Act00LogonMaster.moveInOrderTo(getBaseContext() , curTempA.getInt(0), curTempB.getInt(6));
				curTempA.close();
				curTempB.close();
				goToView();
				dbTables.close();
			}
		});

		bCompGameChange.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			dbTables.open();
			Cursor curTempA = dbTables.getRow(metadata.getInt("Component",0), "LOCAL");
			Log.e("LogFlag", "Component name = " + curTempA.getString(2));
			if (Act00LogonMaster.alRequiredComponents.contains(curTempA.getString(2))) {
				Act00LogonMaster.centerToast(getBaseContext(), "This element cannot be changed.");
			} else if (Act00LogonMaster.dbToList(getBaseContext(), metadata.getInt("Game",0), curTempA.getString(2), 0, 0, 0, 0, 0, "ALL", 0, 0, 0).size() > 0) {
				Act00LogonMaster.centerToast(getBaseContext(), "Remove the elements from this game before changing the component structure");
			} else {
				Cursor curTempB = dbTables.getAllLink(metadata.getInt("Game", 0), 0, metadata.getInt("Component", 0));
				int linkId = curTempB.getInt(0);
				Log.e("LogFlag", "Par Focus id size - " + alCompParent_id.size());// + alCompTypeScore.get(sCompType.getSelectedItemPosition()));
				dbTables.updateLink(linkId,curTempB.getInt(2),curTempB.getInt(3),alCompParent_id.get(sCompParent.getSelectedItemPosition()),curTempA.getInt(0),curTempB.getInt(6),alCompTypeScore.get(sCompType.getSelectedItemPosition()) + " | ",curTempB.getInt(8), curTempB.getInt(9));
				curTempB = dbTables.getAllInherit(linkId);
				if (curTempB.getCount() == 0) {
					if (rbReqYes.isChecked()) {
						dbTables.createInherit(linkId, 0, -1);
					} else if (rbReq1.isChecked()) {
						dbTables.createInherit(linkId, 1, -1);
					}
				} else if (curTempB.getCount() == 1) {
					if (rbReqNo.isChecked()) {
						dbTables.deleteRow(dbTables.getAllInherit(linkId).getInt(0), "INHER");
					} else if (rbReqYes.isChecked()) {
						dbTables.updateInher(linkId, 0);
					} else if (rbReq1.isChecked()) {
						dbTables.updateInher(linkId, 1);
					}
				}
				curTempB.close();
			}
			curTempA.close();
			dbTables.close();
			gameView();
			}
		});

		lvGame.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			cbSetInGame.setVisibility(View.GONE);
			int game0_id = metadata.getInt("Game", 0);
			editor.clear();
			editor.putInt("Game", game0_id);
			editor.commit();
			createView();
			}
		});

		lvComp.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			int game0_id = metadata.getInt("Game", 0);
			int comp0_id = metadata.getInt("Component", 0);
			editor.clear();
			editor.putInt("Game", game0_id);
			editor.putInt("Component", comp0_id);
			editor.commit();
			gameView();
			}
		});

		lvElement.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			int game0_id = metadata.getInt("Game", 0);
			int comp0_id = metadata.getInt("Component", 0);
			editor.clear();
			editor.putInt("Game", game0_id);
			editor.putInt("Component", comp0_id);
			editor.commit();
			compView();
			}
		});



		bNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			if (key == "Game") {
				clear();
				gameView();
			} else if (key == "Component") {
				clear();
				compView();
			} else {
				String score = "1";
				if(bNext.getText().equals("Save and Quit")){
					score = "0";
				}
				int majVerMax = 0;
				int minVerMax = 0;
				dbTables.open();
				Cursor curStuff = dbTables.getAllLink(metadata.getInt("Game", 0), 0, 0);
				while (!curStuff.isAfterLast()){
					majVerMax = Math.max(majVerMax, curStuff.getInt(2));
					minVerMax = Math.max(minVerMax, curStuff.getInt(3));
					curStuff.moveToNext();
				}
				curStuff.close();
				finish();
				majVerMax = Math.max(1,majVerMax);
				minVerMax = Math.max(1,minVerMax);
				Cursor curTempA = dbTables.getAllLink(metadata.getInt("Game", 0),0, metadata.getInt("Game",0));
				int gameLink = curTempA.getInt(0);
				dbTables.updateLink(gameLink, majVerMax, minVerMax, -1, metadata.getInt("Game", 0), curTempA.getInt(6), score, 0, 0);
				dbTables.close();
				if(score.equals("0")){
					startActivity(new Intent(getBaseContext(), Act00LogonMaster.class));
					curTempA.close();
				} else {
					String query = "SELECT FOCUS.NAME, SCORE.SCORE, EL.NAME " +
							"FROM LOCAL AS FOCUS JOIN LINKS AS MAP ON FOCUS._ID = MAP.PARENT JOIN " +
							"LINKS AS LINK ON MAP.FOCUS = LINK.PARENT JOIN LOCAL AS EL ON LINK.FOCUS = EL._ID JOIN " +
							"LINKS AS SCORE ON LINK.FOCUS = SCORE.FOCUS JOIN LOCAL AS CAT ON CAT._ID = SCORE.PARENT " +
							"WHERE FOCUS.NAME LIKE \"theGame\" AND CAT.NAME LIKE \"MapType\" AND MAP.GAME_ID = " + metadata.getInt("Game", 0) + " AND LINK.GAME_ID = " + metadata.getInt("Game", 0) + " AND SCORE.GAME_ID = " + metadata.getInt("Game", 0);
					curTempA = dbAdapter.db.rawQuery(query, null);
					curTempA.moveToFirst();
					editor.putInt("Map", curTempA.getInt(1));
                    curTempA.close();
                    editor.putString("user", "GM");
                    editor.commit();
                    try {
                        startActivity(new Intent(getBaseContext(), Class.forName(metadata.getString("current", ""))));
                    } catch (Exception e) {
                        startActivity(new Intent(getBaseContext(), Act11GroupManager.class));
                    }
				}
			}
			}
		});
		dbTables.close();
		goToView();
	}

	public void goToView(){
		dbTables.open();
		curTemp1 = dbTables.getRow (metadata.getInt("Component", 0), "LOCAL");
		if (metadata.getString("key", "").equals("Component")){
			editor.remove("key");
			editor.commit();
			gameView();
		} else if (metadata.getInt("Component", 0) == 0) {
			curTemp1.close();
			createView();
		} else if (metadata.getInt(curTemp1.getString(2), 0) == 0) {
	   		curTemp1.close();
			gameView();
		} else {
			curTemp1.close();
			compView();
		}
		dbTables.close();
	}
	
	public void createView() {
		clear();
		key = "";
		bNext.setText("Edit Game");
		sBreakdown = "pick a game to change";
		LayoutParams param = (LayoutParams) bNext.getLayoutParams();
		param.addRule(RelativeLayout.BELOW, R.id.a10_rlLocal);
		bNext.setLayoutParams(param);
		svMain.setBackgroundColor(0xFFE1DFEF);
		dbTables.open();
		tvBreakdown.setText(sBreakdown);
		tvTitle.setText("Game Creation");
		bStore.setVisibility(View.GONE);
		bRemove.setVisibility(View.GONE);
		rlComp.setVisibility(View.GONE);
		rlElement.setVisibility(View.GONE);
		lvComp.setVisibility(View.GONE);
		lvElement.setVisibility(View.GONE);
		bNext.setVisibility(View.VISIBLE);
		
		if (metadata.getInt("Game", 0) == 0) {
			bNext.setVisibility(View.GONE);
			bRemove.setVisibility(View.GONE);
			etName.getText().clear();
			etDesc.getText().clear();
			bStore.setText("Create");
			hsvMap.setVisibility(View.GONE);
			bReset.setVisibility(View.GONE);
		} else {
			alGame0.clear();
			alGame0_color.clear();
			curTemp1 = dbTables.getRow(metadata.getInt("Game", 0), "LOCAL");
			alGame0.add(curTemp1.getString(2));
			ArrayList<int[]> error = Errors(metadata.getInt("Game", 0), 0, 0);
			if (error.size() > 0) {
				alGame0_color.add(0xFFD06E6E);
			} else {
				alGame0_color.add(0xFFE1DFEF);
			}
			etName.setText(curTemp1.getString(2));
			etDesc.setText(curTemp1.getString(3));
			curTemp1.close();
			bRemove.setVisibility(View.VISIBLE);
			bStore.setText("Update");
			lvGame.setAdapter(new AdapterAAColor(getBaseContext(), R.layout.my_list_items, alGame0, alGame0_color));
			hsvMap.setVisibility(View.VISIBLE);
			bReset.setVisibility(View.VISIBLE);
		}

		alBreakdown.clear();
		alBreakdown_id.clear();
		ArrayList<Integer> alBreakdown_error = new ArrayList<Integer>();
		alBreakdown_color.clear();
		alBreakdown = Act00LogonMaster.dbToList(this, 0, "Game", 0, 0, 0, 0, 0, "ALL", 0, 0, 2);
		alBreakdown_id = Act00LogonMaster.dbToList(this, 0, "Game", 0, 0, 0, 0, 0, "ALL", 0, 0, 1);
		for (int i = 0; i < alBreakdown.size(); i++) {
			if (Errors(alBreakdown_id.get(i), 0, 0).size() > 0) {
				alBreakdown_color.add(0xFFD06E6E);
			} else {
				alBreakdown_color.add(0xFFFFFFFF);
			}
		}
		
		lvBreakdown.setAdapter(new AdapterAAColor(this, R.layout.my_list_items, alBreakdown, alBreakdown_color));

		dbTables.close();
		key = "Game";
	}

	public void gameView() {
		Log.e("LogFlag", "Component = " +metadata.getInt("Component",0));
		Log.e("LogFlag", "Game = " + metadata.getInt("Game", 0));
		Log.e("LogFlag", "key = " + key);
		clear();
		key = "";
		dbTables.open();
		svMain.setBackgroundColor(0xFFEFEFED);
		sBreakdown = "select component to edit";
		hsvMap.setVisibility(View.VISIBLE);
		bStore.setVisibility(View.GONE);
		rlElement.setVisibility(View.GONE);
		bNext.setText("Focus Elements");
		LayoutParams param = (LayoutParams) bNext.getLayoutParams();
		param.addRule(RelativeLayout.BELOW, R.id.a10_rlComp);
		bNext.setLayoutParams(param);
		tvTitle.setText("Game Alteration - Components");
		lvElement.setVisibility(View.GONE);
		tvBreakdown.setText(sBreakdown);
		etName.getBackground().setColorFilter(0x00D06E6E, PorterDuff.Mode.SRC_ATOP);
		alGame0.clear();
		alGame0_color.clear();
		curTemp1 = dbTables.getRow(metadata.getInt("Game", 0), "LOCAL");
		alGame0.add(curTemp1.getString(2));
		curTemp1.close();
		
		if (Errors(metadata.getInt("Game", 0), 0, 0).size() == 0) {
			alGame0_color.add(0xFFE1DFEF);
		} else {
			alGame0_color.add(0xFFD06E6E);
		}
		lvGame.setAdapter(new AdapterAAColor(this, R.layout.my_list_items, alGame0, alGame0_color));

		alBreakdown.clear();
		alBreakdown_id.clear();
		alBreakdown_color.clear();

		alBreakdown.addAll(Act00LogonMaster.dbToList(this, metadata.getInt("Game", 0), "Component", 0, 0, 0, 0, 0, "ALL", 0, 0, 2));
		alBreakdown_id.addAll(Act00LogonMaster.dbToList(this, metadata.getInt("Game", 0), "Component", 0, 0, 0, 0, 0, "ALL", 0, 0, 1));
		
		Cursor curComp = dbTables.getAllLocal("Component");
		
		while (!curComp.isAfterLast()) {
			tempString = curComp.getString(2);
			tempInt = curComp.getInt(0);
			if (!alBreakdown.contains(tempString)){
				alBreakdown.add(tempString);
				alBreakdown_id.add(tempInt);
			}
			curComp.moveToNext();
		}
		curComp.close();
// this is where you need to look to duplicate the "game" mode on the breakdown array list.
		for (int i=0; i<alBreakdown.size(); i++){
			curTemp1 = dbTables.getAllLink(0,0,alBreakdown_id.get(i));
			if(curTemp1.getCount()>0) {
				if (metadata.getInt("Component", 0) > 0) {
					curTemp1 = dbTables.getAllLink(0, metadata.getInt("Component", 0), alBreakdown_id.get(i));
					curTemp2 = dbTables.getAllLink(metadata.getInt("Game", 0), 0, alBreakdown_id.get(i));
					if (curTemp1.getCount() == 0 && curTemp2.getInt(4) != metadata.getInt("Component", 0)) {
						alBreakdown.remove(i);
						alBreakdown_id.remove(i);
						i = i - 1;
					}
				} else {
					curTemp1 = dbTables.getAllLink(0, 0, alBreakdown_id.get(i));
					curTemp2 = dbTables.getAllLink(metadata.getInt("Game", 0), 0, alBreakdown_id.get(i));
					curTemp3 = dbTables.getRow(curTemp2.getInt(4), "LOCAL");
					if (curTemp1.getInt(4) != 0 && !curTemp3.getString(1).equals("System")) {
						alBreakdown.remove(i);
						alBreakdown_id.remove(i);
						i = i - 1;
					}
					curTemp3.close();
				}
			}
		}

		Log.e("LogFlag", "Breakdown count = " + alBreakdown.size());

		curTemp1 = dbTables.getAllLink(metadata.getInt("Game",0),0,metadata.getInt("Component",0));
		int iBreakdownCount = alBreakdown.size();
		ArrayList <int[]> alErrors = Errors(metadata.getInt("Game", 0), 0, 0);
		if(curTemp1.getCount() > 0) {
			for (int j = 0; j < alErrors.size(); j++) {
				Log.e("LogFlag", "true false test: " + (!alBreakdown_id.contains(alErrors.get(j)[1])) + " " + (alErrors.get(j)[1] != metadata.getInt("Component", 0)) + " " + (metadata.getInt("Componenet", 0) == 0) + " " + (curTemp1.getInt(4) != alErrors.get(j)[1]));
				if ((!alBreakdown_id.contains(alErrors.get(j)[1])) && alErrors.get(j)[1] != metadata.getInt("Component", 0) && (metadata.getInt("Component", 0) == 0 || curTemp1.getInt(4) != alErrors.get(j)[1])) {
					curTemp2 = dbTables.getRow(alErrors.get(j)[1], "LOCAL");
					alBreakdown.add(0, curTemp2.getString(2));
					alBreakdown_id.add(0, curTemp2.getInt(0));
					curTemp2.close();
				}
			}
		}
		if(alBreakdown.size() > iBreakdownCount){
			int iSpacePlace = alBreakdown.size() - iBreakdownCount;
			alBreakdown.add(iSpacePlace,"");
			alBreakdown_id.add(iSpacePlace,0);
		}

		if (metadata.getInt("Component",0)>0) {
			curTemp1 = dbTables.getAllLink(metadata.getInt("Game", 0), 0, metadata.getInt("Component", 0));

			if (curTemp1.getCount() > 0) {
				if (curTemp1.getInt(4) > 0) {
					curTemp2 = dbTables.getRow(curTemp1.getInt(4), "LOCAL");
					alBreakdown.add(0, "");
					alBreakdown_id.add(0, 0);
					Log.e("LogFlag", curTemp2.getString(2));
					alBreakdown.add(0, curTemp2.getString(2));
					alBreakdown_id.add(0, curTemp2.getInt(0));
					curTemp2.close();
				}
			}
		}

		for (int i=0;i<alBreakdown.size();i++){
			curTemp1 = dbTables.getAllLink(metadata.getInt("Game", 0), 0, alBreakdown_id.get(i));
			Log.e("LogFlag", "alBreakdown info:  Game = " + metadata.getInt("Game", 0) + " Component = " + alBreakdown_id.get(i));
			Log.e("LogFlag", "curCount = " + curTemp1.getCount());
			if (alBreakdown.get(i).equals("")){
				alBreakdown_color.add(0xFF000000);
			} else if (curTemp1.getCount() > 0 || alBreakdown.get(i).equals("Game")) {
				int alBreakdownid = alBreakdown_id.get(i);
				if(alBreakdown.get(i).equals("Game")){
					alBreakdownid = 0;
				}
				if (Errors(metadata.getInt("Game", 0), alBreakdownid, 0).size() > 0) {
					alBreakdown_color.add(0xFFD06E6E);
				} else {
					alBreakdown_color.add(0xFFFFFFFF);
				}
			} else {
				ArrayList<int[]> error = Errors(0, alBreakdown_id.get(i), 0);
				int errorflag = 0;
				for (int j = 0; j < error.size(); j++) {
					if (error.get(j)[0] == 0 && error.get(j)[1] == alBreakdown_id.get(i) && error.get(j)[2] == 0) {
						errorflag = 1;
					}
				}
				if (errorflag == 1) {
					alBreakdown_color.add(0xFFB66464);
				} else {
					alBreakdown_color.add(0xFFDDDDDD);
				}
			}
		}
		lvBreakdown.setAdapter(new AdapterAAColor(getBaseContext(), R.layout.my_list_items, alBreakdown, alBreakdown_color));

		curTemp1.close();

		if (metadata.getInt("Component", 0) == 0) {
			lvComp.setVisibility(View.GONE);
			bRemove.setVisibility(View.GONE);
			rlComp.setVisibility(View.GONE);
			bNext.setVisibility(View.GONE);
			etName.setText("");
			etDesc.setText("");
			bStore.setText("Create");
			cbSetInGame.setVisibility(View.GONE);
			bReset.setVisibility(View.GONE);
		} else {
			cbSetInGame.setVisibility(View.VISIBLE);
			bReset.setVisibility(View.VISIBLE);
			curTemp1 = dbTables.getRow(metadata.getInt("Component", 0), "LOCAL");
			etName.setText(curTemp1.getString(2));
			etDesc.setText(curTemp1.getString(3));
			bStore.setText("Update");
			bRemove.setVisibility(View.VISIBLE);
			bRemove.setText("Remove from System");
			bCompGameChange.setVisibility(View.GONE);
			bNext.setVisibility(View.GONE);
			alComp0.clear();
			alComp0_color.clear();
			alComp0.add(curTemp1.getString(2));
			curTemp1 = dbTables.getAllLink(metadata.getInt("Game",0), 0, metadata.getInt("Component", 0));
			ArrayList<int[]> error = Errors(0, metadata.getInt("Component", 0), 0);
//			int errorflag = 0;
//			for (int j = 0; j < error.size(); j++) {
//				if (error.get(j)[0] == 0 && error.get(j)[1] == metadata.getInt("Component", 0) && error.get(j)[2] == 0) {
//					errorflag = 1;
//				}
//			}
			if (error.size()>0) {
				alComp0_color.add(0xFFB66464);
			} else {
				alComp0_color.add(0xFFDDDDDD);
			}
			if (curTemp1.getCount()==0){
				cbSetInGame.setChecked(false);
				rlComp.setVisibility(View.GONE);
				lvElement.setVisibility(View.GONE);
			} else {
				cbSetInGame.setChecked(true);
				rlComp.setVisibility(View.VISIBLE);
				curTemp2 = dbTables.getAllLink(metadata.getInt("Game", 0),curTemp1.getInt(4),0);
				if (curTemp2.getInt(0) == curTemp1.getInt(0)){
					bPosChange.setVisibility(View.GONE);
				} else {
					bPosChange.setVisibility(View.VISIBLE);
				}
				alCompParent.clear();
				curTemp2 = dbTables.getRow(curTemp1.getInt(4),"LOCAL");
				if (curTemp2.getString(1).equals("System")) {
					alCompParent.add("System Component");
					alCompParent_id.add(curTemp1.getInt(4));
					String score = curTemp1.getString(7);
					sCompType.setSelection(alCompTypeScore.indexOf(score.substring(0, 1)));
					Log.e("LogFlag", "score = " + score);
					etCompScore.setHint(alCompTypeHint.get(alCompTypeScore.indexOf(score.substring(0, 1))));
					if (score.length() <= 4) {
						cbSame.setChecked(false);
					} else {
						cbSame.setChecked(true);
						etCompScore.setText(score.substring(4));
						etCompScore.setVisibility(View.VISIBLE);
					}
				} else if(curTemp1.getInt(4) == 0){
					alCompParent.clear();
					alCompParent.add("Select Parent");
					alCompParent_id.clear();
					alCompParent_id.add(0);
					Cursor curParent = dbTables.getAllLocal("Component");
					ArrayList<Integer> alParId = Act00LogonMaster.dbToList(this, metadata.getInt("Game", 0), "Component", 0, 0, 0, 0, 0, "ALL", 0, 0, 1);
					while (!curParent.isAfterLast()) {
						
						if ((curParent.getInt(0) != metadata.getInt("Component", 0)) && (alParId.contains(curParent.getInt(0)))) {
							int loopFlag = 0;
							int loopComp = curParent.getInt(0);
							while (loopFlag == 0) {
								curTemp2 = dbTables.getAllLink(0, 0, loopComp);
								if (curTemp2.getCount() > 0) {
									if (curTemp2.getInt(4) != -1) {
										if (curTemp2.getInt(4) != metadata.getInt("Component", 0)) {
											loopComp = curTemp2.getInt(4);
										} else {
											loopFlag = 2;
										}
									} else {
										loopFlag = 1;
									}
								} else {
									loopFlag = 1;
								}
								curTemp2.close();
							}
							if (loopFlag == 1 && !alCompParent.contains(curParent.getString(2))) {
								String tempString = curParent.getString(2);
								int tempInt = curParent.getInt(0);
								alCompParent.add(tempString);
								alCompParent_id.add(tempInt);
							}
						}
						curParent.moveToNext();
					}
					curParent.close();
				} else {
					curTemp2 = dbTables.getRow(curTemp1.getInt(4), "LOCAL");
					alCompParent.add(curTemp2.getString(2));
					alCompParent_id.clear();
					Log.e("LogFlag", "Parent ID = " + curTemp1.getInt(4));
					alCompParent_id.add(curTemp1.getInt(4));
					String score = curTemp1.getString(7);
					if (!score.equals("")) {
						Log.e("LogFlag", "score: " + score);
						sCompType.setSelection(alCompTypeScore.indexOf(score.substring(0, 1)));
						etCompScore.setHint(alCompTypeHint.get(alCompTypeScore.indexOf(score.substring(0, 1))));
					}
					if (score.length() <= 4) {
						cbSame.setChecked(false);
					} else {
						cbSame.setChecked(true);
						etCompScore.setText(score.substring(4));
						etCompScore.setVisibility(View.VISIBLE);
					}
					curTemp2.close();
				}
				curTemp2.close();
				aaCompParent.notifyDataSetChanged();
				error = Errors(metadata.getInt("Game", 0), metadata.getInt("Component", 0), 0);
				if (error.size() > 0) {
					alComp0_color.add(0xFFD06E6E);
				} else {
					alComp0_color.add(0xFFEFEFED);
				}
//				String key0 = key;
//				key = "";
				if (curTemp1.getInt(4) != 0){
					bNext.setVisibility(View.VISIBLE);
					curTemp2 = dbTables.getAllInherit(curTemp1.getInt(0));
					if (curTemp2.getCount() == 0) {
						rbReqNo.setChecked(true);
					} else if (curTemp2.getInt(2) == 0) {
						rbReqYes.setChecked(true);
					} else {
						rbReq1.setChecked(true);
					}
					curTemp2.close();
				}
//				 key = key0;
			}
			curTemp1.close();
			aaCompParent.notifyDataSetChanged();
			lvComp.setAdapter(new AdapterAAColor(getBaseContext(), R.layout.my_list_items, alComp0, alComp0_color));
			lvComp.setVisibility(View.VISIBLE);
		}
		key = "Component";
		Log.e("LogFlag", "key = " + key);
		dbTables.close();
	}

	public void compView() {
		clear();
		Boolean buttongone = false;
		svMain.setBackgroundColor(0xFFDFEFE2);
		hsvMap.setVisibility(View.VISIBLE);
		lvGame.setVisibility(View.VISIBLE);
		lvComp.setVisibility(View.VISIBLE);
		rlComp.setVisibility(View.GONE);
		bStore.setVisibility(View.GONE);
		tvTitle.setText("Game Alteration - Elements");
		sBreakdown = "select an element to edit";
		tvBreakdown.setText(sBreakdown);
		dbTables.open();
		if(Errors(metadata.getInt("Game", 0),0,0).size()==0){
			bNext.setText("Start Game");
		} else {
			bNext.setText("Save and Quit");
		}
		LayoutParams bParam = (LayoutParams) bNext.getLayoutParams();
		bParam.addRule(RelativeLayout.BELOW, R.id.a10_rlElement);
		bNext.setLayoutParams(bParam);
		bNext.setVisibility(View.VISIBLE);
		key = dbTables.getRow(metadata.getInt("Component", 0), "LOCAL").getString(2);
		alBreakdown.clear();
		alBreakdown_id.clear();
		alBreakdown_color.clear();

		alBreakdown = Act00LogonMaster.dbToList(this, 0, key, metadata.getInt("Component", 0), 0, 0, 0, 0, "ALL", 0, 0, 2);
		alBreakdown_id = Act00LogonMaster.dbToList(this, 0, key, metadata.getInt("Component", 0), 0, 0, 0, 0, "ALL", 0, 0, 1);

		curTemp1 = dbTables.getAllLocal(key);

		while (!curTemp1.isAfterLast()){
			if(!alBreakdown.contains(curTemp1.getString(2))){
				alBreakdown.add(curTemp1.getString(2));
				alBreakdown_id.add(curTemp1.getInt(0));
			}
			curTemp1.moveToNext();
		}
		ArrayList<int[]> errorInt = Errors(metadata.getInt("Game", 0), metadata.getInt("Component", 0), 0);
		ArrayList<Integer> error = new ArrayList<Integer>();
		for (int i = 0; i < errorInt.size(); i++) {
			if (alBreakdown_id.contains(errorInt.get(i)[2]) && !error.contains(errorInt.get(i)[2])) 
				error.add(errorInt.get(i)[2]);
		}
		for (int i = 0; i < alBreakdown.size(); i++) {
			curTemp1 = dbTables.getAllLink(metadata.getInt("Game", 0), 0, alBreakdown_id.get(i));
			curTemp2 = dbTables.getAllLink(metadata.getInt("Game",0), 0, metadata.getInt("Component", 0));
			curTemp3 = dbTables.getAllInherit(curTemp2.getInt(0));
			Log.e("LogFlag", "Item ID: " + alBreakdown_id.get(i));
			if(curTemp1.getCount() == 0 && curTemp3.getCount() == 0) {
				alBreakdown_color.add(0xFFDDDDDD);
			} else if (curTemp1.getCount() == 0 && curTemp3.getCount() > 0) {
				if(curTemp3.getInt(2)!=1 || Act00LogonMaster.dbToList(this, metadata.getInt("Game", 0), key, 0, 0, 0, 0, 0, "ALL", 0, 0, 0).size() == 0){
					alBreakdown_color.add(0xFFDDDDDD);	
				} else {
					Log.e("LogFlag", "item being colored:" + alBreakdown.get(alBreakdown_color.size()));
					alBreakdown_color.add(0xFFD06E6E);
				}
			} else if (error.contains(alBreakdown_id.get(i))) {
				alBreakdown_color.add(0xFFD06E6E);
			} else {
				alBreakdown_color.add(0xFFFFFFFF);
			}
			curTemp1.close();
			curTemp2.close();
			curTemp3.close();
		}
		lvBreakdown.setAdapter(new AdapterAAColor(getBaseContext(), R.layout.my_list_items, alBreakdown, alBreakdown_color));
		alGame0.clear();
		alGame0_color.clear();
		curTemp1 = dbTables.getRow(metadata.getInt("Game", 0), "LOCAL");
		alGame0.add(curTemp1.getString(2));
		if (Errors(metadata.getInt("Game", 0), 0, 0).size() == 0) {
			alGame0_color.add(0xFFE1DFEF);
		} else {
			alGame0_color.add(0xFFD06E6E);
			buttongone = true;
		}
		lvGame.setAdapter(new AdapterAAColor(getBaseContext(), R.layout.my_list_items, alGame0, alGame0_color));
		alComp0.clear();
		alComp0_color.clear();
		curTemp1 = dbTables.getRow(metadata.getInt("Component", 0), "LOCAL");
		alComp0.add(curTemp1.getString(2));
		if (Errors(metadata.getInt("Game", 0), metadata.getInt("Component", 0), 0).size() == 0) {
			alComp0_color.add(0xFFEFEFED);
		} else {
			alComp0_color.add(0xFFD06E6E);
		}
		lvComp.setAdapter(new AdapterAAColor(getBaseContext(), R.layout.my_list_items, alComp0, alComp0_color));
		curTemp1 = dbTables.getAllLink(metadata.getInt("Game", 0), 0, metadata.getInt(key, 0));
		int elcount = curTemp1.getCount();
		curTemp1.close();
		String key0;
		if (metadata.getInt(key, 0) == 0) {
			bRemove.setVisibility(View.GONE);
			lvElement.setVisibility(View.GONE);
			rlElement.setVisibility(View.GONE);
			cbSetInGame.setVisibility(View.GONE);
			bStore.setText("Create");
			bReset.setVisibility(View.GONE);
		} else if(elcount == 0){
			bRemove.setVisibility(View.VISIBLE);
			bReset.setVisibility(View.VISIBLE);
			rlElement.setVisibility(View.GONE);
			lvElement.setVisibility(View.VISIBLE);
			bStore.setText("Update");
			key0 = key;
			key = "";
			cbSetInGame.setVisibility(View.VISIBLE);
			cbSetInGame.setChecked(false);
			curTemp1 = dbTables.getRow(metadata.getInt(key0, 0), "LOCAL");
			etName.setText(curTemp1.getString(2));
			etDesc.setText(curTemp1.getString(3));
			alElement0.clear();
			alElement0_color.clear();
			alElement0.add(curTemp1.getString(2));
			if (Errors(metadata.getInt("Game", 0), metadata.getInt("Component", 0), metadata.getInt(key0, 0)).size() == 0) {
				alElement0_color.add(0xFFDFEFE2);
			} else {
				alElement0_color.add(0xFFD06E6E);
			}
			lvElement.setAdapter(new AdapterAAColor(getBaseContext(), R.layout.my_list_items, alElement0, alElement0_color));
			key = key0;
		} else {
			bRemove.setVisibility(View.VISIBLE);
			rlElement.setVisibility(View.VISIBLE);
			lvElement.setVisibility(View.VISIBLE);
			bStore.setText("Update");
			key0 = key;
			key = "";
			cbSetInGame.setVisibility(View.VISIBLE);
			bReset.setVisibility(View.VISIBLE);
			cbSetInGame.setChecked(true);
			tvElInstructions.setVisibility(View.GONE);
			curTemp1 = dbTables.getRow(metadata.getInt(key0, 0), "LOCAL");
			etName.setText(curTemp1.getString(2));
			etDesc.setText(curTemp1.getString(3));
			alElement0.clear();
			alElement0_color.clear();
			alElement0.add(curTemp1.getString(2));
			if (Errors(metadata.getInt("Game", 0), metadata.getInt("Component", 0), metadata.getInt(key0, 0)).size() == 0) {
				alElement0_color.add(0xFFDFEFE2);
			} else {
				alElement0_color.add(0xFFD06E6E);
			}
			lvElement.setAdapter(new AdapterAAColor(getBaseContext(), R.layout.my_list_items, alElement0, alElement0_color));
			bStore.setText("Change");
			etName.setText(curTemp1.getString(2));
			etDesc.setText(curTemp1.getString(3));
			curTemp1.close();
			curTemp2 = dbTables.getAllLink(metadata.getInt("Game", 0), metadata.getInt("Component", 0), metadata.getInt(key0, 0));
			int minPos = curTemp2.getInt(6);
			curTemp3 = dbTables.getAllLocal(key0);
			while (!curTemp3.isAfterLast()){
				curTemp4 = dbTables.getAllLink(metadata.getInt("Game", 0), metadata.getInt("Component", 0), curTemp3.getInt(0));
					if(curTemp4.getCount()>0){
					minPos = Math.min(minPos,curTemp4.getInt(6));
				}
				curTemp4.close();
				curTemp3.moveToNext();
			}
			curTemp3.close();
			Log.e("LogFlag","minPos = " + minPos + " and current possition = "+ curTemp2.getInt(6));
			if(minPos == curTemp2.getInt(6)){
				bElPosChange.setVisibility(View.GONE);
			} else {
				bElPosChange.setVisibility(View.VISIBLE);
			}
			Log.e("LogFlag", "bPosChange Visiblility = " + bPosChange.getVisibility() + " where visible = " + View.VISIBLE + " and gone = " + View.GONE);
			if(curTemp2.getInt(7) == 0){
				cbEditable.setChecked(true);
			} else {
				cbEditable.setChecked(false);
			}
			if(curTemp2.getInt(8) == 0){
				cbHidden.setChecked(false);
			} else {
				cbHidden.setChecked(true);
			}
			curTemp2.close();
			key = key0;
			int index = 1;
			final ArrayList<Integer> alLinkId = new ArrayList<Integer>();
			alLinkId.add(0);
			alLinkId.add(0);
			// Cursor will list aspects for recorded scores for the element:
			// ElementCategory, ElementName, ElementScore, ElementLinkId		
			String query = "SELECT FOCUS.CATEGORY, FOCUS.NAME, EL.VALUE, EL._ID " +
					"FROM LOCAL AS CAT JOIN LINKS AS COMP ON CAT._ID = COMP.FOCUS " +
					"JOIN LOCAL AS FOCUS ON CAT.NAME = FOCUS.CATEGORY JOIN LINKS AS EL ON EL.FOCUS = FOCUS._ID " +
					"WHERE COMP.GAME_ID = " + metadata.getInt("Game", 0) + " AND EL.GAME_ID = " + metadata.getInt("Game", 0) + " AND EL.PARENT = " + metadata.getInt(key, 0) + " " +
					"ORDER BY COMP.ARRANGE ASC, EL.ARRANGE ASC"; 
			Cursor curScore = dbAdapter.db.rawQuery(query, null);
			if (curScore.getCount() > 0) {
				curScore.moveToFirst();
				String sPreComp = "";
				final TextView tvScoreTitle = (TextView) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_textview, null);
				tvScoreTitle.setId(index * 10);
				LayoutParams lpTVSTitle = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				lpTVSTitle.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				tvScoreTitle.setLayoutParams(lpTVSTitle);
				tvScoreTitle.setBackgroundColor(0xFFFFFFFF);
				tvScoreTitle.setTextSize(15);
				tvScoreTitle.setText("Current Scores:");
				rlScore.addView(tvScoreTitle);
				while (!curScore.isAfterLast()) {
					if (!curScore.getString(0).equals(sPreComp)) {
						index = index + 1;
						alLinkId.add(0);
						final TextView tvComp = (TextView) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_textview, null);
						tvComp.setId(index * 10);
						LayoutParams lpTVComp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						lpTVComp.addRule(RelativeLayout.BELOW, (index - 1) * 10);
						lpTVComp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
						lpTVComp.setMargins(2, 2, 0, 0);
						tvComp.setLayoutParams(lpTVComp);
						tvComp.setTextSize(15);
						tvComp.setText(curScore.getString(0));
						rlScore.addView(tvComp);
					}
					index = index + 1;
					alLinkId.add(curScore.getInt(3));
					sPreComp = curScore.getString(0);
					final Button bScoreRemove = (Button) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_button, null);
					bScoreRemove.setId(index * 10);
					LayoutParams lpB = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					lpB.addRule(RelativeLayout.BELOW, (index - 1) * 10);
					lpB.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
					lpB.setMargins(12, 2, 0, 0);
					bScoreRemove.setLayoutParams(lpB);
					bScoreRemove.setText("remove");
					rlScore.addView(bScoreRemove);
					final TextView tvElement = (TextView) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_textview, null);
					tvElement.setId(index * 10 + 1);
					LayoutParams lpTVEl = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					lpTVEl.addRule(RelativeLayout.BELOW, (index - 1) * 10);
					lpTVEl.addRule(RelativeLayout.RIGHT_OF, index * 10);
					lpTVEl.setMargins(10, 18, 0, 0);
					tvElement.setLayoutParams(lpTVEl);
					tvElement.setText(curScore.getString(1));
					rlScore.addView(tvElement);
					final TextView tvScore = (TextView) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_textview, null);
					tvScore.setId(index * 10 + 2);
					LayoutParams lpTVScore = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					lpTVScore.addRule(RelativeLayout.BELOW, (index - 1) * 10);
					lpTVScore.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
					lpTVScore.setMargins(0, 18, 10, 0);
					tvScore.setLayoutParams(lpTVScore);
					String score = "";
					String tempString = curScore.getString(2);
					while (tempString.contains("<")){
						score += tempString.substring(0,tempString.indexOf("<"));
						tempString = tempString.substring(tempString.indexOf("<"));
						curTemp2 = dbTables.getRow(Integer.parseInt(tempString.substring(1,tempString.indexOf(">"))), "LOCAL");
						score += curTemp2.getString(2);
						curTemp2.close();
						tempString = tempString.substring(tempString.indexOf(">")+1);
					}							
					score += tempString;
					tvScore.setText(score);
					if (curScore.getString(2).equals("-0")) {
						tvScore.setVisibility(View.GONE);
					}
					rlScore.addView(tvScore);
					bScoreRemove.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
							dbTables.open();
							int newIndex = (int)view.getId() / 10;
							Log.e("LogFlag", "LINKS Row to delete: " + alLinkId.get(newIndex));
							dbTables.deleteRow(alLinkId.get(newIndex), "LINKS");
							dbTables.close();
							compView();
						}
					});
					curScore.moveToNext();
				}
			}
			curScore.close();
			// cursor will show all potential children of the element. the
			// following columns: Component Id, Component Type, Element Id,
			// Category of element, Inherited score (null if not), Score (null if
			// none), component Name, inherited Indicator (null if not
			// inherited)
			query = "SELECT COMP.FOCUS AS COMPID, COMP.VALUE AS COMPTYPE , EL._ID AS ELID, LOCAL.NAME, INHER.INHER_TYPE, SCORE.VALUE " +
					"FROM LINKS AS COMP LEFT JOIN INHER AS INHER ON COMP._ID = INHER.LINK_ID " +
					"JOIN LOCAL ON COMP.FOCUS = LOCAL._ID JOIN LOCAL AS EL ON LOCAL.NAME = EL.CATEGORY LEFT JOIN (" +
					"SELECT FOCUS, VALUE FROM LINKS WHERE PARENT = " + metadata.getInt(key, 0) + " AND GAME_ID = " + metadata.getInt("Game", 0) + ") AS SCORE ON EL._ID = SCORE.FOCUS " +
					"WHERE COMP.PARENT = " + metadata.getInt("Component", 0) + " AND LOCAL.CATEGORY LIKE \"COMPONENT\" AND COMP.GAME_ID = " + metadata.getInt("Game", 0);
			Log.e("LogFlag", "curComp - " + query);
			Cursor curComp = dbAdapter.db.rawQuery(query, null);
			ArrayList<Integer> alScored = new ArrayList<Integer>();
			if (curComp != null) {
				curComp.moveToFirst();
				int iReqFlag = 1;
				while (!curComp.isAfterLast()){					
					if (curComp.getString(5) == null){
					} else if (!curComp.getString(3).equals("MapType") && !curComp.getString(5).equals("")){
						alScored.add(curComp.getInt(2));
						Log.e("LogFlag", "MapType is Scored");
					}
					curComp.moveToNext();
				}
				curComp.moveToFirst();
				// this array will record the following for required
				// components: compId, compType, elementId, linkname,
				// inherited indicator, (eventually) ViewIndex
				alElInher = new ArrayList<Object[]>();
				while (!curComp.isAfterLast()) {
					Log.e("LogFlag", !alScored.contains(curComp.getInt(2)) + " && " + (curComp.getString(4) != null && curComp.getString(1).length() == 4) + " && " + prereqChecker(metadata.getInt(key, 0), curComp.getInt(2)));
					if (!alScored.contains(curComp.getInt(2)) && (curComp.getString(4) != null && curComp.getString(1).length() == 4) && prereqChecker(metadata.getInt(key, 0), curComp.getInt(2))) {
						if ((curComp.getInt(4) != 1)  || (Act00LogonMaster.dbToList(this, metadata.getInt("Game", 0), curComp.getString(3), metadata.getInt(key, 0), 0, 0, 0, 0, "ALL", 0, 0, 1).size() == 0)) {
							Object[] oElComp = new Object[6];
							oElComp[0] = curComp.getInt(0);
							oElComp[1] = curComp.getString(1);
							oElComp[2] = curComp.getInt(2);
							oElComp[3] = curComp.getString(3);
							oElComp[4] = curComp.getInt(4);
							alElInher.add(oElComp);
						}
					}
					curComp.moveToNext();
				}
			}
			final ArrayList<Integer> alFlag = new ArrayList<Integer>();
			for (int j = 0; j <= index; j++) {
				alFlag.add(1);
			}
			int emptyFlag = 0;
			if (alElInher.size() > 0) {
				emptyFlag = 1;
				Object oPreComp = "";
				index = index + 1;
				final int InherIndex = index;
				final ArrayList<String> tempEquation = new ArrayList();
				alFlag.add(1);
				final ArrayList<Integer>alTempElId = new ArrayList<Integer>();
				final TextView tvInherTitle = (TextView) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_textview, null);
				tvInherTitle.setId(index * 10);
				LayoutParams lpInherTitle = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				lpInherTitle.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				lpInherTitle.setMargins(0, 5, 0, 5);
				tvInherTitle.setLayoutParams(lpInherTitle);
				tvInherTitle.setBackgroundColor(0xFFFFFFFF);
				tvInherTitle.setTextSize(15);
				tvInherTitle.setText("Inherited Properties");
				rlInherited.addView(tvInherTitle);
				final Button bSetInher = (Button) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_button, null);
				bSetInher.setId(index * 10 + 1);
				LayoutParams lpbInher = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				lpbInher.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				lpbInher.setMargins(0, 2, 15, 0);		
				bSetInher.setText("Set Inherited Traits");
				for (int i = 0; i < alElInher.size(); i++) {
					if (alElInher.get(i)[0] != oPreComp) {
						index = index + 1;
						alFlag.add(1);
						final TextView tvPar = new TextView(getBaseContext());
						tvPar.setId(index * 10);
						LayoutParams lpPar = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
						lpPar.addRule(RelativeLayout.BELOW, (index - 1) * 10);
						lpPar.setMargins(2, 2, 0, 0);
						tvPar.setLayoutParams(lpPar);
						curTemp2 = dbTables.getRow((Integer) alElInher.get(i)[0], "LOCAL");
						tvPar.setText(curTemp2.getString(2));
						curTemp2.close();
						tvPar.setGravity(Gravity.LEFT);
						tvPar.setTextSize(15);
						tvPar.setTextColor(0xFF000000);
						rlInherited.addView(tvPar);
					}
					index = index + 1;
					alFlag.add(0);
					oPreComp = alElInher.get(i)[0];
					String score = (String) alElInher.get(i)[1];
					final RelativeLayout rlTemp = new RelativeLayout(getBaseContext());
					rlTemp.setId(index * 10);
					LayoutParams lpRLTemp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
					lpRLTemp.addRule(RelativeLayout.BELOW, (index - 1) * 10);
					rlTemp.setLayoutParams(lpRLTemp);
					rlInherited.addView(rlTemp);
					View tvTemp = new View(getBaseContext());
					int spinnerFlag = 1;
					ArrayList<String> alTempCompName = new ArrayList<String>();
					if ((Integer) alElInher.get(i)[4] == 0) {
						tvTemp = (TextView) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_textview, null);
						curTemp2 = dbTables.getRow((Integer) alElInher.get(i)[2], "LOCAL");
						((TextView) tvTemp).setText(curTemp2.getString(2) + ":  ");
						curTemp2.close();
						alElInher.set(i, new Object[]{alElInher.get(i)[0], alElInher.get(i)[1], alElInher.get(i)[2], alElInher.get(i)[3], alElInher.get(i)[4], index});
					} else {
						spinnerFlag = 0;
						final int j = i;
						final ArrayList<Object[]> alElInherTemp = new ArrayList<Object[]>();
						ArrayList<String> tempElName = new ArrayList<String>();
						alElInherTemp.add(null);
						alElInherTemp.add(new Object[]{alElInher.get(i)[0], alElInher.get(i)[1], alElInher.get(i)[2], alElInher.get(i)[3], alElInher.get(i)[4], index});
						tempElName.add("Select Element");
						curTemp2 = dbTables.getRow((Integer) alElInher.get(i)[2], "LOCAL");
						tempElName.add(curTemp2.getString(2));
						while (i == j) {
							if (alElInher.size() > i + 1) {
								if (alElInher.get(i + 1)[0] == alElInherTemp.get(1)[0]) {
									alElInherTemp.add(new Object[]{alElInher.get(i + 1)[0], alElInher.get(i + 1)[1], alElInher.get(i + 1)[2], alElInher.get(i + 1)[3], alElInher.get(i + 1)[4], index});
									curTemp2 = dbTables.getRow((Integer) alElInher.get(i + 1)[2], "LOCAL");
									tempElName.add(curTemp2.getString(2));
									alElInher.remove(i);
								} else {
									i = i + 1;
								}
							} else {
								i = i + 1;
							}
						}
						curTemp2.close();
						i = i - 1;
						tvTemp = (Spinner) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_spinner, null);
						final int TempIndex = index;
						((Spinner) tvTemp).setAdapter(new ArrayAdapter(getBaseContext(), R.layout.my_list_items, tempElName));
						((Spinner) tvTemp).setOnItemSelectedListener(new OnItemSelectedListener() {
							int Temp_i = j;
							int Temp_index = TempIndex;

							@Override
							public void onItemSelected(AdapterView<?> arg0, View view, int position, long arg3) {
								if (position == 0) {
									alFlag.set(Temp_index, 0);
									alElInher.set(Temp_i, null);
									try {
										findViewById(Temp_index + 2).setVisibility(View.GONE);
										findViewById(Temp_index + 3).setVisibility(View.GONE);
									} catch (Exception e) {
										Log.e("LogFlag","Cannot remove views due to - " + e);
									}
								} else {
									alElInher.set(Temp_i, alElInherTemp.get(position));
									if (!((String) alElInher.get(Temp_i)[1]).substring(0, 1).equals("c")) {
										findViewById(Temp_index * 10 + 2).setVisibility(View.VISIBLE);
										((EditText)findViewById(Temp_index * 10 + 2)).setHint(alCompTypeHint.get(position));
										if (((String) alElInher.get(Temp_i)[1]).length() > 4) {
											findViewById(Temp_index * 10 + 2).setVisibility(View.VISIBLE);
											if (!((String) alElInher.get(Temp_i)[1]).substring(0, 1).equals("e")){
												alFlag.set(Temp_index, 1);
											} else {
												findViewById(Temp_index * 10 + 3).setVisibility(View.VISIBLE);
											}
										}
									} else {
										alFlag.set(Temp_index, 1);
									}
								}
								if (alFlag.contains(0)) {
									bSetInher.setVisibility(View.GONE);
								} else {
									bSetInher.setVisibility(View.VISIBLE);
								}
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
							}
						});
					}			
					tvTemp.setId(index * 10 + 1);
					LayoutParams tvLP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					tvLP.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
					tvLP.setMargins(25, 20, 0, 0);
					tvLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
					tvTemp.setLayoutParams(tvLP);
					rlTemp.addView(tvTemp);
					if (!score.substring(0, 1).equals("c")) {
						alFlag.set(index, 0);
						final EditText etTemp = (EditText) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_edittextscore, null);;
						if(score.substring(0,1).equals("t")) {
							etTemp.setInputType(InputType.TYPE_CLASS_TEXT);
						}
						etTemp.setId(index * 10 + 2);
						LayoutParams etLP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						etLP.addRule(RelativeLayout.RIGHT_OF, index * 10 + 1);
						etLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
						etTemp.setLayoutParams(etLP);
						etTemp.setHint(alCompTypeHint.get(alCompTypeScore.indexOf(score.substring(0, 1))));
						if (spinnerFlag == 1) {
							if (score.length() > 4) {
								etTemp.setText(score.substring(4));
								alFlag.set(index, 1);
							}
						} else {
							etTemp.setVisibility(View.GONE);
						}
						rlTemp.addView(etTemp);
						if (score.substring(0, 1).equals("d") || score.substring(0, 1).equals("r")) {
							final Button bTemp = (Button) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_button, null);
							bTemp.setId(index * 10 + 3);
							LayoutParams bLP2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
							bLP2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
							bLP2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
							bLP2.setMargins(0, 1, 2, 0);
							bTemp.setLayoutParams(bLP2);
							bTemp.setText("Store as value");
							rlTemp.addView(bTemp);
							bTemp.setVisibility(View.VISIBLE);
							EditText etTemp2 = (EditText) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_edittextscore, null);
							etTemp2.setId(index * 10 + 4);
							LayoutParams etLP2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
							etLP2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
							etLP2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
							etLP2.setMargins(0, 1, 15, 0);
							etTemp2.setLayoutParams(etLP2);
							etTemp2.setVisibility(View.GONE);
							rlTemp.addView(etTemp2);

							etTemp2.addTextChangedListener(new TextWatcher() {
								@Override
								public void afterTextChanged(Editable arg0) {
									if (!key.equals("")) {
										if (!((EditText) getCurrentFocus()).getText().toString().equals("")) {
											if (isNumeric((EditText) getCurrentFocus(), 0, -1)
													|| (isNumeric((EditText) getCurrentFocus(), 0, ((EditText) getCurrentFocus()).getText()
															.length() - 1) && ((EditText) getCurrentFocus()).toString()
															.substring(((EditText) getCurrentFocus()).getText().length() - 1).equals("%"))
													|| (((EditText)getCurrentFocus()).getHint().equals("text") && ((EditText)getCurrentFocus()).toString().length()>0)) {
												alFlag.set(((int)getCurrentFocus().getId() - 3) / 10, 1);
											}
										} else {
											alFlag.set(((int)(int)getCurrentFocus().getId() - 3) / 10, 0);
										}
									}

									if (!alFlag.contains(0)) {
										((Button) findViewById(InherIndex * 10 + 1)).setVisibility(View.VISIBLE);
									} else {
										((Button) findViewById(InherIndex * 10 + 1)).setVisibility(View.GONE);
									}
								}

								@Override
								public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
								}

								@Override
								public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
								}
							});

							bTemp.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View button) {
									int id = button.getId();
									String key0 = key;
									key = "";
									((EditText) findViewById(id + 1)).setText(SubCalculator.calc(((EditText) findViewById(id - 1)).getText().toString()) + "");
									((EditText) findViewById(id + 1)).setVisibility(View.VISIBLE);
									button.setVisibility(View.GONE);
									key = key0;
								}
							});
						} else if (score.substring(0, 1).equals("e")){
							ArrayList<String> alCompList = new ArrayList<String>();
							alCompList.add("Select Component");
							query = "SELECT COMP.NAME, COMP._ID FROM LOCAL AS EL JOIN LOCAL AS COMP WHERE EL.CATEGORY = COMP.NAME AND COMP._ID <> " + alElInher.get(i)[0];
							curTemp2 = dbAdapter.db.rawQuery(query, null);
							if(curTemp2.getCount() > 0){
								curTemp2.moveToFirst();
							}
							while (!curTemp2.isAfterLast()){
								int newFocus = curTemp2.getInt(1);
								int loopFlag = 0;
								while (loopFlag == 0){
									curTemp3 = dbTables.getRow(newFocus, "LOCAL");
									if(newFocus == -1){
										loopFlag = 2;
									} else if (curTemp3.getString(1).equals("System")){
										loopFlag = 2;
									} else if (newFocus == metadata.getInt("Component", 0)){
										loopFlag = 1;
									} else {
										newFocus = dbTables.getAllLink(0, 0, newFocus).getInt(4);
									}
									curTemp3.close();
								}
								curTemp3 = dbTables.getAllLink(metadata.getInt("Game", 0), 0, curTemp2.getInt(1));
								if (loopFlag == 1 && !alTempCompName.contains(curTemp2.getString(0)) && !curTemp3.getString(7).contains("c |")){
									alTempCompName.add(curTemp2.getString(0));
								}
								curTemp3.close();
								curTemp2.moveToNext();
							}
							curTemp2.close();
							if (alTempCompName.size()>0){
								alTempCompName.add(0,"Component");
								((LayoutParams)etTemp.getLayoutParams()).addRule(RelativeLayout.LEFT_OF,index * 10 + 3);
								final EditText etSymbol = (EditText) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_edittext, null);
								etSymbol.setId(index * 10 + 3);
								LayoutParams lpETS = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
								lpETS.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
								lpETS.addRule(RelativeLayout.ALIGN_PARENT_TOP);
								lpETS.setMargins(0, 1, 0, 0);
								etSymbol.setLayoutParams(lpETS);
								etSymbol.setHint("+ - * / max");
								etSymbol.setVisibility(View.GONE);
								rlTemp.addView(etSymbol);
								EditText etTemp2 = (EditText) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_edittextscore, null);
								etTemp2.setId(index * 10 + 4);
								LayoutParams etLP2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
								etLP2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
								etLP2.addRule(RelativeLayout.BELOW, index * 10 + 1);
								etLP2.setMargins(20, 1, 0, 0);
								etTemp2.setLayoutParams(etLP2);
								etTemp2.setHint("wieght");
								etTemp2.setVisibility(View.GONE);
								rlTemp.addView(etTemp2);
								Spinner sTemp = (Spinner)getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_spinner, null);
								sTemp.setId(index * 10 + 5);
								LayoutParams sLP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
								sLP.addRule(RelativeLayout.RIGHT_OF,index * 10 + 4);
								sLP.addRule(RelativeLayout.BELOW, index * 10 + 1);
								sLP.setMargins(5, 10, 5, 0);
								sTemp.setLayoutParams(sLP);
								sTemp.setAdapter(new ArrayAdapter(getBaseContext(), R.layout.my_list_items,alTempCompName));
								sTemp.setVisibility(View.GONE);
								rlTemp.addView(sTemp);
								Spinner sTemp1 = (Spinner)getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_spinner, null);
								sTemp1.setId(index * 10 + 6);
								LayoutParams sLP1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
								sLP1.addRule(RelativeLayout.RIGHT_OF, index * 10 + 5);
								sLP1.addRule(RelativeLayout.BELOW, index * 10 + 1);
								sLP1.setMargins(0, 10, 2, 0);
								sTemp1.setLayoutParams(sLP1);
								sTemp1.setVisibility(View.GONE);
								rlTemp.addView(sTemp1);
								Button bTemp1 = (Button)getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_button, null);
								bTemp1.setId(index * 10 + 7);
								LayoutParams bLP1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
								bLP1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
								bLP1.addRule(RelativeLayout.BELOW, index * 10 + 5);
								bLP1.setMargins(0, 10, 2, 0);
								bTemp1.setLayoutParams(bLP1);
								bTemp1.setText("Condense Equation");
								bTemp1.setVisibility(View.GONE);
								rlTemp.addView(bTemp1);
								
								etSymbol.addTextChangedListener(new TextWatcher(){
									String preString;
									@Override
									public void afterTextChanged(Editable current) {
										int viewId = (int)getCurrentFocus().getId();
										findViewById(InherIndex * 10 + 1).setVisibility(View.GONE);
										if (current.length() == 0){
											findViewById(viewId + 1).requestFocus();
											if (findViewById(viewId + 1).hasFocus()){
												((EditText)findViewById(viewId + 1)).setText("");
												findViewById(viewId + 1).setVisibility(View.GONE);
											}
											if (((EditText)findViewById(viewId-1)).getText().length() > 0){
												findViewById(InherIndex * 10 + 1).setVisibility(View.VISIBLE);
											}
										}
										if (new ArrayList<String>(Arrays.asList("+","-","*","/","max")).contains(current.toString())){
											findViewById(viewId + 1).setVisibility(View.VISIBLE);
										} else if (!current.toString().equals("m") && !current.toString().equals("ma") && !current.toString().equals("")){
											current.replace(0, current.length(), preString);
										}
									}
									@Override
									public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {	
										preString = arg0.toString();
									}
									@Override
									public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {	
									}
								});
								etTemp2.addTextChangedListener(new TextWatcher() {
									@Override
									public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
									}
									@Override
									public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
									}
									@Override
									public void afterTextChanged(Editable current) {
										findViewById(InherIndex * 10 + 1).setVisibility(View.GONE);
										findViewById((int)getCurrentFocus().getId() + 1).setVisibility(View.VISIBLE);
										alFlag.set(((int)getCurrentFocus().getId() - 4) / 10, 0); 
										if (current.length() == 0){
											((Spinner)findViewById((int)getCurrentFocus().getId() + 1)).setSelection(0);
											findViewById((int)getCurrentFocus().getId() + 3).setVisibility(View.GONE);
											findViewById((int)getCurrentFocus().getId() + 2).setVisibility(View.GONE);
											findViewById((int)getCurrentFocus().getId() + 1).setVisibility(View.GONE);
											if(((EditText)findViewById((int)getCurrentFocus().getId() - 2)).length() > 0){
												alFlag.set(((int)getCurrentFocus().getId() - 4) / 10, 1);
											}
										}
										if (alFlag.contains(0)){
											findViewById(InherIndex * 10 + 1).setVisibility(View.GONE);
										} else {
											findViewById(InherIndex * 10 + 1).setVisibility(View.VISIBLE);
										}
									}
								});
								sTemp.setOnItemSelectedListener(new OnItemSelectedListener() {

									@Override
									public void onItemSelected(AdapterView<?> view, View arg1, int position, long arg3) {
										dbTables.open();
										findViewById((int)view.getId() + 2).setVisibility(View.GONE);
										if (position ==  0){
											findViewById((int)view.getId() + 1).setVisibility(View.GONE);
										} else {
											ArrayList<String> alTemp = new ArrayList<String>();
											alTempElId.clear();
											Cursor curTemp = dbTables.getAllLocal((String) ((Spinner) view).getSelectedItem());
											while (!curTemp.isAfterLast()){
												alTempElId.add(curTemp.getInt(0));
												alTemp.add(curTemp.getString(2));
												curTemp.moveToNext();
											}
											curTemp.close();
											alTemp.add(0,"Select Element");
											alTempElId.add(0,0);
											((Spinner)findViewById((int)view.getId() + 1)).setAdapter(new ArrayAdapter(getBaseContext(), R.layout.my_list_items, alTemp));
											findViewById((int)view.getId() + 1).setVisibility(View.VISIBLE);
											((Spinner)findViewById((int)view.getId() + 1)).setSelection(0);
										}
										dbTables.close();
									}
									@Override
									public void onNothingSelected(AdapterView<?> arg0) {
									}
								});
								sTemp1.setOnItemSelectedListener(new OnItemSelectedListener() {
									@Override
									public void onItemSelected(AdapterView<?> view, View arg1, int position, long arg3) {
										if (position == 0){
											findViewById((int)view.getId() + 1).setVisibility(View.GONE);
										} else {
											findViewById((int)view.getId() + 1).setVisibility(View.VISIBLE);
											findViewById((int)view.getId() + 1).requestFocus();
										}
									}
									@Override
									public void onNothingSelected(AdapterView<?> arg0) {
									}
								});
								bTemp1.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										EditText view = (EditText)findViewById((int)v.getId() - 5);
										int index = ((int)v.getId() - 7) / 10;
										for (int i = tempEquation.size(); i <= index; i++){
											tempEquation.add(null);
										}
										if (tempEquation.get(index) == null){
											tempEquation.add(index,view.getText().toString());
										}
										view.setInputType(InputType.TYPE_CLASS_TEXT);
										String symbol = ((EditText)findViewById((int)v.getId() - 4)).getText().toString();
										String multiplier =((EditText)findViewById((int)v.getId() - 3)).getText().toString();
										tempEquation.set(index, tempEquation.get(index) + symbol + multiplier + "<" + alTempElId.get(((Spinner)findViewById((int)v.getId() - 1)).getSelectedItemPosition()) + ">");
										curTemp2 = dbTables.getRow(alTempElId.get(((Spinner)findViewById((int)v.getId() - 1)).getSelectedItemPosition()), "LOCAL");
										view.setText(view.getText().toString() + symbol + multiplier + curTemp2.getString(2));
										curTemp2.close();
										findViewById((int)v.getId() - 3).requestFocus();
										((EditText)findViewById((int)v.getId() - 3)).setText("");
										findViewById((int)v.getId() - 4).requestFocus();
										((EditText)findViewById((int)v.getId() - 4)).setText("");
										view.setInputType(InputType.TYPE_CLASS_NUMBER);
									}
								});
							}
						}
						etTemp.addTextChangedListener(twScore);
						etTemp.addTextChangedListener(new TextWatcher() {
							String pre = "";
							@Override
							public void afterTextChanged(Editable current) {
								if (!key.equals("")) {
									EditText view = (EditText) getCurrentFocus();
									int index = ((int)view.getId() - 1) / 10;
									int position = alCompTypeHint.indexOf(view.getHint());
									if (position == 3 || position == 4){
										if (view.getHint().length() == current.length()) {
											alFlag.set(index, 1);
										} else {
											alFlag.set(index, 0);
											if (alCompTypeHint.indexOf(view.getHint().toString()) != 2){
												try {
													((EditText) findViewById((int)view.getId() + 2)).setVisibility(View.GONE);
												} catch (Exception e) {
													Log.e("LogFlag", "couldn't remove textbox due to " + e);
												}
											}
										}
									} else if (position == 2){
										boolean ViewsExist = true;
										try {
											if (current.length() > 0){
												((EditText)findViewById((int)view.getId() + 1)).setVisibility(View.VISIBLE);
											} else {
												((EditText)findViewById((int)view.getId() + 1)).setVisibility(View.GONE);
											} 
										} catch (Exception e) {
											Log.e("LogFlag", "Could not produce additional views due to - " + e);
											ViewsExist = false;
										}
										
										if (pre.length() > current.length() && tempEquation.size()>=index){
											if (tempEquation.get(index) == null){
												tempEquation.set(index,"");
											}
											int length = Math.max(tempEquation.get(index).length() - 1,0);
											if (tempEquation.get(index).substring(0,length).contains(">")){
												int eend = tempEquation.get(index).substring(0,length).lastIndexOf(">")+ 1;
												String sym = "";
												if (tempEquation.get(index).substring(eend,eend+1).equals("m")){
													sym = tempEquation.get(index).substring(eend,eend + 3);
												} else {
													sym = tempEquation.get(index).substring(eend, eend + 1);
												}
												int end = current.toString().lastIndexOf(sym);
												String key0 = key;
												key = "";
												current.replace(end, current.length(), "");
												key = key0;
												tempEquation.set(index, tempEquation.get(index).substring(0,eend));
											} else {
												tempEquation.set(index, "");
												current.replace(0,current.length(),"");
											}
										}
										if (current.length() > 0) {
											if (ViewsExist){
												if ((((EditText)findViewById((int)view.getId() + 2)).getText().length() == 0)){
													alFlag.set(((int)view.getId() - 1) / 10, 1);
												} else {
													alFlag.set(((int)view.getId() - 1) / 10, 0);
												}
											} else {
												alFlag.set(((int)view.getId() - 1) / 10, 1);
											}
										} else {
											alFlag.set(((int)view.getId() - 1) / 10, 0);
										}
									}
									if (position == 5){
										if(current.length()>0){
											alFlag.set(((int)view.getId()-1)/10,1);
										} else {
											alFlag.set(((int)view.getId()-1)/10,0);
										}
									}
									String logString = "";
									for (int i = 0; i<alFlag.size(); i++){
										logString = logString + alFlag.get(i) + ", ";
									}
									Log.e("LogFlag", "alFlag = " + logString);
									if (alFlag.contains(0)) {
										((Button) findViewById(InherIndex * 10 + 1)).setVisibility(View.GONE);
									} else {
										((Button) findViewById(InherIndex * 10 + 1)).setVisibility(View.VISIBLE);
									}
								}
							}

							@Override
							public void beforeTextChanged(CharSequence was, int arg1, int arg2, int arg3) {
								pre = was.toString();
							}
							@Override
							public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
							}
						});
					} else {
						alFlag.set(index, 1);
					}
				}
				lpbInher.addRule(RelativeLayout.BELOW,index * 10);
				bSetInher.setLayoutParams(lpbInher);
				rlInherited.addView(bSetInher);
				if (alFlag.contains(0)) {
					bSetInher.setVisibility(View.GONE);
				} else {
					bSetInher.setVisibility(View.VISIBLE);
				}
				bSetInher.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dbTables.open();
						boolean resFlag = false;
						for (int i = 0; i < alElInher.size(); i++) {
							int parentEl = metadata.getInt(key, 0);
							int gameId = metadata.getInt("Game", 0);
							String CompType = (String) alElInher.get(i)[3];
							int focusEl = (Integer) alElInher.get(i)[2];
							int viewIndex = (Integer) alElInher.get(i)[5];
							
							String score = "";
							int HintIndex = 0;
							try {
								score = ((TextView) findViewById(viewIndex * 10 + 2)).getText().toString();
								HintIndex = alCompTypeHint.indexOf(((EditText) findViewById(viewIndex * 10 + 2)).getHint());
								if (HintIndex == 3 || HintIndex == 4) {
									if (findViewById(viewIndex * 10 + 3).getVisibility() == View.GONE) {
										score = ((EditText) findViewById(viewIndex * 10 + 4)).getText().toString();
									}
								} else if (HintIndex == 2){
									if (tempEquation.get(viewIndex) == null){
										tempEquation.set(viewIndex, ((EditText)findViewById(viewIndex * 10 + 2)).getText().toString());
									}
									score = tempEquation.get(viewIndex);
								} 
//										score = .getAllLinkLocal(metadata.getInt("Game", 0), metadata.getInt("Component", 0), focusEl).getString(7);
							} catch (Exception e) {
								if (score == ""){
									Cursor curTempA = dbTables.getAllLink(metadata.getInt("Game", 0), metadata.getInt("Component", 0), focusEl);
									if(curTempA.getCount() > 0){
										score = curTempA.getString(7);
									} else {
										score = "-0";
									}
									curTempA.close();
								}
							}
							int order = 0;
							ArrayList<Integer> alOrder = Act00LogonMaster.dbToList(getBaseContext(), metadata.getInt("Game",0), CompType, 0, 0, 0, 0, 0, "ALL", 0, 0, 6);
							for (int j=0; j<alOrder.size(); j++){
								order = Math.max(alOrder.get(j),order);
							}
							Log.e("Flag",focusEl + "");
							Integer iEditable = 0;
							Integer iHidden = 0;
							if(Act00LogonMaster.dbToList(getBaseContext(), metadata.getInt("Game",0),"Component",0,focusEl,0,0,0,"ALL",0,0,0).size()>0){
								Cursor curTempA = dbTables.getRow((Integer) Act00LogonMaster.dbToList(getBaseContext(), metadata.getInt("Game", 0), "Component", 0, focusEl, 0, 0, 0, "ALL", 0, 0, 0).get(0), "LINKS");
								iEditable = curTempA.getInt(8);
								iHidden = curTempA.getInt(9);
								curTempA.close();
							}
							Cursor curTempA = dbTables.getAllLink(metadata.getInt("Game", 0),0,metadata.getInt("Game", 0));
							int newVer = curTempA.getInt(2) + 1;
							int majVer = curTempA.getInt(3);
							curTempA.close();
							if(resChecker(focusEl,score)) {
								Log.e("LogFlag", "resChecker = true");
								dbTables.createLink(gameId, majVer, newVer, parentEl, focusEl, order, score, iEditable, iHidden, -1);
							} else {
								Log.e("LogFlag", "resChecker = false");
								resFlag = true;
							}
						}
						dbTables.close();
						if (resFlag){
							Act00LogonMaster.centerToast(getBaseContext(), "Some scores don't meet the criteria.  Please check the properties for the elements");
						}
						compView();
					}
				});
			}
			// Everything above this point should work. Optional and
			// inherited remain.
			if (curComp != null) {
				curComp.moveToFirst();
			}
			final ArrayList<Integer> alOpCompId = new ArrayList<Integer>();
			ArrayList<String> alOpCompName = new ArrayList<String>();
			final ArrayList<String> alOpCompType = new ArrayList<String>();
//				final ArrayList<String> alOpCompLink = new ArrayList<String>();
			// ArrayList (alEl) will contain the following: component Id,
			// element id
			final ArrayList<int[]> alOpEl = new ArrayList<int[]>();
			alOpCompId.add(0);
			alOpCompName.add("set a component");
			alOpCompType.add("");
			alOpEl.add(new int[]{0, 0});
			int CompIndex = 0;
			while (!curComp.isAfterLast()) {
				if (curComp.getString(4) == null && curComp.getString(5) == null && prereqChecker(metadata.getInt(key, 0), curComp.getInt(2))) {
//						Log.e("LogFlag","prereq checker = " + prereqChecker(metadata.getInt(key, 0),curComp.getInt(2)) + " for " + .getLocal(curComp.getInt(2)).getString(2));
					if (!alOpCompId.contains(curComp.getInt(0))) {
						alOpCompId.add(curComp.getInt(0));
						alOpCompName.add(curComp.getString(3));
						alOpCompType.add(curComp.getString(1));
//							alOpCompLink.add(curComp.getString(3));
						CompIndex = alOpCompId.size() - 1;
					}
					int[] iEl = new int[2];
					iEl[0] = CompIndex;
					iEl[1] = curComp.getInt(2);
					alOpEl.add(iEl);
				}
				curComp.moveToNext();
			}

			Act00LogonMaster.close(curComp);
			if (alOpCompId.size() > 1) {
				TextView tvTitle = (TextView) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_textview, null);
				tvTitle.setId(index * 10);
				LayoutParams lpTitle = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				lpTitle.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				lpTitle.setMargins(0, 5, 0, 5);
				tvTitle.setLayoutParams(lpTitle);
				tvTitle.setBackgroundColor(0xFFFFFFFF);
				tvTitle.setTextSize(15);
				tvTitle.setText("Optional Properties");
				rlOptional.addView(tvTitle);
				index = index + 1;
				index = setupSelectorIntro(index, alOpCompName, alOpEl, alOpCompType, rlOptional);
			}

need to list the character properties for the attribute change possibility within the actions.

			// This cursor mimics the structure of the cursor in the options section.. early attemps were made to copy code that. The cursor will return all components that are not children of the component in focus. columns are: 
			// comp id, compType, Element Id, LinkName, null, Prerequisit (if they have a score), compName, null
			query = "SELECT EL.COMPNAME, null AS COMPID, REQ.VALUE, EL.ELNAME, EL.ELID FROM PREREQS AS REQ LEFT JOIN (" +
					"SELECT ELNAME.CATEGORY AS COMPNAME, ELINFO._ID AS ELLINKID, ELINFO.VALUE, ELNAME.NAME AS ELNAME, ELNAME._ID AS ELID " +
					"FROM LINKS AS ELINFO LEFT JOIN LOCAL AS ELNAME ON ELINFO.FOCUS = ELNAME._ID) AS EL ON REQ.PREREQ_ID = EL.ELID " +
					"WHERE REQ.FOCUS = " + metadata.getInt(key, 0) + " AND REQ.GAME_ID = " +metadata.getInt("Game", 0) + " " +
					"UNION " +
					"SELECT COMP1.COMPNAME, COMP1.COMPID, COMP1.VALUE, EL1.ELNAME, EL1.ELID FROM (" +
					"SELECT COMP.NAME AS COMPNAME, COMP._ID AS COMPID, COMPINFO.VALUE FROM LOCAL AS COMP JOIN LINKS AS COMPINFO ON COMP._ID = COMPINFO.FOCUS " +
					"WHERE COMPINFO.GAME_ID = " + metadata.getInt("Game", 0) + " AND COMP.CATEGORY LIKE \"COMPONENT\") AS COMP1 JOIN (" +
					"SELECT EL._ID AS ELID, EL.CATEGORY, EL.NAME AS ELNAME, ELINFO.ARRANGE FROM LOCAL AS EL JOIN LINKS AS ELINFO ON EL._ID = ELINFO.FOCUS " +
					"WHERE ELINFO.GAME_ID = " + metadata.getInt("Game", 0) + " AND ELINFO.FOCUS != " + metadata.getInt(key0, 0) + ") AS EL1 ON COMP1.COMPNAME = EL1.CATEGORY ORDER BY COMPNAME ASC, ELNAME ASC;";
			Log.e("LogFlag", "curElAll - " + query);
			Cursor curElAll = dbAdapter.db.rawQuery(query, null);
			if (curElAll.getCount()>0) {
				curElAll.moveToFirst();
				Log.e("LogFlag", "curElAll:  compname | compID | score | elName | elID");
				while (!curElAll.isAfterLast()){
					Log.e("LogFlag", curElAll.getString(0) + "|" + curElAll.getString(1) + "|" + curElAll.getString(2) + "|" + curElAll.getString(3) + "|" + curElAll.getString(4));
					curElAll.moveToNext();
				}
			}
			// alCurPrereq will list all the current prerequisit info for
			// display. Columns are: compname, elementname, score, element id, and index
			final ArrayList<Object[]> alCurPrereq = new ArrayList<Object[]>();
			ArrayList<int[]> alEl = new ArrayList<int[]>();
			ArrayList<Integer> alCompAll_id = new ArrayList<Integer>();
			ArrayList<String> alCompName = new ArrayList<String>();
			ArrayList<String> alCompLink = new ArrayList<String>();
			ArrayList<String> alCompTypeString = new ArrayList<String>();
			alCompAll_id.add(0);
			alCompName.add("set a component");
			alCompLink.add("");
			alCompTypeString.add("");
			int prereqFlag = 0;
//			this list will grab all the elements that are not children of the component for potential prerequisites
			if (curElAll.getCount() > 0) {
				curElAll.moveToFirst();
				while (!curElAll.isAfterLast()) {
					curTemp2 = dbTables.getAllPrereq(metadata.getInt("Game",0),  metadata.getInt(key,0), curElAll.getInt(4));
					curTemp3 = dbTables.getAllPrereq(metadata.getInt("Game", 0), curElAll.getInt(4), metadata.getInt(key,0));
					if (!curElAll.getString(2).contains("|")) {
						alCurPrereq.add(new Object[]{curElAll.getString(0), curElAll.getString(3), curElAll.getString(2), curElAll.getInt(4)});
						Log.e("LogFlag","Not even Close to CompLoop");
					} else if (curTemp2.getCount()==0 && curTemp3.getCount() == 0){
						int comp1 = curElAll.getInt(1);
						if (comp1 == metadata.getInt("Component", 0) && curElAll.getInt(3) != metadata.getInt(key,0)) {
							Log.e("LogFlag", "alCompName vs curEl CompName: " + alCompName.get(alCompName.size()-1) + " | " + curElAll.getString(0) + " | " + alCompName.get(alCompName.size()-1).equals(curElAll.getString(0)));
							if (!alCompName.contains(curElAll.getString(0))) {
								alCompAll_id.add(curElAll.getInt(1));
								alCompName.add(curElAll.getString(0));
								alCompTypeString.add(curElAll.getString(2));
							}
							alEl.add(new int[]{alCompName.size() - 1, curElAll.getInt(4)});
							Log.e("LogFlag", "Not CompLoop");
						} else{
							Log.e("LogFlag", "CompLoop");
							int loopFlag = 0;
							int step = 0;
							while (loopFlag == 0) {
								step = step + 1;
								curTemp2 = dbTables.getAllLink(0, 0, comp1);
								Log.e("LogFlag", comp1 + ":in stage 1");
								Log.e("LogFlag", "comp1 - " + comp1);
								if (comp1 == -1) {
									Log.e("LogFlag", "alCompName vs curEl CompName: " + alCompName.get(alCompName.size()-1) + " | " + curElAll.getString(0) + " | " + alCompName.get(alCompName.size()-1).equals(curElAll.getString(0)));
									if (!alCompName.contains(curElAll.getString(0))) {
										alCompAll_id.add(curElAll.getInt(1));
										alCompName.add(curElAll.getString(0));
										alCompTypeString.add(curElAll.getString(2));
									}
									alEl.add(new int[]{alCompName.size() - 1, curElAll.getInt(4)});
									loopFlag = 1;
								} else if (comp1 == metadata.getInt("Component", 0)
										|| curTemp2.getCount() == 0) {
									loopFlag = 1;
									Log.e("LogFlag", comp1 + ":dismissed stage 2");
								} else {
									comp1 = curTemp2.getInt(4);
								}
								curTemp2.close();
							}
						}
					}
					curElAll.moveToNext();
					Log.e("LogFlag", "curElAll position: " + curElAll.getPosition());
					curTemp2.close();
					curTemp3.close();
				}
//				curTemp2 = .getAllLinkLocal(metadata.getInt("Game", 0), 0, metadata.getInt("Component",0));
//				curTemp3 = .getIdLinkInherit(curTemp2.getInt(0));
//				Log.e("LogFlag", "alCurPrereq count = " + alCurPrereq.size() + ", alCompAll count = " + alCompAll_id.size() + ", curTemp3 count = " + curTemp3.getCount());
//				if (alCurPrereq.size() > 0 || alCompAll_id.size() > 1 && curTemp3.getCount() == 0) {
				if (alCurPrereq.size() > 0 || alCompAll_id.size() > 1) {
					emptyFlag = 1;
					index = index + 1;
					TextView tvPrereqTitle = (TextView) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_textview, null);
					tvPrereqTitle.setId(index * 10);
					LayoutParams lpPrereqTitle = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
					lpPrereqTitle.addRule(RelativeLayout.ALIGN_PARENT_TOP);
					lpPrereqTitle.setMargins(0, 5, 0, 5);
					tvPrereqTitle.setLayoutParams(lpPrereqTitle);
					tvPrereqTitle.setBackgroundColor(0xFFFFFFFF);
					tvPrereqTitle.setTextSize(15);
					tvPrereqTitle.setText("Prerequisit Elements");
					rlPrereq.addView(tvPrereqTitle);
					String sPreComp = "";
//					alcurprereq contains all the active prerequisit properties for the element in question
					for (int i = 0; i < alCurPrereq.size(); i++) {
						Log.e("LogFlag", "sPreComp v alCurPrereq:" + sPreComp + " | " + alCurPrereq.get(i)[0]);
						if (!alCurPrereq.get(i)[0].equals(sPreComp)) {
							index = index + 1;
							TextView tvPrereqComp = (TextView) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_textview, null);
							tvPrereqComp.setId(index * 10);
							LayoutParams lpTVPrereqComp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
							lpTVPrereqComp.addRule(RelativeLayout.BELOW, (index - 1) * 10);
							lpTVPrereqComp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
							lpTVPrereqComp.setMargins(2, 2, 0, 0);
							tvPrereqComp.setLayoutParams(lpTVPrereqComp);
							tvPrereqComp.setTextSize(15);
							tvPrereqComp.setText((String) alCurPrereq.get(i)[0]);
							rlPrereq.addView(tvPrereqComp);
							sPreComp = (String)(alCurPrereq.get(i)[0]);
						}
						index = index + 1;
						alCurPrereq.set(i, new Object[]{alCurPrereq.get(i)[0],alCurPrereq.get(i)[1], alCurPrereq.get(i)[2], alCurPrereq.get(i)[3], index});
						Button bPrereqScoreRemove = (Button) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_button, null);
						bPrereqScoreRemove.setId(index * 10);
						LayoutParams lpPreB = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						lpPreB.addRule(RelativeLayout.BELOW, (index - 1) * 10);
						lpPreB.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
						lpPreB.setMargins(12, 2, 0, 0);
						bPrereqScoreRemove.setLayoutParams(lpPreB);
						bPrereqScoreRemove.setText("remove");
						rlPrereq.addView(bPrereqScoreRemove);
						TextView tvPrereqElement = (TextView) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_textview, null);
						tvPrereqElement.setId(index * 10 + 1);
						LayoutParams lpTVPreEl = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						lpTVPreEl.addRule(RelativeLayout.BELOW, (index - 1) * 10);
						lpTVPreEl.addRule(RelativeLayout.RIGHT_OF, index * 10);
						lpTVPreEl.setMargins(10, 18, 0, 0);
						tvPrereqElement.setLayoutParams(lpTVPreEl);
						tvPrereqElement.setText((String) alCurPrereq.get(i)[1]);
						rlPrereq.addView(tvPrereqElement);
						TextView tvPrereqScore = (TextView) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_textview, null);
						tvPrereqScore.setId(index * 10 + 2);
						LayoutParams lpTVPreScore = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						lpTVPreScore.addRule(RelativeLayout.BELOW, (index - 1) * 10);
						lpTVPreScore.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
						lpTVPreScore.setMargins(0, 18, 10, 0);
						tvPrereqScore.setLayoutParams(lpTVPreScore);
						if (((String) alCurPrereq.get(i)[2]).equals("0")){
							tvPrereqScore.setText("");
						} else {
							tvPrereqScore.setText((String) alCurPrereq.get(i)[2]);
						}
						rlPrereq.addView(tvPrereqScore);
						bPrereqScoreRemove.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View view) {
								dbTables.open();
								Cursor curTempA = dbTables.getAllLocal("Game Info");
								int imaxId = 0;
								while (!curTempA.isAfterLast()){
									if(curTempA.getString(2).equals("Max Player Count")){
										imaxId = curTempA.getInt(0);
									}
									curTempA.moveToNext();
								}
								if(((TextView)(findViewById((int)view.getId()+1))).getText().toString().equals("Min Player Count") & metadata.getInt(key, 0) == imaxId){
									Act00LogonMaster.centerToast(getBaseContext(), "this element cannot be removed.");
								} else {
									int newIndex = (int)view.getId() / 10;
									for (int i = 0; i < alCurPrereq.size(); i++) {
										if ((Integer) alCurPrereq.get(i)[4] == newIndex) {
											Log.e("LogFlag", "del - reqId: " + (Integer) alCurPrereq.get(i)[3] + " & focusId: " + metadata.getInt(key, 0));
											Cursor curTempB = dbTables.getAllPrereq(metadata.getInt("Game", 0), metadata.getInt(key, 0), (Integer) alCurPrereq.get(i)[3]);
											dbTables.deleteRow(curTempB.getInt(0), "PREREQS");
											curTempB.close();
											i = alCurPrereq.size();
										}
									}
								}
								curTempA.close();
								compView();
								dbTables.close();
							}
						});
					}
					Log.e("LogFlag", "alCompName size = " + alCompName.size());
					if (alCompName.size() > 1) {
						Log.e("LogFlag", "prereq setup");
						index = index + 1;
						index = setupSelectorIntro(index, alCompName, alEl, alCompTypeString, rlPrereq);
					}
				}
//				curTemp2.close();
//				curTemp3.close();
			}
			curElAll.close();

			if (emptyFlag == 1) {
				tvElInstructions.setVisibility(View.VISIBLE);
			};
			rlRestric.removeAllViews();
			index = index + 1;
			final TextView tvResTitle = (TextView) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_textview,null);
			tvResTitle.setId(index*10);
			LayoutParams lpResTitle = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			lpResTitle.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			lpResTitle.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			lpResTitle.setMargins(0,10, 0, 6);
			tvResTitle.setLayoutParams(lpResTitle);
			tvResTitle.setBackgroundColor(0xFFFFFFFF);
			tvResTitle.setTextSize(15);
			tvResTitle.setText("Score Restrictions");
			rlRestric.addView(tvResTitle);
			curTemp1 = dbTables.getAllRes(metadata.getInt("Game", 0),metadata.getInt(key, 0));
			if(curTemp1.getCount() > 0) {
				curTemp1.moveToFirst();
				while (!curTemp1.isAfterLast()) {
					index = index + 1;
					final Button bResRemove = (Button) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_button, null);
					final TextView tvResScore = (TextView) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_textview, null);
					final TextView tvResID = (TextView) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_textview, null);
					bResRemove.setId(index * 10);
					tvResScore.setId(index * 10 + 1);
					tvResID.setId(index * 10 + 2);
					LayoutParams lpResB = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					LayoutParams lpResTV = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					LayoutParams lpResID = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					lpResB.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
					lpResTV.addRule(RelativeLayout.RIGHT_OF, index * 10);
					lpResID.addRule(RelativeLayout.RIGHT_OF, index * 10 + 1);
					lpResB.setMargins(12, 2, 0, 6);
					lpResTV.setMargins(20, 2, 0, 6);
					lpResID.setMargins(20, 2, 0, 6);
					lpResB.addRule(RelativeLayout.BELOW, (index - 1) * 10);
					lpResTV.addRule(RelativeLayout.BELOW, (index - 1) * 10);
					lpResID.addRule(RelativeLayout.BELOW, (index - 1) * 10);
					String sScore = curTemp1.getString(3);
					String sValue = "";
					if (sScore.length() > 4) {
						if (sScore.substring(3, 5).equals("id")) {
							curTemp2 = dbTables.getRow(Integer.parseInt(sScore.substring(5)), "LOCAL");
							query = "SELECT B.VALUE FROM LOCAL AS A JOIN LINKS AS B ON A._ID = B.PARENT WHERE A.CATEGORY NOT LIKE \"COMPONENT\" AND A.CATEGORY NOT LIKE \"GAME\" AND B.FOCUS = " + sScore.substring(5) + " AND B.GAME_ID = " + metadata.getInt("Game",0);
							curTemp3 = dbAdapter.db.rawQuery(query, null);
							sValue = curTemp2.getString(2);
							if (curTemp3.getCount() > 0){
								sValue = sValue + " (" + curTemp3.getInt(7) + ")";
							}
							curTemp2.close();
							curTemp3.close();
						} else {
							sValue = sScore.substring(3);
						}
					}
					sScore = sScore.substring(0, 2);
					Log.e("LogFlag", "sScore = " + sScore);

					if (sScore.equals("gt")) {
						sScore = "greater than ";
					} else if (sScore.equals("lt")) {
						sScore = "less than ";
					} else if (sScore.equals("ge")) {
						sScore = "greater than or equal to ";
					} else if (sScore.equals("le")) {
						sScore = "less than or equal to ";
					} else if (sScore.equals("eq")) {
						sScore = "equal to ";
					} else {
						sScore = "unknown value ";
					}

					bResRemove.setText("Remove");
					tvResScore.setText(sScore + sValue);
					tvResID.setText(curTemp1.getInt(0) + "");
					tvResID.setVisibility(View.INVISIBLE);
					bResRemove.setLayoutParams(lpResB);
					tvResScore.setLayoutParams(lpResTV);
					tvResID.setLayoutParams(lpResID);
					rlRestric.addView(bResRemove);
					rlRestric.addView(tvResScore);
					rlRestric.addView(tvResID);
					bResRemove.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dbTables.open();
							int ResID = Integer.parseInt(((TextView) findViewById(v.getId() + (int) 2)).getText().toString());
							curTemp1 = dbTables.getRow(ResID, "RESTRICTS");
							curTemp2 = dbTables.getRow(curTemp1.getInt(2), "LOCAL");
							if ((curTemp2.getString(2).equals("Min Player Count") || curTemp2.getString(2).equals("Max Player Count")) & curTemp1.getString(3).contains("g")) {
								Act00LogonMaster.centerToast(getBaseContext(), "This cannot be removed");
							} else {
								dbTables.deleteRow(curTemp1.getInt(0), "RESTRICTS");
							}
							compView();
							dbTables.close();
						}
					});

					curTemp1.moveToNext();
				}
			}

			// start of user input for restrictions
			ArrayList <String> alCompr = new ArrayList<String>();
			int ScoreTypeIndicator = 0;
			if (curTemp1.getCount() > 0) {
				curTemp1.moveToFirst();
				while (!curTemp1.isAfterLast()){
					if(curTemp1.getString(3).contains("g")){
						ScoreTypeIndicator = ScoreTypeIndicator + 1;
					} else if(curTemp1.getString(3).contains("l")){
						ScoreTypeIndicator = ScoreTypeIndicator + 2;
					} else if(curTemp1.getString(3).contains("q")){
						ScoreTypeIndicator = ScoreTypeIndicator + 3;
					}
					curTemp1.moveToNext();
				}
			}
			if(ScoreTypeIndicator == 0){
				alCompr.add("Select inequality");
				alCompr.add("less than");
				alCompr.add("less than or equal to");
				alCompr.add("equal to");
				alCompr.add("greater than or equal to");
				alCompr.add("greater than");
			} else if (ScoreTypeIndicator == 1){
				alCompr.add("Select inequality");
				alCompr.add("less than");
				alCompr.add("less than or equal to");
			} else if (ScoreTypeIndicator == 2){
				alCompr.add("Select inequality");
				alCompr.add("greater than or equal to");
				alCompr.add("greater than");
			}
			if(ScoreTypeIndicator < 3) {
				index = index + 1;
				ArrayList <String> alScore = new ArrayList<String>(Arrays.asList("Select Type", "Constant", "Element"));
				final Button bResAdd = (Button) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_button, null);
				final Spinner sResCompr = (Spinner) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_spinner, null);
				final Spinner sResScore = (Spinner) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_spinner, null);
				ArrayAdapter aaScoreCompr = new ArrayAdapter(this, R.layout.my_list_items, alCompr);
				ArrayAdapter aaScoreType = new ArrayAdapter(this, R.layout.my_list_items, alScore);
				bResAdd.setId(index * 10);
				sResCompr.setId(index * 10 + 1);
				sResScore.setId(index * 10 + 2);
				Log.e("LogFlag", "idset = " + sResScore.getId());
				LayoutParams lpResBAdd = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				LayoutParams lpResSC = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				LayoutParams lpResS = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				lpResBAdd.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				lpResSC.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				lpResS.addRule(RelativeLayout.RIGHT_OF, index * 10 + 1);
				lpResBAdd.setMargins(0, 2, 15, 6);
				lpResSC.setMargins(12, 2, 0, 6);
				lpResS.setMargins(20, 2, 0, 6);
				lpResSC.addRule(RelativeLayout.BELOW, (index - 1) * 10);
				lpResS.addRule(RelativeLayout.BELOW, (index - 1) * 10);
				lpResBAdd.addRule(RelativeLayout.BELOW, (index) * 10 + 1);
				bResAdd.setLayoutParams(lpResBAdd);
				sResCompr.setLayoutParams(lpResSC);
				sResScore.setLayoutParams(lpResS);
				bResAdd.setText("Add");
				bResAdd.setVisibility(View.GONE);
				sResCompr.setAdapter(aaScoreCompr);
				sResScore.setAdapter(aaScoreType);
				rlRestric.addView(bResAdd);
				rlRestric.addView(sResCompr);
				rlRestric.addView(sResScore);

				Log.e("LogFlag", "idset = " + sResScore.getId());

				sResCompr.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> par, View view, int pos, long l) {
						if(pos == 0){
							try{
								rlRestric.removeView(findViewById(par.getId()+(int)2));
							} catch (Exception e){
								Log.e("LogFlag", "couldn't remove spinner due to - " + e);
							}
							findViewById(par.getId() + (int)1).setVisibility(View.GONE);
							findViewById(par.getId() - (int)1).setVisibility(View.GONE);
						} else {
							findViewById(par.getId() + (int)1).setVisibility(View.VISIBLE);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> adapterView) {

					}
				});

				sResScore.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int position, long arg3) {

						if(position == 0){
							try{
								rlRestric.removeView(findViewById(parent.getId() + (int)1));
								bResAdd.setVisibility(View.GONE);
							} catch (Exception e){
								Log.e("LogFlag", "Could not remove view due to " + e);
							}
						} else if (position == 1) {
							try{
								rlRestric.removeView(findViewById(parent.getId() + (int)1));
								bResAdd.setVisibility(View.GONE);
							} catch (Exception e){
								Log.e("LogFlag", "Could not remove view due to " + e);
							}
							EditText etResValue = (EditText) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_edittextscore, null);
							etResValue.setId(parent.getId() + (int)1);
							LayoutParams lpResETVal = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
							lpResETVal.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
							lpResETVal.addRule(RelativeLayout.BELOW, parent.getId() - (int)1);
							lpResETVal.setMargins(120, 2, 0, 6);
							etResValue.setLayoutParams(lpResETVal);
							etResValue.setHint("Enter Value");
							rlRestric.addView(etResValue);

							Log.e("LogFlag", "id = " + parent.getId());

							etResValue.addTextChangedListener(new TextWatcher() {
								@Override
								public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

								}
								@Override
								public void onTextChanged(CharSequence cs, int i, int i1, int i2) {
									if(cs.length() == 0){
										bResAdd.setVisibility(View.GONE);
									} else {
										bResAdd.setVisibility(View.VISIBLE);
									}
								}
								@Override
								public void afterTextChanged(Editable editable) {

								}
							});
						} else {
							try{
								rlRestric.removeView(findViewById(parent.getId() + (int)1));
								bResAdd.setVisibility(View.GONE);
							} catch (Exception e){
								Log.e("LogFlag", "Could not remove view due to " + e);
							}
							dbTables.open();
							String query = "SELECT C.NAME, C._ID FROM LOCAL AS C JOIN (PREREQS AS A LEFT JOIN RESTRICTS AS B ON A.FOCUS = B.FOCUS) ON C._ID = A.PREREQ_ID WHERE B.FOCUS IS NOT NULL AND B.VALUE NOT LIKE \"%id\" || [PREREQ_ID]  || \"%\" AND A.FOCUS = " + metadata.getInt(key,0) + " AND A.GAME_ID = " + metadata.getInt("Game", 0) + " AND B.GAME_ID = " + metadata.getInt("Game",0) + " ORDER BY NAME";
							curTemp1 = dbTables.db.rawQuery(query, null);
							if(curTemp1.getCount() == 0) {
								TextView tvResNone = (TextView) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_textview,null);
								tvResNone.setId(parent.getId()+ (int)1);
								LayoutParams lpResNone = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
								lpResNone.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
								lpResNone.addRule(RelativeLayout.BELOW, parent.getId() - (int)1);
								lpResNone.setMargins(0,2, 0, 6);
								tvResNone.setLayoutParams(lpResNone);
								tvResNone.setText("NO AVAILIBLE PREREQUISIT ELEMENTS");
								rlRestric.addView(tvResNone);
							} else {
								curTemp1.moveToFirst();
								ArrayList <String> alPrereqs = null;
								alPrereqs.add("Choose an Element");
								while (!curTemp1.isAfterLast()){
									alPrereqs.add(curTemp1.getString(0));
									curTemp1.moveToNext();
								}
								Spinner sResPrereq = (Spinner) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_spinner,null);
								sResPrereq.setId(parent.getId() + (int)1);
								sResPrereq.setAdapter(new ArrayAdapter(getBaseContext(), R.layout.my_list_items, alPrereqs));
								LayoutParams lpResPrereqs = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
								lpResPrereqs.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
								lpResPrereqs.addRule(RelativeLayout.BELOW, parent.getId() - (int)1);
								lpResPrereqs.setMargins(30, 2, 0, 6);
								sResPrereq.setLayoutParams(lpResPrereqs);

								sResPrereq.setOnItemSelectedListener(new OnItemSelectedListener() {
									@Override
									public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
										if(pos == 0){
											bResAdd.setVisibility(View.GONE);
										} else {
											bResAdd.setVisibility(View.VISIBLE);
										}
									}
									@Override
									public void onNothingSelected(AdapterView<?> adapterView) {

									}
								});
							}
							curTemp1.close();
							dbTables.close();
						}
					}
					@Override
					public void onNothingSelected(AdapterView<?> adapterView) {

					}
				});
				bResAdd.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dbTables.open();
						String sCompr = null;
						sCompr = ((Spinner)findViewById(v.getId()+(int)1)).getSelectedItem().toString();
						sCompr = sCompr.replace("less than or equal to", "le ");
						sCompr = sCompr.replace("greater than or equal to", "ge ");
						sCompr = sCompr.replace("greater than", "gt ");
						sCompr = sCompr.replace("equal to", "eq ");
						sCompr = sCompr.replace("less than", "lt ");
						boolean bSFlag = false;
						String sValue = null;
						try {
							sValue = ((Spinner)findViewById(v.getId()+(int)3)).getSelectedItem().toString();
							curTemp1 = dbTables.db.rawQuery("SELECT _ID FROM LOCAL WHERE NAME IS LIKE \"" + sValue + "\"", null);
							sValue = curTemp1.getString(0);
							curTemp1.close();
							bSFlag = true;
						} catch (Exception e){
							Log.e("LogFlag", "exception - " + e);
							sValue = ((EditText)findViewById(v.getId()+(int)3)).getText().toString();
						}
						if (bSFlag) {
							dbTables.createRes(metadata.getInt("Game", 0), metadata.getInt(key,0),sCompr + " id" + sValue, -1);
						} else {
							dbTables.createRes(metadata.getInt("Game", 0), metadata.getInt(key,0),sCompr + " " + sValue, -1);
						}
						compView();
						dbTables.close();
					}
				});
			}
		}
		Log.e("LogFlag", "bPosChange Visiblility = " + bPosChange.getVisibility() + " where visible = " + View.VISIBLE + " and gone = " + View.GONE);
		dbTables.close();
	}

	public void gameDetailsSetter() {
		// Verify which buttons should be show
		bCompGameChange.setVisibility(View.GONE);
//		Log.e("LogFlag", "game change should be here");
		if ((rbReqNo.isChecked() && sCompType.getSelectedItemPosition() != 0)
				|| ((rbReqYes.isChecked() || rbReq1.isChecked()) && (sCompType.getSelectedItemPosition() == 1 || sCompType.getSelectedItemPosition() == 2 || (sCompType.getSelectedItemPosition() > 2 && (!cbSame
						.isChecked() || (((sCompType.getSelectedItemPosition() == 3 || sCompType.getSelectedItemPosition() == 4) && etCompScore
						.getHint().length() == etCompScore.getText().length()))))))) {
//			Log.e("LogFlag", "game change should be here");
			bCompGameChange.setVisibility(View.VISIBLE);
			Cursor curTempA = dbTables.getAllLink(metadata.getInt("Game", 0), 0, metadata.getInt("Component", 0));
			int iTypeScore;
			if (curTempA.getString(7).equals("")){
				iTypeScore = -1;
			} else {
				iTypeScore = alCompTypeScore.indexOf(curTempA.getString(7).substring(0, 1));
			}
			if (iTypeScore == sCompType.getSelectedItemPosition()) {
				Cursor curTempB = dbTables.getAllInherit(curTempA.getInt(0));
				if (curTempB.getCount() != 0) {
					if ((curTempB.getInt(2) == 0 && rbReqYes.isChecked())
							|| (curTempB.getInt(2) == 1 && rbReq1.isChecked())
							&& alCompTypeScore.indexOf(curTempA.getString(7).substring(0, 1)) == sCompType.getSelectedItemPosition()
							&& ((curTempA.getString(7).length() <= 4 && !cbSame.isChecked()) || (curTempA.getString(7).length() > 4 && cbSame
									.isChecked()))) {
						bCompGameChange.setVisibility(View.GONE);
					}
//					} else if (rbReqNo.isChecked()) {
//						but.setVisibility(View.GONE);
				}
				curTempB.close();
			}
			curTempA.close();
			
		}
		String key0 = key;
		key = "";
		if (!rbReqYes.isChecked() && !rbReq1.isChecked() && sCompType.getSelectedItemPosition() != 0) {
			cbSame.setVisibility(View.INVISIBLE);
			cbSame.setChecked(false);
			etCompScore.setVisibility(View.GONE);
			etCompScore.setText("");
		} else {
			cbSame.setVisibility(View.VISIBLE);
			if (cbSame.isChecked()) {
				etCompScore.setVisibility(View.VISIBLE);
			} else {
				etCompScore.setVisibility(View.GONE);
				etCompScore.setText("");
			}
		}
		key = key0;
	}

	public ArrayList<int[]> Errors(int game, int comp, int sub) {
		Log.e("LogFlag", "Errors");
		ArrayList<int[]> result = new ArrayList<int[]>();
		// this will return list of arrays containing Local ids [gameId, compId,
		// subcompId] for objects that require additional data.
		Cursor curGame = null;
		Cursor curSysComp = null;
		Cursor curComp = null;
		Cursor curCompChild = null;
		Cursor curSub = null;
		Cursor curInher = null;
		Cursor curReq = null;
		Cursor curRes = null;
		Cursor curTempA = null;
		Cursor curTempB = null;



//		Cursor curPrereq = null;
//		dbLog.add("curPrereq");
		// things to test:
		// game: 5. has a system component
		// component: 1. is the component within the hiarchy?
		// 2. does it contain a score type,
		// 3. does each container contain something 6. does the parent belong to
		// the game?
		// element: 4. does it contain a link for every inherited element that
		// is necessary, 7. ensure that a prerequisits are met. 8. ensure that Restrictions aren't violated
		if (game == 0) {
			curGame = dbTables.getAllLocal("Game");
		} else {
			curGame = dbTables.getRow(game, "LOCAL");
		}
		if (curGame != null) {
			curGame.moveToFirst();
		}
		if (comp == 0) {
			curComp = dbTables.getAllLocal("Component");
		} else {
			curComp = dbTables.getRow(comp, "LOCAL");
		}
		if (curComp != null) {
			curComp.moveToFirst();
		}

		while (!curComp.isAfterLast()) {
			int comp0 = curComp.getInt(0);
			curGame.moveToFirst();
			while (!curGame.isAfterLast()) {
				int game0 = curGame.getInt(0);
				Log.e("LogFlag", "comp0 = " + comp0);
				curCompChild = dbTables.getAllLink(game0, 0, comp0);
				// 1. & 2.
				if(curCompChild.getCount()==0){
					if (!result.contains(new int[]{game0, comp0, 0}) && sub == 0) {
						result.add(new int[]{game0, comp0, 0});
					}
				} else {
				//	3.?
					curCompChild.moveToFirst();
					while (!curCompChild.isAfterLast()) {
						if (curCompChild.getInt(4) == 0 && !result.contains(new int[]{game0, comp0, 0}) && sub == 0) {
							result.add(new int[]{game0, comp0, 0});
						}
						curTempA = dbTables.getAllLocal(curComp.getString(2));
						if (curCompChild.getString(6).substring(0, 1).equals("c")
								&& curTempA.getCount() == 0
								&& !result.contains(new int[]{game0, comp0, 0}) && sub == 0) {
							result.add(new int[]{game0, comp0, 0});
						}
						curTempA.close();
						// 6.
						if (curCompChild.getCount() > 0 && curCompChild.getInt(4)>0) {
							curTempA = dbTables.getAllLink(game0, 0, curCompChild.getInt(4));
							curTempB = dbTables.getRow(curCompChild.getInt(4), "LOCAL");
							Log.e("LogFlag", "curCompChild = " + curCompChild.getInt(4));
							if (((curTempA.getCount() == 0 && !curTempB.getString(1).equals("System")) || curCompChild.getInt(4) == 0)
									&& !result.contains(new int[]{game0, comp0, 0}) && sub == 0) {
								result.add(new int[]{game0, comp0, 0});
							}
							curTempA.close();
							curTempB.close();
						}
						curCompChild.moveToNext();
					}
				}
				// 5.

				curSysComp = DBAdapter.db.rawQuery("SELECT LINK.FOCUS FROM LINKS AS LINK LEFT JOIN LINKS AS GL ON LINK.FOCUS = GL.FOCUS " +
						"WHERE LINK.PARENT = 0 AND GL.FOCUS IS null AND GL.GAME_ID = " + game0, null);
				if (curSysComp.getCount() > 0 && !result.contains(new int[]{game0, 0, 0}) && comp == 0 && sub == 0) {
					result.add(new int[]{game0, 0, 0});
				}
				// 4.
				if (sub == 0) {
					curTempA = dbTables.getRow(comp0, "LOCAL");
					curSub = dbTables.getAllLocal(curTempA.getString(2));
					curTempA.close();
				} else {
					curSub = dbTables.getRow(sub, "LOCAL");
				}
				if (curSub.getCount() > 0) {
					curSub.moveToFirst();
				}
				while (!curSub.isAfterLast()) {
					int sub0 = curSub.getInt(0);
//					The query below will check to see if every inherited element (that has a 0) will be linked and also that every group that is inherited (with a 1) has a link.
//							This will be one by joining the inherited table to the linked table.  Then looking at children form both components (ensuring the children aren't components) and seeing if they have a link.
//							the groups will be a bit more complex... but the same principle.
					String query = "SELECT VALUE FROM " +
							"(SELECT REQ.GAME_ID AS GAMEID, REQ.COMPID, REQ.ELID, REAL.VALUE FROM " +
								"(SELECT MAINNAME._ID AS COMPID, MAINCHILD._ID AS ELID, LLMAIN.GAME_ID, MAINCHILD.NAME AS MAINNAME, MAINCHILD._ID AS MAINID, SUBNAME.CATEGORY AS SUBCAT, SUBNAME.NAME AS SUBNAME, SUBNAME._ID AS SUBID, INHER.INHER_TYPE AS TYPE " +
									"FROM INHER LEFT JOIN LINKS AS LLMAIN ON INHER.LINK_ID = LLMAIN._ID JOIN LOCAL AS MAINNAME ON MAINNAME._ID = LLMAIN.PARENT " +
									"JOIN LOCAL AS MAINCHILD ON MAINCHILD.CATEGORY LIKE MAINNAME.NAME JOIN LINKS AS SUBLINK ON SUBLINK.PARENT = LLMAIN.FOCUS " +
									"JOIN LOCAL AS SUBNAME ON SUBLINK.FOCUS = SUBNAME._ID " +
									"WHERE SUBLINK.GAME_ID = LLMAIN.GAME_ID AND TYPE = 0 AND SUBNAME.CATEGORY IS NOT \"Component\")	AS REQ " +
								"LEFT JOIN LINKS AS REAL ON REAL.PARENT = REQ.MAINID AND REAL.FOCUS = REQ.SUBID AND REAL.GAME_ID = REQ.GAME_ID " +
							"UNION " +
							"SELECT REQ.GAME_ID AS GAMEID, REQ.COMPID, REQ.ELID, REAL.VALUE FROM " +
								"(SELECT MAINNAME._ID AS COMPID, MAINCHILD._ID AS ELID, LLMAIN.GAME_ID, MAINCHILD.NAME AS MAINNAME, MAINCHILD._ID AS MAINID, SUBNAME.CATEGORY AS SUBCAT, SUBNAME.NAME AS SUBNAME, SUBNAME._ID AS SUBID, INHER.INHER_TYPE AS TYPE " +
									"FROM INHER LEFT JOIN LINKS AS LLMAIN ON INHER.LINK_ID = LLMAIN._ID " +
									"JOIN LOCAL AS MAINNAME ON MAINNAME._ID = LLMAIN.PARENT JOIN LOCAL AS MAINCHILD ON MAINCHILD.CATEGORY LIKE MAINNAME.NAME " +
									"JOIN LOCAL AS SUBNAME ON SUBNAME._ID = LLMAIN.FOCUS) AS REQ " +
							"LEFT JOIN (" +
									"SELECT LLSCORE.GAME_ID, LLSCORE.PARENT AS COMPID, PARNAME._ID AS ELID, ELNAME._ID AS SUBID, LLSCORE.VALUE " +
									"FROM LINKS AS LLSCORE JOIN LOCAL AS ELNAME ON LLSCORE.FOCUS = ELNAME._ID JOIN LOCAL AS PARNAME ON ELNAME.CATEGORY LIKE PARNAME.NAME) AS REAL " +
							"ON REQ.GAME_ID = REAL.GAME_ID AND REQ.ELID = REAL.COMPID AND REQ.SUBID = REAL.ELID " +
							"WHERE TYPE = 1) AS COMB " +
							"WHERE GAMEID = " + game0 + " AND COMPID = " + comp0 + " AND ELID = " + sub0 + " AND VALUE IS NULL";
					curInher = DBAdapter.db.rawQuery(query, null);
					// 4.
					if(curInher.getCount()>0 & !result.contains(new int[]{game0, comp0, sub0})) {
						result.add(new int[]{game0, comp0, sub0});
					};
					curInher.close();
					// 7. & 8.
					curReq = dbTables.getAllLink(game0,sub0,0);
					curRes = dbTables.getAllLink(game0, sub0,0);
					boolean errorflag = false;

					while (!curReq.isAfterLast()){
						if(!prereqChecker(sub0, curReq.getInt(5))){
							errorflag = true;
						}
						curReq.moveToNext();
					}
					while (!curRes.isAfterLast()){
						if(!resChecker(curRes.getInt(5), curRes.getString(7))){
							errorflag = true;
						}
						curRes.moveToNext();
					}
					if (errorflag && !result.contains(new int[]{game0, comp0, sub0})) {
						result.add(new int[]{game0, comp0, sub0});
					}
					curReq.close();
					curRes.close();
					curSub.moveToNext();
					curSysComp.close();
				}

				curSub.close();
				curGame.moveToNext();
			}
			curCompChild.close();
			curComp.moveToNext();
		}
		curGame.close();
		curComp.close();
		for (int[] i : result) {
		}
		return result;
	}

	public void clear() {
//		Log.e("LogFlag", "clear start");
		String key0 = key;
		key = "";
		etName.setText("");
		etDesc.setText("");
//		etLinkName.setText("");
		sCompParent.setSelection(0);
		rgReq.clearCheck();
		sCompType.setSelection(0);
		cbSetInGame.setVisibility(View.GONE);
		cbEditable.setChecked(false);
//		rlOption.setVisibility(View.GONE);
//		bLink.setVisibility(View.GONE);
//		bCompGame.setVisibility(View.GONE);
//		bNext.setVisibility(View.GONE);
//		etCompType.setVisibility(View.GONE);
//		etCompType.setText("");
		key = key0;
		rlScore.removeAllViews();
		rlInherited.removeAllViews();
		rlOptional.removeAllViews();
		rlPrereq.removeAllViews();
//		iDelCheck = 0;
//
	}

	public boolean isNumeric(Object entry, int start, int end) {
		String string = "";
		try {
			string = ((EditText)entry).getText().toString();
		} catch (Exception error) {
			string = (String) entry;
		}
		if (end != -1) {
			try {
				int i = Integer.parseInt(string.substring(start, end));
			} catch (NumberFormatException nfe) {
				return false;
			}
		} else {
			try {
				int i = Integer.parseInt(string.substring(start));
			} catch (NumberFormatException nfe) {
				return false;
			}
		}
		return true;
	}

	public int setupSelectorIntro(int index, ArrayList<String> alCompName, final ArrayList<int[]> alEl, final ArrayList<String> alCompTypeString, final RelativeLayout rl) {
		final ArrayList<Integer> alElIndex = new ArrayList<Integer>();
		final ArrayList<String> alCompList = new ArrayList<String>();
		final String[] equation = new String[1];
		alCompList.add("Component");
		final ArrayList<Integer> alElList = new ArrayList<Integer>();
		final Spinner sComp = (Spinner) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_spinner, null);
		sComp.setId(index * 10);
		LayoutParams lpSComp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpSComp.addRule(RelativeLayout.BELOW, (index - 1) * 10);
		lpSComp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		lpSComp.setMargins(25, 0, 0, 0);
		sComp.setLayoutParams(lpSComp);
		rl.addView(sComp);		
		sComp.setAdapter(new ArrayAdapter(getBaseContext(), R.layout.my_list_items, alCompName));
		final Spinner sEl = (Spinner) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_spinner, null);
		sEl.setId(index * 10 + 1);
		LayoutParams lpSEl = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpSEl.addRule(RelativeLayout.BELOW, (index - 1) * 10);
		lpSEl.addRule(RelativeLayout.RIGHT_OF, index * 10);
		lpSEl.setMargins(10, 0, 0, 0);
		sEl.setLayoutParams(lpSEl);
		sEl.setVisibility(View.GONE);
		rl.addView(sEl);
		final EditText etScore = (EditText) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_edittextscore, null);
		etScore.setId(index * 10 + 2);
		LayoutParams lpETScore = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpETScore.addRule(RelativeLayout.BELOW, index * 10);
		lpETScore.addRule(RelativeLayout.RIGHT_OF,index * 10 + 4);
		lpETScore.setMargins(5, 0, 0, 0);
		etScore.setLayoutParams(lpETScore);
		etScore.setVisibility(View.GONE);
		rl.addView(etScore);
		final Button bVal = (Button) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_button, null);
		bVal.setId(index * 10 + 3);
		LayoutParams lpBVal = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpBVal.setMargins(2, 0, 0, 0);
		lpBVal.addRule(RelativeLayout.RIGHT_OF, index * 10 + 2);
		lpBVal.addRule(RelativeLayout.BELOW, index * 10);
		bVal.setLayoutParams(lpBVal);
		bVal.setText("store as value");
		bVal.setVisibility(View.GONE);
		rl.addView(bVal);
		final Button bNew = (Button) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_button, null);
		bNew.setId(index * 10 + 4);
		LayoutParams lpNew = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpNew.setMargins(5, 0, 0, 0);
		lpNew.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		lpNew.addRule(RelativeLayout.BELOW, index * 10);
		bNew.setLayoutParams(lpNew);
		bNew.setText("set trait");
		bNew.setVisibility(View.GONE);
		rl.addView(bNew);
		final EditText etVal = (EditText) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_edittextscore, null);
		etVal.setId(index * 10 + 5);
		LayoutParams lpETVal = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpETVal.addRule(RelativeLayout.BELOW, index * 10);
		lpETVal.addRule(RelativeLayout.RIGHT_OF,index * 10 + 4);
		lpETVal.setMargins(10, 0, 0, 0);
		etVal.setLayoutParams(lpETVal);
		etVal.setVisibility(View.GONE);
		rl.addView(etVal);
		final EditText etSymbol = (EditText) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_edittext, null);
		etSymbol.setId(index * 10 + 6);
		LayoutParams lpETSym = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpETSym.setMargins(5, 0, 0, 0);
		lpETSym.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lpETSym.addRule(RelativeLayout.BELOW, index * 10);
		etSymbol.setLayoutParams(lpETSym);
		etSymbol.setHint("+ - * / max");
		etSymbol.setVisibility(View.GONE);
		rl.addView(etSymbol);
		final Spinner sNewComp = (Spinner) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_spinner, null);
		sNewComp.setId(index * 10 + 7);
		LayoutParams lpSNComp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpSNComp.setMargins(5, 5, 0, 0);
		lpSNComp.addRule(RelativeLayout.RIGHT_OF,index * 10 + 5);
		lpSNComp.addRule(RelativeLayout.BELOW, index * 10);
		sNewComp.setLayoutParams(lpSNComp);
		sNewComp.setVisibility(View.GONE);
		rl.addView(sNewComp);
		final Spinner sNewEl = (Spinner) getLayoutInflater().from(getBaseContext()).inflate(R.layout.temp_spinner, null);
		sNewEl.setId(index * 10 + 8);
		LayoutParams lpSNEl = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lpSNEl.setMargins(5, 5, 0, 0);
		lpSNEl.addRule(RelativeLayout.RIGHT_OF,index * 10 + 7);
		lpSNEl.addRule(RelativeLayout.BELOW, index * 10 + 2);
		sNewEl.setLayoutParams(lpSNEl);
		sNewEl.setVisibility(View.GONE);
		rl.addView(sNewEl);		
		
		sComp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View View, int position, long arg3) {
				dbTables.open();
				bNew.setVisibility(View.GONE);
				etScore.setVisibility(View.GONE);
				etSymbol.setVisibility(View.GONE);
				bVal.setVisibility(View.GONE);
				etVal.setVisibility(View.GONE);
				if (position == 0) {
					sEl.setVisibility(View.GONE);
				} else {
					String sType = alCompTypeString.get(position);
					ArrayList<String> alElName = new ArrayList<String>();
					alElIndex.clear();
					alElName.add("set the element");
					alElIndex.add(0);
					for (int i = 0; i < alEl.size(); i++) {
						if (position == alEl.get(i)[0]) {
							Cursor curTempA = dbTables.getRow(alEl.get(i)[1], "LOCAL");
							alElName.add(curTempA.getString(2));
							curTempA.close();
							alElIndex.add(i);
						}
					}
					sEl.setAdapter(new ArrayAdapter(getBaseContext(), R.layout.my_list_items, alElName));
					sEl.setVisibility(View.VISIBLE);
					etScore.setHint(alCompTypeHint.get(alCompTypeScore.indexOf(sType.substring(0, 1))));
					if (sType.length() > 4) {
						String key0 = key;
						key = "";
						etScore.setText(sType.substring(4));
						key = key0;
					}
				}
				dbTables.close();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		sEl.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int position, long arg3) {
				dbTables.open();
				bVal.setVisibility(View.GONE);
				etVal.setVisibility(View.GONE);
				if (position != 0) {
					String typeScore = alCompTypeString.get(alEl.get(alElIndex.get(position))[0]).substring(0, 1);
					if (typeScore.equals("c")) {
						bNew.setVisibility(View.VISIBLE);
						etScore.setVisibility(View.GONE);
					} else if (rl == rlPrereq){
						etScore.setVisibility(View.INVISIBLE);
						etVal.setVisibility(View.VISIBLE);
						etVal.setHint("minimum value");
					} else {
						etScore.setVisibility(View.VISIBLE);
						if (typeScore.equals("e")){
							alCompList.clear();
							alCompList.add("Select Component");
							String query = "SELECT COMP.NAME, COMP._ID FROM LOCAL AS EL JOIN LOCAL AS COMP WHERE EL.CATEGORY = COMP.NAME";
							Cursor curComp = dbAdapter.db.rawQuery(query, null);
							if(curComp.getCount() > 0){
								curComp.moveToFirst();
							}
							int i = 0;
							while (!curComp.isAfterLast()){
								int newFocus = curComp.getInt(1);
								int loopFlag = 0;
								while (loopFlag == 0){
									Cursor curTempA = dbTables.getRow(newFocus, "LOCAL");
									if(newFocus == -1){
										loopFlag = 2;
									} else if (curTempA.getString(1).equals("System")){
										loopFlag = 2;
									} else if (newFocus == metadata.getInt("Component", 0)){
										loopFlag = 1;
									} else {
										Cursor curTempB = dbTables.getAllLink(0, 0, newFocus);
										newFocus = curTempB.getInt(4);
										curTempB.close();
									}
									curTempA.close();
								}
								if(loopFlag ==1){
									Log.e("LogFlag", "focus ID = " + curComp.getInt(1));
									Cursor curTempA = dbTables.getAllLink(metadata.getInt("Game", 0), 0, curComp.getInt(1));
									Log.e("LogFlag", "curTemp1 count:" + curTemp1.getCount());
									Cursor curTempB = dbTables.getAllInherit(curTempA.getInt(0));
									if (!alCompList.contains(curComp.getString(0)) && (!curTemp1.getString(7).contains("c |") && (curTemp2.getCount() == 0))){
										alCompList.add(curComp.getString(0));
									}
									curTempA.close();
									curTempB.close();
								}
								curComp.moveToNext();
							}
							curComp.close();
							sNewComp.setAdapter(new ArrayAdapter(getBaseContext(),R.layout.my_list_items,alCompList));
							etVal.setHint("multiplier");
							LayoutParams lpTemp = (LayoutParams) etVal.getLayoutParams();
							lpTemp.addRule(RelativeLayout.BELOW, etScore.getId());
							lpTemp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
							etVal.setLayoutParams(lpTemp);
							((LayoutParams)etScore.getLayoutParams()).addRule(RelativeLayout.LEFT_OF,etSymbol.getId());
							etSymbol.setVisibility(View.VISIBLE);
							if (!etScore.getText().toString().equals("") && (etVal.getText().toString().equals("") || sNewEl.getVisibility() == View.VISIBLE)){
								bNew.setVisibility(View.VISIBLE);
							} else {
								bNew.setVisibility(View.INVISIBLE);
							}
						} else {
							LayoutParams lpTemp = (LayoutParams) etVal.getLayoutParams();
							lpTemp.addRule(RelativeLayout.BELOW, sComp.getId());
							lpTemp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
							etVal.setLayoutParams(lpTemp);							
							bNew.setVisibility(View.GONE);
							String key0 = key;
							key = "";
							etScore.setText("");
							key = key0;
						}
					}
				} else {
					etScore.setVisibility(View.GONE);
					etSymbol.setVisibility(View.GONE);
				}
				dbTables.close();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		etSymbol.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable current) {
				if (current.length() == 0){
					etVal.setText("");
					etVal.setVisibility(View.GONE);
					sNewComp.setVisibility(View.GONE);
					sNewEl.setVisibility(View.GONE);
					if(etScore.getText().length() > 0){
						bNew.setVisibility(View.VISIBLE);
						bNew.setText("set trait");
					}
				}
				if (new ArrayList<String>(Arrays.asList("+","-","*","/","max")).contains(current.toString())){
					etVal.setVisibility(View.VISIBLE);
					bNew.setVisibility(View.INVISIBLE);
					etVal.requestFocus();
				} else if (!new ArrayList<String>(Arrays.asList("m","ma")).contains(current.toString())){
					current.replace(0,current.length(),"");
					etScore.requestFocus();
				}
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {	
			}
		});
		etScore.addTextChangedListener(twScore);
		etScore.addTextChangedListener(new TextWatcher() {
			String pre = "";
			@Override
			public void afterTextChanged(Editable current) {
				if (!key.equals("")) {
					EditText view = (EditText) getCurrentFocus();
					if (alCompTypeHint.indexOf(etScore.getHint()) == 2){
						if (current.length() < pre.length() && equation[0] != null){
							int end = 0;
							int eend = 0;
							if (equation[0].substring(0,Math.max(equation[0].length() - 1,0)).contains(">")){
								eend = equation[0].substring(0,equation[0].length() - 1).lastIndexOf(">") + 1;
								String tempSym;
								if (equation[0].substring(eend,eend+1).equals("m")){
									tempSym = equation[0].substring(eend,eend+3);
								} else {
									tempSym = equation[0].substring(eend,eend+1);
								}
								end = current.toString().lastIndexOf(tempSym);
							}
							String key0 = key;
							key = "";
							current = current.replace(end, current.length(), "");
							key = key0;
							equation[0] = equation[0].substring(0, eend);
						}
						if (current.length() == 0 && (sNewEl.getVisibility() == View.GONE || sNewEl.getSelectedItemPosition() == 0)){
							bNew.setVisibility(View.INVISIBLE);
						} else {
							bNew.setVisibility(View.VISIBLE);
							bNew.setText("set trait");
						}
					} else {
						if (etScore.getHint().length() == current.length() && !etScore.getHint().toString().equals("")) {
							bNew.setVisibility(View.VISIBLE);
						} else {
							bNew.setVisibility(View.GONE);
							((EditText) findViewById((int)view.getId() + 3)).setVisibility(View.GONE);
							findViewById((int)view.getId() + 3).requestFocus(); 
							((EditText) findViewById((int)view.getId() + 3)).setText("");
						}
					}
				}
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			pre = s + "";
			}
		});
		bVal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				bVal.setVisibility(View.GONE);
				etVal.setVisibility(View.VISIBLE);
				etVal.setText(SubCalculator.calc(etScore.getText().toString()) + "");
			}
		});
		etVal.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable current) {
				if (!key.equals("")) {
					bNew.setVisibility(View.VISIBLE);
					if (current.toString().equals("")) {
						Log.e("LogFlag", "1");
						bNew.setVisibility(View.INVISIBLE);
						sNewComp.setVisibility(View.GONE);
						sNewEl.setVisibility(View.GONE);
						if (alCompTypeHint.indexOf(etScore.getHint()) == 2 && etScore.getText().length() > 0){
							bNew.setVisibility(View.VISIBLE);
						}
					} else if (rl == rlPrereq){
					} else if (alCompTypeHint.indexOf(etScore.getHint())==2 && sNewEl.getVisibility() == View.GONE){
						Log.e("LogFlag", "alcomplist size: " + alCompList.size());
						bNew.setVisibility(View.INVISIBLE);
						sNewComp.setVisibility(View.VISIBLE);
					}
				}
			}
		});
		
		sNewComp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (position > 0){
					dbTables.open();
					ArrayList<String>alElName2 = new ArrayList<String>();
					ArrayList<Integer>alElId = new ArrayList<Integer>();
					alElName2.add("Select Element");
					alElId.add(0);
					Cursor curEl = dbTables.getAllLocal((String)sNewComp.getSelectedItem());
					while (!curEl.isAfterLast()){
						if (curEl.getInt(0) != alEl.get(alElIndex.get(sEl.getSelectedItemPosition()))[1]){
							alElName2.add(curEl.getString(2));
							alElId.add(curEl.getInt(0));
						}
						curEl.moveToNext();
					}
					curEl.close();
					dbTables.close();
					sNewEl.setAdapter(new ArrayAdapter(getBaseContext(),R.layout.my_list_items,alElName2));
					sNewEl.setVisibility(View.VISIBLE);
				} else {
					sNewEl.setVisibility(View.GONE);
					bNew.setVisibility(View.INVISIBLE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {	
			}
		});
		
		sNewEl.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (position > 0){
					bNew.setVisibility(View.VISIBLE);
					bNew.setText("condense equation");
				} else {
					bNew.setVisibility(View.INVISIBLE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		bNew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dbTables.open();
				if(rl == rlPrereq){
					Cursor curTempA1 = dbTables.getAllPrereq(metadata.getInt("Game", 0), metadata.getInt(key,0), alEl.get(alElIndex.get(sEl.getSelectedItemPosition()))[1]);
					if(curTempA1.getCount()>0){
						Act00LogonMaster.centerToast(getBaseContext(), "This requirement already exists. Please remove the prior value.");
						curTempA1.close();
					} else {
						int score;
//						need to make it able to save requirements if it is an object.
						if(etVal.getVisibility() == View.GONE){
							score = 0;
						} else {
							score = Integer.parseInt(etVal.getText().toString());
						}
						dbTables.createPrereq(metadata.getInt("Game", 0), alEl.get(alElIndex.get(sEl.getSelectedItemPosition()))[1], metadata.getInt(key, 0), score, 0);
						curTempA1.close();
						compView();
					}
				} else {
					Cursor curTempA1 = dbTables.getAllLink(metadata.getInt("Game", 0), 0, metadata.getInt("Game", 0));
					int majVer = curTempA1.getInt(2);
					int newVer = curTempA1.getInt(3) + 1;
					curTempA1 = dbTables.getRow((Integer)Act00LogonMaster.dbToList(getBaseContext(), metadata.getInt("Game",0),"Component",0,alEl.get(alElIndex.get(sEl.getSelectedItemPosition()))[1],0,0,0,"ALL",0,0,0).get(0), "LINKS");
					int iEditable = curTempA1.getInt(8);
					int iHidden = curTempA1.getInt(9);
					curTempA1.close();
					String score = "-0";
					int order = 0;
					Cursor curOrder = dbTables.getAllLink(metadata.getInt("Game", 0), metadata.getInt(key, 0), 0);
					while (!curOrder.isAfterLast()){
						order = Math.max(order, curOrder.getInt(6));
						curOrder.moveToNext();
					}
					curOrder.close();
					if (alCompTypeHint.indexOf(etScore.getHint()) == 2){
						if (equation[0] == null){
							equation[0] = etScore.getText() + "";
						}
						if (sNewEl.getVisibility() == View.VISIBLE){
							etScore.setInputType(InputType.TYPE_CLASS_TEXT);
							etScore.setText(etScore.getText() + etSymbol.getText().toString() + etVal.getText() + sNewEl.getSelectedItem());
							etScore.setInputType(InputType.TYPE_CLASS_NUMBER);
							equation[0] = equation[0] + etSymbol.getText() + etVal.getText().toString() + "<" + alElIndex.get(sNewEl.getSelectedItemPosition()) + ">";
							sNewEl.setVisibility(View.GONE);
							sNewComp.setVisibility(View.GONE);
							etSymbol.requestFocus();
							etSymbol.setText("");
							bNew.setVisibility(View.VISIBLE);
						} else {
							score = equation[0];
							dbTables.createLink(metadata.getInt("Game", 0), majVer, newVer, metadata.getInt(key, 0),
									alEl.get(alElIndex.get(sEl.getSelectedItemPosition()))[1], order + 1, score, iEditable, iHidden, -1);
							compView();
						}
						etSymbol.setText("");
						etVal.setText("");
						sNewComp.setSelection(0);
					} else {
						if (etScore.getVisibility() == View.VISIBLE) {
							score = etScore.getText().toString();
						}
						if (etVal.getVisibility() == View.VISIBLE) {
							score = etVal.getText().toString();
						}
						dbTables.createLink(metadata.getInt("Game", 0), majVer, newVer, metadata.getInt(key, 0),
								alEl.get(alElIndex.get(sEl.getSelectedItemPosition()))[1], order, score, iEditable, iHidden, -1);
						compView();
					}
				}
				dbTables.close();
			}
		});
		return index;
	}

	public boolean prereqChecker(int parent, int focus){
		Cursor curPrereq = dbTables.getAllPrereq(metadata.getInt("Game", 0), focus, 0);
		while (!curPrereq.isAfterLast()){
			Cursor curTempA = dbTables.getAllLink(metadata.getInt("Game",0), parent, curPrereq.getInt(3));
			if (curTempA.getCount()>0){
				if (curTempA.getString(7) != null){
					if (isNumeric(curTempA.getString(7),0,-1)){
						if (Integer.parseInt(curTempA.getString(7)) < curPrereq.getInt(4)){
							curTempA.close();
							curPrereq.close();
							return false;
							}
					} else {
						curTempA.close();
						curPrereq.close();
						return false;
						}
				}
			} else {
				curTempA.close();
				curPrereq.close();
				return false;
			}
			curTempA.close();
			curPrereq.moveToNext();
			
		}
		curPrereq.close();
		return true;
	}

	public boolean resChecker(int focus, String score){
		Cursor curTemp1A;
		Cursor curTemp1B;
		curTemp1A = dbTables.getAllRes(metadata.getInt("Game", 0), focus);
		int reqScore = 0;
		if (curTemp1A.getCount() > 0){
			curTemp1A.moveToFirst();
			while (!curTemp1A.isAfterLast()){
				if(curTemp1A.getString(3).contains("id")){
					String query = "SELECT VALUE " +
							"FROM (SELECT ll._ID, CATEGORY, PARENT FROM LINKS AS ll LEFT JOIN LOCAL AS l on ll.FOCUS = l._ID) AS dbf LEFT JOIN (" +
							"SELECT ll._ID, GAME_ID, NAME, FOCUS, VALUE FROM LINKS AS ll LEFT JOIN LOCAL AS l on ll.PARENT = l._ID) AS dbp on dbf._ID = dbp._ID " +
							"WHERE NOT dbf.CATEGORY LIKE dbp.NAME AND FOCUS = " + curTemp1A.getString(3).substring(5) + " AND GAME_ID = " + metadata.getInt("Game", 0);
					curTemp1B = dbAdapter.db.rawQuery(query,null);
					try {
						curTemp1B.moveToFirst();
						reqScore = curTemp1B.getInt(0);
					} catch (Exception e) {
						Act00LogonMaster.centerToast(getBaseContext(), "The prereq score is not a number 1");
						curTemp1B.close();
						curTemp1A.close();
						return false;
					}
					curTemp1B.close();
				} else {
					try{
						reqScore = Integer.parseInt(curTemp1A.getString(3).substring(3));
					} catch (Exception e){
						Act00LogonMaster.centerToast(getBaseContext(), "The prereq score is not a number 2");
						curTemp1A.close();
						return false;
					}
				}
				try {
					if ((curTemp1A.getString(3).substring(0, 2).equals("gt") && Integer.parseInt(score) <= reqScore) ||
							(curTemp1A.getString(3).substring(0, 2).equals("ge") && Integer.parseInt(score) < reqScore) ||
							(curTemp1A.getString(3).substring(0, 2).equals("eq") && Integer.parseInt(score) != reqScore) ||
							(curTemp1A.getString(3).substring(0, 2).equals("le") && Integer.parseInt(score) > reqScore) ||
							(curTemp1A.getString(3).substring(0, 2).equals("lt") && Integer.parseInt(score) >= reqScore)) {
						curTemp1A.close();
						return false;
					}
				} catch (Exception e){
					Act00LogonMaster.centerToast(getBaseContext(), "The actual score is not a number");
					curTemp1A.close();
					return false;
				}
				curTemp1A.moveToNext();
			}
		}
		curTemp1A.close();
		return true;
	}
}
