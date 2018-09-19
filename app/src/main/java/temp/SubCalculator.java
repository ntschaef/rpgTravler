package happyface.rpgtraveler.com;

import android.util.Log;

public class SubCalculator {
	public static int calc (String rolls){
		int result = 0;
		int multi = Integer.parseInt(rolls.substring(0,2));
		if (rolls.length() == 5){
			int dice = Integer.parseInt(rolls.substring(3,5));
			for (int i = 0; i < multi; i++){
				result = (int)(Math.random() * dice) + 1 + result;
			}
		} else if (rolls.length() == 9){
			int choice = Integer.parseInt(rolls.substring(4,6));
			int dice = Integer.parseInt(rolls.substring(7,9));
//			Log.e("LogFlag",multi + "|" + choice + "|" + dice);
			int[] iResult = new int[multi];
			for (int i = 0; i < choice; i++){
				int temp = (int)(Math.random() * dice) + 1;
				for (int j = 0; j < multi; j++){
					if (iResult[j] < temp){
						int temp0 = iResult[j];
						iResult[j] = temp;
						temp = temp0;
					}
				}
			}
			for (int i = 0; i < multi; i++){
				result = iResult[i] + result;
			}
		}
		return result;
	}

}
