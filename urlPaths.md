# URL Paths

## Ads

Get the list of all ads (in json format)
`GET http://host:port/classads` returns

 ```
 [
  {"0" : {"title" : String,
          "description" : String,
          "price" : Float,
          "categoryID" : Integer,
          (other fields : ...)}},
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
 (other fields : ...)
}
```

Delete an ad (with the id)
`DELETE http://host:port/classads`

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

Get the attributes of a specific category
`GET http://host:port/categories/category?id=0` returns

```
{
 "title" : String,
 "description" : String,
 "price" : Float,
 (other fields : ...)
}
```

## Users

Get the list of all users (in json format)
`GET http://host:port/users`

Post a user (in json format)
`POST http://host:port/users`

Delete a user (with the id)
`DELETE http://host:port/users`
