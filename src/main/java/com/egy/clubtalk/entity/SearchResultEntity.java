package com.egy.clubtalk.entity;

public class SearchResultEntity {
    private String title;
    private String description;
    private String image;
    private SearchResultType type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SearchResultType getType() {
        return type;
    }

    public void setType(SearchResultType type) {
        this.type = type;
    }

    public static SearchResultEntity fromUser(UserEntity user) {
        SearchResultEntity entity = new SearchResultEntity();
        entity.setTitle(user.getFullName());
        entity.setImage(user.getPicture());
        entity.setDescription(String.format("@%s", user.getUsername()));
        entity.setType(SearchResultType.USER);

        return entity;
    }
}
