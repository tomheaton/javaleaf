# javaleaf

java nanoleaf

## example

| variable | value |
| --- | --- |
| host | The IP Address of the Nanoleaf product |
| accessToken | The Authorization Token of the Nanoleaf product |
| port | The Port of the Nanoleaf product (default: `16021`) |

```java
import dev.tomheaton.NanoleafClient;

NanoleafClient client = new NanoleafClient("192.168.4.20", "zbp3aHIDoj0Ox2iAr857WMFck58mOBaL", 16021);

client.getEffects();
```


## todo

- [ ] publish to GitHub Maven
- [x] add docstrings
- [ ] clean up http request code
- [ ] return data
- [ ] add promises
- [ ] add tests?
- [ ] improve `README.md`
- [x] add example
- [ ] add apiEndpoint to NanoleafClient constructor (allows users to change the API endpoint for Nanoleaf if, say, a new API version was released). 
- [ ] add auth generation

## credits

- Jack Lafond's [nanoleaf.js](https://github.com/jacc/nanoleaf.js/) for the idea and base structure
- [this article](https://documenter.getpostman.com/view/1559645/RW1gEcCH) on Postman for providing details on the API endpoints
