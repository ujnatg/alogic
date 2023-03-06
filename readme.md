# Assignment: Alogic

- Test scenarios are raw, because target service is behind firewall(cloudflare) which additionally
protect using captcha. 
- On the page https://helloacm.com/tools/unix-timestamp-converter/ method body listed. 
I was able to apply white-box technic as well as blackbox. 

## White-box
```
if (is_numeric($s)) {
$data = date("Y-m-d h:i:s", (integer)$s);
} else {
$data = strtotime($s);
}
```
we can see that is_numeric() used as a switcher. According to php docs,
https://www.php.net/manual/en/function.is-numeric.php, I found that some edge cases like
hex, binary, power, decimal are return true. Casting to integer could cause loosing 
some precision. Date format "Y-m-d h:i:s" is bad, it does not store am\pm, so same values
represent different time, ex.: 1970-01-01 4:00:00 is returned for both 4:00 and 16:00 
strtotime($s) accept not only formats like '1970-01-01 4:00:00', but also "now", "+1 day",
"last Monday", etc. such inputs should also cause 4xx error. 

Because there are enough requirements for this API, it is very hard to determine which
behavior is expected for corner cases.

## Black-box
For black-box used class equivalents and boundary values technic. Taken in 
account midnight and noon behavior, leap year, quantity of days in specific months.

## Improvement
According to the REST concepts, API endpoint should be rewritten in next way:
https://helloacm.com/api/unix-timestamp-converter/cached/epoch/1451613802
https://helloacm.com/api/unix-timestamp-converter/epoch/1451613802
https://helloacm.com/api/unix-timestamp-converter/cached/date/2016-01-01%202:3:22
https://helloacm.com/api/unix-timestamp-converter/2016-01-01%202:3:22
this approach will properly route requests to appropriate handler, and problems, caused by
is_numeric. 

backend ignoring expected content type header, json is not supported

returned value in the plain text wrapped into double quotes, when epoch sent, 
when date used, returned value is not wrapped into double quotes. 
n plain text mode, value should be returned as plain text, without wrapping.

cached parameter should be moved into header, since headers are used to control cache



## External dependencies

For this project to run, you would need to install below dependencies on your machine:

- **[Java 18](https://openjdk.java.net/projects/jdk/18/)** (as the core programming language)
- **[Maven 3.8.5](https://maven.apache.org/download.cgi)** (for dependency management)
- **[allure]** - https://docs.qameta.io/allure/


###Example
If you have all dependencies installed:
run local: 
mvn clean compile test -Dtest=BlackBoxAPITest
allure generate allure-results --clean -o allure-report
allure open allure-report

## 