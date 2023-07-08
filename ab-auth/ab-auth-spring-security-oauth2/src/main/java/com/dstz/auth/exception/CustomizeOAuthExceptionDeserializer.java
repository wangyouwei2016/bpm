package com.dstz.auth.exception;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.common.util.OAuth2Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自定义反序列化
 *
 * @author lightning
 */
public class CustomizeOAuthExceptionDeserializer extends StdDeserializer<OAuth2Exception> {
    public CustomizeOAuthExceptionDeserializer() {
        super(OAuth2Exception.class);
    }

    @Override
    public OAuth2Exception deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {

        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = jp.nextToken();
        }
        Map<String, Object> errorParams = new HashMap<String, Object>();
        for (; t == JsonToken.FIELD_NAME; t = jp.nextToken()) {
            // Must point to field name
            String fieldName = jp.getCurrentName();
            // And then the value...
            t = jp.nextToken();
            // Note: must handle null explicitly here; value deserializers won't
            Object value;
            if (t == JsonToken.VALUE_NULL) {
                value = null;
            }
            else if (t == JsonToken.START_ARRAY) {
                value = jp.readValueAs(List.class);
            } else if (t == JsonToken.START_OBJECT) {
                value = jp.readValueAs(Map.class);
            } else {
                value = jp.getText();
            }
            errorParams.put(fieldName, value);
        }

        Object errorCode = errorParams.get(OAuth2Exception.ERROR);
        String errorMessage = errorParams.get(OAuth2Exception.DESCRIPTION) != null ? errorParams.get(OAuth2Exception.DESCRIPTION).toString() : null;
        if (errorMessage == null) {
            errorMessage = errorCode == null ? "OAuth Error" : errorCode.toString();
        }

        OAuth2Exception ex;
        if (OAuth2Exception.INVALID_CLIENT.equals(errorCode)) {
            ex = new InvalidClientException(errorMessage);
        } else if (OAuth2Exception.UNAUTHORIZED_CLIENT.equals(errorCode)) {
            ex = new UnauthorizedClientException(errorMessage);
        } else if (OAuth2Exception.INVALID_GRANT.equals(errorCode)) {
            if (errorMessage.toLowerCase().contains("redirect") && errorMessage.toLowerCase().contains("match")) {
                ex = new RedirectMismatchException(errorMessage);
            } else {
                ex = new InvalidGrantException(errorMessage);
            }
        } else if (OAuth2Exception.INVALID_SCOPE.equals(errorCode)) {
            ex = new InvalidScopeException(errorMessage);
        } else if (OAuth2Exception.INVALID_TOKEN.equals(errorCode)) {
            ex = new InvalidTokenException(errorMessage);
        } else if (OAuth2Exception.INVALID_REQUEST.equals(errorCode)) {
            ex = new InvalidRequestException(errorMessage);
        } else if (OAuth2Exception.REDIRECT_URI_MISMATCH.equals(errorCode)) {
            ex = new RedirectMismatchException(errorMessage);
        } else if (OAuth2Exception.UNSUPPORTED_GRANT_TYPE.equals(errorCode)) {
            ex = new UnsupportedGrantTypeException(errorMessage);
        } else if (OAuth2Exception.UNSUPPORTED_RESPONSE_TYPE.equals(errorCode)) {
            ex = new UnsupportedResponseTypeException(errorMessage);
        } else if (OAuth2Exception.INSUFFICIENT_SCOPE.equals(errorCode)) {
            ex = new InsufficientScopeException(errorMessage, OAuth2Utils.parseParameterList((String) errorParams
                    .get("scope")));
        } else if (OAuth2Exception.ACCESS_DENIED.equals(errorCode)) {
            ex = new UserDeniedAuthorizationException(errorMessage);
        } else {
            ex = new OAuth2Exception(errorMessage);
        }

        Set<Map.Entry<String, Object>> entries = errorParams.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            if (!"error".equals(key) && !"error_description".equals(key)) {
                Object value = entry.getValue();
                ex.addAdditionalInformation(key, value == null ? null : value.toString());
            }
        }
        return ex;
    }
}
