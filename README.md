
What
=====
- Web service that randomly populates random number of SpecimenReplicate records.

Build
======

```
mvn -DskipTests clean compile assembly:single
```

Run
====

```
export CLASSPATH=target/ws-1.0-SNAPSHOT-jar-with-dependencies.jar
java ca.gc.agr.mbb.seqdb.ws.http.Main
```
Starts web server on port `8080`

**Note: needs java 1.7.x**


URLs to try
============

## List all available URLs:
URL: `http://localhost:8080/seqdb-ws/v1/`

```
{

    "meta": {
        "ellapsedMillis": 0,
        "thisUrl": "http://localhost:8080/seqdb-ws/v1/",
        "debugToggleUrl": "http://localhost:8080/seqdb-ws/v1/DEBUG",
        "debug": false,
        "payloadType": "pagingpayload",
        "mode": "mock",
        "timestamp": "Sun Feb 9 06:28:15 EST 2014"
    },
    "payload": {
        "limit": 100,
        "baseUrl": "http://localhost:8080/seqdb-ws/v1/",
        "offset": 0,
        "urls": [
            {
                "urlPath": "container"
            },
            {
                "urlPath": "container/count"
            },
            {
                "urlPath": "location"
            },
            {
                "urlPath": "location/count"
            }
        ],
        "total": 4
    }

}
```

To construct the full URL for the web service, combine the `payload.baseUrl` with one of the `payload.urls`.

##Get count of containers:
URL: `http://localhost:8080/seqdb-ws/v1/container/count`

Should result in something like:
```
{
    "meta": {
        "ellapsedMillis": 0,
        "thisUrl": "http://localhost:8080/",
        "debugToggleUrl": "http://localhost:8080/seqdb-ws/v1/DEBUG",
        "debug": true,
        "payloadType": "countpayload",
        "mode": "mock",
        "timestamp": "Sun Feb 9 06:28:15 EST 2014"
    },
    "payload": {
        "total": 4
    }
}
```


##Get all container GET URIs
URL: `http://localhost:8080/seqdb-ws/v1/container`

This should result in something like:

```
{

    "meta": {
        "ellapsedMillis": 0,
        "thisUrl": "http://localhost:8080/seqdb-ws/v1/container",
        "debugToggleUrl": "http://localhost:8080/seqdb-ws/v1/DEBUG",
        "debug": false,
        "payloadType": "pagingpayload",
        "mode": "mock",
        "timestamp": "Sun Feb 9 06:28:15 EST 2014"
    },
    "payload": {
        "limit": 100,
        "baseUrl": "http://localhost:8080/seqdb-ws/v1/container",
        "offset": 0,
        "nextPageurl": "http://default_next_url.com",
        "urls": [
            {
                "urlPath": "0"
            },
            {
                "urlPath": "1"
            },
            {
                "urlPath": "2"
            },
.
.
. [content removed]
.
.
            {
                "urlPath": "40"
            },
            {
                "urlPath": "41"
            },
        ],
        "total": 42
    }

}
```
To construct the full URL for the web service, combine the `payload.baseUrl` with one of the `payload.urls`.


##Get a single container record by primary key
Now, use one of the above to get the full record:
URL: http://localhost:8080/seqdb-ws/v1/container/1

Should result in something like:
```
{
    "meta": {
        "ellapsedMillis": 0,
        "thisUrl": "http://localhost:8080/",
        "debugToggleUrl": "http://localhost:8080/seqdb-ws/v1/DEBUG",
        "debug": true,
        "payloadType": "container",
        "mode": "mock",
        "timestamp": "Sun Feb 9 06:28:15 EST 2014"
    },
    "payload": {
        "id": 1,
        "containerNumber": "43",
        "containerType": {
            "name": "t2",
            "baseType": "t6",
            "numberOfWells": 10,
            "numberOfColumns": 8,
            "numberOfRows": 9,
            "id": 42
        },
        "locations": "http://localhost:8080/seqdb-ws/v1/location/container/1"
    }
}
```
Note above that payload.locations is the full URL to get the locations associated with a container.
It has the form: location/container/PRIMARY_KEY

Using this URL http://localhost:8080/seqdb-ws/v1/location/container/1 should produce something like:
```
{

    "meta": {
        "ellapsedMillis": 0,
        "thisUrl": "http://localhost:8080/seqdb-ws/v1/location/container/1",
        "debugToggleUrl": "http://localhost:8080/seqdb-ws/v1/DEBUG",
        "debug": false,
        "payloadType": "pagingpayload",
        "mode": "mock",
        "timestamp": "Sun Feb 9 06:28:15 EST 2014"
    },
    "payload": {
        "limit": 100,
        "baseUrl": "http://localhost:8080/seqdb-ws/v1/location",
        "offset": 0,
        "nextPageurl": "http://default_next_url.com",
        "urls": [
            {
                "urlPath": "41"
            },
            {
                "urlPath": "86"
            },
            {
                "urlPath": "126"
            },
.
.
. [content removed]
.
.

            {
                "urlPath": "786"
            },
            {
                "urlPath": "801"
            },
            {
                "urlPath": "836"
            }
        ],
        "total": 29
    }

}
```



Debugging
====
The payload.urls by default only contain an urlPath that needs to be combined with the pyload.baseUrl to get the resource service URL.
There is a debug mode that supports an easier interactive session and prints a `debugFullUrl` that allows easier access.
This can be turned on in the following ways:
- Invoke the `meta.debugToggleUrl`  Above:  http://localhost:8080/seqdb-ws/v1/DEBUG
- Add `?meta__toggleDebug=true` to the base services URL: `http://localhost:8080/seqdb-ws/v1?meta__toggleDebug=true`

Turning on debugging will now cause all service requests that has `urls:` to include `debugFullUrl`s:
```
{

    "meta": {
        "ellapsedMillis": 0,
        "thisUrl": "http://localhost:8080/seqdb-ws/v1/",
        "debugToggleUrl": "http://localhost:8080/seqdb-ws/v1/DEBUG",
        "debug": true,
        "payloadType": "pagingpayload",
        "mode": "mock",
        "timestamp": "Sun Feb 9 06:28:15 EST 2014"
    },
    "payload": {
        "limit": 100,
        "baseUrl": "http://localhost:8080/seqdb-ws/v1/",
        "offset": 0,
        "nextPageurl": "http://default_next_url.com",
        "urls": [
            {
                "urlPath": "container",
                "debugFullUrl": "http://localhost:8080/seqdb-ws/v1/container"
            },
            {
                "urlPath": "container/count",
                "debugFullUrl": "http://localhost:8080/seqdb-ws/v1/container/count"
            },
            {
                "urlPath": "location",
                "debugFullUrl": "http://localhost:8080/seqdb-ws/v1/location"
            },
            {
                "urlPath": "location/count",
                "debugFullUrl": "http://localhost:8080/seqdb-ws/v1/location/count"
            }
        ],
        "total": 4
    }

}
```