package lbeen.sys.user.core;

import org.nutz.integration.shiro.SimpleShiroToken;

/**
 * @author 李斌
 */
public class MyToken extends SimpleShiroToken {
    public static final Object CREDENTIALS = new Object();

    public MyToken(Object principal) {
        super(principal);
    }

    @Override
    public Object getCredentials() {
        return CREDENTIALS;
    }
}
