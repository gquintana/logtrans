# LogTrans

LogTrans is a library to parse Logs write in pure Java.
It's strongly inspired by Logstash.

## Why?

Why not use Logstash?

* Logstash configuration is code, code must be tested. Sadly, Logstash makes testing hard and slow.
* We want to implement some complex filtering logic with a real programming language like Java.

Why not use Elasticsearch Ingest Node?

* We don't always want to send Logs to Elasticsearch.