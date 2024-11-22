# Kit

Kotlin utilities.

## JsonKit

Contextual serializers for:

* `java.time.Instant`
* `java.time.LocalDate`
* `java.time.LocalTime`
* `java.time.LocalDate`
* `java.net.Uri`
* `java.net.Url`
* `java.util.UUID`
* `java.time.YearMonth`
* `java.time.Year`

## NetKit

Extract query parameters from URI:

```kotlin
val uri = URI.create("https://example.com?a=1&a=2&b=9&a=3&c=0")

val queryParams = uri.queryParams("a")
assertThat(queryParams).containsExactly("1", "2", "3")

val queryParam = uri.queryParam("c")
assertThat(queryParam).isEqualTo("0")
```

## RandomKit

## StringKit

## TimeKit
