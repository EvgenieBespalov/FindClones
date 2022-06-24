package GUI.PrintClonesToGUI;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class RedirectingOutputStream extends OutputStream {

    private PrintClones gui;

    public RedirectingOutputStream(PrintClones gui) {
        this.gui = gui;
    }

    @Override
    public void write(int b) throws IOException {
        gui.appendANSI(String.valueOf((char) b));
    }
}