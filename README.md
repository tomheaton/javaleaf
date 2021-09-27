# javaleaf

java nanoleaf
Credit to [this article](https://documenter.getpostman.com/view/1559645/RW1gEcCH) on Postman for providing details on the API endpoints.


## example

| variable | value |
| --- | --- |
| host | The IP Address of the Nanoleaf product |
| accessToken | The Authorization Token of the Nanoleaf product |
| port | The Port of the Nanoleaf product (default: `16021`) |

```java
import dev.tomheaton.NanoleafClient;

NanoleafClient client = new NanoleafClient(":ipAddress:", 16021, ":accessToken:");

client.getEffects();
```


## todo:

- [ ] publish to GitHub Maven
- [x] add docstrings
- [ ] clean up http request code
- [ ] return data
- [ ] add promises
- [ ] add tests?
- [ ] improve `README.md`
- [x] add example