package happyface.rpgtraveler.com;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.widget.Toast;

public class Dice {
	ArrayList<Integer> rolls = new ArrayList<Integer>();
	public ArrayList<Integer> roller (Context con, int sides, int multiples){
		Calendar cal = Calendar.getInstance();
		int rem = cal.get(cal.MINUTE) + (cal.get(cal.DAY_OF_YEAR) - 1) % 100;
		int base = cal.get(Calendar.SECOND) * 1000 + cal.get(Calendar.MILLISECOND) + rem;
		for (int i = 0; i < 2; i++){
			int oldBase = base;
			int tempnewBase = ((((oldBase - oldBase % 1) / 1) % 10) * 1000)
					+ ((((oldBase - oldBase % 10) / 10) % 10) * 100)
					+ ((((oldBase - oldBase % 100) / 100) % 10) * 10)
					+ ((((oldBase - oldBase % 1000) / 1000) % 10) * 1)
					+ ((((oldBase - oldBase % 10000) / 10000) % 10) * 10000)
					+ 1;
			int newBase = tempnewBase;
			for (int j = 0; j < Math.log(1000)/Math.log(sides); j++){
				rolls.add((newBase % sides) + 1);
				newBase = (newBase - rolls.get(j)+1)/sides;
			}
			base = tempnewBase + rem + cal.get(Calendar.HOUR_OF_DAY);
			if (rolls.size() < multiples) {
				i=0;
				
			} else {
				while(rolls.size() > multiples) {
					rolls.remove(rolls.size()-1);
				}
				i=10;
			}
		}
		return rolls;
	}
}
