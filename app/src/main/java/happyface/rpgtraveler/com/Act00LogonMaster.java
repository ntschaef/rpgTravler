package happyface.rpgtraveler.com;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


public class Act00LogonMaster extends Activity {
    SharedPreferences metadata;
    SharedPreferences.Editor editor;
//    DBAdapter dbAdapter;
//    DBTables dbTables;
    static Cursor curTemp1 = null;
    static Cursor curTemp2 = null;
    static Cursor curTemp3 = null;
    static Cursor curTemp4 = null;
    static Cursor curTemp5 = null;
    RelativeLayout rlLogon;
    TextView tvDescription;
    RadioGroup rgMasterType;
    RadioButton rbGM;
    RadioButton rbPlayer;
    RelativeLayout rlSearch;
    TextView tvSearch;
    LinearLayout llMain;
    LinearLayout llTop;
    RelativeLayout rlTop;
    Spinner sTop;
    LinearLayout llTopB;
    Button bTopEdit;
    Button bTopRemove;
    LinearLayout llBot;
    RelativeLayout rlBot;
    Spinner sBot;
    LinearLayout llBotB;
    Button bBotEdit;
    Button bBotRemove;
    Button bPlay;

    @Override
    protected void onResume(){
        super.onResume();
        setContentView(R.layout.activity00_logonmaster);
//        dbAdapter = new DBAdapter(this);
//        dbTables = new DBTables(this);
        metadata = getSharedPreferences("metadata", 0);
        editor = metadata.edit();
        rlLogon = (RelativeLayout) findViewById(R.id.a00_rlLogon);
        tvDescription = (TextView) findViewById(R.id.a00_tvDescription);
        rgMasterType = (RadioGroup) findViewById(R.id.a00_rgMasterType);
        rbGM = (RadioButton) findViewById(R.id.a00_rbGM);
        rbPlayer = (RadioButton) findViewById(R.id.a00_rbPlayer);
        rlSearch = (RelativeLayout) findViewById(R.id.a00_rlSearch);
        tvSearch = (TextView);
//        llMain;
//        llTop;
//        rlTop;
//        sTop;
//        llTopB;
//        bTopEdit;
//        bTopRemove;
//        llBot;
//        rlBot;
//        sBot;
//        llBotB;
//        bBotEdit;
//        bBotRemove;
//        bPlay;

//        android:
//        id = "@+id/a00_rlLogon"
//        android:
//        id = "@+id/a00_tvDescription"
//        android:
//        id = "@+id/a00_rgMasterType"
//        android:
//        id = "@+id/a00_rbGM"
//        android:
//        id = "@+id/a00_rbPlayer"
//        android:
//        id = "@+id/a00_rlSearch"
//        android:
//        id = "@+id/a00_tvSearch"
//        android:
//        id = "@+id/a00_etSearch"
//        android:
//        id = "@+id/a00_llMain"
//        android:
//        id = "@+id/a00_llTop"
//        android:
//        id = "@+id/a00_rlTop"
//        android:
//        id = "@+id/a00_sTop"
//        android:
//        id = "@+id/a00_llTopB"
//        android:
//        id = "@+id/a00_bTopEdit"
//        android:
//        id = "@+id/a00_bTopRemove"
//        android:
//        id = "@+id/a00_llBot"
//        android:
//        id = "@+id/a00_rlBot"
//        android:
//        id = "@+id/a00_sBot"
//        android:
//        id = "@+id/a00_llBotB"
//        android:
//        id = "@+id/a00_bBotEdit"
//        android:
//        id = "@+id/a00_bBotRemove"
//        android:
//        id = "@+id/a00_bPlay"
    }
}
