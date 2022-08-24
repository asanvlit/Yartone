package ru.asanvlit.util.string;

public enum FromToTransliteration {

    LATIN_TO_CYRILLIC("Latin-Russian/BGN"),
    CYRILLIC_TO_LATIN("Russian-Latin/BGN");

    public final String label;

    private FromToTransliteration(String label) {
        this.label = label;
    }
}
