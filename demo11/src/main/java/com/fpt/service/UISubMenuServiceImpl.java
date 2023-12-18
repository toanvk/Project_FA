package com.fpt.service;

import com.fpt.repository.SubMenuRepository;
import com.fpt.repository.UIMenuRepository;
import com.fpt.dto.UISubMenuDto;
import com.fpt.model.UIMenu;
import com.fpt.model.UISubmenu;
import org.springframework.stereotype.Service;

@Service
public class UISubMenuServiceImpl implements UISubMenuService{
    private final SubMenuRepository subMenuRepository;
    private final com.fpt.repository.UIMenuRepository UIMenuRepository;

    public UISubMenuServiceImpl(SubMenuRepository subMenuRepository, UIMenuRepository UIMenuRepository) {
        this.subMenuRepository = subMenuRepository;
        this.UIMenuRepository = UIMenuRepository;
    }

    @Override
    public UISubmenu createSubMenu(UISubMenuDto uiSubmenu) {
        UISubmenu sub = new UISubmenu();
        sub.setName(uiSubmenu.getName());
        sub.setUrl(uiSubmenu.getUrl());
        sub.setIcon(uiSubmenu.getIcon());
        sub.setSortOrder(uiSubmenu.getSortOrder());
        sub.setEnable(uiSubmenu.isEnable());
        UIMenu m = UIMenuRepository.findById(uiSubmenu.getMenu_id()).orElse(null);
        sub.setMenu(m);
        subMenuRepository.save(sub);
        return sub;
    }

    @Override
    public UISubmenu getSubMenuById(Long id) {
        UISubmenu sub = subMenuRepository.findById(id).orElse(null);
        return sub;
    }

    @Override
    public UISubmenu updateMenu(UISubMenuDto uiSubmenu) {
        UISubmenu sub = subMenuRepository.findById(uiSubmenu.getId()).orElse(null);
        if(sub != null) {
            sub.setName(uiSubmenu.getName());
            sub.setUrl(uiSubmenu.getUrl());
            sub.setIcon(uiSubmenu.getIcon());
            sub.setEnable(uiSubmenu.isEnable());
            subMenuRepository.save(sub);
            return sub;
        }
        return null;
    }

    @Override
    public void deleteSubMenu(Long id) {
        subMenuRepository.deleteById(id);
    }
}
