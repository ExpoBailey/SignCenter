package com.explorer.bailey.sc.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhuwj
 * @description
 * @since 2019/1/26
 */
public class LottoUtils {

    public static int probableModel(double[] probables) {
        // 计算出所有概率的总和
        double sumRate = 0;
        for (double probable : probables) {
            sumRate += probable;
        }

        if (sumRate <= 0) {
            return -1;
        }

        // 计算出所有奖品的概率占比区段集合，这个集合中最大值为1
        List<Double> sortRateList = new ArrayList<>();

        // 概率所占比例
        double rate = 0;

        for (double prob : probables) {
            rate += prob;
            sortRateList.add(rate / sumRate);
        }

        // 生成一个0-1之间的随机数，放入这个sortRateList中排序，得出的索引位置就是中奖奖品的索引。
        double random = Math.random();
        sortRateList.add(random);
        Collections.sort(sortRateList);
        return sortRateList.indexOf(random);
    }

}
