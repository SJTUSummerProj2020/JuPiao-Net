package com.se128.jupiter.controller;

import com.se128.jupiter.entity.Goods;
import com.se128.jupiter.service.GoodsService;
import com.se128.jupiter.util.constant.Constant;
import com.se128.jupiter.util.logutils.LogUtil;
import com.se128.jupiter.util.msgutils.Msg;
import com.se128.jupiter.util.msgutils.MsgCode;
import com.se128.jupiter.util.msgutils.MsgUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GoodsController {

    private final GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @RequestMapping("/getGoodsByGoodsId")
    public Msg getGoodsByGoodsId(@RequestBody Map<String, String> params) {
        Integer goodsId = Integer.valueOf(params.get(Constant.GOODS_ID));
        LogUtil.info("getGoodsByGoodsId = " + goodsId);
        try {
            Goods goods = goodsService.getGoodsByGoodsId(goodsId);
            JSONObject data = JSONObject.fromObject(goods);
            return MsgUtil.makeMsg(MsgCode.DATA_SUCCESS, data);
        } catch (Exception e) {
            return MsgUtil.makeMsg(MsgCode.DATA_ERROR);
        }
    }

    @RequestMapping("/getGoodsByName")
    public Msg getGoodsByName(@RequestBody Map<String, String> params) {
        try {
            String name = params.get(Constant.NAME);
            LogUtil.info("getGoodsByName = " + name);
            List<Goods> goods = goodsService.getGoodsByName(name);
            JSONObject data = new JSONObject();
            JSONArray goodsList = JSONArray.fromObject(goods);
            data.put("goods", goodsList.toString());
            return MsgUtil.makeMsg(MsgCode.DATA_SUCCESS, data);
        } catch (Exception e) {
            return MsgUtil.makeMsg(MsgCode.DATA_ERROR);
        }
    }


    @RequestMapping("/getAllGoods")
    public Msg getAllGoods(@RequestBody Map<String, String> params) {
        try{
            Integer pageId = Integer.valueOf(params.get(Constant.PAGE_ID));
            Integer pageSize = Integer.valueOf(params.get(Constant.PAGE_SIZE));
            Integer goodsType = Integer.valueOf(params.get(Constant.GOODS_TYPE));

            Page<Goods> goodsPage = goodsService.getAllGoods(pageId, pageSize, goodsType);
            JSONObject data = new JSONObject();
            data.put("totalNum", goodsPage.getTotalElements());
            JSONArray goods = JSONArray.fromObject(goodsPage.getContent());
            data.put("goods", goods.toString());
            return MsgUtil.makeMsg(MsgCode.DATA_SUCCESS, data);
        } catch (Exception e){
            return MsgUtil.makeMsg(MsgCode.DATA_ERROR);
        }
    }

    @RequestMapping("/editGoods")
    public Msg editGoods(@RequestBody Goods goods) {
        LogUtil.info("editGoods");
        Goods goods1 = goodsService.editGoods(goods);
        try{
            JSONObject data = JSONObject.fromObject(goods1);
            return MsgUtil.makeMsg(MsgCode.EDIT_SUCCESS, data);
        } catch (Exception e){
            return MsgUtil.makeMsg(MsgCode.EDIT_ERROR);
        }
    }

    @RequestMapping("/addGoods")
    public Msg addGoods(@RequestBody Goods goods) {
        LogUtil.info("addGoods");
        goods.setBuyCounter(0);
        goods.setViewCounter(0);
        Goods goods1 = goodsService.addGoods(goods);
        try {
            JSONObject data = JSONObject.fromObject(goods);
            return MsgUtil.makeMsg(MsgCode.ADD_SUCCESS, data);
        } catch (Exception e){
            return MsgUtil.makeMsg(MsgCode.ADD_ERROR);
        }
    }

    @RequestMapping("/deleteGoodsByGoodsId")
    public Msg deleteGoodsByGoodsId(@RequestBody Map<String, String> params) {
        Integer goodsId = Integer.valueOf(params.get(Constant.GOODS_ID));
        LogUtil.info("deleteGoodsWithGoodsId = " + goodsId);
        goodsService.deleteGoodsByGoodsId(goodsId);
        return MsgUtil.makeMsg(MsgCode.DELETE_SUCCESS);
    }

    @RequestMapping("/getPopularGoods")
    public Msg getPopularGoods(@RequestBody Map<String, String> params) {
        Integer number = Integer.valueOf(params.get(Constant.NUMBER));
        JSONObject data = new JSONObject();
        for (Integer goodsType = -1; goodsType < Constant.NUMBER_OF_TYPE; goodsType++) {
            List<Goods> goods = goodsService.getPopularGoods(number, goodsType);
           try {
                JSONArray goodsList = JSONArray.fromObject(goods);
                if(goodsType == -1)
                {
                    data.put("itemAll", goodsList.toString());
                }else
                {
                    data.put("item"+goodsType.toString(), goodsList.toString());
                }
            } catch (Exception e){
                return MsgUtil.makeMsg(MsgCode.DATA_ERROR);
            }
        }
        return MsgUtil.makeMsg(MsgCode.DATA_SUCCESS, data);
    }


}
