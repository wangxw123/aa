package com.reapal.inchannel.szylidentify.service;

import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/3/29.
 */
@Service
public interface SzylIdentifyService {

    /**
     * 深圳银联三、四要素鉴权
     * @param req
     * @return
     */
    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req);

    /**
     *  深圳银联二要素鉴权
     * @param req
     * @return
     */
    public InchannelIdentifyResponse  realName(InchannelIdentifyRequest req);
}
