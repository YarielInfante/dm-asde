import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created by yarielinfante on 11/16/16.
 */
public class MainTest {

    public static void main(String... args){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjo4Mjk0NDA3OTcwOjEyMzQ1Njc4OTAxMTIzNCIsImV4cCI6MTQ4MTcyOTI1MX0.5vyZa5fS09AVyz75nFzE2YR9Q2irnEBb12ljni9eOuc");
        HttpEntity<String> entity = new HttpEntity<>("", responseHeaders);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);

        RestTemplate template = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
//
//        ResponseEntity<InitialGeneral> exchange = template.exchange("http://172.19.1.103:8081/api/neo/initial-load",
//                HttpMethod.GET,
//                entity,
//                new ParameterizedTypeReference<InitialGeneral>() {
//                }, Collections.emptyMap());
    }
}
