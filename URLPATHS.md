# URL Paths

## Ads

Get the list of all ads (in json format)
`GET http://host:port/classads` returns

 ```
 [
  {"0" : {"id" : Integer,
          "deleted" : boolean,
          "nbVues" : int,
          "time" : String,
          "title" : String,
          "description" : String,
          "price" : Float,
          "categoryID" : Integer,
          "userID" : Integer,
          "images" : [String, String, ...]}},
  {"1" : {...}},
]
 ```

Get the list of all ads of a specific user (in json format)
`GET http://host:port/classads/user/{UserId}` returns

 ```
 [
  {"0" : {"id" : Integer,
          "deleted" : boolean,
          "nbVues" : int,
          "time" : String,
          "title" : String,
          "description" : String,
          "price" : Float,
          "categoryID" : Integer,
          "userID" : Integer,
          "images" : [String, String, ...]}},
  {"1" : {...}},
]
 ```

Get an ab by its ID (in json format)
`GET http://host:port/classads/ads/ad/{id}` returns the ad if exists or an error message 400

Post an ad (in json format)
`POST http://host:port/classads` expects

```
{
 "title" : String,
 "description" : String,
 "price" : Float,
 "categoryID" : Integer,
 "userID" : Integer,
 "images" : [String, String, ...]
}
```

Modifies an ad by its ID (in json format)
`PUT http://host:port/classads/` expects

```
{
 "id" : Integer,
 "deleted" : boolean,
 "nbVues" : int,
 "time" : String,  (ex:"2019-05-21T14:40:10.600755")
 "title" : String,
 "description" : String,
 "price" : Float,
 "categoryID" : Integer,
 "userID" : Integer,
 "images" : [String, String, ...]
}
```

Delete an ad (with the id)
`DELETE http://host:port/classads/ads/ad/{id}`

## Search

Search ads from a request
`GET http://host:port/search?request="<request>"` returns a list of ads like getAll.

Delete ads in elasticsearch data (not be used in frontend, just for development purpose)
`DELETE http://host:port/search/{toId}` will delete ads from id=0 to id=toId.

## Categories

Get a tree view representation of the categories
`GET http://host:port/categories/treeview` returns

```
[
 {"0" : {"name" : String,
        "children" : ["0" : {"name" : String,
                             "children" : [...]}]
        }
 },
 {"1" : {...}},
]
```

Get the categories indices
`GET http://host:port/categories/index` returns

```
{
 "nameCategory0" : "0",
 "nameCategory1" : "1"
}
```

## Users

Get the list of all users (in json format)
`GET http://host:port/users`

Post a user (in json format)
`POST http://host:port/users`

Delete a user (with the id)
`DELETE http://host:port/users`

## Responses

Get the list of all the responses from a user (with the id, format json)

`GET http://host:port/classadsresponses/users/{uid}`


Get the list of all messages between two users: user who posted the ad with id "aid" and the user with id "uid" who responded to this ad.
2 query params: offset (first message index (from the most recent)) and limit (number max. of messages you want to receive)

`GET http://host:port/classadsresponses/users/{uid}/ads/{aid}?offset=0&limit=10`
 (example of query params to get the 10 most recent messages)
 

Create a new response (in json format)

`POST http://host:port/classadsresponses/users/{uid}/ads/{aid}`


# api-gateway
## classads-service

`localhost/api/classads`
Everything request that has something to do with the classads (service) is routed through localhost/api/classads. This will redirect the request to localhost:port/classads of the classads service. One possible issue is that the categories are implemented at a different path so it has to be fixed.

## user-service

`localhost/api/user`
Same as above, localhost:port/user

## search-service

`localhost/api/search`
Same as above, localhost:port/search

In the future, all api calls in the form of `localhost/api/service` will be translated as `localhost:port/service`


