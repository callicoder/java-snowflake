## Java distributed Unique ID generator inspired by Twitter snowflake

You can read about Twitter snowflake [here](https://blog.twitter.com/engineering/en_us/a/2010/announcing-snowflake.html).

The IDs are 64-bits in size and are generated with the combination of the following:

+ **Epoch timestamp in milliseconds precision** - **41 bits**. The maximum timestamp that can be represented using 41 bits is `2^41 - 1`, or `2199023255551`, which comes out to be `Wednesday, September 7, 2039 3:47:35.551 PM`. That gives us 69 years with respect to a custom epoch..
+ **Node ID** - **10 bits**. This gives us 1024 nodes/machines.
+ **Local counter per machine** - **12 bits**. The counter’s max value would be 4095.

## How to use

The `Snowflake` class should be used as a singleton in your application.

```java
Snowflake s = new Snowflake(275)
s.nextId()
```

Read the blog to understand more:

- [Generating unique IDs in a distributed environment at high scale.](https://www.callicoder.com/distributed-unique-id-sequence-number-generator/)
