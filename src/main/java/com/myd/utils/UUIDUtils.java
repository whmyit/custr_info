package com.myd.utils;

import java.util.UUID;

/**
 *
 * Copyright: Copyright (c) 2019 Sinosoft Co.,Ltd.
 *
 * @Description: UUID工具类
 *
 * @author: WuDi
 * @date: 2019年4月17日 下午3:45:24
 */
public class UUIDUtils {

    /**
     *
     * @Description: 获取32位UUID
     *
     * @return：String
     * @author: WuDi
     * @date: 2019年4月17日 下午3:45:58
     */
    public static final String getUUID() {
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString().replace("-", "");
        return uid;
    }

    public static void main(String[] args) {
        System.out.println(getUUID());
    }

}
