package com.gmail.marozalena.onlinemarket.web.constant;

public class UrlConstants {

    public static final String URL_TO_USERS_PAGE = "/private/users";
    public static final String URL_TO_USER_PAGE = "/private/users/{id}";
    public static final String URL_TO_DELETE_USERS_PAGE = "/private/users/delete";
    public static final String URL_TO_ADD_NEW_USER_PAGE = "/private/users/new";
    public static final String URL_TO_REVIEWS_PAGE = "/reviews";
    public static final String URL_TO_UPDATE_REVIEWS_PAGE = "/reviews/save";
    public static final String URL_TO_DELETE_REVIEW_PAGE = "/reviews/delete";
    public static final String URL_TO_ARTICLES_PAGE = "/articles";
    public static final String URL_TO_ARTICLE_PAGE = "/articles/{id}";
    public static final String URL_TO_ADD_NEW_ARTICLE_PAGE = "/articles/new";
    public static final String URL_TO_DELETE_ARTICLE_PAGE = "/articles/delete/{id}";
    public static final String URL_TO_UPDATE_ARTICLE_PAGE = "/articles/{id}/save";
    public static final String URL_TO_PROFILE_PAGE = "/profile";
    public static final String URL_TO_PROFILE_PAGES = "/profile/**";
    public static final String URL_TO_DELETE_COMMENT_PAGE = "/articles/{id}/comments/{idCom}/delete";
    public static final String URL_TO_ITEMS_PAGE = "/items";
    public static final String URL_TO_ITEM_PAGE = "/items/{id}";

    private UrlConstants() {
    }
}
