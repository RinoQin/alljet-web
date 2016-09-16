/*
 * Copyright (C), 2013-2016, 上海汽车集团股份有限公司
 * FileName: Test.java
 * Author:   v_qinyuchen
 * Date:     2016年6月29日 下午4:14:55
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TestHOUHOU {

    // @Test
    public void testAppLinkJson() throws URISyntaxException, ClientProtocolException, IOException {
        String url = "http://www.sit.com/cxb/csctspz/index.shtml";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        ResponseHandler<JsonObject> rh = new ResponseHandler<JsonObject>() {
            @Override
            public JsonObject handleResponse(final HttpResponse response) throws IOException {
                StatusLine statusLine = response.getStatusLine();
                HttpEntity entity = response.getEntity();
                if (statusLine.getStatusCode() >= 300) {
                    throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
                }
                if (entity == null) {
                    throw new ClientProtocolException("Response contains no content");
                }
                Gson gson = new GsonBuilder().create();
                ContentType contentType = ContentType.getOrDefault(entity);
                Charset charset = contentType.getCharset();
                Reader reader = new InputStreamReader(entity.getContent(), charset);
                return gson.fromJson(reader, JsonObject.class);
            }
        };
        JsonObject response = httpclient.execute(httpGet, rh);
        String errorCode = response.get("errorCode").getAsString();
        System.out.println("errorCode:{" + errorCode + "}");
        JsonArray result = response.getAsJsonArray("result");
        Iterator<JsonElement> jumpList = result.iterator();
        while (jumpList.hasNext()) {
            JsonElement jump = jumpList.next();
            JsonObject jOb = jump.getAsJsonObject();
            String title = jOb.get("title").getAsString();
            String key = jOb.get("key").getAsString();
            String value = jOb.get("value").getAsString();
            System.out.println("jump:{" + jump + "}");
            System.out.println("title:" + title + "," + "key:" + key + "," + "value:" + value + "");
        }
        System.out.println("result:{" + result + "}");

    }

    @Test
    public void testListCompare() {
        SaiYaPerson kakaluote = new SaiYaPerson();
        SaiYaPerson beijita = new SaiYaPerson();
        SaiYaPerson sunwufan = new SaiYaPerson();
        SaiYaPerson wutiankesi = new SaiYaPerson();
        SaiYaPerson telankesi = new SaiYaPerson();
        SaiYaPerson sunwutian = new SaiYaPerson();
        SaiYaPerson fang = new SaiYaPerson();

        kakaluote.setName("卡卡罗特");
        kakaluote.setPower(10000000);
        beijita.setName("贝吉塔");
        beijita.setPower(9800000);
        sunwufan.setName("孙悟饭");
        sunwufan.setPower(5000000);
        wutiankesi.setName("悟天克斯");
        wutiankesi.setPower(4000000);
        telankesi.setName("特兰克斯");
        telankesi.setPower(800000);
        sunwutian.setName("孙悟天");
        sunwutian.setPower(760000);
        fang.setName("芳");
        fang.setPower(60000);

        List<SaiYaPerson> saiYaPersons = new ArrayList<SaiYaPerson>();

        saiYaPersons.add(telankesi);
        saiYaPersons.add(beijita);
        saiYaPersons.add(fang);
        saiYaPersons.add(sunwutian);
        saiYaPersons.add(wutiankesi);
        saiYaPersons.add(kakaluote);
        saiYaPersons.add(sunwufan);
        Gson gson = new Gson();
        System.out.println(gson.toJson(saiYaPersons));
        Collections.sort(saiYaPersons);
        System.out.println(gson.toJson(saiYaPersons));
    }

}
