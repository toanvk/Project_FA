package com.fpt.repository;

import com.fpt.model.UIMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UIMenuRepository extends JpaRepository<UIMenu, Long> {
    List<UIMenu> findAllByOrderBySortOrderAsc();
}
