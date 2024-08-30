package ViewPackages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderPasswordField extends JPasswordField {
    private String placeholder;
    private boolean isPlaceholder = true;

    public PlaceholderPasswordField(String placeholder) {
        this.placeholder = placeholder;
        setForeground(Color.GRAY);
        setEchoChar((char) 0); // Yer tutucu metin için karakter gizlemeyi kapat
        setText(placeholder);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (isPlaceholder) {
                    setText(""); // Yer tutucu metni kaldır
                    setEchoChar('*'); // Şifre karakterlerini göster
                    setForeground(Color.BLACK);
                    isPlaceholder = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getPassword().length == 0) {
                    setText(placeholder); // Yer tutucu metni geri getir
                    setEchoChar((char) 0); // Yer tutucu metin için karakter gizlemeyi kapat
                    setForeground(Color.GRAY);
                    isPlaceholder = true;
                }
            }
        });
    }

    @Override
    public void setText(String t) {
        super.setText(t);
        if (t.isEmpty()) {
            setForeground(Color.GRAY);
            setEchoChar((char) 0); // Yer tutucu metin için karakter gizlemeyi kapat
            isPlaceholder = true;
        } 
    }
}
