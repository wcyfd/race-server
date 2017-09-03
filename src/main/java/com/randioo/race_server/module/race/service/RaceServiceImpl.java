package com.randioo.race_server.module.race.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.randioo.race_server.entity.bo.Role;
import com.randioo.race_server.entity.po.RaceEntity;
import com.randioo.race_server.util.JedisUtils;
import com.randioo.race_server.util.JedisUtils3;
import com.randioo.randioo_server_base.service.ObserveBaseService;

@Service("raceService")
public class RaceServiceImpl extends ObserveBaseService implements RaceService {

    @Override
    public void createRace(Role role) {
        List<RaceEntity> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            RaceEntity raceEntity = new RaceEntity();
            raceEntity.setRaceId(i);
            raceEntity.setName("比赛" + i);

            list.add(raceEntity);
        }

        JedisUtils.setObjectList("races", list, 0);

    }

    @Override
    public void getRace(Role role) {
        List<RaceEntity> list = JedisUtils.getObjectList(RaceEntity.class, "races");
        System.out.println(list);
    }

}
