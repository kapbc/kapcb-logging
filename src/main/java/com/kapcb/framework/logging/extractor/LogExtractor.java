package com.kapcb.framework.logging.extractor;

import com.kapcb.framework.common.constants.enums.IntegerPool;
import com.kapcb.framework.common.constants.enums.StringPool;
import com.kapcb.framework.logging.model.ILog;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a>Title: LogExtractor </a>
 * <a>Author: Kapcb <a>
 * <a>Description: LogExtractor <a>
 *
 * @author Kapcb
 * @version 1.0
 * @date 2021/11/27 12:49
 * @since 1.0
 */
@Slf4j
@UtilityClass
public class LogExtractor {

    private static final Map<Class<?>, Marshaller> MARSHALLER_MAP = new ConcurrentHashMap<>(4);

    /**
     * get HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Objects.nonNull(requestAttributes) ? requestAttributes.getRequest() : null;
    }

    /**
     * get HttpServletResponse
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Objects.nonNull(requestAttributes) ? requestAttributes.getResponse() : null;
    }

    public static Object getArgs(String[] paramNames, Object[] args) {
        if (ArrayUtils.isEmpty(paramNames) || ArrayUtils.isEmpty(args)) {
            return null;
        }
        Object target;
        if (Objects.equals(args.length, IntegerPool.ONE.value())) {
            target = args[IntegerPool.ZERO.value()];
        } else {
            target = args;
        }
        HttpServletRequest request = getRequest();
        if (Objects.nonNull(request) && StringUtils.isNotBlank(request.getContentType())) {
            String contentType = request.getContentType();
            if (MediaType.APPLICATION_XML_VALUE.equals(contentType)) {
                return xmlArgs(target);
            }
            if (MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
                return target;
            }
        }
        return appletArgs(paramNames, args);
    }

    /**
     * extractor request information to log
     *
     * @param log     ILog
     * @param headers String[]
     */
    public static void logHttpRequest(ILog log, String[] headers) {
        HttpServletRequest request = getRequest();
        if (Objects.nonNull(request)) {
            log.setClientIp(request.getRemoteAddr());
            log.setRequestUrl(request.getRequestURL().toString());
            log.setHost(request.getLocalAddr());
            log.setPort(request.getLocalPort());
            log.setHttpMethod(request.getMethod());
            if (ArrayUtils.isNotEmpty(headers)) {
                Map<String, String> requestHeadMap = new HashMap<>(4);
                for (String header : headers) {
                    requestHeadMap.put(header, request.getHeader(header));
                }
                log.setHeaders(requestHeadMap);
            }
        }
    }

    public static Object getResult(Object resp) {
        if (Objects.isNull(resp)) {
            return null;
        }
        HttpServletResponse response = getResponse();
        if (Objects.nonNull(response) && MediaType.APPLICATION_XML_VALUE.equals(response.getContentType())) {
            return xmlArgs(resp);
        } else {
            return resp;
        }
    }

    private static Object appletArgs(String[] paramNames, Object[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paramNames.length; i++) {
            String paramValue = StringPool.EMPTY_STRING.value();
            if (Objects.nonNull(args[i])) {
                paramValue = args[i].toString();
            }
            sb.append(paramNames[i]).append(StringPool.EQUALS.value()).append(paramValue).append(StringPool.AND.value());
        }
        if (Objects.equals(sb.lastIndexOf(StringPool.AND.value()), IntegerPool.MINUS_ONE.value())) {
            sb.deleteCharAt(sb.lastIndexOf(StringPool.AND.value()));
        }
        return sb.toString();
    }

    /**
     * parse xml data
     *
     * @param pointArgs Object
     * @return Object
     */
    private static Object xmlArgs(Object pointArgs) {
        if (Objects.nonNull(pointArgs)) {
            try (StringWriter stringWriter = new StringWriter()) {
                Marshaller marshaller = getMarshaller(pointArgs.getClass());
                if (Objects.nonNull(marshaller)) {
                    marshaller.marshal(pointArgs, stringWriter);
                    return stringWriter.toString().replace("standalone=\"yes\"", "");
                }
            } catch (JAXBException e) {
                log.error("log extractor parse xml data error, error message is : {}", e.getMessage());
            } catch (IOException e) {
                log.error("string writer get close error, error message is : {}", e.getMessage());
            }
        }
        return pointArgs;
    }

    private static Marshaller getMarshaller(Class<?> clazz) throws JAXBException {
        if (MARSHALLER_MAP.containsKey(clazz)) {
            return MARSHALLER_MAP.get(clazz);
        } else {
            Marshaller marshaller = JAXBContext.newInstance(clazz).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, StringPool.CHARACTER_SET_UTF_8_LOWER.value());
            MARSHALLER_MAP.put(clazz, marshaller);
            return marshaller;
        }
    }
}
