# URL Paths

## Ads

Get the list of all ads (in json format)
`GET http://host:port/classads` returns

 ```
 [
  {"0" : {"id" : Integer,
          "title" : String,
          "description" : String,
          "price" : Float,
          "categoryID" : Integer,
          "userID" : Integer}},
  {"1" : {...}},
]
 ```

Post an ad (in json format)
`POST http://host:port/classads` expects

```
{
 "title" : String,
 "description" : String,
 "price" : Float,
 "categoryID" : Integer,
 "userID" : Integer
 "images" : JsonArray
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
