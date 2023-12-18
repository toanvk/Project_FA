package com.fpt.service;

import com.fpt.dto.UISubMenuDto;
import com.fpt.model.UISubmenu;

public interface UISubMenuService {
    UISubmenu createSubMenu(UISubMenuDto uiSubmenu);
    UISubmenu getSubMenuById(Long id);
    UISubmenu updateMenu(UISubMenuDto uiSubmenu);
    void deleteSubMenu(Long id);
}
