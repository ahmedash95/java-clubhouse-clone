package com.egy.clubtalk.entity;

public enum SearchResultType {
    USER, ROOM;
    public String getType() {
        switch (this) {
            case USER:
                return "user";
            case ROOM:
                return "room";
        }

        return null;
    }
}
