package com.fpt.repository;

import com.fpt.model.UISubmenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubMenuRepository extends JpaRepository<UISubmenu, Long> {
}
