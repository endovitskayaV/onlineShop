function setCookie(cookie) {
    $.cookie(cookie.name, cookie.value, {
        expires: 60 * 60,
        path: '/'
    });
}