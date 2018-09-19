package happyface.rpgtraveler.com;

import android.app.AliasActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.location.LocationListener;
import android.util.Log;

public class DBTables extends DBAdapter{
	//public class DBTables extends DBAdapter{
	//	These are the summaries for the Tables and the fields for each
	//		LOCAL - ID, CATEGORY, NAME, DESCRIPTION, ALIAS
	//		LINK - ID, GAMEID, GAMEMAJ, GAMEMIN, PARENT, FOCUS, ARRANGE, SCORE, EDITABLE, VISIBLE, ALIAS
	//		INHERITED - ID, LINK, TYPE, ALIAS
	//		PREREQS - ID, GAMEID, FOCUS, PREREQ_ID, SCORE, ALIAS
	//		RESTRICTIONS - ID, GAMEID, FOCUS, SCORE, ALIAS
	//		EXTERNAL - Not yet implemented
	//These will be clarified further in the section for each table	
	
	public static final String ID = "_ID";
	public static final String CAT = "CATEGORY";
	public static final String NAME = "NAME";
	public static final String DESC = "DESCRIPTION";
	public static final String GAMEID = "GAME_ID";
	public static final String MAJVER = "GAME_MAJ";
	public static final String MINVER = "GAME_MIN";
	public static final String PARENT = "PARENT";
	public static final String FOCUS = "FOCUS";
	public static final String ARRANGE = "ARRANGE";
	public static final String SCORE = "VALUE";
	public static final String GAME_TRAIT = "GAME_TRAIT";
	public static final String VISIBLE = "VISIBLE";
	public static final String ALIAS = "ALIAS_ID";
	public static final String LINK = "LINK_ID";
	public static final String TYPE = "INHER_TYPE";
	public static final String PREREQ = "PREREQ_ID";

	public static final String NAME_LOCAL = "LOCAL";
	public static final String NAME_LINK = "LINKS";
	public static final String NAME_INHERITED = "INHER";
	public static final String NAME_PREREQS = "PREREQS";
	public static final String NAME_RESTRICTIONS = "RESTRICTS";
	
	public DBTables(Context ctx) {
		super(ctx);
	}

	// This class is intended to capture all local values independent of the game type.  The following are the database categories:
	// ID - the unique id for the entry (auto-generated)
	// CATEGORY - this allows the database to further restrict the search, it also will be used in identifying appropriate links.  The categories that will be used for the default game is as follows
	
    // THESE MAY NEED TO BE UPDATED
	// -Link:       System - list of all the links that occur between the different components.
	// -Game:		GM Created - list of all the game shells that are available.
	// -Component:	GM Created - list of all game categories.
	// *Action:		GM Created - Attributes that will be used to establish U/I in Act11GameActions.
	// *Party: 		GM Created - list of all the parties that a user is involved with.
	// *Character:	GM Created - Character templates for use in adventures.
	// Item:		GM Created - list of the base items created for the game.
	// Trait:		GM Created - required traits that a playable character will gain upon creation.
	// Race:  		GM Created - list of races that exist in the game.
	// Class:		GM Created - list of classes (or jobs) that each of the characters (playable and non-playable) can have.
	// Skill: 		GM Created - list of skills that characters can level up.
	// Ability:		GM Created - list of bonus abilities that a character can perform.
	// Deity:		GM Created - list of worshiped entities that exist in the game.
	// Attribute:	GM Created - list of the attributes that applies to each of the characters/items.
	// -Avatar:		GM & Character Created - Named characters that are referenced from the "Character" category
	// -Inventory:	Character Created - Named items that are owned by the Character
	// -Note:		Character Created - Comments on other entries for user 		
	// ALL * Categories are required for every game.  ALL - Categories are independent of games (includes categories that are altered ONLY by the player).

	// NAME - this is the main entry, which will create subjects and entries that will be scored (ex: Player1 (NAME) has a STR (NAME) of 11 (SCORE))
	// DESCRIPTION - Explanation of the NAME
	// ALIAS - if an external source has the same name, what is the new name for the external source (null if created internally, if external this will most likely be the same as name if the data is imported).

	public long createLocal(String category, String name, String desc, int alias){
		ContentValues initialValues = new ContentValues();
		initialValues.put(CAT, category);
		initialValues.put(NAME, name);
		initialValues.put(DESC, desc);
		initialValues.put(ALIAS, alias);

		long result = db.insert(NAME_LOCAL, null, initialValues);
		return result;
	}
	
