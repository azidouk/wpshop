# wpshop

This code provides a simple RESTful software service that will allow a
merchant to create a new simple offer. All offers have shopper friendly 
descriptions and are priced up front in a defined currency. This could 
easily be extended to delete and update existing offers.

To create a new offer, run:
```
curl -d "{\"name\":\"value1\", \"description\":\"value2\", \"price\":{\"price\":100, \"currency\":\"GBP\"}}" -H "Content-Type: application/json" -X POST http://localhost:8080/offer/
```

To get all offers, run:
```
curl -H "Content-Type: application/json" -X GET http://localhost:8080/offer/
```

To get a specific offer, run:
```
curl -H "Content-Type: application/json" -X GET http://localhost:8080/offer/$id
```
