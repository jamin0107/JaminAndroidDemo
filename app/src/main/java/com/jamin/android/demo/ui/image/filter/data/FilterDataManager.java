package com.jamin.android.demo.ui.image.filter.data;

/**
 * Created by jamin on 2017/1/16.
 */

public class FilterDataManager {

    //单个滤镜组的滤镜
    public FilterInfo[] filter_infos = null;


    public FilterDataManager() {
        initFilter();
    }

    private void initFilter() {

        filter_infos = new FilterInfo[]{
                new FilterInfo(FilterType.ORIGINAL, "Original", 1),
                new FilterInfo(FilterType.PINK_THIN_FILTER, "S1", 2),
                new FilterInfo(FilterType.PINK_THICK_FILTER, "S2", 3),
                new FilterInfo(FilterType.GREEN_THIN_FILTER, "S3", 4),
                new FilterInfo(FilterType.RED_THICK_FILTER, "S4", 5),
                new FilterInfo(FilterType.YELLOW_THIN_FILTER, "S5", 6),
                new FilterInfo(FilterType.COFFEE_THIN_FILTER, "S6", 7),
                new FilterInfo(FilterType.FILM, "S7", 8),
                new FilterInfo(FilterType.LONELY, "S8", 9),
//        };
//
//        filter_infos[1] = new FilterInfo[]{
                new FilterInfo(FilterType.ORIGINAL, "Original", 10),
                new FilterInfo(FilterType.SINGLE_COLOR_PINK, "V1", 11),
                new FilterInfo(FilterType.VIOLET, "V2", 12),
                new FilterInfo(FilterType.VINTAGE, "V3", 13),
                new FilterInfo(FilterType.ROMANCE, "V4", 14),
                new FilterInfo(FilterType.OLDPHOTO, "V5", 15),
                new FilterInfo(FilterType.INKBLUE, "V6", 16),
                new FilterInfo(FilterType.DAMP, "V7", 17),
                new FilterInfo(FilterType.MOMENT, "V8", 18),
//        };
//
//        filter_infos[2] = new FilterInfo[]{
                new FilterInfo(FilterType.ORIGINAL, "Original", 19),
                new FilterInfo(FilterType.PURPLE_THIN_FILTER, "SC1", 20),
                new FilterInfo(FilterType.SUNSHINE, "SC2", 21),
                new FilterInfo(FilterType.LONDON, "SC3", 22),
                new FilterInfo(FilterType.DIANA, "SC4", 23),
                new FilterInfo(FilterType.INSTANT, "SC5", 24),
                new FilterInfo(FilterType.FOREST, "SC6", 25),
                new FilterInfo(FilterType.FAKE, "SC7", 26),
                new FilterInfo(FilterType.STANNUM, "SC8", 27),
//        };
//
//        filter_infos[3] = new FilterInfo[]{
                new FilterInfo(FilterType.ORIGINAL, "Original", 28),
                new FilterInfo(FilterType.PINK_MIDDLE_FILTER, "H1", 29),
                new FilterInfo(FilterType.GREEN_MIDDLE_FILTER, "H2", 30),
                new FilterInfo(FilterType.RED_MIDDLE_FILTER, "H3", 31),
                new FilterInfo(FilterType.BLUE_THIN_FILTER, "H4", 32),
                new FilterInfo(FilterType.BLUE_MIDDLE_FILTER, "H5", 33),
                new FilterInfo(FilterType.PURPLE_MIDDLE_FILTER, "H6", 34),
                new FilterInfo(FilterType.PURPLE_THICK_FILTER, "H7", 35),
                new FilterInfo(FilterType.YELLOW_MIDDLE_FILTER, "H8", 36),
//        };
//
//        filter_infos[4] = new FilterInfo[]{
                new FilterInfo(FilterType.ORIGINAL, "Original", 37),
                new FilterInfo(FilterType.BW, "BW1", 38),
                new FilterInfo(FilterType.SILENCE, "BW2", 39),
                new FilterInfo(FilterType.BLACK_AND_WHITE_MIDDLE_FILTER, "BW3", 40),
                new FilterInfo(FilterType.BLACK_AND_WHTIE_THICK, "BW4", 41),
                new FilterInfo(FilterType.VINTAGE_BLACK_WHITE, "BW5", 42),
                new FilterInfo(FilterType.BLACK_AND_WHTIE_THIN_FILTER, "BW6", 43),
                new FilterInfo(FilterType.SKETCH, "BW7", 44),
                new FilterInfo(FilterType.MONO_BLACK_WHITE, "BW8", 45)
        };


    }

}
