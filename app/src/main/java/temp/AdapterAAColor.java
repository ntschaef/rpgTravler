package happyface.rpgtraveler.com;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class AdapterAAColor extends ArrayAdapter{
	ArrayList<Integer> color = new ArrayList<Integer> ();
	public AdapterAAColor(Context context, int resource, List objects, ArrayList<Integer> colorList) {
		super(context, resource, objects);
		for (int i = 0; i < colorList.size(); i++) {
			color.add(colorList.get(i));
		}
	}
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	View view = super.getView(position, convertView, parent);
    	view.setBackgroundColor(color.get(position));
    	
    	return view;
	}
}
