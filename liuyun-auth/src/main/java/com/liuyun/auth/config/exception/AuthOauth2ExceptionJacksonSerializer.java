package com.liuyun.auth.config.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/25 23:27
 **/
public class AuthOauth2ExceptionJacksonSerializer extends StdSerializer<AuthOauth2Exception> {

    private static final long serialVersionUID = 2535094107573591445L;

    protected AuthOauth2ExceptionJacksonSerializer() {
        super(AuthOauth2Exception.class);
    }

    @Override
    public void serialize(AuthOauth2Exception value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("code", value.getHttpErrorCode());
        gen.writeStringField("message", value.getMessage());
        // gen.writeStringField("message", value.getSummary());
        gen.writeEndObject();
    }
}
