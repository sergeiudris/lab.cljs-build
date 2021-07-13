# hello-world

A bare-minimum Pedestal service that greets visitors.

## Usage

Launch the application using the command `lein run`.

Once the application has started, visit the following URLs via
a browser or cURL:

* <localhost:8080/hello>
* <localhost:8080/hello?name=You>

For example:

```sh
$ curl -i "localhost:8080/hello"
HTTP/1.1 200 OK
Date: Fri, 25 Apr 2014 14:43:34 GMT
Content-Type: text/plain
Transfer-Encoding: chunked
Server: Jetty(9.1.3.v20140225)

Hello, World!

$ curl "localhost:8080/hello?name=You"
Hello, You!
```

## Links
* [Other Pedestal examples](http://pedestal.io/samples)