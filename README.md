# Kit

Kotlin utilities.

## JsonKit

Contextual serializers for:

* `java.math.BigDecimal`
* `java.net.Uri`
* `java.net.Url`
* `java.time.Instant`
* `java.time.LocalDate`
* `java.time.LocalDateTime`
* `java.time.LocalTime`
* `java.time.YearMonth`
* `java.time.Year`
* `java.util.UUID`

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

Extension functions to convert strings to:

* `java.util.UUID`
* `java.time.Instant`
* `java.time.LocalDate`
* `java.time.LocalDateTime`
* `java.time.LocalTime`
* `java.time.YearMonth`

First letter capitalization:
    
```kotlin
val capitalized = capitalize(str)

assertThat(capitalized).isEqualTo("Hello")
```

## TimeKit
