package com.randioo.race_server.module.match.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.randioo.mahjong_public_server.protocol.Match.MatchJoinGameRequest;
import com.randioo.race_server.entity.bo.Role;
import com.randioo.race_server.module.match.service.MatchService;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.cache.RoleCache;
import com.randioo.randioo_server_base.template.IActionSupport;

@Controller
@PTAnnotation(MatchJoinGameRequest.class)
public class MatchJoinGameAction implements IActionSupport {

    @Autowired
    private MatchService matchService;

    @Override
    public void execute(Object data, IoSession session) {
        MatchJoinGameRequest request = (MatchJoinGameRequest) data;
        Role role = (Role) RoleCache.getRoleBySession(session);
        String roomId = request.getRoomId();
        matchService.joinGame(role, roomId);
    }

}
