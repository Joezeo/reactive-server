# reactive_server

This project is a reactive web server template, using webflux + reactive-mongo as basic framework, a template for efficiently dealing with highly concurrent requests.  

Servers bases on this template from my own have `7000+` throughput under `10000+` concurrency with `1s` response time in average via pressure test by Jmeter.

### Usage
The most important class was `com.toocol.common.web.AbstractHttpAction`, one HttpAction represent one http request handler, and add annotation `com.toocol.common.web.ActionMapping` to register http uri mapping. For example:  
```java
@Slf4j
@AllArgsConstructor
// uri is represent to "/article/add_article"
@ActionMapping(module = "article", action = "add_article")
public class AddArticleAction extends AbstractHttpAction<String> implements IVessel {

    @Override
    public Mono<String> action(JSONObject param) throws Exception {
        ArticleAddress articleAddress = ArticleAddress.builder()
                .digest(UUID.randomUUID().toString())
                .url("https://www.guitarcs.article/1001")
                .build();
                
        return vessel().articleAddressRepo.insert(articleAddress)
                .thenReturn("Success");
    }

}
```
