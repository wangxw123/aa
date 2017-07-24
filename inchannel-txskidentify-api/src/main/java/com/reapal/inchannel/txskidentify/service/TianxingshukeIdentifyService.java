package com.reapal.inchannel.txskidentify.service;

import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import org.springframework.stereotype.Service;

/**
 * @The author  zhongyuting
 * 有盾三.四要素鉴权
 */
@Service
public interface TianxingshukeIdentifyService {

    //三 四要鉴权
    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req);

    //二要素鉴权
    public InchannelIdentifyResponse realName(InchannelIdentifyRequest req);

    //身份证返回照片
    public  String savePictoServer(InchannelIdentifyRequest req);
}

