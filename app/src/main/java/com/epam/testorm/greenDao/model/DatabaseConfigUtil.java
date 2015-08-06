package com.epam.testorm.greenDao.model;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;


public class DatabaseConfigUtil {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.epam.testorm.greenDao.model");

        Entity news = schema.addEntity("News");
        news.addIdProperty().autoincrement();
        news.addStringProperty("url");
        news.addLongProperty("timestamp");

        Entity author = schema.addEntity("Author");
        author.addIdProperty();
        author.addLongProperty("ref");
        author.addStringProperty("network");
        author.addStringProperty("user_id");
        author.addStringProperty("displayName");
        author.addStringProperty("avatar");
        author.addStringProperty("profile");

        Entity content = schema.addEntity("Content");
        content.addIdProperty().autoincrement();
        content.addStringProperty("description");
        content.addStringProperty("comment");
        content.addStringProperty("title");

        Entity images = addMediaContentTable(schema, "Images");
        Entity links = addMediaContentTable(schema, "Links");
        Entity videos = addMediaContentTable(schema, "Videos");
        Entity audios = addMediaContentTable(schema, "Audios");

        Property personId = news.addLongProperty("authorId").getProperty();
        news.addToOne(author, personId);

        Property contentId = news.addLongProperty("contentId").getProperty();
        news.addToOne(content, contentId);

        addRelations(content, images, "images");
        addRelations(content, links, "links");
        addRelations(content, videos, "videos");
        addRelations(content, audios, "audios");

        new DaoGenerator().generateAll(schema, "app/src/main/java");
    }

    private static void addRelations(Entity content, Entity images, String name) {
        Property contentImagesId = images.addLongProperty("contentId").getProperty();
        images.addToOne(content, contentImagesId);
        ToMany toMany = content.addToMany(images, contentImagesId);
        toMany.setName(name);
    }

    private static Entity addMediaContentTable(Schema schema, String className) {
        Entity entity = schema.addEntity(className);
        entity.addIdProperty().autoincrement();
        entity.addStringProperty("url");
        entity.addStringProperty("title");
        entity.addStringProperty("image");
        entity.addStringProperty("original");
        entity.addStringProperty("thumbnail");
        entity.addStringProperty("description");
        return entity;
    }
}
