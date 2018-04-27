package ru.reksoft.onlineShop.controller.util;

public class CookiesUtils {
    public static final String COOKIE_BASKET_PREFIX = "basket-";
    public static final String COOKIE_BASKET_ITEM_ID = "itemId-";
    public static final String COOKIE_BASKET_ITEM_QUANTITY = "quantity-";
    public static final String COOKIE_BASKET_ID = "basketId-";
    public static final int cookieAge = 60 * 60 * 24 * 7;

    public static int getCookieId(String cookieName) {
        return Integer.parseInt(cookieName.substring(cookieName.lastIndexOf('-') + 1, cookieName.length()));

    }
}
