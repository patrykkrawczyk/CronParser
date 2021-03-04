# CronParser

This is a command line app to parse Cron strings.

## Usage
By default it expects arguments in this format:
```
MINUTE HOUR DAY_OF_MONTH MONTH DAY_OF_WEEK command
```

But this can be manipulated with configuration in Main class, some examples are:

| Format        | Example           |
| ------------- |:-------------|
| MINUTE HOUR DAY_OF_MONTH MONTH DAY_OF_WEEK command | `*/15 0 */10 */5 */1 /usr/bin/find` |
| MINUTE HOUR command | `*/15 0 /usr/bin/find` |
| HOUR MINUTE DAY_OF_WEEK command | `* * * /usr/bin/find` |

Used:
* JDK 11
* Maven
* Junit 5
* Lombok

## Commands

Run tests:
`mvn clean test`

Build:
`mvn clean package`

Run:
`java -jar target/CronParser-1.0-SNAPSHOT.jar "*/15 0 1 * 1-5 /usr/bin/find"`
 
## Testing methodology:

| Test        | Description           |
| ------------- |:-------------|
| CronTabParserDefaultTermsTest | Tests parsing assuming default set of 5 terms in standard order |
| CronTabParserMixedTermsTest | Tests if we can create a cron with less than 5 terms or with a different order (Hours then Minutes) |
| CronTabParserValidationTest | Tests edge cases like invalid characters or values out of bounds |
| CronTabPrinterDefaultTermsTest | Tests printing assuming default set of 5 terms in standard order |
| CronTabPrinterMixedTermsTest | Tests printing with less than 5 terms or with a different order (Hours then Minutes) |


## Class structure:

| Test        | Description           |
| ------------- |:-------------|
| Main | Sets the default expected order of terms. Accepts and does simple validation of input argument |
| CronTabParser | Extracts given number of expected terms. For each detected term, validates it with regexp for correct format and passes term string to TermParser |
| TermParser | For each term string extracted by CronTabParser, it detects type of the term string, validtes it, and creates instance of Term class |
| TermParserHandlers | Contains logic for parsing and validation of different kinds of term strings |
| CronTabPrinter | Simply prints CronTab in given format in given order |
| TermType | Defines kinds of Terms that we support along with their MIN and MAX values |
| Term | A single term representing one of the TermTypes with values defined by user |
| CronTab | Aggregate of Terms and command. A 1:1 representation of argument provided at the beginning  |
