# reactive_server

This project is a reactive web server template, using webflux + reactive-mongo as basic framework, a template for efficiently dealing with highly concurrent requests.  

Servers bases on this template from my own have `7000+` throughput under `10000+` concurrency with `1s` response time in average via pressure test by Jmeter.

### Usage
**1. Reactive Http action**  
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
                .url("https://www.toocol.article/1001")
                .build();
                
        return vessel().articleAddressRepo.insert(articleAddress)
                .thenReturn("Success");
    }

}
```

**2. Reactive mongodb repository**  
Just create a document class implement `com.toocol.common.database.IDocument` and add annotation `org.springframework.data.mongodb.core.mapping.Document`, then create a interface extends `com.toocol.common.database.mongo.AsyncMongoRepo`:  
```yml
spring:
  data:
    mongodb:
      host: localhost
      port: 27017
      database: your-database
```
```java
public interface ArticleAddressRepo extends AsyncMongoRepo<ObjectId, ArticleAddress> {

}
```
```java
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "article_address")
public class ArticleAddress implements IDocument {
    private static final long serialVersionUID = -4092392708753755910L;

    @Id
    private ObjectId id;

    /**
     * the digest of article, use md5 algorithm.
     */
    private String digest;

    /**
     * the url of article's HTML file.
     */
    private String url;

    public String getId() {
        return id.toHexString();
    }

    public void setId(String id) {
        this.id = new ObjectId(id);
    }

    public static ArticleAddressBuilder builder() {
        return new ArticleAddressBuilder();
    }

    public static final class ArticleAddressBuilder {
        private String digest;
        private String url;

        private ArticleAddressBuilder() {
        }

        public static ArticleAddressBuilder anArticleAddress() {
            return new ArticleAddressBuilder();
        }

        public ArticleAddressBuilder digest(String digest) {
            this.digest = digest;
            return this;
        }

        public ArticleAddressBuilder url(String url) {
            this.url = url;
            return this;
        }

        public ArticleAddress build() {
            ArticleAddress articleAddress = new ArticleAddress();
            articleAddress.setDigest(digest);
            articleAddress.setUrl(url);
            return articleAddress;
        }
    }
}
```

**3. Vessel**  
This template using extra Vessel to avoid the problem of circular dependency, you need add two class like this in your own project:  
```java
@Primary
@Component
public class Vessel extends DefaultVessel {

    /**
     * every bean in spring should add to there.
     */
    public ArticleAddressRepo articleAddressRepo;

}
```
```java
/**
* This interface was created to acquire Vessel instance
*/
public interface IVessel extends IBasisVessel<Vessel> {
    /**
     * @return vessel instance
     */
    @Override
    default Vessel vessel() {
        return AbstractVessel.get().as();
    }
}

```
