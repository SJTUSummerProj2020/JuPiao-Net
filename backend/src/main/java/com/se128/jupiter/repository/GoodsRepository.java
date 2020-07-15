package com.se128.jupiter.repository;

import com.se128.jupiter.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Integer> {

    @Query(value = "select g from Goods g")
    List<Goods> getAllGoods();

    Goods getGoodsByGoodsId(Integer goodsId);

    List<Goods> getGoodsByGoodsType(Integer goodsType);

    @Query(value = "from Goods where name like %?1%")
    List<Goods> getGoodsByName(String name);

    List<Goods> findAllByNameLike(String name);

    @Query(nativeQuery = true, value = "select * from Goods order by goods_id limit 3")
    List<Goods> getGoodsByPageId(Integer pageId);

    @Query(value = "SELECT * FROM Goods WHERE goods_type = ?1",
            countQuery = "SELECT count(*) FROM goods WHERE goods_type = ?1",
            nativeQuery = true)
    Page<Goods> findByGoodsType(Integer goodsType, PageRequest pageable);

    @Query(nativeQuery = true,
            value = "select * from Goods where goods_type =?2 order by view_counter desc limit ?1")
    List<Goods> getPopularGoods(Integer number, Integer goodsType);

    @Query(nativeQuery = true,
            value = "select * from Goods order by view_counter desc limit ?1")
    List<Goods> getPopularGoodsInAll(Integer number);
}
