package com.hervmo.manager.listeners.warehouse;

import com.hervmo.manager.events.registration.RegistrationCompleteEvent;
import com.hervmo.manager.events.warehouse.WarehouseAddArticleInBoxEvent;
import com.hervmo.manager.models.entities.Articles;
import com.hervmo.manager.models.entities.BoxArticlesMap;
import com.hervmo.manager.models.entities.Boxes;
import com.hervmo.manager.models.entities.Users;
import com.hervmo.manager.services.UsersService;
import com.hervmo.manager.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import java.util.UUID;

@Slf4j
public class WarehouseAddArticleInBoxEventListener implements ApplicationListener<WarehouseAddArticleInBoxEvent> {


    @Override
    public void onApplicationEvent(WarehouseAddArticleInBoxEvent event) {
        Articles article = event.getArticle();
        Boxes box = event.getBox();
        BoxArticlesMap boxArticlesMap = event.getBoxArticlesMap();

        log.debug("article {}", article);
        log.debug("box {}", box);
        log.debug("bx {}", boxArticlesMap);
    }
}
