package com.randioo.race_server.module.fight.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.randioo.mahjong_public_server.protocol.Fight.FightHuRequest;
import com.randioo.race_server.entity.bo.Role;
import com.randioo.race_server.module.fight.service.FightService;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.cache.RoleCache;
import com.randioo.randioo_server_base.template.IActionSupport;

@Controller
@PTAnnotation(FightHuRequest.class)
public class FightHuAction implements IActionSupport {

    @Autowired
    private FightService fightService;

    @Override
    public void execute(Object data, IoSession session) {
        FightHuRequest request = (FightHuRequest) data;
        Role role = (Role) RoleCache.getRoleBySession(session);
        fightService.hu(role, request.getTempGameCount(), request.getCallCardListId());
    }
}
