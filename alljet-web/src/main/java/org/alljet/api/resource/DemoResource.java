/*
 * Copyright (C), 2013-2016, 上海汽车集团股份有限公司
 * FileName: DemoResource.java
 * Author:   v_qinyuchen
 * Date:     2016年6月6日 下午2:29:23
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.alljet.api.entity.DemoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Scope("prototype")
// 保证是多例的
@Component
// 不注入到spring中前台的json是不能转换为实体对象的，把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>
@Path("v2/resource/demo")
public class DemoResource {
	
	/** log */
    private static final Logger log = LoggerFactory.getLogger(DemoResource.class);

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String get(@QueryParam("username") String username) {
    	log.debug("{\"get\":\"" + username + "\"}");
        return "{\"get\":\"" + username + "\"}";
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("id/{id}")
    public String post(@PathParam("id") String id) {
    	log.debug("{\"post\":\"" + id + "\"}");
        return "{\"post\":\"" + id + "\"}";
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    public String put(DemoEntity student) {
    	log.debug("{\"put\":\"" + student.getTestName() + "\"}");
        return "{\"put\":\"" + student.getTestName() + "\"}";
    }

    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    @Path("del/{username}")
    public String delete(@PathParam("username") String username) {
    	log.debug("{\"del\":\"" + username + "\"}");
        return "{\"del\":\"" + username + "\"}";
    }

}
