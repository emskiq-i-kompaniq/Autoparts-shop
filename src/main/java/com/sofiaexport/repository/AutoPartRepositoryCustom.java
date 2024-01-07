package com.sofiaexport.repository;

import com.sofiaexport.commands.FindAutoPartsCommand;
import com.sofiaexport.model.AutoPart;
import java.util.List;

public interface AutoPartRepositoryCustom {
    List<AutoPart> findAutoParts(FindAutoPartsCommand command);
}
