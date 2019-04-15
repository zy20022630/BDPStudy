package com.zy.study;

import java.io.Serializable;

/*
    用户日志记录信息
 */
public class Behavior implements Serializable {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 日志时间（格式：yyyy-MM-dd HH:mm:ss）
     */
    private String logTime;


    /**
     * （ipv-浏览）时间（时间戳）
     */
    private String ipvTime;
    /**
     * （ipv-浏览）商品类目ID（脱敏过的商品类目）
     */
    private String ipvCate;
    /**
     * （ipv-浏览）品牌ID（脱敏过的品牌词）
     */
    private String ipvBrand;


    /**
     * （cart-加入购物车）时间（时间戳）
     */
    private String cartTime;
    /**
     * （cart-加入购物车）商品类目ID（脱敏过的商品类目）
     */
    private String cartCate;
    /**
     * （cart-加入购物车）品牌ID（脱敏过的品牌词）
     */
    private String cartBrand;


    /**
     * （fav-喜欢）时间（时间戳）
     */
    private String favTime;
    /**
     * （fav-喜欢）商品类目ID（脱敏过的商品类目）
     */
    private String favCate;
    /**
     * （fav-喜欢）品牌ID（脱敏过的品牌词）
     */
    private String favBrand;


    /**
     * （buy-购买）时间（时间戳）
     */
    private String buyTime;
    /**
     * （buy-购买）商品类目ID（脱敏过的商品类目）
     */
    private String buyCate;
    /**
     * （buy-购买）品牌ID（脱敏过的品牌词）
     */
    private String buyBrand;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getIpvTime() {
        return ipvTime;
    }

    public void setIpvTime(String ipvTime) {
        this.ipvTime = ipvTime;
    }

    public String getIpvCate() {
        return ipvCate;
    }

    public void setIpvCate(String ipvCate) {
        this.ipvCate = ipvCate;
    }

    public String getIpvBrand() {
        return ipvBrand;
    }

    public void setIpvBrand(String ipvBrand) {
        this.ipvBrand = ipvBrand;
    }

    public String getCartTime() {
        return cartTime;
    }

    public void setCartTime(String cartTime) {
        this.cartTime = cartTime;
    }

    public String getCartCate() {
        return cartCate;
    }

    public void setCartCate(String cartCate) {
        this.cartCate = cartCate;
    }

    public String getCartBrand() {
        return cartBrand;
    }

    public void setCartBrand(String cartBrand) {
        this.cartBrand = cartBrand;
    }

    public String getFavTime() {
        return favTime;
    }

    public void setFavTime(String favTime) {
        this.favTime = favTime;
    }

    public String getFavCate() {
        return favCate;
    }

    public void setFavCate(String favCate) {
        this.favCate = favCate;
    }

    public String getFavBrand() {
        return favBrand;
    }

    public void setFavBrand(String favBrand) {
        this.favBrand = favBrand;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getBuyCate() {
        return buyCate;
    }

    public void setBuyCate(String buyCate) {
        this.buyCate = buyCate;
    }

    public String getBuyBrand() {
        return buyBrand;
    }

    public void setBuyBrand(String buyBrand) {
        this.buyBrand = buyBrand;
    }

}