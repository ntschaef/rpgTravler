package happyface.rpgtraveler.com;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
//import android.widget.AdapterView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
//import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
//import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class Act00LogonMaster_old extends Activity {
	SharedPreferences metadata;
	Editor editor;
	DBAdapter dbAdapter;
	DBTables dbTables;
	static Cursor curTemp1 = null;
	static Cursor curTemp2 = null;
	static Cursor curTemp3 = null;
	static Cursor curTemp4 = null;
	static Cursor curTemp5 = null;
	RelativeLayout rlLogon;
	RadioGroup rgGroup;
	RelativeLayout rlSearch;
	TextView tvSearch;
	EditText etSearch;
	LinearLayout llMain;
	LinearLayout llTop;
	Spinner sTop;
	LinearLayout llTopB;
	Button bTopEdit;
	Button bTopRemove;
	LinearLayout llBot;
	Spinner sBot;
	LinearLayout llBotB;
	Button bBotEdit;
	Button bBotRemove;
	Button bPlay;
	ArrayList<Integer> alSearch;
	ArrayAdapter aaTop;
	ArrayList<String> alTop;
	ArrayList<Integer> alTop_idLOCAL;
	ArrayList<Integer> alTop_idLINK;
	ArrayAdapter aaBot;
	ArrayList<String> alBot;
	ArrayList<Integer> alBot_idLOCAL;
	ArrayList<Integer> alBot_idLINK;

	ArrayList<String>alReqFocus = new ArrayList<String>(Arrays.asList("Game", "Metadata", "Turn Type", "Turn Focus", "Game Info", "Map", "MapType", "MapProps", "Group", "Player", "MapPreferences", "MapPrefProps", "Character", "CharProps", "CharStats", "CharCalc", "MainStatus", "CharLocation", "CharTemplate", "TemplateProps", "CharTempAction", "Actions", "ActProps", "AttrEffects", "AttrCharProps", "ActRangeOption", "ActRangeProps", "ActShape", "ActDirect", "Passable", "theGame", "Ordered", "Simutanious", "PlayerFocus", "CharFocus", "Min Player Count", "Max Player Count", "Full Sum", "DefaultMap", "Hexagon", "Square (simplified)", "Square (complex)", "MapCellSize", "Archive", "Damage", "CurrentHP", "Normal", "Hurt", "Gone", "xPosition", "yPosition", "zPosition", "Standard piece", "HPMax", "Basic Action", "Partial Sum", "No Attributes", "MinRange", "MaxRange", "Width", "Line", "Cone", "Forward", "Targeted", "Stopped", "Not Stopped"));
	ArrayList<String> alReqParent = new ArrayList<String>(Arrays.asList("System", "Game", "Metadata", "Metadata", "Metadata", "Metadata", "Map", "Map", "Game", "Group", "Player", "MapPreferences", "Player", "Character", "CharProps", "CharProps", "CharProps", "CharProps", "Character", "CharTemplate", "CharTemplate", "Game", "Actions", "Actions", "AttrEffects", "Actions", "ActRangeOption", "ActRangeOption", "ActRangeOption", "ActRangeOption", "Metadata", "Turn Type", "Turn Type", "Turn Focus", "Turn Focus", "Game Info", "Game Info", "Game Info", "Map", "MapType", "MapType", "MapType", "MapProps", "Player", "CharStats", "CharCalc", "MainStatus", "MainStatus", "MainStatus", "CharLocation", "CharLocation", "CharLocation", "CharTemplate", "TemplateProps", "Actions", "ActProps", "AttrEffects", "ActRangeProps", "ActRangeProps", "ActRangeProps", "ActShape", "ActShape", "ActDirect", "ActDirect", "Passable", "Passable"));
	ArrayList<String> alReqScore = new ArrayList<String>(Arrays.asList("", "c | ", "c | ", "c | ", "e | ", "c | ", "c | ", "e | ", "c | ", "c | ", "c | ", "e | ", "c | ", "c | ", "e | ", "d | ", "e | ", "e | ", "c | ", "e | ", "a | ", "c | ", "e | ", "c | ", "e | ", "c | ", "e | ", "c | ", "c | ", "c | ", "", "1", "0", "0", "1", "", "", "", "", "1", "0", "2", "", "", "", "", "id50", "1", "0", "", "", "", "", "", "", "", "", "", "", "", "1", "0", "1", "0", "1", "0"));
	ArrayList<Integer> alReqHidden = new ArrayList<Integer>(Arrays.asList(null, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
	ArrayList<Integer> alReqTrait = new ArrayList<Integer>(Arrays.asList(null, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1));
	ArrayList<String> alReqType = new ArrayList<String>(Arrays.asList("", "ONE", "ONE", "ONE", "ALL", "ONE", "ONE", "ALL", "ONE", "ONE", "ONE", "ALL", "NONE", "NONE", "ALL", "ALL", "DEP", "ALL", "NONE", "ALL", "NONE", "NONE", "ALL", "ONE", "ALL", "NONE", "ALL", "ONE", "ONE", "ONE", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
	ArrayList<String> alAddType = new ArrayList<String>(Arrays.asList("dep", "res", "req", "res", "res", "res", "req", "res", "res", "res", "res", "req", "res", "req", "res", "res"));
	ArrayList<String> alAddFocus = new ArrayList<String>(Arrays.asList("MainStatus", "Min Player Count", "Max Player Count", "Max Player Count", "Full Sum", "Damage", "CurrentHP", "CurrentHP", "CurrentHP", "HPMax", "Partial Sum", "Partial Sum", "MinRange", "MaxRange", "MaxRange", "Width"));
	ArrayList<String> alAddLink = new ArrayList<String>(Arrays.asList("CurrentHP", "", "Min Player Count", "Min Player Count", "", "", "HPMax", "", "HPMax", "", "Full Sum", "Full Sum", "", "MinRange", "MinRange", ""));
	ArrayList<String> alAddComparison = new ArrayList<String>(Arrays.asList("", "ge", "", "ge", "ge", "ge", "", "gt", "le", "ge", "le", "", "ge", "", "ge", "ge"));
	ArrayList<Integer> alAddValue = new ArrayList<Integer>(Arrays.asList(null, 1, 1, null, 1, 0, 1, 0, null, 1, null, 1, 0, 0, null, 0));

	static ArrayList<String> alRequiredCompType;
	static ArrayList<String> alRequiredCompDesc;
	boolean focusGame;
	boolean GM;

	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.activity00_logonmaster);
		dbAdapter = new DBAdapter(this);
		dbTables = new DBTables(this);
		metadata = getSharedPreferences("metadata", 0);
		editor = metadata.edit();
		rlLogon = (RelativeLayout) findViewById(R.id.a00_rlLogon);
		rgGroup = (RadioGroup) findViewById(R.id.a00_rgMasterType);
		rlSearch = (RelativeLayout) findViewById(R.id.a00_rlSearch);
		tvSearch = (TextView) findViewById(R.id.a00_tvSearch);
		etSearch = (EditText) findViewById(R.id.a00_etSearch);
		llMain = (LinearLayout) findViewById(R.id.a00_llMain);
		llTop = (LinearLayout) findViewById(R.id.a00_llTop);
		sTop = (Spinner) findViewById(R.id.a00_sTop);
		llTopB = (LinearLayout) findViewById(R.id.a00_llTopB);
		bTopEdit = (Button) findViewById(R.id.a00_bTopEdit);
		bTopRemove = (Button) findViewById(R.id.a00_bTopRemove);
		llBot = (LinearLayout) findViewById(R.id.a00_llBot);
		sBot = (Spinner) findViewById(R.id.a00_sBot);
		llBotB = (LinearLayout) findViewById(R.id.a00_llBotB);
		bBotEdit = (Button) findViewById(R.id.a00_bBotEdit);
		bBotRemove = (Button) findViewById(R.id.a00_bBotRemove);
		bPlay = (Button) findViewById(R.id.a00_bPlay);
		alTop = new ArrayList<String>();
		alTop_idLOCAL = new ArrayList<Integer>();
		alTop_idLINK = new ArrayList<Integer>();
		alBot = new ArrayList<String>();
		alBot_idLOCAL = new ArrayList<Integer>();
		alBot_idLINK = new ArrayList<Integer>();
		aaTop = new ArrayAdapter(getBaseContext(), R.layout.my_list_items, alTop);
		sTop.setAdapter(aaTop);
		editor.clear();
		editor.commit();
		focusGame = true;

		Log.e("LogFlag", "LOCAL OPEN DB COUNT - " + metadata.getInt("OpenDB", 0));

		dbTables.open();

//	    initialize local required data: Game, Party, Character
		if (dbTables.getAllLocal("ALL").getCount() == 0) {
			dbTables.createLocal("System", "Game", "This component indicates the different game platforms that can be used.", -1);
			dbTables.createLocal("Component", "Metadata", "This component contains information that will be useful while getting a group together.", -1);
			dbTables.createLocal("Component", "Turn Type", "This will establish the order in which the turn is performed.", -1);
			dbTables.createLocal("Component", "Turn Focus", "This will establish if the turn is for the player or the character.", -1);
			dbTables.createLocal("Component", "Game Info", "This will give properties about the game.", -1);
			dbTables.createLocal("Component", "MapPreferences", "This will alter the map to make it specific to the current playthrough", -1);
			dbTables.createLocal("Component", "Map", "This component is used to load map profiles.", -1);
			dbTables.createLocal("Component", "MapType", "This attribute will determine which map style the game will use.", -1);
			dbTables.createLocal("Component", "Group", "This component indicates the ongoing games.", -1);
			dbTables.createLocal("Component", "Party", "Within each game this component will organize charaters into teams", -1);
			dbTables.createLocal("Component", "Character", "Within each game, this component will list ALL controllable characters and the id of the player using them.", -1);
			dbTables.createLocal("Component", "CharLocation", "This will be the x, y, and z coordinates that determines the characters location on the map.", -1);
			dbTables.createLocal("Component", "CharHealth", "This will be the number that determines the Character's Main Status", -1);
			dbTables.createLocal("Component", "CharProps", "These are the numeric properties that are associated with each player (even if they might be empty).", -1);
			dbTables.createLocal("Component", "CharDesc", "These are the text properties that are associated with each player (even if they might be empty).", -1);
			dbTables.createLocal("Component", "MainStatus", "This is the main disposition of a character.  It will be dependent on the HP score.", -1);
			dbTables.createLocal("Component", "CharTemplate", "These containers will represent the base property of a character.  It will be reusable.", -1);
			dbTables.createLocal("Component", "TemplateProps", "The properties that come with the template", -1);
			dbTables.createLocal("Component", "Actions", "Within each game, this component will list all the actions that a character can take.", -1);
			dbTables.createLocal("Component", "ActProps", "Properties of the actions", -1);
			dbTables.createLocal("Component", "AttrEffects", "This will contain the options of attributes that are effected by the action and how.", -1);
			dbTables.createLocal("Component", "AttrEffectsProps", "This will be the container to identify the attribute change.", -1);
			dbTables.createLocal("Component", "ActRangeOption", "This will allow multiple different ranges to be applied to a single action.", -1);
			dbTables.createLocal("Component", "PositionChange", "This will identify which status will change.", -1);
			dbTables.createLocal("Component", "ActRangeProps", "This describes the different traits required to describe the actions.", -1);
			dbTables.createLocal("Component", "ActShape", "This describes the type of shape that an action will operate within.", -1);
			dbTables.createLocal("Component", "ActDirect", "This will indicate the direction the action will face.", -1);
			dbTables.createLocal("Component", "Passable", "This describes if a collision will happen.", -1);
			dbTables.createLocal("Metadata", "theGame", "This element is used to capture all of the game information.", -1);
			dbTables.createLocal("Turn Type", "Ordered", "Standard turn order.  Players take predetermined turns to play the game.", -1);
			dbTables.createLocal("Turn Type", "Simutanious", "This turn system should be used with large parties.  All players go at the same time and respond to the last round.", -1);
			dbTables.createLocal("Turn Focus", "PlayerFocus", "This should be utilized when one turn allotment is summed for the player.", -1);
			dbTables.createLocal("Turn Focus", "CharFocus", "This should be utilized when one turn allotment is summed for each character.", -1);
			dbTables.createLocal("Game Info", "Min Player Count", "This is the min amount of characters in the game.", -1);
			dbTables.createLocal("Game Info", "Max Player Count", "This is the max amount of characters in the game.", -1);
			dbTables.createLocal("Game Info", "Full Sum", "This will restrict how many actions can take place during a turn.", -1);
			dbTables.createLocal("Map", "DefaultMap", "The standard map that will be used by default.", -1);
			dbTables.createLocal("MapType", "Hexagon", "This Map Type is to be used when universal movement calculations are favored over directional options", -1);
			dbTables.createLocal("MapType", "Square (simplified)", "This Map Type is used when diagonals lengths are calculated the same as straight ('circles' around a point will be a square)", -1);
			dbTables.createLocal("MapType", "Square (complex)", "This Map Type is used when directional options and it takes longer to move in a diagonal direction (alternates between 2 moves and 1)", -1);
			dbTables.createLocal("Party", "Archive", "This party will contain all characters templates stored for future use.", -1);
			dbTables.createLocal("CharDesc", "CharName", "Character Name.", -1);
			dbTables.createLocal("TemplateProps", "HPMax", "Maximum hit points.  This will determine the amount of hit points that a character starts with.", -1);
			dbTables.createLocal("CharProps", "xPosition", "this identifies the x coordinate for the character on the map.", -1);
			dbTables.createLocal("CharProps", "yPosition", "this identifies the y coordinate for the character on the map.", -1);
			dbTables.createLocal("CharProps", "zPosition", "this identifies the z (elevation) coordinate for the character on the map.", -1);
			dbTables.createLocal("CharProps", "HP Personal", "Temporary hit points.  The amount of hit points that a character has during the course of the game.  This determines the character status.", -1);
			dbTables.createLocal("CharTemplate", "Standard piece", "This will be the standard template for most pieces used in games.", -1);
			dbTables.createLocal("MainStatus", "Normal Status", "This is the normal status of a character.", -1);
			dbTables.createLocal("MainStatus", "Gone", "This is the final status of the character. If the current HPTemp is not caputrued under a different MainStatus element, then this status is achieved and the piece is removed.", -1);
			dbTables.createLocal("Action", "Basic Action", "This will be the basic action of the game", -1);
			dbTables.createLocal("AttrEffects", "No Attributes", "This will identify the empty set off attributes that are effected.", -1);
			dbTables.createLocal("AttrEffectsProps", "AttrHP", "This will identify the empty set off attributes that are effected.", -1);
			dbTables.createLocal("AttrEffectsProps", "AttrLocation", "this will identify if the location changes", -1);
			dbTables.createLocal("ActProps", "Partial Sum", "This will establish how much of the turn is taken up by this action.", -1);
			dbTables.createLocal("ActRangeProps", "MaxRange", "The max range of the effect.", -1);
			dbTables.createLocal("ActRangeProps", "MinRange", "The min range of the effect.", -1);
			dbTables.createLocal("ActRangeProps", "Width", "The span of the effect (cones will be measured in degrees, line will be distance).", -1);
			dbTables.createLocal("ActShape", "Line", "Can effect any direction", -1);
			dbTables.createLocal("ActShape", "Cone", "Will effect quarter of a circle.", -1);
			dbTables.createLocal("ActDirect", "Forward", "The action is always directed in the forward direction (which will no change even if the board is rotated). ", -1);
			dbTables.createLocal("ActDirect", "Targeted", "The player will chose the target direction for the action.", -1);
			dbTables.createLocal("PositionChange", "Will Move", "This will identify a change in the character's position.", -1);
			dbTables.createLocal("PositionChange", "Stay put", "This will identify there is no change in the character's position.", -1);
			dbTables.createLocal("Passable", "Stopped", "You Shall Not Pass!", -1);
			dbTables.createLocal("Passable", "Not Stopped", "Will avoid collision.", -1);
		}

		dbTables.close();

		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				dbTables.open();
				if (checkedId == R.id.a00_rbGM) {
					GM = true;
					rlSearch.setVisibility(View.VISIBLE);
					llMain.setVisibility(View.VISIBLE);
					gameMaster();
				} else {
					GM = false;
					Cursor curAll = dbTables.getAllLocal("ALL");
					curAll.moveToFirst();
					Log.e("LogFlag", "LOCAL TABLE:  _ID|   Category  |  Name   |    Description    |    Alias");
					for (int i = 0; i < curAll.getCount(); i++) {
						Log.e("LogFlag", curAll.getInt(0) + " | " + curAll.getString(1) + " | " + curAll.getString(2) + " | " + curAll.getString(3) + " | " + curAll.getInt(4) + " | ");
						curAll.moveToNext();
					}
					Log.e("LogFlag", "-----------------------------------------------------------------------");
					curAll = dbTables.getAllLink(0, 0, 0);
					curAll.moveToFirst();
					Log.e("LogFlag", "LINK TABLE: _ID|Game|majorVer|minorVer|parent|focus|arrange|   score   | editable | hidden | alias");
					for (int i = 0; i < curAll.getCount(); i++) {
						Log.e("LogFlag", curAll.getInt(0) + " | " + curAll.getInt(1) + " | " + curAll.getInt(2) + " | " + curAll.getInt(3) + " | " + curAll.getInt(4) + " | " + curAll.getInt(5) + " | " + curAll.getInt(6) + " | " + curAll.getString(7) + " | " + curAll.getInt(8) + " | " + curAll.getInt(9) + "  | " + curAll.getInt(10));
						curAll.moveToNext();
					}
					Log.e("LogFlag", "-----------------------------------------------------------------------");
					curAll = dbTables.getAllInherit(0);
					curAll.moveToFirst();
					Log.e("LogFlag", "INHER TABLE: ID   |  LINK    |   TYPE   |   ALLIAS");
					for (int i = 0; i < curAll.getCount(); i++) {
						Log.e("LogFlag", curAll.getInt(0) + " | " + curAll.getInt(1) + " | " + curAll.getInt(2) + " | " + curAll.getInt(3));
						curAll.moveToNext();
					}
					Log.e("LogFlag", "-----------------------------------------------------------------------");
					curAll = dbTables.getAllPrereq(0, 0, 0);
					curAll.moveToFirst();
					Log.e("LogFlag", "PREREQ TABLE: ID|  GAME|  FOCUS  |  PREREQ_ID  |  SCORE  |  ALIAS");
					for (int i = 0; i < curAll.getCount(); i++) {
						Log.e("LogFlag", curAll.getInt(0) + " | " + curAll.getInt(1) + " | " + curAll.getInt(2) + " | " + curAll.getInt(3) + " | " + curAll.getInt(4) + " | " + curAll.getInt(5));
						curAll.moveToNext();
					}
					Log.e("LogFlag", "-----------------------------------------------------------------------");
					curAll = dbTables.getAllRes(metadata.getInt("Game", 0), 0);
					curAll.moveToFirst();
					Log.e("LogFlag", "RES TABLE: ID   |  GAME_ID  |  LINK_ID   |  RESTRICTION   |  ALIAS");
					for (int i = 0; i < curAll.getCount(); i++) {
						Log.e("LogFlag", curAll.getInt(0) + " | " + curAll.getInt(1) + " | " + curAll.getInt(2) + " | " + curAll.getString(3) + " | " + curAll.getInt(4));
						curAll.moveToNext();
					}
					curAll.close();


					if (Build.VERSION.SDK_INT >= 23) {
						if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
							Log.e("LogFlag", "Permission is granted");
						} else {

							Log.e("LogFlag", "Permission is revoked");
							ActivityCompat.requestPermissions(Act00LogonMaster.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
						}
					} else { //permission is automatically granted on sdk<23 upon installation
						Log.e("LogFlag", "Permission is granted");
					}


					try {
						File sd = Environment.getExternalStorageDirectory();
						File data = Environment.getDataDirectory();

						if (sd.canWrite()) {
							Log.e("LogFlag", "/data/" + getPackageName() + "/databases/GAMEDATA");
							String currentDBPath = "/data/" + getPackageName() + "/databases/GAMEDATA";
							String backupDBPath = "GAMEDATA1.db";
							File currentDB = new File(data, currentDBPath);
							File backupDB = new File(sd, backupDBPath);

							if (currentDB.exists()) {
								FileChannel src = new FileInputStream(currentDB).getChannel();
								FileChannel dst = new FileOutputStream(backupDB).getChannel();
								dst.transferFrom(src, 0, src.size());
								src.close();
								dst.close();
								Log.e("LogFlag", "database copied to download");
							}
						}
					} catch (Exception e) {
						Log.e("LogFlag", "error during copy: " + e);
					}

					try {
						File sd = Environment.getExternalStorageDirectory();
//						File data = Environment.getDataDirectory();
						File data = null;

						if (sd.canWrite()) {
							Log.e("LogFlag", "/data/" + getPackageName() + "/databases/GAMEDATA");
							String currentDBPath = getDatabasePath("GAMEDATA").toString();
							String backupDBPath = "/download/GAMEDATA.db";
							File currentDB = new File(data, currentDBPath);
							File backupDB = new File(sd, backupDBPath);

							if (currentDB.exists()) {
								FileChannel src = new FileInputStream(currentDB).getChannel();
								FileChannel dst = new FileOutputStream(backupDB).getChannel();
								dst.transferFrom(src, 0, src.size());
								src.close();
								dst.close();
								Log.e("LogFlag", "database copied to download");
							}
						}
					} catch (Exception e) {
						Log.e("LogFlag", "error during copy: " + e);
					}
					player();
				}
				dbTables.close();
			}
		});

		etSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (GM) {
					gameMaster();
				} else {
					player();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	void gameMaster() {
		dbTables.open();
		Log.e("LogFlag", "Open DB test (should be 2).  Actual count - " + metadata.getInt("OpenDB", 0));
		tvSearch.setText("Game Search");
		int iTopLOCALTemp = 0;
		int iTopLINKTemp = 0;
		int iBotLOCALTemp = 0;
		int iBotLINKTemp = 0;
		if (sTop.getSelectedItemPosition() > 0) {
			iTopLOCALTemp = alTop_idLOCAL.get(sTop.getSelectedItemPosition());
			iTopLINKTemp = alTop_idLINK.get(sTop.getSelectedItemPosition());
		}
		if (sBot.getSelectedItemPosition() > 0) {
			iBotLOCALTemp = alBot_idLOCAL.get(sBot.getSelectedItemPosition());
			iBotLINKTemp = alBot_idLINK.get(sBot.getSelectedItemPosition());
		}
		final int iTopLOCAL = iTopLOCALTemp;
		final int iTopLINK = iTopLINKTemp;
		final int iBotLOCAL = iBotLOCALTemp;
		final int iBotLINK = iBotLINKTemp;
		alTop.clear();
		alTop_idLOCAL.clear();
		alTop_idLINK.clear();
		alBot.clear();
		alBot_idLOCAL.clear();
		alBot_idLINK.clear();
		bPlay.setVisibility(View.VISIBLE);
		bPlay.setText("Create Game");
		curTemp1 = dbTables.getRow(iTopLOCAL, "LOCAL");
		Log.e("LogFlag", "test text: " + curTemp1.getCount());
		if (focusGame || curTemp1.getCount() == 0) {
			alTop.addAll(dbToList(this, 0, "Game", 0, 0, 0, 0, 0, "ALL", 0, 0, 2));
			alTop_idLOCAL.addAll(dbToList(this, 0, "Game", 0, 0, 0, 0, 0, "ALL", 0, 0, 1));
			alTop_idLINK.addAll(dbToList(this, 0, "Game", 0, 0, 0, 0, 0, "ALL", 0, 0, 0));
			for (int i = 0; i < alTop.size(); i++) {
				int iPartyCount = 0;
				ArrayList<String> alTemp = dbToList(this, alTop_idLOCAL.get(i), "Group", 0, 0, 0, 0, 0, "ALL", 0, 0, 2);
				for (int j = 0; j < alTemp.size(); j++) {
					iPartyCount = iPartyCount + 1;
				}
				if (!etSearch.getText().toString().equals("") && !alTop.get(i).contains(etSearch.getText())) {
					alTop.remove(i);
					alTop_idLOCAL.remove(i);
					alTop_idLINK.remove(i);
					i = i - 1;
				} else {
					if (dbTables.getRow(alTop_idLINK.get(i), "LINKS").getString(7).equals("0")) {
						alTop.set(i, alTop.get(i) + " - under construction");
					} else {
						alTop.set(i, alTop.get(i) + " - " + iPartyCount + " active");
					}
				}
			}
			if (alTop.size() == 0) {
				llTop.setVisibility(View.GONE);
				if (etSearch.getText().toString().equals("")) {
					tvSearch.setVisibility(View.GONE);
					etSearch.setVisibility(View.GONE);
				} else {
					tvSearch.setVisibility(View.VISIBLE);
					etSearch.setVisibility(View.VISIBLE);
				}
			} else {
				llTop.setVisibility(View.VISIBLE);
				tvSearch.setVisibility(View.VISIBLE);
				etSearch.setVisibility(View.VISIBLE);
				alTop.add(0, "Choose a Game");
				alTop_idLOCAL.add(0, 0);
				alTop_idLINK.add(0, 0);
			}
		} else {
			alTop.add("Reset List");
			alTop.add(curTemp1.getString(2));
			alTop_idLOCAL.add(0, 0);
			alTop_idLINK.add(0, 0);
			alTop_idLOCAL.add(iTopLOCAL);
			alTop_idLINK.add(iTopLINK);
		}
		curTemp1.close();
		aaTop.notifyDataSetChanged();

		if (focusGame) {
			llTopB.setVisibility(View.GONE);
			llBot.setVisibility(View.GONE);
			sBot.setSelection(0);
			bPlay.setText("Create Game");
			bPlay.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.e("LogFlag", "game adjustment with game ID 0");
					startActivity(new Intent(getBaseContext(), Act10GameManager.class));
				}
			});
		} else {
			llTopB.setVisibility(View.VISIBLE);
			tvSearch.setText("Group Search");
			sTop.setSelection(1);
			bTopEdit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dbTables.open();
					moveInOrderTo(getBaseContext(), iTopLINK, 1);
					editor.putString("key", "Component");
					editor.putInt("Game", iTopLOCAL);
					editor.commit();
					Log.e("LogFlag", "game adjustment with game ID 2");
					dbTables.close();
					startActivity(new Intent(getBaseContext(), Act20MainGamePlay.class));
				}
			});
			bTopRemove.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					final Object[] view = verify(getBaseContext(), rlLogon);
					((Button) view[1]).setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							dbTables.open();
							((PopupWindow) view[0]).dismiss();
							removeFromGame(getBaseContext(), iTopLINK);
							focusGame = true;
							dbTables.close();
							gameMaster();
						}
					});
				}
			});
			if (dbTables.getRow(iTopLINK, "LINKS").getString(7).equals("0")) {
				bPlay.setText("Work on Game");
				llBot.setVisibility(View.GONE);
				bPlay.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dbTables = new DBTables(getBaseContext());
						dbTables.open();
						moveInOrderTo(getBaseContext(), iTopLINK, 1);
						editor.putString("key", "Component");
						editor.putInt("Game", iTopLOCAL);
						editor.commit();
						Log.e("LogFlag", "game adjustment with game ID 0.5");
						dbTables.close();
						startActivity(new Intent(getBaseContext(), Act10GameManager.class));
					}
				});
			} else {
				bPlay.setText("Create Group");
				alBot = dbToList(this, alTop_idLOCAL.get(sTop.getSelectedItemPosition()), "Group", 0, 0, 0, 0, 0, "ALL", 0, 0, 2);
				alBot_idLOCAL = dbToList(this, alTop_idLOCAL.get(sTop.getSelectedItemPosition()), "Group", 0, 0, 0, 0, 0, "ALL", 0, 0, 1);
				alBot_idLINK = dbToList(this, alTop_idLOCAL.get(sTop.getSelectedItemPosition()), "Group", 0, 0, 0, 0, 0, "ALL", 0, 0, 0);
				for (int i = 0; i < alBot.size(); i++) {
					if (!alBot.get(i).contains(etSearch.getText())) {
						alBot.remove(i);
						alBot_idLOCAL.remove(i);
						alBot_idLINK.remove(i);
						i = i - 1;
					}
				}
				if (alBot.size() == 0) {
					llBot.setVisibility(View.GONE);
				} else {
					llBot.setVisibility(View.VISIBLE);
					alBot.add(0, "Choose a Group");
					alBot_idLOCAL.add(0, 0);
					alBot_idLINK.add(0, 0);
					aaBot = new ArrayAdapter(getBaseContext(), R.layout.my_list_items, alBot);
					sBot.setAdapter(aaBot);
				}
// these seemed obsolete... may have to revisit.
//				if (alBot_idLOCAL.contains(iBotLOCAL)){
//					sBot.setSelection(alBot_idLOCAL.indexOf(iBotLOCAL));
//					bPlay.setText("Play>>>");
//				} else {
//					sBot.setSelection(0);
//				}
				bPlay.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dbTables = new DBTables(getBaseContext());
						dbTables.open();
						moveInOrderTo(getBaseContext(), alTop_idLINK.get(1), 1);
						editor.putInt("Game", iTopLOCAL);
						editor.commit();
						dbTables.close();
						startActivity(new Intent(getBaseContext(), Act11GroupManager.class));
					}
				});
				sBot.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
											   final int pos1, long arg3) {
						dbTables = new DBTables(getBaseContext());
						dbTables.open();
						if (pos1 == 0) {
							llBotB.setVisibility(View.GONE);
							bPlay.setText("Create Party");
							bPlay.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									moveInOrderTo(getBaseContext(), iTopLINK, 1);
									editor.putInt("Game", iTopLINK);
									editor.commit();
									startActivity(new Intent(getBaseContext(), Act11GroupManager.class));
								}
							});
						} else {
							llBotB.setVisibility(View.VISIBLE);
							bPlay.setText("Play>>>");
							bBotEdit.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									moveInOrderTo(getBaseContext(), alBot_idLINK.get(pos1), 1);
									moveInOrderTo(getBaseContext(), iTopLINK, 1);
									int compId = 0;
									Cursor curComp = dbTables.getAllLocal("Component");
									while (!curComp.isAfterLast()) {
										if (curComp.getString(2).equals("Group")) {
											compId = curComp.getInt(0);
											curComp.moveToLast();
										}
										curComp.moveToNext();
									}
									editor.putString("key", "Group");
									editor.putInt("Game", iTopLINK);
									editor.putInt("Component", compId);
									editor.putInt("Group", alBot_idLINK.get(pos1));
									editor.commit();
									Log.e("LogFlag", "game adjustment with game ID 3");
									startActivity(new Intent(getBaseContext(), Act10GameManager.class));
								}
							});
							bBotRemove.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View arg0) {
									final Object[] view = verify(getBaseContext(), rlLogon);
									((Button) view[1]).setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View arg0) {
											((PopupWindow) view[0]).dismiss();
											removeFromGame(getBaseContext(), alBot_idLINK.get(1));
											gameMaster();
										}
									});
								}
							});
							bPlay.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									moveInOrderTo(getBaseContext(), alBot_idLINK.get(pos1), 1);
									moveInOrderTo(getBaseContext(), iTopLINK, 1);
									editor.putString("User", "GM");
									editor.putInt("Party", alBot_idLOCAL.get(pos1));
									editor.commit();
									startActivity(new Intent(getBaseContext(), Act20MainGamePlay.class));
								}
							});
						}
						dbTables.close();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
			}
		}
