package com.epam.testorm.realm;

import io.realm.RealmObject;

/**
 * Created by Mike on 07.07.2015.
 */
public class RealmEntitlement extends RealmObject {
    private String value;

    public RealmEntitlement() {
    }

    public RealmEntitlement(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
