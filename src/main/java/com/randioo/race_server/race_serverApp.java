package com.randioo.race_server;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.keepalive.KeepAliveFilter;

import com.randioo.mahjong_public_server.protocol.ClientMessage.CS;
import com.randioo.mahjong_public_server.protocol.Heart.CSHeart;
import com.randioo.mahjong_public_server.protocol.Heart.HeartRequest;
import com.randioo.mahjong_public_server.protocol.Heart.HeartResponse;
import com.randioo.mahjong_public_server.protocol.Heart.SCHeart;
import com.randioo.mahjong_public_server.protocol.ServerMessage.SC;
import com.randioo.race_server.handler.HeartTimeOutHandler;
import com.randioo.race_server.module.race.service.RaceService;
import com.randioo.race_server.module.race.service.RaceServiceImpl;
import com.randioo.race_server.util.JedisUtils;
import com.randioo.randioo_platform_sdk.RandiooPlatformSdk;
import com.randioo.randioo_server_base.config.ConfigLoader;
import com.randioo.randioo_server_base.config.GlobleArgsLoader;
import com.randioo.randioo_server_base.config.GlobleMap;
import com.randioo.randioo_server_base.config.GlobleXmlLoader;
import com.randioo.randioo_server_base.heart.ProtoHeartFactory;
import com.randioo.randioo_server_base.init.GameServerInit;
import com.randioo.randioo_server_base.init.LogSystem;
import com.randioo.randioo_server_base.sensitive.SensitiveWordDictionary;
import com.randioo.randioo_server_base.utils.SpringContext;

import redis.clients.jedis.JedisPool;

/**
 * Hello world!
 *
 */
public class race_serverApp {

    /**
     * @param args
     * @author wcy 2017年8月17日
     */
    public static void main(String[] args) {

        // GlobleXmlLoader.init("./server.xml");
        // GlobleArgsLoader.init(args);
        //
        // LogSystem.init(race_serverApp.class);
        //
        // ConfigLoader.loadConfig("com.randioo.majiang_collections_server.entity.file",
        // "./config.zip");
        //
        // SensitiveWordDictionary.readAll("./sensitive.txt");

        SpringContext.initSpringCtx("ApplicationContext.xml");
        RaceService raceService = new RaceServiceImpl();
        long t1 = System.currentTimeMillis();
        raceService.createRace(null);
        raceService.getRace(null);
        long t2 = System.currentTimeMillis();
        System.out.println(t2-t1);

        // // 平台接口初始化
        // RandiooPlatformSdk randiooPlatformSdk =
        // SpringContext.getBean(RandiooPlatformSdk.class);
        // randiooPlatformSdk.setDebug(GlobleMap.Boolean(GlobleConstant.ARGS_PLATFORM));
        // randiooPlatformSdk.setActiveProjectName(GlobleMap.String(GlobleConstant.ARGS_PLATFORM_PACKAGE_NAME));
        //
        // GameServerInit gameServerInit = ((GameServerInit)
        // SpringContext.getBean(GameServerInit.class));
        // // 设置CS
        // gameServerInit.setMessageLite(CS.getDefaultInstance());
        //
        // // 心跳工厂
        // ProtoHeartFactory protoHeartFactory = new ProtoHeartFactory();
        // protoHeartFactory.setHeartRequest(CS.newBuilder().setHeartRequest(HeartRequest.newBuilder()).build());
        // protoHeartFactory.setHeartResponse(SC.newBuilder().setHeartResponse(HeartResponse.newBuilder()).build());
        // protoHeartFactory.setScHeart(SC.newBuilder().setSCHeart(SCHeart.newBuilder()).build());
        // protoHeartFactory.setCsHeart(CS.newBuilder().setCSHeart(CSHeart.newBuilder()).build());
        //
        // HeartTimeOutHandler heartTimeOutHandler =
        // SpringContext.getBean(HeartTimeOutHandler.class);
        // gameServerInit.setKeepAliveFilter(
        // new KeepAliveFilter(protoHeartFactory, IdleStatus.READER_IDLE,
        // heartTimeOutHandler, 5, 10));
        // gameServerInit.start();
        //
        // // LiteHttpServer server = new LiteHttpServer();
        //
        // GlobleMap.putParam(GlobleConstant.ARGS_LOGIN, true);

    }
}
