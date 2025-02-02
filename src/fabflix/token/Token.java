/* ====================================================================================================
   DATE:      06 APR 2018
   AUTHORS:   Kelly McKeown   David Hopkins
   UCInetIDs: KPMCKEOW        HOPKINSD
   ID#'s:     42806243        70404050
   CLASS:     CS 122B
   PROJECT:   #2
   ----------------------------------------------------------------------------------------------------
   DESCRIPTION:
   ====================================================================================================
 */

package fabflix.token;

import java.security.SecureRandom;
import java.util.Arrays;

public class Token implements Comparable<Token>{
    private final byte[] token;
    private static final SecureRandom rngesus = new SecureRandom();
    private static final int TOKEN_SIZE = 64;

    /* -------------------- CONSTRUCTORS -------------------- */
    private Token(byte[] token) {
        this.token = token;
    }

    static Token generateToken() {
        byte[] token = new byte[TOKEN_SIZE];
        rngesus.nextBytes(token);
        return new Token(token);
    }


    /* -------------------- FUNCTION DEFINITIONS -------------------- */
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (byte b : token) {
            buf.append(format(Integer.toHexString(Byte.toUnsignedInt(b))));
        }
        return buf.toString();
    }

    private String format(String binS) {
        int length = 2 - binS.length();
        char[] padArray = new char[length];
        Arrays.fill(padArray, '0');
        String padString = new String(padArray);
        return padString + binS;
    }

    public boolean validate(String id) {
        return equals(new Token(convert(id)));
    }

    private static byte[] convert(String tok) {
        int len = tok.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(tok.charAt(i), 16) << 4) + Character.digit(tok.charAt(i + 1), 16));
        }
        return data;
    }

    private static Token validateSssionToken(String token) {
        if (token.length() == TOKEN_SIZE * 2) {
            int len = token.length();
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                data[i / 2] = (byte) ((Character.digit(token.charAt(i), 16) << 4) + Character.digit(token.charAt(i), 16));
            }
            return new Token(data);
        } else {
            return null;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(token);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Token) {
            Token t = (Token) o;
            if (t.token.length != token.length) {
                return false;
            } else {
                for (int i = 0; i < t.token.length; i++) {
                    if (t.token[i] != token[i]) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Token o) {
        if (o.token.length != token.length) {
            return token.length - o.token.length;
        } else {
            for (int i = 0; i < o.token.length; i++) {
                if (o.token[i] != token[i]) {
                    return token[i] - o.token[i];
                }
            }
        }
        return 0;
    }
}
