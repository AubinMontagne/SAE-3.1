package src.Metier;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.FileInputStream;
import java.io.IOException;

public class RtfFileReader {

    public String getRtfFileAsText(String filePath) {
        RTFEditorKit rtfEditorKit = new RTFEditorKit();
        DefaultStyledDocument document = new DefaultStyledDocument();

        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            rtfEditorKit.read(inputStream, document, 0);
            return document.getText(0, document.getLength());
        } catch (IOException | BadLocationException e) {
            e.printStackTrace();
            return null;
        }
    }
}