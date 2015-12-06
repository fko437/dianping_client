package com.fukai.dianping_client.utils;

import com.fukai.dianping_client.R;

/**
 * Created by fukai on 2015/11/28.
 */
public class MyUtils {
    //返回值
    public static final int RequestCityCode = 2;
//    public static String[] navsort ={"美食","电影","酒店","KTV"
//                                        ,"自助餐","休闲娱乐","旅游"
//                                        ,"购物","都市丽人","母婴","女装"
//                                        ,"美妆","户外运动","生活服务","全部"};
    public static String[] navsort ={"美食","电影","酒店","KTV","自助餐","休闲娱乐","都市丽人","生活服务","全部"};
    public static int[] navSortImages = {R.drawable.food_select, R.drawable.movie_select,R.drawable.hotel_select
                                            ,R.drawable.ktv_select,R.drawable.hot_pot_select,R.drawable.fun_select
                                            ,R.drawable.hair_select,R.drawable.life_select,R.drawable.all};
    public static String[] allCategory ={"全部生活", "今日新单", "餐饮美食", "休闲娱乐", "电影", "生活服务",
            "摄影写真", "酒店", "旅游", "教育培训", "抽奖公益", "购物", "丽人", "食品茶酒"};

    public static int[] allCategoryImages = {R.drawable.sort_list_all, R.drawable.sort_list_youhui,R.drawable.sort_list_food
            ,R.drawable.sort_list_yule,R.drawable.sort_list_movie,R.drawable.sort_list_life
            ,R.drawable.sort_list_sheying,R.drawable.sort_list_hotel,R.drawable.sort_list_tour
            ,R.drawable.sort_list_edu,R.drawable.sort_list_lottery,R.drawable.sort_list_shop
            ,R.drawable.sort_list_beauty,R.drawable.sort_list_shipin};
    public static long allCategoryNumber[] =new long[allCategory.length + 5];
}
