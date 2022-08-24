package ru.asanvlit.service;

import ru.asanvlit.dto.enums.ConfirmCodeType;
import ru.asanvlit.model.ConfirmCodeTypeEntity;

public interface ConfirmCodeTypeService {

    ConfirmCodeTypeEntity getConfirmCodeTypeByType(ConfirmCodeType confirmCodeType);
}
