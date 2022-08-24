package ru.asanvlit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.asanvlit.dto.enums.ConfirmCodeType;
import ru.asanvlit.exception.notfound.YartoneConfirmCodeTypeNotFoundException;
import ru.asanvlit.model.ConfirmCodeTypeEntity;
import ru.asanvlit.repository.postgres.ConfirmCodeTypeRepository;
import ru.asanvlit.service.ConfirmCodeTypeService;

@RequiredArgsConstructor
@Service
public class ConfirmCodeTypeServiceImpl implements ConfirmCodeTypeService {

    private final ConfirmCodeTypeRepository confirmCodeTypeRepository;

    @Override
    public ConfirmCodeTypeEntity getConfirmCodeTypeByType(ConfirmCodeType confirmCodeType) {
        return confirmCodeTypeRepository.findByConfirmCodeType(confirmCodeType)
                .orElseThrow(YartoneConfirmCodeTypeNotFoundException::new);
    }
}
