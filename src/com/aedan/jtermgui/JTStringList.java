package com.aedan.jtermgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Created by Aedan Smith on 8/28/2016.
 * <p>
 * Class for rendering Strings to the JTDisplay.
 */

class JTStringList extends JComponent implements MouseWheelListener {

    /**
     * The lines for the StringList to render.
     */
    String lines = "";

    /**
     * The current StringList string.
     */
    private StringBuilder currentString = new StringBuilder();

    /**
     * The index of the cursor.
     */
    private int cursorIndex = 0;

    /**
     * The number of lines currently being displayed.
     */
    int numLines = 0;

    /**
     * The Colors for the StringList to draw.
     */
    public Color fontColor = Color.WHITE, backgroundColor = Color.BLACK;

    /**
     * The current font size for the StringList.
     */
    private int currentFontSize = 18;

    /**
     * The amount to translate the font Y.
     */
    private int fontTransY = -3;

    /**
     * The current font for the StringList.
     */
    private Font currentFont = new Font("Monospaced", Font.PLAIN, currentFontSize);

    /**
     * The current Display for the StringList.
     */
    private JTDisplay jtDisplay;

    /**
     * Default JTStringList constructor
     *
     * @param jtDisplay The Display for the StringList to display to.
     */
    JTStringList(JTDisplay jtDisplay) {
        this.jtDisplay = jtDisplay;
    }

    @Override
    public void paint(Graphics g1) {
        Graphics2D g = ((Graphics2D)g1);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(fontColor);
        g.setFont(currentFont);
        String[] lines = (this.lines + "\000").split("\n");
        int i;
        for (i = 0; i < lines.length - 1; i++) {
            g.drawString(lines[i], 5, currentFontSize + (i * currentFontSize) + fontTransY);
        }
        g.drawString(lines[i] + currentString, 5, currentFontSize + (i * currentFontSize) + fontTransY);

        repaint();
    }

    void snapToInput() {
        fontTransY = -currentFontSize * numLines + jtDisplay.getHeight() - 35 - currentFontSize * 2;
        if (fontTransY > -3) fontTransY = -3;
    }

    void incrementCursorIndex(){
        if (cursorIndex < currentString.length())
            cursorIndex++;
    }

    void decrementCursorIndex(){
        if (cursorIndex != 0)
            cursorIndex--;
    }

    void insertCharAtCursor(char c){
        currentString.insert(cursorIndex, c);
    }

    void setCurrentString(String s){
        currentString = new StringBuilder(s);
        cursorIndex = 0;
    }

    void removeCurrentStringLastChar() {
        if (currentString.length() != 0)
            currentString.deleteCharAt(cursorIndex - 1);
    }

    public String getCurrentString() {
        return currentString.toString();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        fontTransY -= e.getWheelRotation() * currentFontSize * 2;
        if (fontTransY > -3) fontTransY = -3;
    }

}
