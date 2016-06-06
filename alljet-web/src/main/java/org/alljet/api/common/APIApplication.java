/*
 * Copyright (C), 2013-2016, 上海汽车集团股份有限公司
 * FileName: APIApplication.java
 * Author:   v_qinyuchen
 * Date:     2016年6月3日 下午4:38:08
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.api.common;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class APIApplication extends ResourceConfig {

    public APIApplication() {
        super();

        packages("org.alljet.api");
        // 输出工具提供者,继承了JacksonJsonProvider，实现了自己的构造方法，重写了isWriteable，getSize，writeTo方法
        // register(GsonProvider.class);
        // json转换工具,不知道具体干嘛，继承了JacksonFeature，重写了configure方法，JacksonFeature实现了Feature接口
        // register(CustomJsonProvider.class);
        // 返回结果,应该用来对rest返回的结果进行封装的实体类吧
        // register(ApiResult.class);

        // json转换工具,不知道具体干嘛，继承了JacksonFeature，重写了configure方法，JacksonFeature实现了Feature接口
        // Feature used to register Multipart providers.
        // register(MultiPartFeature.class);
        // Feature used to register Jackson JSON providers.
        // 是一个 feature ，用 Jackson JSON 的提供者来解释 JSON。
        // register(JacksonFeature.class);

        // 输出工具提供者
        // register(JacksonJsonProvider.class);

        // Logging 这个应该是日志吧
        // register(LoggingFilter.class);

        // register filters
        // 是 Spring filter 提供了 JAX-RS 和 Spring 请求属性之间的桥梁
        // register(RequestContextFilter.class);
        // register(LoggingResponseFilter.class);
        // register(CORSResponseFilter.class);

        // register exception mappers
        // register(GenericExceptionMapper.class);
        // register(AppExceptionMapper.class);
        // register(NotFoundExceptionMapper.class);

        // register features
        // register(JacksonFeature.class);
        register(MultiPartFeature.class);
    }
}
