package com.it.crowd.service.api;

import com.it.crowd.entity.Menu;

import java.util.List;

/**
 * @author wyj
 * @description
 * @create 2020-12-14
 */
public interface MenuService {
    List<Menu> getAll();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void removeMenu(Integer id);
}
