package rms.exceptions;

public class UnauthorizedResponseException extends ForbiddenResponseException {

    public UnauthorizedResponseException(String actualUserLogin, String expectedUserLogin) {
        super("The path userLogin: '"
                + actualUserLogin
                + "' does not match the authentication userLogin: '"
                + expectedUserLogin + "'");
    }

}
