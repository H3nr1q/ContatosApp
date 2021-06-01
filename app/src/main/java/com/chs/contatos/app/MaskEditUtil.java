package com.chs.contatos.app;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MaskEditUtil {
    public static final String FORMAT_FONE = "(##) ####-#####";

    public static TextWatcher mask(final EditText editText, final String mask) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String str = MaskEditUtil.unmask(s.toString());
                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (final char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > old.length()) {
                        mascara += m;
                        continue;
                    }
                    try {
                        mascara += str.charAt(i);
                    } catch (final Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                editText.setText(mascara);
                editText.setSelection(mascara.length());

            }
        };
    }

    private static String unmask(String toString) {
        return toString.replaceAll("[.]", "")
                .replaceAll("[-]", "")
//                .replaceAll("[/]", "")
                .replaceAll("[(]", "")
                .replaceAll("[ ]", "")
//                .replaceAll("[:]", "")
                .replaceAll("[)]", "");
    }
}
