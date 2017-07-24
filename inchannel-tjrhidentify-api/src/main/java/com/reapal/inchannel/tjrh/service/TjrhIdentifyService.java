package com.reapal.inchannel.tjrh.service;

import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;

/**
 * Created by guoguangxiao on 17/1/23.
 */
public interface TjrhIdentifyService {

    /**
     * Description: 二要素鉴权
     * @Author guoguangxiao
     * @Data 17/1/23
     * @param identifyRequest
     * @return
     */
    InchannelIdentifyResponse realName(InchannelIdentifyRequest identifyRequest);

}
