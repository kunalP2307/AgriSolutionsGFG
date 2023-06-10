﻿# SIH-Project
 ![MongoDB API(https://myoctocat.com/assets/images/base-octocat.svg)](https://webimages.mongodb.com/_com_assets/cms/kuzt9r42or1fxvlq2-Meta_Generic.png)
 
 # How to start on local server
 
**1. Install all dependencies using following commands**
 
 ```
 npm i
 ```
 
**2. To start API use following commands**

```
node index.js
```

or

```
nodemon index.js 
```

# How to use MongoDB API

## Endpoints Available on API

### * GET Requests

**1. To Find All Collections available in database**

```
http://IP:7000/get-collections 
```

**2. To Create New Collection**

```
http://IP:7000/create-collection/COLLECTION_NAME
```

**3.  Drop Collection**
```
http://IP:7000/drop-collection/COLLECTION_NAME
```

**4. To find all data available in collection**

```
http://IP:7000/find/COLLECTION_NAME
```

**5. To Check Connectivity**

```
http://IP:7000
```

### * POST Requests

**1. To Insert one document in collection**


```
http://IP:7000/insert-one/COLLECTION_NAME
```

* Data Format

```
{
  "name":"sonu",
  "rollNo":"TAI&D32",
  "weight":40
}
```

**2. To Insert Multiple Documents in collection**

```
http://IP:7000/insert-many/COLLECTION_NAME
```

* Data Format

```
[
 {
   "name":"sonu",
   "rollNo":"TAI&D32",
   "weight":40
 },
 {
   "name":"ram",
   "rollNo":"TAI&D33",
   "weight":40
 },
 {
   "name":"shyam",
   "rollNo":"TAI&D53",
   "weight":40
 }
]
```

**3. To Find any data with and condition**

```
http://IP:7000/find-with-and/COLLECTION_NAME
```

* Data Format

```
{
"name":"ram",
"weight":42
}
```

in above name is first condition and weight is second condition

**4. To Find any data with or condition**

```
http://IP:7000/find-with-or/COLLECTION_NAME
```

* Data Format

```
[
 {"name":"ram"},
 {"weight":42} 
]
```

Where name json is first condition and weight json is second condition

**5. Update only one doc**

```
http://IP:7000/update/COLLECTION_NAME
```

* Data Format

```
{
"filter":{"name":"ram"},
"update":{"weight":42}
}
```

where filter is any condition you want to apply and update is what you want to update in doc

**6. Update multiple docs**
 
 ```
http://IP:7000/update-many/COLLECTION_NAME
```

* Data Format

```
{
"filter":{"name":"ram"},
"update":{"weight":42}
}
```

where filter is any condition you want to apply and update is what you want to update in doc

**7. Delete one doc**

 ```
http://IP:7000/delete/COLLECTION_NAME
```

* Data Format

```
{
"filter":{"name":"ram"}
}
```

where filter is condition by which you want to delete perticular doc

**8. Delete multiple doc**

 ```
http://IP:7000/delete-many/COLLECTION_NAME
```

* Data Format

```
{
"filter":{"name":"ram"}
}
```

where filter is condition by which you want to delete perticular doc value
