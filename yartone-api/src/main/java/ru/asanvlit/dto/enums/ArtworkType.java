package ru.asanvlit.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArtworkType {

    DIGITAL_ART("Digital art"),
    PENCIL_DRAWING("Pencil drawing"),
    PASTEL_DRAWING("Pastel drawing"),
    MARKER_DRAWING("Marker drawing"),
    PEN_DRAWING("Pen drawing"),
    PHOTOSHOP("Photoshop"),
    GRAFFITI("Graffiti");

    private final String description;
}
