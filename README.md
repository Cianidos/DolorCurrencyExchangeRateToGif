# DolorCurrencyExchangeRateToGif
Random gif dependend on currency exchange rate api

# Awailable endpoint
```localhost:8080/{currency}```

## example
```localhost:8080/rub```


# How to start without docker
```
../DolorCurrencyExchangeRateToGif> ./gradlew bootRun
```

# Docker
## Build
```
DolorCurrencyExchangeRateToGif> docker build -t currency_to_gif .
```
## Run
```
 DolorCurrencyExchangeRateToGif> docker run -it -p 8080:8080 currency_to_gif
```
