
What
=====
- Web service for SeqDB
- This is the server Web Services for the project https://github.com/mgrah043/sequence_database_barcode_scanner

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

**Note: needs java >= v1.7**


URLs to try
============

## List all available services via URIs:
URI: `http://localhost:8080/seqdb-ws/v1/`

```
{

    "meta": {
        "ellapsedMillis": 0,
        "thisUrl": "http://localhost:8080/seqdb-ws/v1/",
        "debugToggleUrl": "http://localhost:8080/seqdb-ws/v1/DEBUG",
        "debug": false,
        "mode": "mock",
        "timestamp": "Sun Feb 9 06:28:15 EST 2014",
	status: 200
    },
    "pagingPayload": {
        "limit": 100,
        "baseUrl": "http://localhost:8080/seqdb-ws/v1/",
        "offset": 0,
	"count": 4,
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

To construct the full URI for the web service, combine the `pagingPayload.baseUrl` with one of the `pagingPayload.urls.urlPath`.

##Get count of containers:
URI: `http://localhost:8080/seqdb-ws/v1/container/count`

Should result in something like:
```
{
    "meta": {
        "ellapsedMillis": 0,
        "thisUrl": "http://localhost:8080/",
        "debugToggleUrl": "http://localhost:8080/seqdb-ws/v1/DEBUG",
        "debug": true,
        "mode": "mock",
        "timestamp": "Sun Feb 9 06:28:15 EST 2014"
	status": 200
    },
    "countPayload": {
        "total": 4
    }
}
```


##Get all container GET URIs
URI: `http://localhost:8080/seqdb-ws/v1/container`

This should result in something like:

```
{

    "meta": {
        "ellapsedMillis": 0,
        "thisUrl": "http://localhost:8080/seqdb-ws/v1/container",
        "debugToggleUrl": "http://localhost:8080/seqdb-ws/v1/DEBUG",
        "debug": false,
        "mode": "mock",
        "timestamp": "Sun Feb 9 06:28:15 EST 2014"
	status: 200
    },
    "pagingPayload": {
        "limit": 100,
        "baseUrl": "http://localhost:8080/seqdb-ws/v1/container",
        "offset": 0,
	nextPageUrl": "http://localhost:8080/seqdb-ws/v1/container?offset=100&limit=100",
	"count": 100,
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


##Get a single container record by primary key
Now, use one of the above to get the full record:
URI: `http://localhost:8080/seqdb-ws/v1/container/1`

Should result in something like:
```
{
    "meta": {
        "ellapsedMillis": 0,
        "thisUrl": "http://localhost:8080/",
        "debugToggleUrl": "http://localhost:8080/seqdb-ws/v1/DEBUG",
        "debug": true,
        "mode": "mock",
        "timestamp": "Sun Feb 9 06:28:15 EST 2014",
	status: 200
    },
    "container": {
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
Note above that `container.locations` is the full URI to get the locations associated with a container.
It has the form: `location/container/PRIMARY_KEY`

Using this URI  `http://localhost:8080/seqdb-ws/v1/location/container/1` should produce something like:
```
{

    "meta": {
        "ellapsedMillis": 0,
        "thisUrl": "http://localhost:8080/seqdb-ws/v1/location/container/1",
        "debugToggleUrl": "http://localhost:8080/seqdb-ws/v1/DEBUG",
        "debug": false,
        "mode": "mock",
        "timestamp": "Sun Feb 9 06:28:15 EST 2014",
	status: 200
    },
    "pagingPayload": {
        "limit": 100,
        "baseUrl": "http://localhost:8080/seqdb-ws/v1/location",
        "offset": 0,
	nextPageUrl": "http://localhost:8080/seqdb-ws/v1/location?offset=10&limit=10",
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
The `pagingPayload.urls` by default only contain an `urlPath` that needs to be combined with the `pagingPayload.baseUrl` to get the resource service URI.
There is a debug mode that supports an easier interactive session and prints a `debugFullUrl` that allows easier access.
This can be turned on through either of the following ways:
- Invoke the `meta.debugToggleUrl`  Above:  `http://localhost:8080/seqdb-ws/v1/DEBUG`
- Add `?meta__toggleDebug=true` to the base services URI: `http://localhost:8080/seqdb-ws/v1?meta__toggleDebug=true`

Turning on debugging will now cause all service requests that produce a `pagingPayload` to include `debugFullUrl`s:
```
{

    "meta": {
        "ellapsedMillis": 0,
        "thisUrl": "http://localhost:8080/seqdb-ws/v1/",
        "debugToggleUrl": "http://localhost:8080/seqdb-ws/v1/DEBUG",
        "debug": true,
        "mode": "mock",
        "timestamp": "Sun Feb 9 06:28:15 EST 2014",
	status: 200
    },
    "pagingPayload": {
        "limit": 100,
        "baseUrl": "http://localhost:8080/seqdb-ws/v1/",
        "offset": 0,
	"count": 4,
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