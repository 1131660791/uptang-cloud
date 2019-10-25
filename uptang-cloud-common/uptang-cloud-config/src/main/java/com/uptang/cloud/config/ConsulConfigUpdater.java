package com.uptang.cloud.config;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-08
 */
public class ConsulConfigUpdater {
    private static final String CONSUL_CONFIG_LOCAL_PATH = "/configs";
    private static final String CONSUL_CONFIG_BASE_URL = "http://xfs_consul.weave.local:8500/v1/kv/uptang_configs/";
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json;charset=UTF-8");
    private static final Charset DEFAULT_CHAR_SET = StandardCharsets.UTF_8;

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    public static void main(String[] args) {
        // 获取目录下所有配置文件
        URL resource = ConsulConfigUpdater.class.getResource(CONSUL_CONFIG_LOCAL_PATH);
        File[] configFiles = new File(resource.getPath()).listFiles();
        if (Objects.isNull(configFiles) || configFiles.length <= 0) {
            return;
        }

        Arrays.stream(configFiles).forEach(file -> {
            String configUrl = file.getName() + "?dc=dc1";
            try {
                // #1 先删除
                HTTP_CLIENT.newCall(new Request.Builder().url(CONSUL_CONFIG_BASE_URL + configUrl).delete().build()).execute();

                // #2 再增加
                Request request = new Request.Builder()
                        .url(CONSUL_CONFIG_BASE_URL + configUrl)
                        .put(RequestBody.create(MEDIA_TYPE, FileUtils.readFileToString(file, DEFAULT_CHAR_SET)))
                        .build();
                Response response = HTTP_CLIENT.newCall(request).execute();
                if (response.isSuccessful()) {
                    System.out.println("增加配置(" + file.getName() + ")成功！");
                } else {
                    System.out.println("增加配置(" + file.getName() + ")失败！");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
