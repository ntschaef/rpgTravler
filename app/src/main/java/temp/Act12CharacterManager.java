package happyface.rpgtraveler.com;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class Act12CharacterManager extends Activity {
	protected static int RRnum = 0;
    protected static int attSet = 1;
    protected static ArrayList <String> AttOrder = new ArrayList<String>();
	protected static ArrayList <Integer> rawScores = new ArrayList<Integer> ();
	protected static ArrayList <Integer> orderScores = new ArrayList<Integer> ();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity12_charmanager);
	    Intent oldintent = getIntent();
	    final DBTables dbTables = new DBTables(this);
	    final Intent newintent = new Intent(this,Act00LogonMaster.class);
	    final int i = oldintent.getIntExtra("charNum", 0);
//	    final Button bRR = (Button) findViewById(R.id.CC_bRR);
//	    final Button bNext = (Button) findViewById(R.id.CC_bNext);
//	    final EditText name = (EditText) findViewById(R.id.CC_name);
//	    final EditText align = (EditText) findViewById(R.id.CC_alignment);
//	    final EditText deity = (EditText) findViewById(R.id.CC_deity);
//	    final EditText gender = (EditText) findViewById(R.id.CC_gender);
//	    final EditText race = (EditText) findViewById(R.id.CC_race);
//	    final EditText height = (EditText) findViewById(R.id.CC_height);
//	    final EditText weight = (EditText) findViewById(R.id.CC_weight);
//	    final TextView STR = (TextView) findViewById(R.id.CC_STR);
//	    final TextView DEX = (TextView) findViewById(R.id.CC_DEX);
//	    final TextView CON = (TextView) findViewById(R.id.CC_CON);
//	    final TextView INT = (TextView) findViewById(R.id.CC_INT);
//	    final TextView WIS = (TextView) findViewById(R.id.CC_WIS);
//	    final TextView CHA = (TextView) findViewById(R.id.CC_CHA);
//	    final EditText description = (EditText) findViewById(R.id.CC_description);
	    int RRtemp = 3;
	    
	    if (i > 0){
//	    	PlayDB.open();
//	    	Cursor cur = PlayDB.getAllChars();
//	    	cur.moveToPosition(i);
//	    	name.setText(cur.getString(1));
//	    	align.setText(cur.getString(2));
//	    	deity.setText(cur.getString(3));
//	    	gender.setText(cur.getString(4));
//	    	race.setText(cur.getString(5));
//	    	height.setText(cur.getString(6));
//	    	weight.setText(cur.getString(7));
//	    	STR.setText(cur.getString(8));
//	    	DEX.setText(cur.getString(9));
//	    	CON.setText(cur.getString(10));
//	    	INT.setText(cur.getString(11));
//	    	WIS.setText(cur.getString(12));
//	    	CHA.setText(cur.getString(13));
//	    	description.setText(cur.getString(14));
//	    	PlayDB.close();
//	    	bRR.setVisibility(View.GONE);
//	    	RRtemp=0;
//	    }
//	    
//	    RRnum = RRtemp;
//	    OnClickListener bListen = new OnClickListener(){
//			@Override
//			public void onClick(View view) {
//				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//				imm.hideSoftInputFromWindow(CharacterCreation.this.getCurrentFocus().getWindowToken(), 0);
//				int test = 0;
//				if (name.getText().equals("")){
//					test = 1;
//					Toast.makeText(CharacterCreation.this, "not clear", Toast.LENGTH_SHORT).show();
//				}
//				int RR = RRnum;
//				if (view == bRR && RRnum == 3){
//				    LayoutInflater inflater = (LayoutInflater) CharacterCreation.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//					View inflateview = inflater.inflate(R.layout.pw_cc, null, false);
//					final PopupWindow pw = new PopupWindow(inflateview, 600, 500, true);
//					pw.showAtLocation(view, Gravity.CENTER, 0, 0);
//					final Button pwbbstr = (Button) inflateview.findViewById(R.id.pwccbutstr);
//				    final Button pwbbdex = (Button) inflateview.findViewById(R.id.pwccbutdex);
//				    final Button pwbbcon = (Button) inflateview.findViewById(R.id.pwccbutcon);
//				    final Button pwbbint = (Button) inflateview.findViewById(R.id.pwccbutint);
//				    final Button pwbbwis = (Button) inflateview.findViewById(R.id.pwccbutwis);
//				    final Button pwbbcha = (Button) inflateview.findViewById(R.id.pwccbutcha);
//					final String ButString1 = ((Button) pwbbstr).getText().toString();
//					AttOrder.clear();
//					attSet = 1;
//				    OnClickListener popBListen = new OnClickListener(){
//						@Override
//						public void onClick(View button) {
//							if (((Button) button).getText() == ButString1){
//								AttOrder.add(button.getTag().toString());
//								((Button) button).setText("Priority: " + attSet);
//								attSet = attSet + 1;
//								if (attSet == 7){
//									pw.dismiss();
//									for (int i = 0; i < AttOrder.size(); i++){
//										if(AttOrder.get(i).equals("STR")){
//											STR.setText(orderScores.get(i).toString());
//										}
//										if(AttOrder.get(i).equals("DEX")){
//											DEX.setText(orderScores.get(i).toString());
//										}
//										if(AttOrder.get(i).equals("CON")){
//											CON.setText(orderScores.get(i).toString());
//										}
//										if(AttOrder.get(i).equals("INT")){
//											INT.setText(orderScores.get(i).toString());
//										}
//										if(AttOrder.get(i).equals("WIS")){
//											WIS.setText(orderScores.get(i).toString());
//										}
//										if(AttOrder.get(i).equals("CHA")){
//											CHA.setText(orderScores.get(i).toString());
//										}
//									}
//								}
//							} else {
//								Toast.makeText(CharacterCreation.this, "You've already set this one.  If you regret your choice, please remake the character.", Toast.LENGTH_LONG).show();
//							}
//						}
//				    };
//					pwbbstr.setOnClickListener(popBListen);
//					pwbbdex.setOnClickListener(popBListen);
//					pwbbcon.setOnClickListener(popBListen);
//					pwbbint.setOnClickListener(popBListen);
//					pwbbwis.setOnClickListener(popBListen);
//					pwbbcha.setOnClickListener(popBListen);
//				}
//				if (view == bRR){
//					RR = RRnum - 1;
//					bRR.setText(RR + " attempts left");	
//					rawScores.clear();
//					for (int i = 0; i < 6; i++){
//						ArrayList <Integer> rawDice = new Dice().roller(CharacterCreation.this, 6, 5);
//						ArrayList <Integer> orderDice = Order(rawDice);
//						rawScores.add(orderDice.get(0) + orderDice.get(1) + orderDice.get(2));
//						Toast.makeText(CharacterCreation.this, Calendar.getInstance().get(Calendar.getInstance().MILLISECOND)+"",Toast.LENGTH_LONG);
//					}
//					orderScores = Order(rawScores);
//				}
//				if (view == bRR && RRnum < 3) {
//					LayoutInflater inflater = (LayoutInflater) CharacterCreation.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//					View inflateview = inflater.inflate(R.layout.pw_check, null, false);
//					TextView checkSTR = (TextView) inflateview.findViewById(R.id.checkSTR);
//					TextView checkDEX = (TextView) inflateview.findViewById(R.id.checkDEX); 
//					TextView checkCON = (TextView) inflateview.findViewById(R.id.checkCON);
//					TextView checkINT = (TextView) inflateview.findViewById(R.id.checkINT);
//					TextView checkWIS = (TextView) inflateview.findViewById(R.id.checkWIS);
//					TextView checkCHA = (TextView) inflateview.findViewById(R.id.checkCHA);
//					final Button checkYes = (Button) inflateview.findViewById(R.id.checkButYes);
//					final Button checkNo = (Button) inflateview.findViewById(R.id.checkButNo);
//					for (int i = 0; i < AttOrder.size(); i++){
//						if(AttOrder.get(i).equals("STR")){
//							checkSTR.setText("STR: " + STR.getText() + "  ->  " + orderScores.get(i).toString());
//						}
//						if(AttOrder.get(i).equals("DEX")){
//							checkDEX.setText("DEX: " + DEX.getText() + "  ->  " + orderScores.get(i).toString());
//						}
//						if(AttOrder.get(i).equals("CON")){
//							checkCON.setText("CON: " + CON.getText() + "  ->  " + orderScores.get(i).toString());
//						}
//						if(AttOrder.get(i).equals("INT")){
//							checkINT.setText("INT: " + INT.getText() + "  ->  " + orderScores.get(i).toString());
//						}
//						if(AttOrder.get(i).equals("WIS")){
//							checkWIS.setText("WIS: " + WIS.getText() + "  ->  " + orderScores.get(i).toString());
//						}
//						if(AttOrder.get(i).equals("CHA")){
//							checkCHA.setText("CHA: " + CHA.getText() + "  ->  " + orderScores.get(i).toString());
//						}
//					}
//					final PopupWindow pw = new PopupWindow(inflateview, 450, 700, true);
//					pw.showAtLocation(view, Gravity.CENTER, 0, 0);
//					OnClickListener checkClick = new OnClickListener(){
//						@Override
//						public void onClick(View view) {
//							pw.dismiss();
//							if (view == checkYes){
//								for (int i = 0; i < AttOrder.size(); i++){
//									if(AttOrder.get(i).equals("STR")){
//										STR.setText(orderScores.get(i).toString());
//									}
//									if(AttOrder.get(i).equals("DEX")){
//										DEX.setText(orderScores.get(i).toString());
//									}
//									if(AttOrder.get(i).equals("CON")){
//										CON.setText(orderScores.get(i).toString());
//									}
//									if(AttOrder.get(i).equals("INT")){
//										INT.setText(orderScores.get(i).toString());
//									}
//									if(AttOrder.get(i).equals("WIS")){
//										WIS.setText(orderScores.get(i).toString());
//									}
//									if(AttOrder.get(i).equals("CHA")){
//										CHA.setText(orderScores.get(i).toString());
//									}
//								}
//							}
//						}
//					};
//					checkYes.setOnClickListener(checkClick);
//					checkNo.setOnClickListener(checkClick);
//				}
//				if (view == bRR && RRnum == 1){
//					RR = RRnum - 1;
//					bRR.setVisibility(View.GONE);
//				} 
//				PlayDB.open();
//				Cursor cur1 = PlayDB.getAllChars();
//				cur1.moveToFirst();
//				while (cur1.isAfterLast() == false){
//					if (cur1.getString(1).equals(name.getText().toString())){
//						test = 1;
//						cur1.moveToLast();
//					}
//					cur1.moveToNext();
//				}
//				if (view == bNext && RRnum == 0 && test == 0){
//					if(i < 0){
//						PlayDB.createChar(name.getText().toString(), align.getText().toString(), deity.getText().toString(), 
//							gender.getText().toString(), race.getText().toString(), height.getText().toString(), weight.getText().toString(), 
//							Integer.parseInt(STR.getText().toString()), Integer.parseInt(DEX.getText().toString()), 
//							Integer.parseInt(CON.getText().toString()), Integer.parseInt(INT.getText().toString()), 
//							Integer.parseInt(WIS.getText().toString()), Integer.parseInt(CHA.getText().toString()), 
//							description.getText().toString(), 0, 0);
//					} else {
//						Cursor cur = PlayDB.getAllChars();
//						cur.moveToPosition(i);
//						int id = Integer.parseInt(cur.getString(0));
//						int xp = Integer.parseInt(cur.getString(15));
//						int fame = Integer.parseInt(cur.getString(16));
//						PlayDB.updateChar(id,name.getText().toString(), align.getText().toString(), deity.getText().toString(), 
//								gender.getText().toString(), race.getText().toString(), height.getText().toString(), weight.getText().toString(), 
//								Integer.parseInt(STR.getText().toString()), Integer.parseInt(DEX.getText().toString()), 
//								Integer.parseInt(CON.getText().toString()), Integer.parseInt(INT.getText().toString()), 
//								Integer.parseInt(WIS.getText().toString()), Integer.parseInt(CHA.getText().toString()), 
//								description.getText().toString(), xp, fame);
//					}
//					finish();
//				}
//				PlayDB.close();
//				if (view == bNext && RRnum > 0) {
//					Toast.makeText(CharacterCreation.this, "Please use all your rerolls.  It's in your best interest.", Toast.LENGTH_LONG).show();
//				}
//				if (view == bNext && test == 1){
//					Toast.makeText(CharacterCreation.this, "Really?!? Come on... at least give the character a unique name.", Toast.LENGTH_LONG).show();
//				}
//				RRnum = RR;
//			}
	    };
//	    bRR.setOnClickListener(bListen);
//	    bNext.setOnClickListener(bListen);
	}
//
//	public ArrayList<Integer> Order (ArrayList<Integer> string){
//		ArrayList<Integer> result = new ArrayList<Integer>();
//		int size = string.size();
//		result.clear();
//		for (int l = 0; l < (size - 1); l++){
//			int max = string.get(0);
//			int j = 0;
//			for (int k = 1; k < string.size(); k++){
//				max = Math.max(max, string.get(k));
//				if (max == string.get(k)){
//					j = k;
//				}
//			}
//			result.add(max);
//			string.remove(j);
//		}
//		result.add(string.get(0));
//		return result;		
//	}
}
 