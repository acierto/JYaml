package org.ho.yaml;

/**
 * @author THo
 */
public class TextEvent {

    public long timestamp;
    //public KeyEvent event;
    char chr;

    public TextEvent() {
    }

    public TextEvent(long timestamp, char chr) {
        this.timestamp = timestamp;
        this.chr = chr;
    }

    public String getChar() {
        if (chr == '\b')
            return "BACKSPACE";
        else
            return "" + chr;
    }

    public void setChar(String ch) {
        if (ch.equals("BACKSPACE"))
            chr = '\b';
        else
            chr = ch.charAt(0);
    }

    public String toString() {
        return "(" + timestamp + ", " + chr + ")";
    }
}

