package happyface.rpgtraveler.com;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;

public class Act11GroupManager extends Activity{
	SharedPreferences metadata;
	SharedPreferences.Editor editor;
	DBAdapter dbAdapter = new DBAdapter(this);
	DBTables dbTables = new DBTables(this);

	TextView tvTitle;
	EditText etGroup;
	Button bUpdate;
	TextView tvList;
	ListView lvStored;
	Button bBack;
	Button bNext;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity11_groupmanager);

		metadata = getSharedPreferences("metadata", 0);
		editor = metadata.edit();

		tvTitle = (TextView)findViewById(R.id.a11_tvTitle);
		etGroup = (EditText)findViewById(R.id.a11_etGroup);
		bUpdate = (Button)findViewById(R.id.a11_bUpdate);
		tvList = (TextView)findViewById(R.id.a11_tvList);
		lvStored = (ListView)findViewById(R.id.a11_lvStored);
		bBack = (Button)findViewById(R.id.a11_bBack);
		bNext = (Button)findViewById(R.id.a11_bNext);

		ArrayList<String> alList = null;
		ArrayAdapter aaList = new ArrayAdapter(this,R.layout.my_list_items,alList);

//		1 pick group
//		2 pick party
//		3 start game

//		Pick group - verify that groups exist
		dbTables.open();
		tvTitle.setText(dbTables.getRow(metadata.getInt("Game", 0), "LOCAL").getString(2) + " - Group Setup");
		if(Act00LogonMaster.dbToList(this, metadata.getInt("Game", 0), "Group", 0, 0, 0, 0, 0, "ALL", 0, 1, 0).size()==0){
			//been created.
		} else {
			//no groups yet established
			alList.clear();
			alList.add(0, "No Group Yet Created");
			aaList.notifyDataSetChanged();
		}
		dbTables.close();
		alList.clear();
	}
}