	public Cursor getAllLocal(String catagory) throws SQLException {
		// there are times when all entries are needing to be chosen for a specific category.  The term "ALL" should be used as the string to accomplish this.)
		Cursor mCursor;
		if (catagory.equals("ALL")) {
			mCursor = db.query(NAME_LOCAL, null, null, null, null, null, ID + " ASC");
		} else {
			mCursor = db.query(NAME_LOCAL, null, CAT + " like \"" + catagory + "\"", null, null, null, "Upper(" + NAME + ") ASC");
		}
		if (mCursor != null){
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	public boolean updateLocal(int id, String name, String desc){
		ContentValues upVals = new ContentValues();
		
		Cursor test = db.query(true, NAME_LOCAL, null, ID + "=" + id, null, null, null, null, null);

		boolean result = false;
		try {
			test.moveToFirst();

			upVals.put(NAME, name);
			upVals.put(DESC, desc);

			result = db.update(NAME_LOCAL, upVals, ID + "=" + id, null) >0;

		} catch (Exception e){
			Log.e("LogFlag", "this entry does not exist");
		}

		close();
		return result;
	}

	// This table records all the scores associated with a given object from the "Local" or "External" tables 
	// (example: "character1" (object from "Local") gets a "STR" (object from "External") score of "6" (score is stored in "Link" along with the connection).
	// (note that another example that wasn't used is: "character1 (a Character) is a member of "unjoint" (a Party) with a score of "1" (existance Score becuase Character is a Collection component).
	// ID - the primary key to identify the entry
	// GAMEID - This specifies which game this link belongs to
	// GAMEMAJ - this is an indicator for major changes.  This may not be backward compatable.
	// GAMEMIN - this is an indicator for minor changes.  This will be backward compatable.
	// PARENT - this is the id of the object that contains the focus. (character1)
	// FOCUS - this is the id of the object that gets the score. (STR)
	// ARRANGE - orders the object within the parent.
	// SCORE - this is the score that is being given. (in the example above this is "6")
	// EDITABLE - is this score editable by the player? (if no, it is only editable by the GM)
	// VISIBLE - is this score visible to the player? (if no, only the owner & GM can see it)
	// ALIAS - if an external source has the same name, what is the new name for the external source (null if created internally, if external this will most likely be the same as name if the data is imported).

	public long createLink(int game, int maj, int min, int base, int link, int arrange, String score, int edit, int hidden, int alias){
		ContentValues initialValues = new ContentValues();
		initialValues.put(GAMEID, game);
		initialValues.put(MAJVER, maj);
		initialValues.put(MINVER, min);
		initialValues.put(PARENT, base);
		initialValues.put(FOCUS, link);
		initialValues.put(ARRANGE, arrange);
		initialValues.put(SCORE, score);
		initialValues.put(GAME_TRAIT, edit);
		initialValues.put(VISIBLE, hidden);
		initialValues.put(ALIAS, alias);

		return db.insert(NAME_LINK, null, initialValues);
	}

	public Cursor getAllLink(int game, int par, int focus) throws SQLException {
		// there are times when all entries are needing to be chosen for any combination of game, specific link type, and/or base. The term "ALL"/"0" should be used as the string/int (respectively) to accomplish this.) 
		Cursor mCursor;
		if(game == 0){
			if(par == 0){
				if (focus == 0){
					mCursor = db.query(NAME_LINK, null, null, null, null, null, ARRANGE);
				} else {
					mCursor = db.query(NAME_LINK, null, FOCUS + "=" + focus, null, null, null, ARRANGE);
				}
			} else {
				if (focus == 0){
					mCursor = db.query(NAME_LINK, null, PARENT + "=" + par, null, null, null, ARRANGE);
				} else {
					mCursor = db.query(NAME_LINK, null, PARENT + "=" + par + " AND " + FOCUS + "=" + focus, null, null, null, ARRANGE);
				}
			}
		} else {
			if(par == 0){
				if (focus == 0){
					mCursor = db.query(NAME_LINK, null, GAMEID + "=" + game, null, null, null, ARRANGE);
				} else {
					mCursor = db.query(NAME_LINK, null, GAMEID + "=" + game + " AND " + FOCUS + "=" + focus, null, null, null, ARRANGE);
				}
			} else {
				if (focus == 0){
					mCursor = db.query(NAME_LINK, null, GAMEID + "=" + game + " AND " + PARENT + "=" + par, null, null, null, ARRANGE);
				} else {
					mCursor = db.query(NAME_LINK, null, GAMEID + "=" + game + " AND " + PARENT + "=" + par + " AND " + FOCUS + "=" + focus, null, null, null, ARRANGE);
				}
			}
		}
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public boolean updateLink(int id, int maj, int min, int par, int focus, int arrange, String score, int edit, int hidden){

		ContentValues upVals = new ContentValues();
		upVals.put(MAJVER, maj);
		upVals.put(MINVER, min);
		upVals.put(PARENT, par);
		upVals.put(FOCUS, focus);
		upVals.put(ARRANGE, arrange);
		upVals.put(SCORE, score);
		upVals.put(GAME_TRAIT, edit);
		upVals.put(VISIBLE, hidden);

		return db.update(NAME_LINK, upVals, ID + "=" + id, null) >0;
	}

	// These links are used to store dm made links for required links that are needed during object developement.  The Data is as follows:
	// ID - Primary key for the link
	// LINK - numerical id of the entry from the linked table
	// TYPE - which inherited type the link is: 0 - all elements are required; 1 - 1 element is required
	// ALLIAS - Official ID if this comes form the regulated database.

	
	public long createInherit(int link, int type, int allias){
		ContentValues initialValues = new ContentValues();
		initialValues.put(LINK, link);
		initialValues.put(TYPE, type);
		initialValues.put(ALIAS, allias);

		return db.insert(NAME_INHERITED, null, initialValues);
	}

	public Cursor getAllInherit(int link) {

		Cursor mCursor;
		if (link == 0){
			mCursor = db.query(NAME_INHERITED, null, null, null, null, null, null);
		} else {
			mCursor = db.query(NAME_INHERITED, null, LINK + "=" + link, null, null, null, null);
		}

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public boolean updateInher(int link, int type){
		ContentValues upVals = new ContentValues();

		upVals.put(TYPE, type);

		return db.update(NAME_INHERITED, upVals, LINK + "=" + link, null) >0;
	}

	//This database is used to identify attributes that must be accomplished before the base is available.  Data is as follows:

	//ID - Primary key for the link
	//GAME_ID - ID of the game that requires the link
	//FOCUS - local ID of the object that has the requirement
	//PREREQ - local ID of the object that is required.
	//SCORE - This is the quantity that the REQ_ID has to meet for the FOCUS to be avalible.
	//ALIAS - if an external source has the same name, what is the new name for the external source (null if created internally, if external this will most likely be the same as name if the data is imported).
	
	public long createPrereq(int game, int focus, int prereq, int score, int alias){
		ContentValues initialValues = new ContentValues();
		initialValues.put(GAMEID, game);
		initialValues.put(FOCUS, focus);
		initialValues.put(PREREQ, prereq);
		initialValues.put(SCORE, score);
		initialValues.put(ALIAS, alias);

		return db.insert(NAME_PREREQS, null, initialValues);
	}

	public Cursor getAllPrereq(int game, int focus, int prereqID) {
		Cursor mCursor;
		String sWhere = "";
		if(game != 0) {
			sWhere = "p." + GAMEID + " = " + game + " ";
		}
		if (prereqID != 0) {
			if (sWhere != "") {
				sWhere = sWhere + "AND ";
			}
			sWhere = "p." + PREREQ + " = " + prereqID + " ";
		}
		if (focus != 0) {
			if (sWhere != "") {
				sWhere = sWhere + "AND ";
			}
			sWhere = sWhere + "p." + FOCUS + " = " + focus + " ";
		}
		if (sWhere != ""){
			sWhere = " WHERE " + sWhere;
		}
		mCursor = db.rawQuery("SELECT p." + ID + ", p." + GAMEID + ", p." + FOCUS + ", p." + PREREQ + ", p." + SCORE + ", p." + ALIAS + " FROM " + NAME_PREREQS + " AS p" + sWhere + ";",null);

		if(mCursor != null){
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	public boolean updateReq(int id, int game, int focus, int prereqID, int score){

		ContentValues upVals = new ContentValues();
		upVals.put(GAMEID, game);
		upVals.put(FOCUS, focus);
		upVals.put(PREREQ, prereqID);
		upVals.put(SCORE, score);

		return db.update(NAME_PREREQS, upVals, ID + "=" + id, null) >0;
	}

	//This database is used to identify attributes that must be accomplished before the base is available.  Data is as follows:

	//ID - Primary key for the link
	//FOCUS - Primary key of the link containing the score being restricted
	//SCORE - This is the value of the restriction.  It will contain two parts the inequality ("lt" - less than, "le" - less than or equal to, "e" - equal to, "ge" - greater than or equal to, "gt" - greater than) and the value which can be a number or score which identified by "id" follwed by a local id of the element that has the score.

	public long createRes(int gameId, int focus, String restriction, int allias){
		ContentValues initialValues = new ContentValues();
		initialValues.put(GAMEID, gameId);
		initialValues.put(FOCUS, focus);
		initialValues.put(SCORE, restriction);
		initialValues.put(ALIAS, allias);

		return db.insert(NAME_RESTRICTIONS, null, initialValues);
	}

	public Cursor getAllRes(int gameId, int focus) {
		Cursor mCursor;
		if (gameId == 0) {
			if (focus == 0) {
				mCursor = db.query(NAME_RESTRICTIONS, null, null, null, null, null, null);
			} else {
				mCursor = db.query(NAME_RESTRICTIONS, null, FOCUS + " = " + focus, null, null, null, null, null);
			}
		} else {
			if (focus == 0) {
				mCursor = db.query(NAME_RESTRICTIONS, null, GAMEID + " = " + gameId, null, null, null, null);
			} else {
				mCursor = db.query(NAME_RESTRICTIONS, null, FOCUS + " = " + focus +" AND " + GAMEID + " = " + gameId, null, null, null, null, null);
			}
		}
		if(mCursor != null){
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public boolean updatePerms(int id, int gameId, int focus, String restriction){

		ContentValues upVals = new ContentValues();
		upVals.put(GAMEID, gameId);
		upVals.put(SCORE, restriction);
		upVals.put(FOCUS, focus);

		return db.update(NAME_RESTRICTIONS, upVals, ID + "=" + id, null) >0;
	}
}