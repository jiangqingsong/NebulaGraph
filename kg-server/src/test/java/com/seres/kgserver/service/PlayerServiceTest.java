package com.seres.kgserver.service;

import com.seres.kgserver.dao.PlayerDao;
import com.seres.kgserver.pojo.demo_basketballplayer.Player;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.nebula.contrib.ngbatis.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param
 * @author jiangqs
 * @version v1.0
 * @Description:
 * @return
 * @date 2024/2/2 10:20
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PlayerServiceTest {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerDao playerDao;

    @Test
    public void testGetAllEnable() {
        Player player = playerDao.selectById("import-player-003");
        log.info(player.toString());

        Map<String, Object> map = new HashMap<>();
        map.put("name", "import-player-001");
        Long count = playerDao.countByMap(map);
        log.info("total: {}", count);

        //分页
        Page<Player> page = new Page<>(1, 2);
        List<Player> list = playerDao.selectPage(page);
        log.info("page total: {}, currentRows: {}, pageNo:{}, pageSize{}", page.getTotal(), page.getRows().toString(), page.getPageNo(), page.getPageSize());

        //修改
        Player p1 = new Player();
        p1.setId("import-player-003");
        p1.setName("import-player-003-01");
        p1.setAge(33);
        playerDao.updateById(p1);

        //插入
        Player p2 = new Player();
        p2.setId("0202-001");
        p2.setName("Sun Yue");
        p2.setAge(38);
        playerDao.insert(p2);

        Player p3 = new Player("0202-003", "Yao Ming", 55);
        Player p4 = new Player("0202-004", "Li Nan", 55);
        Player p5 = new Player("0202-004", "Yao Ming", 56);

        List<Player> ps = new ArrayList<>();
        ps.add(p3);
        ps.add(p4);
        ps.add(p5);
        playerDao.insertBatch(ps);
    }

    @Test
    public void stringLikeTest() {
        Player player = new Player();
        player.setName("Yao");
        //只和属性值有关
        List<Player> list = playerDao.selectBySelectiveStringLike(player);
        list.forEach(p -> {
            System.out.println(p.toString());
        });
    }

    @Test
    void selectPlayer() {
        List<Player> players = playerDao.selectPlayer();
        players.forEach(p -> System.out.println(p));

    }

    @Test
    void selectAllTags() {
        List<Object> players = playerDao.selectAllTags();
        players.forEach(p -> System.out.println(p));

    }


}
