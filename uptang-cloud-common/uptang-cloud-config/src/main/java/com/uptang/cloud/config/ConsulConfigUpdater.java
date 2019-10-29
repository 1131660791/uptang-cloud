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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-08
 */
public class ConsulConfigUpdater {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json;charset=UTF-8");
    private static final Charset DEFAULT_CHAR_SET = StandardCharsets.UTF_8;

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    private static final Map<AppEnv, String> CONSUL_APIS = new HashMap<AppEnv, String>() {{
        put(AppEnv.DEV, "http://xfs_consul.weave.local:8500/v1/kv");
        put(AppEnv.FAT, "http://xfs_consul.weave.local:8500/v1/kv");
        put(AppEnv.UAT, "http://xfs_consul.weave.local:8500/v1/kv");
        put(AppEnv.PRO, "http://114.116.96.98:8500/v1/kv");
    }};

    /**
     * 需要初始化的环境
     */
    private static final AppEnv APP_ENV = AppEnv.PRO;

    public static void main(String[] args) {
        // 获取目录下所有配置文件
        URL resource = ConsulConfigUpdater.class.getResource("/configs");
        File[] configFiles = new File(resource.getPath()).listFiles();
        if (Objects.isNull(configFiles) || configFiles.length <= 0) {
            return;
        }

        final String consulApi = CONSUL_APIS.get(APP_ENV) + "/uptang_configs/";
        Arrays.stream(configFiles).filter(file -> file.getName().endsWith(APP_ENV.name() + ".yml")).forEach(file -> {
            String configUrl = file.getName() + "?dc=dc1";
            try {
                // #1 先删除
                HTTP_CLIENT.newCall(new Request.Builder().url(consulApi + configUrl).delete().build()).execute();

                // #2 再增加
                Request request = new Request.Builder()
                        .url(consulApi + configUrl)
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

    enum AppEnv {
        DEV, FAT, UAT, PRO
    }
}
