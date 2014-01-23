
What
=====
- Web service for AAFC SeqDb android project
- RESTful web services serving up JSON
- 

Web Services
============

## List all available URLs (including explicit failure URLs)
URL: `http://localhost:4567/v1`

```
[

    {
        "description": "container",
        "path": "/v1/container",
        "url": "http://localhost:4567/v1/container"
    },
    {
        "description": "container count",
        "path": "/v1/container/count/",
        "url": "http://localhost:4567/v1/container/count/"
    },
    {
        "description": "location",
        "path": "/v1/location",
        "url": "http://localhost:4567/v1/location"
    },
.
.
. [content removed]
.
.
    {
        "description": "storage count",
        "url": "http://localhost:4567/v1/storage/count/?FORCE_TEST_HTTP_RESPONSE=400"
    },
    {
        "description": "storage count",
        "url": "http://localhost:4567/v1/storage/count/?FORCE_TEST_HTTP_RESPONSE=404"
    },
    {
        "description": "storage count",
        "url": "http://localhost:4567/v1/storage/count/?FORCE_TEST_HTTP_RESPONSE=405"
    }

]
```

##Get count of specimen replicates
URL: `http://localhost:4567/v1/specimenReplicate/count/`
 (Note trailing slash)


Should result in something like:
```
{
    "type": "specimenReplicates",
    "count": 9992
}
```


##Get all specimen replicate GET URIs
URL: `http://localhost:4567/v1/specimenReplicate/`

This should result in something like:

```
{

    "total": 9992,
    "limit": 100,
    "offset": 0,
    "type": "specimenReplicate",
    "nextPageUrl": "http://localhost:4567/v1/specimenReplicate?offset=100&limit=100",
    "uris": [
        "http://localhost:4567/v1/specimenReplicate/941756",
        "http://localhost:4567/v1/specimenReplicate/922332",
        "http://localhost:4567/v1/specimenReplicate/287714",
        "http://localhost:4567/v1/specimenReplicate/131839",
        "http://localhost:4567/v1/specimenReplicate/710279",
	"http://localhost:4567/v1/specimenReplicate/555856",
.
.
. [content removed]
.
.
        "http://localhost:4567/v1/specimenReplicate/271727",
        "http://localhost:4567/v1/specimenReplicate/645653"
    ]

}

```
##Get a single specimen replicate record by primary key
Now, use one of the above to get the full record:
URL: (http://localhost:4567/v1/specimenReplicate/941756)[http://localhost:4567/v1/specimenReplicate/941756]

Should result in something like:
```
{
    "primaryKey": 941756,
    "name": "mbyfolp",
    "state": "xmbjptcnkuhv",
    "specimenIdentifier": 77596,
    "version": "zsi",
    "contents": "iliqhjyqorcn",
    "notes": "qrkhfmyqyemch tltg z",
    "storageMedium": "minm sg t owioggbbuf",
    "startDate": "2010-01-05 10:02:12",
    "revivalDate": "2014-08-01 01:54:12",
    "dateDestroyed": "2010-07-18 04:46:12",
    "parent": "http://localhost:4567/v1/specimenReplicate/764673",
    "location": {
        "containerNumber": 7,
        "storageUnit": 1,
        "compartment": 2,
        "shelf": 3,
        "rack": 1,
        "dateMoved": "2010-05-12 22:26:12",
        "wellColumn": 1,
        "wellRow": "f"
    }
}
```


