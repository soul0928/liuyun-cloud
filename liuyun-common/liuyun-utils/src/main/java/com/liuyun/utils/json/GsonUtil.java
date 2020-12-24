package com.liuyun.utils.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Gson 工具类
 *
 * @author wangdong
 * @date 2020/12/24 2:40 下午
 *
 **/
public class GsonUtil {

    private GsonUtil() {}

    /**
     * 指定 序列化 反序列化 时间格式
     */
    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 过滤空值
     */
    private static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                // 序列化时间
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                        new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS)))
                )
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                        new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(YYYY_MM_DD)))
                )
                // 反序列化时间
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> {
                    String datetime = json.getAsJsonPrimitive().getAsString();
                    return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS));
                }).registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> {
                    String datetime = json.getAsJsonPrimitive().getAsString();
                    return LocalDate.parse(datetime, DateTimeFormatter.ofPattern(YYYY_MM_DD));
                })
                //当Map的key为复杂对象时,需要开启该方法
                .enableComplexMapKeySerialization()
                //当字段值为空或null时，依然对该字段进行转换
                //.serializeNulls()
                //打开Export注解，但打开了这个注解,副作用，要转换和不转换都要加注解
                //.excludeFieldsWithoutExposeAnnotation()
                //序列化日期格式  "yyyy-MM-dd"
                .setDateFormat(YYYY_MM_DD_HH_MM_SS)
                //自动格式化换行
                .setPrettyPrinting()
                //防止特殊字符出现乱码
                .disableHtmlEscaping()
                .create();
    }

    /**
     * 将对象转成json格式
     *
     * @param obj obj
     * @return java.lang.String
     * @author 王栋
     * @date 2019/9/24 19:21
     **/
    public static String toJson(Object obj) {
        String json = null;
        if (GSON != null) {

            json = GSON.toJson(obj);
            /*if (StringUtils.isNoneBlank(json)) {
                json = StringEscapeUtils.unescapeJavaScript(json);
            }*/

        }
        return json;
    }

    /**
     * 将json转成特定的cls的对象
     *
     * @param json json
     * @param cls cls
     * @return T
     * @author 王栋
     * @date 2019/9/24 19:23
     **/
    public static <T> T toBean(String json, Class<T> cls) {
        T t = null;
        if (GSON != null) {
            //传入json对象和对象类型,将json转成对象
            t = GSON.fromJson(json, cls);
        }
        return t;
    }

    /**
     * toList
     *
     * @param json {@link String}
     * @param cls {@link Class}
     * @return java.util.List<T>
     * @author wangdong
     * @date 2020/12/24 2:41 下午
     **/
    public static <T> List<T> toList(String json, Class<T> cls) {
        List<T> list = new ArrayList<>(16);
        if (GSON != null) {
            JsonArray array = JsonParser.parseString(json).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(GSON.fromJson(elem, cls));
            }
        }
        return list;
    }

    /**
     * toListMaps
     *
     * @param json {@link String}
     * @return java.util.List<java.util.Map<java.lang.String,T>>
     * @author wangdong
     * @date 2020/12/24 2:41 下午
     **/
    public static <T> List<Map<String, T>> toListMaps(String json) {
        List<Map<String, T>> list = null;
        if (GSON != null) {
            list = GSON.fromJson(json,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * toMap
     *
     * @param json {@link String}
     * @return java.util.Map<java.lang.String,T>
     * @author wangdong
     * @date 2020/12/24 2:41 下午
     **/
    public static <T> Map<String, T> toMap(String json) {
        Map<String, T> map = null;
        if (GSON != null) {
            map = GSON.fromJson(json, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}
