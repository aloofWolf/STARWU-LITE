package starwu.lite.web.multipart;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * 
    * @ClassName: MultipartInterceptor
    * @Description: MultipartInterceptor
    * @author Lone Wolf
    * @date 2023年12月15日
 */
@Component
public class MultipartInterceptor extends AbstractJackson2HttpMessageConverter {
  
    public MultipartInterceptor(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
    }
  
    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return false;
    }
  
    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return false;
    }
  
    @Override
    protected boolean canWrite(MediaType mediaType) {
        return false;
    }
}
