package com.epam.testorm.db.model;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

public class DatabaseConfigUtil extends OrmLiteConfigUtil {
  public static void main(String[] args) throws Exception {
    writeConfigFile("ormlite_config.txt");
  }
}