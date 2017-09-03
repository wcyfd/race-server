package com.randioo.race_server.module.race.service;

import com.randioo.race_server.entity.bo.Role;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface RaceService extends ObserveBaseServiceInterface {
    public void createRace(Role role);

    void getRace(Role role);

}
