package com.epam.testorm.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.epam.testorm.R;
import com.epam.testorm.ormlite.model.ORMAudios;
import com.epam.testorm.ormlite.model.ORMAuthor;
import com.epam.testorm.ormlite.model.ORMContent;
import com.epam.testorm.ormlite.model.ORMImages;
import com.epam.testorm.ormlite.model.ORMLinks;
import com.epam.testorm.ormlite.model.ORMNews;
import com.epam.testorm.ormlite.model.ORMVideos;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Database helper which creates and upgrades the database and provides the DAOs for the app.
 * 
 * 
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	/************************************************
	 * Suggested Copy/Paste code. Everything from here to the done block.
	 ************************************************/

	private static final String DATABASE_NAME = "news.db";
	private static final int DATABASE_VERSION = 1; 

	private Dao<ORMNews, Integer> newsDao;
	private Dao<ORMAuthor, Integer> authorDao;
	private Dao<ORMContent, Integer> contentDao;
	private Dao<ORMAudios, Integer> audiosDao;
	private Dao<ORMVideos, Integer> videosDao;
	private Dao<ORMLinks, Integer> linksDao;
	private Dao<ORMImages, Integer> imagesDao;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
	}

	/************************************************
	 * Suggested Copy/Paste Done
	 ************************************************/

	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
		try {
			
			// Create tables. This onCreate() method will be invoked only once of the application life time i.e. the first time when the application starts.
			TableUtils.createTable(connectionSource, ORMNews.class);
			TableUtils.createTable(connectionSource, ORMAuthor.class);
			TableUtils.createTable(connectionSource, ORMContent.class);
			TableUtils.createTable(connectionSource, ORMAudios.class);
			TableUtils.createTable(connectionSource, ORMVideos.class);
			TableUtils.createTable(connectionSource, ORMLinks.class);
			TableUtils.createTable(connectionSource, ORMImages.class);

		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
		try {
			
			// In case of change in database of next version of application, please increase the value of DATABASE_VERSION variable, then this method will be invoked 
			//automatically. Developer needs to handle the upgrade logic here, i.e. create a new table or a new column to an existing table, take the backups of the
			// existing database etc.
			
			TableUtils.dropTable(connectionSource, ORMNews.class, true);
			TableUtils.dropTable(connectionSource, ORMAuthor.class, true);
			TableUtils.dropTable(connectionSource, ORMContent.class, true);
			TableUtils.dropTable(connectionSource, ORMAudios.class, true);
			TableUtils.dropTable(connectionSource, ORMVideos.class, true);
			TableUtils.dropTable(connectionSource, ORMLinks.class, true);
			TableUtils.dropTable(connectionSource, ORMImages.class, true);
			onCreate(sqliteDatabase, connectionSource);
			
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
					+ newVer, e);
		}
	}
	
	// Create the getDao methods of all database tables to access those from android code.
	// Insert, delete, read, update everything will be happened through DAOs

	public Dao<ORMNews, Integer> getNewsDao() throws SQLException {
		if (newsDao == null) {
            newsDao = getDao(ORMNews.class);
		}
		return newsDao;
	}

	public Dao<ORMAuthor, Integer> getAuthorDao() throws SQLException {
		if (authorDao == null) {
            authorDao = getDao(ORMAuthor.class);
		}
		return authorDao;
	}
	public Dao<ORMContent, Integer> getContentDao() throws SQLException {
		if (contentDao == null) {
			contentDao = getDao(ORMContent.class);
		}
		return contentDao;
	}
	public Dao<ORMImages, Integer> getImagesDao() throws SQLException {
		if (imagesDao == null) {
			imagesDao = getDao(ORMImages.class);
		}
		return imagesDao;
	}
	public Dao<ORMVideos, Integer> getVideosDao() throws SQLException {
		if (videosDao == null) {
			videosDao = getDao(ORMVideos.class);
		}
		return videosDao;
	}
	public Dao<ORMLinks, Integer> getLinksDao() throws SQLException {
		if (linksDao == null) {
			linksDao = getDao(ORMLinks.class);
		}
		return linksDao;
	}
	public Dao<ORMAudios, Integer> getAudiosDao() throws SQLException {
		if (audiosDao == null) {
			audiosDao = getDao(ORMAudios.class);
		}
		return audiosDao;
	}
}
