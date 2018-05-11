package ru.reksoft.onlineShop.controller.util;

import javax.servlet.http.Cookie;
import java.util.List;

public class CookiesUtils {
    public static final String COOKIE_BASKET_PREFIX = "basket_";
    private static final String COOKIE_BASKET_ITEM = "item";
    private static final String COOKIE_BASKET_ID = "basketId";

    public static final String BASKET_ID=COOKIE_BASKET_PREFIX+COOKIE_BASKET_ID;
    public static final String ITEM=COOKIE_BASKET_PREFIX+COOKIE_BASKET_ITEM;

    public static final String ITEM_QUANTITY_DELIMITER = "-";
    public static final String PAIRS_DELIMITER = "_";

    public static final int COOKIE_AGE = 60 * 60 * 24 * 7;

    /**
     *
     * @param requestCookies cookies from request
     * @return cookie with name ITEM
     */
    public static Cookie getItemQuantityCookie(List<Cookie> requestCookies) {
        return requestCookies.stream()
                .filter(requestCookie -> ITEM.equals(requestCookie.getName()))
                .findFirst().orElse(null);
    }
}
