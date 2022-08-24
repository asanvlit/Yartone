package ru.asanvlit.util.string;

import com.ibm.icu.text.Transliterator;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StringTransliterator {

    public String transliterate(FromToTransliteration fromTo, String string) {
        Transliterator toTrans = Transliterator.getInstance(fromTo.label);
        return toTrans.transliterate(string);
    }
}

