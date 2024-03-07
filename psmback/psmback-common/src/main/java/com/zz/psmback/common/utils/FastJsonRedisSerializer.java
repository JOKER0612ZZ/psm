package com.zz.psmback.common.utils;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * FastJsonRedisSerializer是一个实现了RedisSerializer接口的类，用于将对象序列化为字节数组并反序列化为对象。
 * 它利用FastJson库来进行序列化和反序列化操作。
 * @param <T> 序列化/反序列化的对象类型
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T>
{
    // 默认字符集为UTF-8
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    // 要序列化/反序列化的对象类型
    private final Class<T> clazz;

    // 静态代码块，用于配置FastJson库以支持自动类型转换
    static {
        // 启用FastJson的自动类型支持
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    /**
     * 构造函数，初始化要序列化/反序列化的对象类型。
     *
     * @param clazz 要序列化/反序列化的对象类型
     */
    public FastJsonRedisSerializer(Class<T> clazz) {
        this.clazz = clazz;
    }


    /**
     * 将对象序列化为字节数组。
     * @param t 要序列化的对象
     * @return 序列化后的字节数组
     * @throws SerializationException 如果序列化过程中发生异常
     */
    @Override
    public byte[] serialize(T t) throws SerializationException
    {
        if (t == null)
        {
            return new byte[0];
        }
        // 使用FastJson将对象转换为JSON字符串，并指定序列化特性为包含类名
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    /**
     * 将字节数组反序列化为对象。
     * @param bytes 要反序列化的字节数组
     * @return 反序列化后的对象
     * @throws SerializationException 如果反序列化过程中发生异常
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException
    {
        if (bytes == null || bytes.length <= 0)
        {
            return null;
        }
        // 将字节数组转换为字符串
        String str = new String(bytes, DEFAULT_CHARSET);

        // 使用FastJson将JSON字符串反序列化为指定类型的对象
        return JSON.parseObject(str, clazz);
    }

    /**
     * 获取Java类型的描述。
     *
     * @param clazz 类型的Class对象
     * @return Java类型的描述
     */
    protected TypeReference<T> getJavaType(Class<?> clazz)
    {
        // 使用FastJson的TypeReference类获取泛型类型的描述
        return new TypeReference<T>(clazz) {};
    }
}


