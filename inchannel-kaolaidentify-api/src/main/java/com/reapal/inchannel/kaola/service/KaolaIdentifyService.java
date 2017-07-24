package com.reapal.inchannel.kaola.service;

import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by huangg on 2015/3/24.
 */
@Service
public interface KaolaIdentifyService {

    /**
     * Description: 二要素鉴权
     * @Author liukai
     * @Data 2016/12/2 16:28
     * @param identifyRequest
     * @return
     */
    public InchannelIdentifyResponse realName(InchannelIdentifyRequest identifyRequest);

    /**
     * Description: 三四六要素鉴权
     * @Author liukai
     * @Data 2016/12/2 16:28
     * @param identifyRequest
     * @return
     */
    public InchannelIdentifyResponse identify(InchannelIdentifyRequest identifyRequest);

    /**
     * Description: 二要素返照片
     * @Author liukai
     * @Data 2016/12/20 16:52
     * @param certId
     * @param usrName
     * @param cardNo
     * @param phone
     * @return
     */
    Map lakalaChannel(String certId, String usrName,String cardNo, String phone);


    /**
     * 身份证查询照片
     * @param identifyRequest
     * @return
     */
    public InchannelIdentifyResponse realNamePhoto(InchannelIdentifyRequest identifyRequest);


}
