package vn.hoidanit.jobhunter.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import vn.hoidanit.jobhunter.domain.RestResponse;

@ControllerAdvice
public class FormatResponse implements ResponseBodyAdvice<Object> {
  @Override
  public boolean supports(
      MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(
      Object body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response) {

    HttpServletResponse httpResponse = ((ServletServerHttpResponse) response).getServletResponse();
    int status = httpResponse.getStatus();
    RestResponse<Object> restResponse = new RestResponse<>();
    restResponse.setStatus(status);
    System.out.println("Calling to Format Response");

    if (body instanceof String) {
      return body;
    }

    if (status >= 400) {
      return body;
    } else {
      restResponse.setMessage("Fetched api success");
      restResponse.setData(body);
    }

    return restResponse;
  }
}
