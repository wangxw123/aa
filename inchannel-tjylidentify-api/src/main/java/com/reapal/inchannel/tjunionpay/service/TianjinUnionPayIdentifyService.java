package com.reapal.inchannel.tjunionpay.service;

import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import org.springframework.stereotype.Service;

/**
 * Created by liukai on 2016/11/23.
 */
@Service
public interface TianjinUnionPayIdentifyService {

    /**
     * Description: 三四六要素鉴权
     * @Author liukai
     * @Data 2016/11/24 9:39
     * @param req
     * @return
     */
    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req);


    /**
     * Description: 二要素鉴权
     * @Author liukai
     * @Data 2016/12/2 16:28
     * @param identifyRequest
     * @return
     */
    InchannelIdentifyResponse realName(InchannelIdentifyRequest identifyRequest);

}