//    	bPlay.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Log.e("LogFlag", "game adjustment with game ID 4");
//				startActivity(new Intent(getBaseContext(), Act10GameManager.class));
//			}
//		});
		sTop.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   final int pos, long arg3) {
				boolean tempFocus = focusGame;
				if (pos == 0) {
					focusGame = true;
				} else {
					focusGame = false;
				}
				etSearch.setText("");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		dbTables.close();
	}

	void player() {
		tvSearch.setText("character search:");
		dbTables.open();
		int iTopLOCAL = 0;
		int iTopLINK = 0;
		if (sTop.getSelectedItemPosition() > 0) {
			iTopLOCAL = alTop_idLOCAL.get(sTop.getSelectedItemPosition());
			iTopLINK = alTop_idLINK.get(sTop.getSelectedItemPosition());
		}
		alTop.clear();
		alTop_idLOCAL.clear();
		alTop_idLINK.clear();
		alBot.clear();
		alBot_idLOCAL.clear();
		alBot_idLINK.clear();
		bPlay.setVisibility(View.VISIBLE);
		bPlay.setText("Create Character");
		alTop.addAll(dbToList(this, 0, "Character", 0, 0, 0, 0, 0, "ALL", 0, 0, 2));
		alTop_idLOCAL.addAll(dbToList(this, 0, "Character", 0, 0, 0, 0, 0, "ALL", 0, 0, 1));
		alTop_idLINK.addAll(dbToList(this, 0, "Character", 0, 0, 0, 0, 0, "ALL", 0, 0, 0));
		for (int i = 0; i < alTop.size(); i++) {
			if (alTop.get(i).contains(etSearch.getText())) {
				// --------Add notification indicater and bring those to the top of the list here
				if (alTop_idLOCAL.get(i) != null) {
					alTop.set(i, alTop.get(i) + " (group: " + dbTables.getRow(alTop_idLOCAL.get(i), "LOCAL").getString(2) + ")");
				}
			}
		}
		if (!etSearch.getText().toString().equals("")) {
			for (int i = 0; i < alTop.size(); i++) {
				if (!alTop.get(i).contains(etSearch.getText())) {
					alTop.remove(i);
					alTop_idLOCAL.remove(i);
					alTop_idLINK.remove(i);
				}
			}
		}
		if (alTop.size() > 0) {
			alTop.add(0, "Choose a Character");
			alTop_idLOCAL.add(0, 0);
			alTop_idLINK.add(0, 0);
			llTop.setVisibility(View.VISIBLE);
			etSearch.setVisibility(View.VISIBLE);
			tvSearch.setVisibility(View.VISIBLE);
		} else {
			llTop.setVisibility(View.GONE);
			etSearch.setVisibility(View.GONE);
			tvSearch.setVisibility(View.GONE);
		}
		aaTop.notifyDataSetChanged();
		if (alTop_idLOCAL.contains(iTopLOCAL)) {
			sTop.setSelection(alTop_idLOCAL.indexOf(iTopLOCAL));
		}
		bPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (bPlay.getText().equals("Create Character")) {
					getBaseContext().deleteDatabase("GAMEDATA");
					finish();
//					Log.e("LogFlag", "game adjustment with game ID 5");
//					startActivity(new Intent(getBaseContext(), Act12CharacterManager.class));
				}
			}
		});
		bTopRemove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Object[] view = verify(getBaseContext(), rlLogon);
				((Button) view[1]).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						((PopupWindow) view[0]).dismiss();
						removeFromGame(getBaseContext(), alTop_idLINK.get(sTop.getSelectedItemPosition()));
						player();
					}
				});
			}
		});
		bTopEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editor.putInt("Character", 0);
				editor.commit();
				startActivity(new Intent(getBaseContext(), Act12CharacterManager.class));
			}
		});
		sTop.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   final int pos, long arg3) {
				if (pos == 0) {
					bPlay.setText("Create Character");
					bPlay.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Log.e("LogFlag", "game adjustment with game ID 6");
							startActivity(new Intent(getBaseContext(), Act10GameManager.class));
						}
					});
					llTopB.setVisibility(View.VISIBLE);
				} else {
					bPlay.setText("Play>>>");
					bPlay.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							editor.putInt("Game", alTop_idLINK.get(pos));
							editor.commit();
							startActivity(new Intent(getBaseContext(), Act20MainGamePlay.class));
						}
					});
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		dbTables.close();
	}

	public static boolean centerToast(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		return true;
	}

	public static boolean close(Cursor cur) {
		try {
			cur.close();
		} catch (Exception e) {
			Log.e("LogFlag", "cursor didn't close due to " + e.toString());
		}
		return true;
	}

	public static ArrayList dbToList(Context context, int game, String cat, int parent, int focus, int maj, int min, int arrange, String score, int edit, int hidden, int colNum) {
		DBAdapter dbAdapterA = new DBAdapter(context);
		dbAdapterA.open();
		ArrayList tempArray = new ArrayList();
		ArrayList<Integer> alWhere = new ArrayList<Integer>(Arrays.asList(focus, parent, game, maj, min, arrange, edit, hidden));
		ArrayList<String> alWhere_name = new ArrayList<String>(Arrays.asList("FOCUS=", "PARENT=", "GAME_ID=", "MAJ_VER>=", "MIN_VER>=", "ARRANGE=", "EDIT=", "HIDDEN="));
		String strWhere = "";
		if (!cat.equals("ALL")) {
			strWhere = "CATEGORY LIKE \"" + cat + "\" AND ";
		}
		for (int i = 0; i < alWhere.size(); i++) {
			if (alWhere.get(i) != 0) {
				strWhere = strWhere + alWhere_name.get(i) + alWhere.get(i) + " AND ";
			}
		}
		if (!score.equals("ALL")) {
			strWhere = strWhere + "SCORE LIKE \"" + score + "\" AND ";
		}
		strWhere = strWhere + "a._ID is not null";
		// for each item referenced the query will return the following columns:
		//   link id, focus, name, description, parent, game, major version, game minor version, arrange, score
		Log.e("Flag", "SELECT b._ID, a._ID, a.NAME, a.DESCRIPTION, b.PARENT, b.GAME_ID, b.GAME_MAJ, b.GAME_MIN, b.ARRANGE, b.VALUE, b.GAME_TRAIT, b.VISIBLE, b.VISIBLE " +
				"FROM LOCAL AS a LEFT JOIN LINKS AS b ON a._ID = b.FOCUS " +
				"WHERE " + strWhere + " ORDER BY b.ARRANGE, a.NAME;");
		Cursor tempCur = dbAdapterA.db.rawQuery("SELECT b._ID, a._ID, a.NAME, a.DESCRIPTION, b.PARENT, b.GAME_ID, b.GAME_MAJ, b.GAME_MIN, b.ARRANGE, b.VALUE, b.GAME_TRAIT, b.VISIBLE, b.VISIBLE " +
				"FROM LOCAL AS a LEFT JOIN LINKS AS b ON a._ID = b.FOCUS " +
				"WHERE " + strWhere + " ORDER BY b.ARRANGE, a.NAME;", null);
		if (tempCur != null) {
			tempCur.moveToFirst();
		}
		while (!tempCur.isAfterLast()) {
			if (tempCur.getString(9) != null) {
				if (tempCur.getString(9).equals(score) || score == "ALL") {
					if (Arrays.asList(0, 1, 4, 5, 6, 7, 8, 10, 11).contains(colNum)) {
						tempArray.add(tempCur.getInt(colNum));
					} else {
						tempArray.add(tempCur.getString(colNum));
					}
				}
			}
			tempCur.moveToNext();
		}
		tempCur.close();
		return tempArray;
	}

	public static boolean moveInOrderTo(Context context, int linkId, int position) {
		DBTables dbTables = new DBTables(context);
		boolean booResult = false;
		Cursor curTempA = dbTables.getRow(linkId, "LINKS");
		int iOldPos = curTempA.getInt(6);
		int focus = curTempA.getInt(5);
		Cursor curTempB = dbTables.getRow(focus, "LOCAL");
		String sCategory = curTempB.getString(1);
		int gameId = 0;
		if (!sCategory.equals("Game")) {
			gameId = curTempA.getInt(1);
		}
		boolean bContinue = true;
		int iPositionEnd = position;
		while (bContinue) {
			if (dbToList(context, gameId, sCategory, 0, 0, 0, 0, iPositionEnd, "ALL", 0, 0, 0).size() > 0 && iPositionEnd != iOldPos) {
				iPositionEnd = iPositionEnd - (iPositionEnd - iOldPos) / Math.abs(iPositionEnd - iOldPos);
			} else {
				bContinue = false;
			}
		}
		while (iPositionEnd != position) {
			int iTempOldPos = iPositionEnd + (position - iOldPos) / Math.abs(position - iOldPos);
			Log.e("LogFlag", "Position of current element = " + iTempOldPos);
			int iTempId = (Integer) dbToList(context, gameId, sCategory, 0, 0, 0, 0, iTempOldPos, "ALL", 0, 0, 0).get(0);
			Cursor curTempC = dbTables.getRow(iTempId, "LINKS");
			dbTables.updateLink(curTempC.getInt(0), curTempC.getInt(2), curTempC.getInt(3), curTempC.getInt(4), curTempC.getInt(5), iPositionEnd, curTempC.getString(7), curTempC.getInt(8), curTempC.getInt(9));
			curTempC.close();
			iPositionEnd = iTempOldPos;
		}
		dbTables.updateLink(curTempA.getInt(0), curTempA.getInt(2), curTempA.getInt(3), curTempA.getInt(4), curTempA.getInt(5), iPositionEnd, curTempA.getString(7), curTempA.getInt(8), curTempA.getInt(9));
		curTempA.close();
		return booResult;
	}

	public static Object[] verify(Context context, View locationView) {
		final boolean[] flag = {false};
		final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View verifyview = inflater.inflate(R.layout.verify, null, false);
		final Button bNo = (Button) verifyview.findViewById(R.id.ver_bNo);
		final PopupWindow pv = new PopupWindow(verifyview, 1200, 760, true);
		pv.showAtLocation(locationView, Gravity.CENTER, 0, 20);

		bNo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				pv.dismiss();
			}
		});
		return new Object[]{pv, verifyview.findViewById(R.id.ver_bYes)};
	}

	public static void removeFromGame(Context context, final int linkId) {
		DBTables dbTables = new DBTables(context);

		Cursor curTempA = dbTables.getRow(linkId, "LINKS");
		int itemId = curTempA.getInt(5);
		int gameId = curTempA.getInt(1);
		ArrayList<Integer> alAll = new ArrayList<Integer>();
		curTempA = dbTables.getRow(itemId, "LOCAL");
		if (curTempA.getString(1).equals("Game")) {
			Cursor curAll = dbTables.getAllLink(itemId, 0, 0);
			while (!curAll.isAfterLast()) {
				int focus = curAll.getInt(5);
				Log.e("LogFlag", "linkId = " + curAll.getInt(0));
				int tempLinkId = curAll.getInt(0);
				dbTables.deleteRow(curAll.getInt(0), "LINKS");
				Cursor curTempB = dbTables.getAllLink(0, focus, 0);
				Cursor curTempC = dbTables.getAllLink(0, 0, focus);
				Cursor curTempD = dbTables.getRow(focus, "LOCAL");
				if (curTempB.getCount() + curTempC.getCount() == 0 && !alRequiredFocus.contains(curTempD.getString(2))) {
					dbTables.deleteRow(focus, "LOCAL");
				}
				curTempB = dbTables.getAllInherit(tempLinkId);
				if (curTempB.getCount() > 0) {
					dbTables.deleteRow(curTempB.getInt(0), "INHER");
				}
				curTempB.close();
				curTempC.close();
				curTempD.close();
				curAll.moveToNext();
			}
			dbTables.deleteRow(itemId, "LOCAL");
			Cursor curTempB = dbTables.getAllPrereq(0, 0, linkId);
			while (!curTempB.isAfterLast()) {
				dbTables.deleteRow(curTempB.getInt(0), "PREREQS");
				curTempB.moveToNext();
			}
			curTempB.close();
			curAll.close();
		} else if (curTempA.getString(1).equals("Component") && !alRequiredComponents.contains(curTempA.getString(2))) {
			Cursor curTempB = dbTables.getRow(linkId, "LINKS");
			Cursor curTempC = dbTables.getAllLink(curTempB.getInt(1), itemId, 0);
			if (curTempC.getCount() == 0) {
				alAll = dbToList(context, gameId, curTempA.getString(2), 0, 0, 0, 0, 0, "ALL", 0, 0, 0);
				Cursor curTempD = dbTables.getAllLink(0, 0, itemId);
				Cursor curTempE = dbTables.getAllLink(gameId, 0, itemId);
				if (curTempD.getCount() - curTempE.getCount() == 0) {
					dbTables.deleteRow(itemId, "LOCAL");
				}
				dbTables.deleteRow(linkId, "LINKS");
				curTempD = dbTables.getAllInherit(linkId);
				if (curTempD.getCount() > 0) {
					dbTables.deleteRow(curTempD.getInt(0), "INHER");
				}
				curTempD.close();
				curTempE.close();
			} else {
				centerToast(context, "remove children components");
			}
			curTempB.close();
			curTempC.close();
		} else if (!curTempA.getString(1).equals("Component") && !alRequiredComponents.contains(curTempA.getString(2))) {
			alAll.add(linkId);
		} else {
			centerToast(context, "this item is needed for the system to run.");
		}
		for (int i = 0; i < alAll.size(); i++) {
			curTempA = dbTables.getRow(alAll.get(i), "LINKS");
			int base = curTempA.getInt(5);
			int par = base;
			int focus = 0;
			ArrayList<Integer> alIdList = new ArrayList<Integer>();
			alIdList.add(curTempA.getInt(0));
			while (focus != base) {
				curTempA = dbTables.getAllLink(gameId, par, 0);
				if (curTempA.getCount() > 0) {
					alIdList.add(curTempA.getInt(0));
					par = curTempA.getInt(5);
				} else {
					Cursor curTempB = dbTables.getRow(alIdList.get(alIdList.size() - 1), "LINKS");
					par = curTempB.getInt(4);
					focus = curTempB.getInt(5);
					curTempB = dbTables.getAllInherit(alIdList.get(alIdList.size() - 1));
					if (curTempB.getCount() > 0) {
						dbTables.deleteRow(curTempB.getInt(0), "INHER");
					}
					curTempB = dbTables.getAllPrereq(gameId, focus, 0);
					while (!curTempB.isAfterLast()) {
						dbTables.deleteRow(curTempB.getInt(0), "PREREQS");
						curTempB.moveToNext();
					}
					curTempB = dbTables.getAllPrereq(gameId, 0, focus);
					while (!curTempB.isAfterLast()) {
						dbTables.deleteRow(curTempB.getInt(0), "PREREQS");
						curTempB.moveToNext();
					}
					dbTables.deleteRow(alIdList.get(alIdList.size() - 1), "LINKS");
					curTempB = dbTables.getAllLink(0, focus, 0);
					Cursor curTempC = dbTables.getAllLink(0, 0, focus);
					Cursor curTempD = dbTables.getRow(focus, "LOCAL");
					if ((curTempB.getCount() + curTempC.getCount()) == 0 && !alRequiredComponents.contains(curTempD.getString(2))) {
						dbTables.deleteRow(focus, "LOCAL");
					}
					curTempB.close();
					curTempC.close();
					curTempD.close();
					alIdList.remove(alIdList.size() - 1);
				}
				curTempA.close();
			}
			dbTables.deleteRow(alAll.get(i), "LINKS");
		}
		curTempA.close();
	}
}