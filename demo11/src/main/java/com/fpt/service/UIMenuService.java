package com.fpt.service;

import com.fpt.dto.UIMenuDto;
import com.fpt.model.UIMenu;

import java.util.List;

public interface UIMenuService {
    UIMenu getMenuById(Long id);
    List<UIMenuDto> getAllMenus(String type);
    UIMenu createMenu(UIMenu uiMenu);
    UIMenu updateMenu(UIMenu uiMenu);
    void updateMenuPositions(List<UIMenu> menus);
    boolean deleteMenu(Long id);
    List<UIMenuDto> listAllSlide();
    List<UIMenuDto> listAllSlideWithStatus();
}
