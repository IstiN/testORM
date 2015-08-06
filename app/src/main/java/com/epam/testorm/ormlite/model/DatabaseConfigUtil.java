package com.epam.testorm.ormlite.model;

import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;


public class DatabaseConfigUtil extends OrmLiteConfigUtil {
	private static final Class<?>[] classes = new Class[] {
			ORMAuthor.class, ORMNews.class, ORMContent.class, ORMAudios.class, ORMImages.class, ORMLinks.class, ORMVideos.class
	};
	public static void main(String[] args) throws SQLException, IOException {
		
		// Provide the name of .txt file which you have already created and kept in res/raw directory
		writeConfigFile("app/src/main/res/raw/ormlite_config.txt", classes);
	}
}
